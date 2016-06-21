package wtfisandroid.drinkinggamescollection.activities.games;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import wtfisandroid.drinkinggamescollection.R;
import wtfisandroid.drinkinggamescollection.data.IHaveNeverEverStatement;
import wtfisandroid.drinkinggamescollection.logic.DatabaseHandler;
import wtfisandroid.drinkinggamescollection.logic.ShakeDetector;
import wtfisandroid.drinkinggamescollection.logic.Utilities;

public class IHaveNeverEverActivity extends AppCompatActivity implements ShakeDetector.OnShakeListener {

	private static final String TAG = "IHaveNeverEverActivity";

	private DatabaseHandler db;
	private List<IHaveNeverEverStatement> statementsList;
	private SensorManager sensorManager;
	private Sensor accelerometer;
	private ShakeDetector shakeDetector;
	private TextView tvStatement;
	private SharedPreferences sharedPref;
	private Utilities utilities;
	private View backgroundView;
	private Random rand = new Random();
	private int lastIndex = -1;
	private long back_pressed;
	private Toolbar toolbar;
	private boolean backToPyramid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		utilities = new Utilities(this);
		sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String currentLanguage = sharedPref.getString(Utilities.LANGUAGE_PREFERENCE_KEY, Locale.getDefault().getDisplayLanguage());
		utilities.setLanguage(currentLanguage);

		setContentView(R.layout.activity_i_have_never_ever);

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		toolbar.setLogo(R.drawable.ic_logo);

		if ( getSupportActionBar() != null ) {
			getSupportActionBar().setDisplayShowTitleEnabled(false);
		}

		backgroundView = findViewById(R.id.iHaveNeverEverLinerLayout);
		changeBackgroundColor();

		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		if ( sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null ) {
			accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			shakeDetector = new ShakeDetector();
			shakeDetector.setOnShakeListener(this);
		}

		db = new DatabaseHandler(getApplicationContext());
		String sql = "SELECT * FROM " + DatabaseHandler.TABLE_STATEMENTS;
		String whereClause = " WHERE ( " + DatabaseHandler.KEY_CATEGORY + " = 'Other' ";
		if ( sharedPref.getBoolean(Utilities.PREF_WORK, true) ) {
			whereClause += " OR " + DatabaseHandler.KEY_CATEGORY + " = 'Work' ";
		}
		if ( sharedPref.getBoolean(Utilities.PREF_SCHOOL, true) ) {
			whereClause += " OR " + DatabaseHandler.KEY_CATEGORY + " = 'School' ";
		}
		if ( sharedPref.getBoolean(Utilities.PREF_LOVE, true) ) {
			whereClause += " OR " + DatabaseHandler.KEY_CATEGORY + " = 'Love' ";
		}
		if ( sharedPref.getBoolean(Utilities.PREF_DRINKING, true) ) {
			whereClause += " OR " + DatabaseHandler.KEY_CATEGORY + " = 'Drinking' ";
		}
		if ( sharedPref.getBoolean(Utilities.PREF_ADULT, true) ) {
			whereClause += " OR " + DatabaseHandler.KEY_CATEGORY + " = 'Adult' ";
		}
		if ( sharedPref.getBoolean(Utilities.PREF_LAW, true) ) {
			whereClause += " OR " + DatabaseHandler.KEY_CATEGORY + " = 'Law' ";
		}

		sql += whereClause + " ) AND LANGUAGE = '" + currentLanguage + "' ";
		statementsList = db.getAllStatements(sql);
		tvStatement = (TextView) findViewById(R.id.tvStatement);
		nextStatement();
	}

	@Override
	public void onResume() {
		super.onResume();
		if ( sensorManager != null )
			sensorManager.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_i_have_never_ever, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if ( sharedPref.getBoolean(Utilities.SOUND_PREFERENCE_KEY, false) ) {
			utilities.playSound(1);
		}
		switch ( item.getItemId() ) {
			case R.id.new_game:
				recreate();
				break;
			case R.id.help:
				ContentFrameLayout rootLayout = (ContentFrameLayout)findViewById(android.R.id.content);
				View.inflate(this, R.layout.manual, rootLayout);
				backToPyramid = true;
				WebView webView = (WebView) findViewById(R.id.wv_manual);
				utilities.generateIHaveNeverEverManual(webView);
				break;
			case R.id.back:
				finish();
				break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		if ( backToPyramid) {
			ContentFrameLayout rootLayout = (ContentFrameLayout)findViewById(android.R.id.content);
			if ( rootLayout != null )
				rootLayout.removeView(findViewById(R.id.wv_manual));

			backToPyramid = false;
		} else if ( back_pressed + 2000 > System.currentTimeMillis()) {
			finish();
		} else {
			Toast.makeText(getBaseContext(), getString(R.string.close_message), Toast.LENGTH_SHORT).show();
			back_pressed = System.currentTimeMillis();
		}
	}

	@Override
	public void onShake(int count) {
		TranslateAnimation translation;
		translation = new TranslateAnimation(0, 0,-150, 0);
		translation.setStartOffset(-500);
		translation.setDuration(2000);
		translation.setFillAfter(true);
		translation.setInterpolator(new BounceInterpolator());
		tvStatement.startAnimation(translation);
		nextStatement();
	}

	private void nextStatement() {
		if ( statementsList.size() > 0 ) {
			changeBackgroundColor();
			int number;
			do {
				number = rand.nextInt(statementsList.size());
			} while ( lastIndex == number );
			lastIndex = number;
			IHaveNeverEverStatement statement = statementsList.get(number);
			tvStatement.setText(statement.getStatement());
		}
	}

	@Override
	public void onPause() {
		if ( sensorManager != null )
			sensorManager.unregisterListener(shakeDetector);

		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		db.close();
	}

	private void changeBackgroundColor() {
		Random rand = new Random();
		int a = rand.nextInt(255);
		int r = rand.nextInt(255);
		int g = rand.nextInt(255);
		int b = rand.nextInt(255);

		toolbar.setBackgroundColor(Color.argb(a, r, g, b));
		backgroundView.setBackgroundColor(Color.argb(a, r, g, b));
	}

	public void onClickNext(View v) {
		if ( sharedPref.getBoolean(Utilities.SOUND_PREFERENCE_KEY, false) )
			utilities.playSound(1);

		nextStatement();
		Animation transition = AnimationUtils.loadAnimation(this, R.anim.translate);
		tvStatement.startAnimation(transition);
	}

	public void onClickQuit(View v) {
		finish();
	}
}
