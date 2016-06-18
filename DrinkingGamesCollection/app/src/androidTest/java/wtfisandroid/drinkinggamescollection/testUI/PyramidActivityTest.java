package wtfisandroid.drinkinggamescollection.testUI;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import java.util.Random;

import wtfisandroid.drinkinggamescollection.R;
import wtfisandroid.drinkinggamescollection.activities.games.pyramid.PyramidActivity;
import wtfisandroid.drinkinggamescollection.logic.Utilities;

public class PyramidActivityTest extends ActivityInstrumentationTestCase2<PyramidActivity> {

	private Solo solo;
	private Resources resources;
	private SharedPreferences sharedPref;
	private int playerCount;

	public PyramidActivityTest() {
		super(PyramidActivity.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
		resources = solo.getCurrentActivity().getResources();
		sharedPref = PreferenceManager.getDefaultSharedPreferences(solo.getCurrentActivity().getApplicationContext());
		playerCount = Integer.valueOf(sharedPref.getString(Utilities.PYRAMID_PLAYER_COUNT_PREFERENCE_KEY, "4"));
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testDialogBackPressed() {
		solo.clickOnText(resources.getString((R.string.back)));
	}

	public void testDialogBackButton() {
		solo.goBack();
	}

	public void test1RoundAlwaysFirst() {
		solo.clickOnText(resources.getString((R.string.start)));
		for ( int i = 0; i < playerCount; i++ ) {
			solo.clickOnView(solo.getView(R.id.ivFirstChoice));
			solo.sleep(3000);
		}
	}

	public void test1RoundAlwaysSecond() {
		solo.clickOnText(resources.getString((R.string.start)));
		for ( int i = 0; i < playerCount; i++ ) {
			solo.clickOnView(solo.getView(R.id.ivSecondChoice));
			solo.sleep(3000);
		}
	}

	public void test1RoundRandChoice() {
		solo.clickOnText(resources.getString((R.string.start)));
		Random rand = new Random();
		int choice;

		for ( int i = 0; i < playerCount; i++ ) {
			choice = rand.nextInt(2) + 1;
			if ( choice == 1 ) {
				solo.clickOnView(solo.getView(R.id.ivFirstChoice));
			} else {
				solo.clickOnView(solo.getView(R.id.ivSecondChoice));
			}
			solo.sleep(3000);
		}
	}

	public void test2RoundsAlwaysFirst() {
		solo.clickOnText(resources.getString((R.string.start)));
		for ( int i = 0; i < (playerCount * 2); i++ ) {
			solo.clickOnView(solo.getView(R.id.ivFirstChoice));
			solo.sleep(3000);
		}
	}

	public void test2RoundsAlwaysSecond() {
		solo.clickOnText(resources.getString((R.string.start)));
		for ( int i = 0; i < (playerCount * 2); i++ ) {
			solo.clickOnView(solo.getView(R.id.ivSecondChoice));
			solo.sleep(3000);
		}
	}

	public void test2RoundsRandChoice() {
		solo.clickOnText(resources.getString((R.string.start)));
		Random rand = new Random();
		int choice;

		for ( int i = 0; i < (playerCount * 2); i++ ) {
			choice = rand.nextInt(2) + 1;
			if ( choice == 1 ) {
				solo.clickOnView(solo.getView(R.id.ivFirstChoice));
			} else {
				solo.clickOnView(solo.getView(R.id.ivSecondChoice));
			}
			solo.sleep(3000);
		}
	}

	public void test3RoundsAlwaysFirst() {
		solo.clickOnText(resources.getString((R.string.start)));
		for ( int i = 0; i < (playerCount * 3); i++ ) {
			solo.clickOnView(solo.getView(R.id.ivFirstChoice));
			solo.sleep(3000);
		}
	}

	public void test3RoundsAlwaysSecond() {
		solo.clickOnText(resources.getString((R.string.start)));
		for ( int i = 0; i < (playerCount * 3); i++ ) {
			solo.clickOnView(solo.getView(R.id.ivSecondChoice));
			solo.sleep(3000);
		}
	}

	public void test3RoundsRandChoice() {
		solo.clickOnText(resources.getString((R.string.start)));
		Random rand = new Random();
		int choice;

		for ( int i = 0; i < (playerCount * 3); i++ ) {
			choice = rand.nextInt(2) + 1;
			if ( choice == 1 ) {
				solo.clickOnView(solo.getView(R.id.ivFirstChoice));
			} else {
				solo.clickOnView(solo.getView(R.id.ivSecondChoice));
			}
			solo.sleep(3000);
		}
	}

	public void test4RoundsAlwaysFirst() {
		solo.clickOnText(resources.getString((R.string.start)));
		for ( int i = 0; i < (playerCount * 4); i++ ) {
			solo.clickOnView(solo.getView(R.id.ivFirstChoice));
			solo.sleep(3000);
		}
	}

	public void test4RoundsAlwaysSecond() {
		solo.clickOnText(resources.getString((R.string.start)));
		for ( int i = 0; i < (playerCount * 4); i++ ) {
			solo.clickOnView(solo.getView(R.id.ivSecondChoice));
			solo.sleep(3000);
		}
	}

	public void test4RoundsRandChoice() {
		solo.clickOnText(resources.getString((R.string.start)));
		Random rand = new Random();
		int choice;

		for ( int i = 0; i < (playerCount * 4); i++ ) {
			choice = rand.nextInt(2) + 1;
			if ( choice == 1 ) {
				solo.clickOnView(solo.getView(R.id.ivFirstChoice));
			} else {
				solo.clickOnView(solo.getView(R.id.ivSecondChoice));
			}
			solo.sleep(3000);
		}
	}

	public void testGoToNextLevel() {
		solo.clickOnText(resources.getString((R.string.start)));
		Random rand = new Random();
		int choice;

		for ( int i = 0; i < (playerCount * 4); i++ ) {
			choice = rand.nextInt(2) + 1;
			if ( choice == 1 ) {
				solo.clickOnView(solo.getView(R.id.ivFirstChoice));
			} else {
				solo.clickOnView(solo.getView(R.id.ivSecondChoice));
			}
			solo.sleep(3000);
		}
		solo.sleep(3000);
		solo.clickOnText(resources.getString((R.string.go_to_next_level)));
	}

}