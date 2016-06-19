package wtfisandroid.drinkinggamescollection.activities.games.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import wtfisandroid.drinkinggamescollection.R;
import wtfisandroid.drinkinggamescollection.data.IHaveNeverEverStatement;
import wtfisandroid.drinkinggamescollection.logic.DatabaseHandler;
import wtfisandroid.drinkinggamescollection.logic.Utilities;

import static android.widget.AdapterView.OnItemSelectedListener;

public class DeleteStatementActivity extends AppCompatActivity {

	private static final String TAG = "delete_statement";
	private DatabaseHandler db;
	private static final String QUERY_ALL_STATEMENTS = "SELECT * FROM statements";
	private Spinner spStatements;
	private Spinner spLanguage;
	private Spinner spCategory;
	private ArrayList<String> statementsText;
	private ArrayAdapter<String> statementsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utilities utilities = new Utilities(this);
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String currentLanguage = sharedPref.getString(Utilities.LANGUAGE_PREFERENCE_KEY, Locale.getDefault().getDisplayLanguage());
		utilities.setLanguage(currentLanguage);

		setContentView(R.layout.activity_i_have_never_ever);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		if ( toolbar != null )
			toolbar.setLogo(R.drawable.ic_logo);

		if ( getSupportActionBar() != null ) {
			getSupportActionBar().setDisplayShowTitleEnabled(false);
		}
		setContentView(R.layout.activity_delete_statement);

		spStatements = (Spinner) findViewById(R.id.spStatement);
		spLanguage = (Spinner) findViewById(R.id.spLanguage);
		spCategory = (Spinner) findViewById(R.id.spCategory);

		db = new DatabaseHandler(getApplicationContext());

		statementsText = new ArrayList<>();

		List<String> languages = new ArrayList<>();
		languages.add(getResources().getString(R.string.all));
		languages.addAll(db.getAllLanguages());

		ArrayAdapter<String> languageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);
		languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		spLanguage.setAdapter(languageAdapter);

		List<String> cats = db.getAllCategories();
		ArrayAdapter<String> catsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cats);
		catsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		spCategory.setAdapter(catsAdapter);

		OnItemSelectedListener itemSelectedListener = new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				String language = "English"; //TODO select all Languages
				if ( !spLanguage.getSelectedItem().toString().equalsIgnoreCase(getResources().getString(R.string.all)) )
					language = spLanguage.getSelectedItem().toString();

				fillStatements(QUERY_ALL_STATEMENTS + " where Category = '" + spCategory.getSelectedItem() + "' AND LANGUAGE = '" + language + "';");
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		};
		spCategory.setOnItemSelectedListener(itemSelectedListener);
		spLanguage.setOnItemSelectedListener(itemSelectedListener);
	}

	@Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
	}

	private void fillStatements(String sql) {//TODO still not 100% working if their is no statement
		statementsText.clear();
		List<IHaveNeverEverStatement> statements = db.getAllStatements(sql);
		for ( IHaveNeverEverStatement statement : statements )
			statementsText.add(statement.getStatement());

		if ( statementsText.isEmpty() )
			statementsText.add(getResources().getString(R.string.no_statements));

		statementsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statementsText);
		spStatements.setAdapter(statementsAdapter);
	}

	public void onClickDeleteStatement(View v) {
		String statement = String.valueOf(spStatements.getSelectedItem());
		db.deleteStatement(statement);
		View view = findViewById(android.R.id.content);
		if ( view != null ) {
			Snackbar snackbar = Snackbar.make(view, R.string.statement_deleted, Snackbar.LENGTH_LONG);
			snackbar.show();
		}
		statementsAdapter.remove(spStatements.getSelectedItem().toString());
	}
}
