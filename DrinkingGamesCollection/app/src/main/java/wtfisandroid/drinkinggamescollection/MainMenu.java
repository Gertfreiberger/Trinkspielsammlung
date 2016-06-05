package wtfisandroid.drinkinggamescollection;

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

import wtfisandroid.drinkinggamescollection.gui.ManualActivity;
import wtfisandroid.drinkinggamescollection.gui.SettingsActivity;
import wtfisandroid.drinkinggamescollection.gui.game.IHaveNeverEverActivity;
import wtfisandroid.drinkinggamescollection.gui.game.pyramid.PyramidActivity;
import wtfisandroid.drinkinggamescollection.logic.ShakeDetector;
import wtfisandroid.drinkinggamescollection.logic.Utilities;

public class MainMenu extends AppCompatActivity implements ShakeDetector.OnShakeListener {


	private static final String TAG = "main";
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

		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		if ( sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null ) {
			accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			shakeDetector = new ShakeDetector();
			shakeDetector.setOnShakeListener(this);
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
		if ( count % 2 == 0 ) {
			Random rand = new Random();
			int number = rand.nextInt(2) + 1; // for first release set to 2
			Intent activity = null;

			switch ( number ) {
				case 1:
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
				activity = new Intent(this, PyramidActivity.class);
				break;
			case R.id.button_i_have_never_ever:
				activity = new Intent(this, IHaveNeverEverActivity.class);
				break;

			case R.id.button_maexchen:
				activity = new Intent(this, Maexchen.class);
				break;

			case R.id.button_polnisches_trinkspiel:
				activity = new Intent(this, PolnischesTrinkspiel.class);
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

}
