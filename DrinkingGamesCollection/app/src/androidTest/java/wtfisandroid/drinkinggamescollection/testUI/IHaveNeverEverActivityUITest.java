package wtfisandroid.drinkinggamescollection.testUI;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import java.util.Locale;

import wtfisandroid.drinkinggamescollection.R;
import wtfisandroid.drinkinggamescollection.activities.games.IHaveNeverEverActivity;
import wtfisandroid.drinkinggamescollection.logic.DatabaseHandler;
import wtfisandroid.drinkinggamescollection.logic.Utilities;

public class IHaveNeverEverActivityUITest extends ActivityInstrumentationTestCase2<IHaveNeverEverActivity> {

	private static final String TAG = "i_have_never_ever";
	private Solo solo;
	private DatabaseHandler db;
	private Resources resources;
	private String currentLanguage;

	public IHaveNeverEverActivityUITest() {
		super(IHaveNeverEverActivity.class);
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
		resources = solo.getCurrentActivity().getResources();
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(solo.getCurrentActivity().getApplicationContext());
		currentLanguage = sharedPref.getString(Utilities.LANGUAGE_PREFERENCE_KEY, Locale.getDefault().getDisplayLanguage());
		db = new DatabaseHandler(solo.getCurrentActivity());
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testQuitButton() {
		solo.goBack();
		solo.sleep(2300);
		solo.goBack();
		solo.goBack();
	}

	public void testBack() {
		solo.clickOnText(resources.getString((R.string.quit)));
	}

	public void testNextButton() {
		int statementsCount = db.getStatementsCount(currentLanguage);
		for ( int i = 0; i < statementsCount * 2; i++ ) {
			solo.clickOnView(solo.getView(R.id.next));
			solo.sleep(2300);
		}
		solo.clickOnText(resources.getString((R.string.next)));
	}

	public void testNextButtonWithQuit() {
		int statementsCount = db.getStatementsCount(currentLanguage);
		for ( int i = 0; i < statementsCount * 2; i++ ) {
			solo.clickOnView(solo.getView(R.id.next));
			if (statementsCount == i) {
				solo.clickOnText(resources.getString((R.string.quit)));
				break;
			}
			solo.sleep(2300);
		}
		solo.clickOnText(resources.getString((R.string.next)));
	}

	public void testNextButtonWithBackPressed() {
		int statementsCount = db.getStatementsCount(currentLanguage);
		for ( int i = 0; i < statementsCount * 2; i++ ) {
			solo.clickOnView(solo.getView(R.id.next));
			if (statementsCount == i) {
				solo.goBack();
				solo.goBack();
				break;
			}
			solo.sleep(2300);
		}
		solo.clickOnText(resources.getString((R.string.next)));
	}

	public void testMenuItems() {
		solo.clickOnMenuItem(resources.getString((R.string.new_game)));
		solo.clickOnMenuItem(resources.getString((R.string.back)));
	}

}