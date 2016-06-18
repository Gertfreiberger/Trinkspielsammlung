package wtfisandroid.drinkinggamescollection.activities.games.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import wtfisandroid.drinkinggamescollection.R;
import wtfisandroid.drinkinggamescollection.data.IHaveNeverEverStatement;
import wtfisandroid.drinkinggamescollection.logic.DatabaseHandler;

public class DeleteStatementActivity extends AppCompatActivity {

	private DatabaseHandler db;
	private static final String QUERY_ALL_STATEMENTS = "SELECT * FROM statements;";
	private Spinner spStatements;
	private Spinner spLanguage;
	private Spinner spCategory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_statement);

		spStatements = (Spinner) findViewById(R.id.spStatement);
		spLanguage = (Spinner) findViewById(R.id.spLanguage);
		spCategory = (Spinner) findViewById(R.id.spCategory);

		db = new DatabaseHandler(getApplicationContext());

		List<IHaveNeverEverStatement> statements = db.getAllStatements(QUERY_ALL_STATEMENTS);
		List<String> statementsText = new ArrayList<>();

		List<String> languages = new ArrayList<>();
		languages.add(getResources().getString(R.string.all));
		languages.addAll(db.getAllLanguages());

		for ( IHaveNeverEverStatement statement : statements )
			statementsText.add(statement.getStatement());

		ArrayAdapter<String> statementsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statementsText);
		statementsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		if ( spLanguage != null )
			spLanguage.setAdapter(statementsAdapter);

		ArrayAdapter<String> languageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);
		statementsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		if ( spStatements != null )
			spStatements.setAdapter(languageAdapter);


		ArrayAdapter<String> catsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statementsText);
		catsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spCategory.setAdapter(catsAdapter);


	}

	@Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
	}

	public void onClickDeleteStatement(View v) {
//		if (!statement.isEmpty()) {
//			String category = String.valueOf(spLanguage.getSelectedItem());
//			String language = String.valueOf(spLanguage.getSelectedItem());
//			db.addStatement(new IHaveNeverEverStatement(statement, category, language));
	}
}
