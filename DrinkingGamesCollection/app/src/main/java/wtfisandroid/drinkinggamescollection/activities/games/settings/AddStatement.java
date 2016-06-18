package wtfisandroid.drinkinggamescollection.activities.games.settings;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import wtfisandroid.drinkinggamescollection.R;
import wtfisandroid.drinkinggamescollection.data.IHaveNeverEverStatement;
import wtfisandroid.drinkinggamescollection.logic.DatabaseHandler;

public class AddStatement extends AppCompatActivity {

	private Spinner spCategory;
	private Spinner spLanguage;
	private DatabaseHandler db;
	private EditText etStatement;
	private Resources resource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_statement);

		spCategory = (Spinner) findViewById(R.id.spCategory);
		spLanguage = (Spinner) findViewById(R.id.spLanguage);
		etStatement = (EditText) findViewById(R.id.etStatement);

		resource = getResources();

		db = new DatabaseHandler(getApplicationContext());
		List<String> cats = db.getAllCategories();
		List<String> languages = db.getAllLanguages();

		ArrayAdapter<String> catsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cats);
		catsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spCategory.setAdapter(catsAdapter);

		ArrayAdapter<String> languageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);
		catsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spLanguage.setAdapter(languageAdapter);
	}

	@Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
	}

	public void onClickAddStatement(View v) {
		String statement = etStatement.getText().toString();
		if (!statement.isEmpty()) {
			String category = String.valueOf(spCategory.getSelectedItem());
			String language = String.valueOf(spLanguage.getSelectedItem());
			db.addStatement(new IHaveNeverEverStatement(statement, category, language));
		} else {
//			Toast.makeText(getApplicationContext(), resource.getString(R.string.empty_statement), Toast.LENGTH_SHORT).show();
//			etStatement.requestFocus();
			etStatement.setError(resource.getString(R.string.empty_statement));
		}

	}
}
