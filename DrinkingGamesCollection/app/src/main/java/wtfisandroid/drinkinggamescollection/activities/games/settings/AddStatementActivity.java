package wtfisandroid.drinkinggamescollection.activities.games.settings;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.AudioManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;
import java.util.Locale;

import wtfisandroid.drinkinggamescollection.R;
import wtfisandroid.drinkinggamescollection.data.IHaveNeverEverStatement;
import wtfisandroid.drinkinggamescollection.logic.DatabaseHandler;
import wtfisandroid.drinkinggamescollection.logic.Utilities;

public class AddStatementActivity extends AppCompatActivity {

	private Spinner spCategory;
	private Spinner spLanguage;
	private DatabaseHandler db;
	private EditText etStatement;
	private Resources resource;
	private Utilities utilities;
	private SharedPreferences sharedPref;
	private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		resource = getResources();
		utilities = new Utilities(this);
		sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String currentLanguage = sharedPref.getString(Utilities.LANGUAGE_PREFERENCE_KEY, Locale.getDefault().getDisplayLanguage());
		utilities.setLanguage(currentLanguage);

		setContentView(R.layout.activity_add_statement);

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		toolbar.setLogo(R.drawable.ic_logo);


		spCategory = (Spinner) findViewById(R.id.spCategory);
		spLanguage = (Spinner) findViewById(R.id.spLanguage);
		etStatement = (EditText) findViewById(R.id.etStatement);


		if ( getSupportActionBar() != null ) {
			getSupportActionBar().setDisplayShowTitleEnabled(false);
		}

		db = new DatabaseHandler(getApplicationContext());
		List<String> cats = db.getAllCategories();
		List<String> languages = db.getAllLanguages();

		ArrayAdapter<String> catsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cats);
		catsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		spCategory.setAdapter(catsAdapter);

		ArrayAdapter<String> languageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);
		catsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		spLanguage.setAdapter(languageAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_add_statement, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch ( item.getItemId() ) {
			case R.id.back:
				if ( sharedPref.getBoolean(Utilities.SOUND_PREFERENCE_KEY, false) )
					utilities.playSound(1, AudioManager.FX_KEYPRESS_RETURN);
				finish();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
	}

	public void onClickAddStatement(View v) {
		String statement = etStatement.getText().toString();
		if ( !statement.isEmpty() ) {
			String category = String.valueOf(spCategory.getSelectedItem());
			String language = String.valueOf(spLanguage.getSelectedItem());
			db.addStatement(new IHaveNeverEverStatement(statement, category, language));
			etStatement.getText().clear();
			View view = findViewById(android.R.id.content);
			Snackbar snackbar = Snackbar.make(view, R.string.statement_added, Snackbar.LENGTH_SHORT);
			snackbar.show();
		} else
			etStatement.setError(resource.getString(R.string.empty_statement));

	}
}
