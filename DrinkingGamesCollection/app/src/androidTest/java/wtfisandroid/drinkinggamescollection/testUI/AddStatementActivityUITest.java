package wtfisandroid.drinkinggamescollection.testUI;

import android.content.res.Resources;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Spinner;

import com.robotium.solo.Solo;

import java.util.Random;

import wtfisandroid.drinkinggamescollection.R;
import wtfisandroid.drinkinggamescollection.activities.games.settings.AddStatementActivity;


public class AddStatementActivityUITest extends ActivityInstrumentationTestCase2<AddStatementActivity> {

	private static final String TAG = "add_statement";
	private Solo solo;
	private Resources resources;

	public AddStatementActivityUITest() {
		super(AddStatementActivity.class);
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

	public void testAddingStatement() {
		Spinner category = (Spinner) solo.getCurrentActivity().findViewById(R.id.spCategory);
		Spinner language = (Spinner) solo.getCurrentActivity().findViewById(R.id.spLanguage);
		Random rand = new Random();
		int choice;
		int languageSize = language.getAdapter().getCount();

		for ( int i = 0; i < languageSize; i++ ) {
			choice = rand.nextInt(languageSize) - 1;
			solo.pressSpinnerItem(1, choice);
			solo.sleep(1000);
		}

		int categorySize = category.getAdapter().getCount();

		for ( int i = 0; i < categorySize; i++ ) {
			choice = rand.nextInt(categorySize) - 1;
			solo.pressSpinnerItem(0, choice);
			solo.sleep(1000);
		}

		solo.clickOnText(resources.getString((R.string.add_statement)));
		solo.sleep(1000);
		solo.enterText(0, "Try the soln. that i have mentioned. Im not too sure on why you would need the offset. " +
						"If you are using this value to retrieve the date then setTime (your file name)" +
						" would anyway set with the same timestamp value");
		solo.clickOnText(resources.getString((R.string.add_statement)));
		solo.sleep(1000);
		solo.clearEditText(0);
		solo.enterText(0, "m trying to import the database file ");
		solo.clickOnText(resources.getString((R.string.add_statement)));
	}

}
