package wtfisandroid.drinkinggamescollection.testUI;

import android.content.res.Resources;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Spinner;

import com.robotium.solo.Solo;

import java.util.Random;

import wtfisandroid.drinkinggamescollection.R;
import wtfisandroid.drinkinggamescollection.activities.games.settings.DeleteStatementActivity;

public class DeleteStatementActivityUITest extends ActivityInstrumentationTestCase2<DeleteStatementActivity> {

	private Solo solo;
	private Resources resources;

	public DeleteStatementActivityUITest() {
		super(DeleteStatementActivity.class);
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
		resources = solo.getCurrentActivity().getResources();
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testMenuItems() {
		solo.clickOnActionBarItem(R.id.back);
	}

	public void testBackPress() {
		solo.goBack();
	}

	public void testCategorySpinner() {
		Spinner category = (Spinner) solo.getCurrentActivity().findViewById(R.id.spCategory);
		Random rand = new Random();
		int choice;

		int categorySize = category.getAdapter().getCount();

		for ( int i = 0; i < categorySize; i++ ) {
			choice = rand.nextInt(categorySize) - 1;
			solo.pressSpinnerItem(0, choice);
			solo.sleep(1000);
		}
	}

	public void testLanguageSpinner() {
		Spinner language = (Spinner) solo.getCurrentActivity().findViewById(R.id.spLanguage);
		Random rand = new Random();
		int choice;

		int languageSize = language.getAdapter().getCount();

		for ( int i = 0; i < languageSize; i++ ) {
			choice = rand.nextInt(languageSize) - 1;
			solo.pressSpinnerItem(1, choice);
			solo.sleep(1000);
		}
	}

	public void testStatementSpinner() {
		Spinner statement = (Spinner) solo.getCurrentActivity().findViewById(R.id.spStatement);
		Random rand = new Random();
		int choice;
		int statementSize = statement.getAdapter().getCount();

		for ( int i = 0; i < 10; i++ ) {
			choice = rand.nextInt(statementSize) - 1;
			solo.pressSpinnerItem(2, choice);
			solo.sleep(1000);
		}
	}


	public void testDeletingStatement() {
		Spinner category = (Spinner) solo.getCurrentActivity().findViewById(R.id.spCategory);
		Spinner language = (Spinner) solo.getCurrentActivity().findViewById(R.id.spLanguage);
		Spinner statement = (Spinner) solo.getCurrentActivity().findViewById(R.id.spStatement);
		Random rand = new Random();
		int choice;

		int categorySize = category.getAdapter().getCount();

		for ( int i = 0; i < categorySize; i++ ) {
			choice = rand.nextInt(categorySize) - 1;
			solo.pressSpinnerItem(0, choice);
			solo.sleep(1000);
		}

		int languageSize = language.getAdapter().getCount();

		for ( int i = 0; i < languageSize; i++ ) {
			choice = rand.nextInt(languageSize) - 1;
			solo.pressSpinnerItem(1, choice);
			solo.sleep(1000);
		}

		int statementSize = statement.getAdapter().getCount();

		for ( int i = 0; i < 10; i++ ) {
			choice = rand.nextInt(statementSize) - 1;
			solo.pressSpinnerItem(2, choice);
			if ( i % 5 == 0 ) {
				solo.clickOnText(resources.getString((R.string.delete_statements)));
				solo.sleep(1000);
			}
			solo.sleep(1000);
		}

		solo.clickOnText(resources.getString((R.string.delete_statements)));
		solo.sleep(1000);
	}

}