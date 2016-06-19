package wtfisandroid.drinkinggamescollection.activities;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

import wtfisandroid.drinkinggamescollection.R;
import wtfisandroid.drinkinggamescollection.activities.games.IHaveNeverEverActivity;
import wtfisandroid.drinkinggamescollection.activities.games.maexchen.Maexchen;
import wtfisandroid.drinkinggamescollection.activities.games.polnisches_game.PolnischesTrinkspiel;
import wtfisandroid.drinkinggamescollection.activities.games.pyramid.PyramidActivity;
import wtfisandroid.drinkinggamescollection.activities.games.settings.SettingsActivity;
import wtfisandroid.drinkinggamescollection.logic.DatabaseHandler;
import wtfisandroid.drinkinggamescollection.logic.ShakeDetector;
import wtfisandroid.drinkinggamescollection.logic.Utilities;

public class MainMenu extends AppCompatActivity implements ShakeDetector.OnShakeListener {

	private SensorManager sensorManager;
	private Sensor accelerometer;
	private ShakeDetector shakeDetector;
	private long back_pressed;
	private Utilities utilities;
	private SharedPreferences sharedPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		utilities = new Utilities(this);
		sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String currentLanguage = sharedPref.getString(Utilities.LANGUAGE_PREFERENCE_KEY, Locale.getDefault().getDisplayLanguage());
		utilities.setLanguage(currentLanguage);

		setContentView(R.layout.activity_main_menu);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		if ( toolbar != null )
			toolbar.setLogo(R.drawable.ic_logo);

		if ( getSupportActionBar() != null ) {
			getSupportActionBar().setDisplayShowTitleEnabled(false);
		}

		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		if ( sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null ) {
			accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			shakeDetector = new ShakeDetector();
			shakeDetector.setOnShakeListener(this);
		}

		if ( sharedPref.getBoolean(Utilities.FIRST_RUN_PREFERENCE_KEY, true) ) {
			initSharedPref();

			DatabaseHandler db = new DatabaseHandler(getApplicationContext());
			db.init();
			db.close();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		if ( sharedPref.getBoolean(Utilities.SOUND_PREFERENCE_KEY, false) )
			utilities.playSound(1);

		switch ( item.getItemId() ) {
			case R.id.action_settings:
				Intent intent = new Intent(this.getApplicationContext(), SettingsActivity.class);
				startActivity(intent);
				break;
			case R.id.back:
				finish();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onResume() {
		super.onResume();
		if ( sensorManager != null )
			sensorManager.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

	}

	@Override
	public void onPause() {
		if ( sensorManager != null )
			sensorManager.unregisterListener(shakeDetector);

		super.onPause();
	}

	@Override
	public void onShake(int count) {
			Random rand = new Random();
			int number = rand.nextInt(4) + 1;
			Intent activity = null;

			switch ( number ) {
				case 1:
					if ( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP ) {
						ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainMenu.this);
						Intent intent = new Intent(MainMenu.this, PyramidActivity.class);
						startActivity(intent, options.toBundle());
					} else
						activity = new Intent(this, PyramidActivity.class);

					break;
				case 2:
					activity = new Intent(this, Maexchen.class);
					break;
				case 3:
					activity = new Intent(this, PolnischesTrinkspiel.class);
					break;
				case 4:
					activity = new Intent(this, IHaveNeverEverActivity.class);
					break;
				default:
					break;
			}

			if ( activity != null )
				startActivity(activity);
	}


	@Override
	public void onBackPressed() {
		if ( back_pressed + 2000 > System.currentTimeMillis() ) {
			finish();
		} else {
			Toast.makeText(getBaseContext(), getString(R.string.close_message), Toast.LENGTH_SHORT).show();
			back_pressed = System.currentTimeMillis();
		}
	}

	public void onClick(View v) {
		if ( sharedPref.getBoolean(Utilities.SOUND_PREFERENCE_KEY, false) )
			utilities.playSound(1);

		Intent activity = null;
		switch ( v.getId() ) {

			case R.id.button_pyramid:
				if ( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP ) {
					ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainMenu.this);
					Intent intent = new Intent(MainMenu.this, PyramidActivity.class);
					startActivity(intent, options.toBundle());
				} else
					activity = new Intent(this, PyramidActivity.class);

				break;
			case R.id.button_i_have_never_ever:
				activity = new Intent(this, IHaveNeverEverActivity.class);
				break;

			case R.id.button_polnisches_trinkspiel:
				activity = new Intent(this, PolnischesTrinkspiel.class);
				break;

			case R.id.button_maexchen:
				activity = new Intent(this, Maexchen.class);
				break;

			case R.id.button_manual:
				activity = new Intent(this, ManualActivity.class);
				break;

			case R.id.button_settings:
				activity = new Intent(this, SettingsActivity.class);
				break;

			case R.id.button_close:
				finish();
				return;

			default:
				break;
		}

		if ( activity != null )
			startActivity(activity);
	}

	private void initSharedPref() {
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putBoolean(Utilities.FIRST_RUN_PREFERENCE_KEY, false);
		editor.putBoolean(Utilities.VIBRATE_PREFERENCE_KEY, true);
		editor.putBoolean(Utilities.SOUND_PREFERENCE_KEY, true);
		editor.putString(Utilities.PYRAMID_PLAYER_COUNT_PREFERENCE_KEY, "2");
		editor.putString(Utilities.LANGUAGE_PREFERENCE_KEY, Locale.getDefault().getDisplayLanguage());

		editor.putBoolean(Utilities.PREF_WORK, true);
		editor.putBoolean(Utilities.PREF_SCHOOL, true);
		editor.putBoolean(Utilities.PREF_LOVE, true);
		editor.putBoolean(Utilities.PREF_DRINKING, true);
		editor.putBoolean(Utilities.PREF_ADULT, true);
		editor.putBoolean(Utilities.PREF_LAW, true);

		editor.apply();
	}
}
