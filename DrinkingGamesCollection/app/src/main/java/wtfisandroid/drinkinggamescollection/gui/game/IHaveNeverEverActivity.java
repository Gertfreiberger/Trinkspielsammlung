package wtfisandroid.drinkinggamescollection.gui.game;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Locale;

import wtfisandroid.drinkinggamescollection.R;
import wtfisandroid.drinkinggamescollection.logic.DatabaseHandler;
import wtfisandroid.drinkinggamescollection.logic.Utilities;

public class IHaveNeverEverActivity extends AppCompatActivity {

	private static final String TAG = "IHaveNeverEverActivity";
	private DatabaseHandler db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Utilities utilities = new Utilities(this);
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String currentLanguage = sharedPref.getString(Utilities.LANGUAGE_PREFERENCE_KEY, Locale.getDefault().getDisplayLanguage());
		utilities.setLanguage(currentLanguage);

		setContentView(R.layout.activity_i_have_never_ever);

		db = new DatabaseHandler(getApplicationContext());
		Log.d(TAG, "onCreate() called with: " + "savedInstanceState = [" + db.getAllStatements() + "]");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		db.close();
	}
}
