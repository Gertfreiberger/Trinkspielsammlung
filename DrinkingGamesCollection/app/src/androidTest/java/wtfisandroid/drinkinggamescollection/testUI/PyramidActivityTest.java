package wtfisandroid.drinkinggamescollection.testUI;

import android.content.res.Resources;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import java.util.Random;

import wtfisandroid.drinkinggamescollection.R;
import wtfisandroid.drinkinggamescollection.gui.game.pyramid.PyramidActivity;

public class PyramidActivityTest extends ActivityInstrumentationTestCase2<PyramidActivity> {

	private Solo solo;
	private Resources resources;

	public PyramidActivityTest() {
		super(PyramidActivity.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
		resources = solo.getCurrentActivity().getResources();
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
		for ( int i = 0; i < 4; i++ ) {
			solo.clickOnView(solo.getView(R.id.ivFirstChoice));
			solo.sleep(3000);
		}
	}

	public void test1RoundAlwaysSecond() {
		solo.clickOnText(resources.getString((R.string.start)));
		for ( int i = 0; i < 4; i++ ) {
			solo.clickOnView(solo.getView(R.id.ivSecondChoice));
			solo.sleep(3000);
		}
	}

	public void test1RoundRandChoice() {
		solo.clickOnText(resources.getString((R.string.start)));
		Random rand = new Random();
		int choice;

		for ( int i = 0; i < 4; i++ ) {
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
		for ( int i = 0; i < 8; i++ ) {
			solo.clickOnView(solo.getView(R.id.ivFirstChoice));
			solo.sleep(3000);
		}
	}

	public void test2RoundsAlwaysSecond() {
		solo.clickOnText(resources.getString((R.string.start)));
		for ( int i = 0; i < 8; i++ ) {
			solo.clickOnView(solo.getView(R.id.ivSecondChoice));
			solo.sleep(3000);
		}
	}

	public void test2RoundsRandChoice() {
		solo.clickOnText(resources.getString((R.string.start)));
		Random rand = new Random();
		int choice;

		for ( int i = 0; i < 8; i++ ) {
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
		for ( int i = 0; i < 8; i++ ) {
			solo.clickOnView(solo.getView(R.id.ivFirstChoice));
			solo.sleep(3000);
		}
	}

	public void test3RoundsAlwaysSecond() {
		solo.clickOnText(resources.getString((R.string.start)));
		for ( int i = 0; i < 8; i++ ) {
			solo.clickOnView(solo.getView(R.id.ivSecondChoice));
			solo.sleep(3000);
		}
	}

	public void test3RoundsRandChoice() {
		solo.clickOnText(resources.getString((R.string.start)));
		Random rand = new Random();
		int choice;

		for ( int i = 0; i < 8; i++ ) {
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
		for ( int i = 0; i < 12; i++ ) {
			solo.clickOnView(solo.getView(R.id.ivFirstChoice));
			solo.sleep(3000);
		}
	}

	public void test4RoundsAlwaysSecond() {
		solo.clickOnText(resources.getString((R.string.start)));
		for ( int i = 0; i < 12; i++ ) {
			solo.clickOnView(solo.getView(R.id.ivSecondChoice));
			solo.sleep(3000);
		}
	}

	public void test4RoundsRandChoice() {
		solo.clickOnText(resources.getString((R.string.start)));
		Random rand = new Random();
		int choice;

		for ( int i = 0; i < 12; i++ ) {
			choice = rand.nextInt(2) + 1;
			if ( choice == 1 ) {
				solo.clickOnView(solo.getView(R.id.ivFirstChoice));
			} else {
				solo.clickOnView(solo.getView(R.id.ivSecondChoice));
			}
			solo.sleep(3000);
		}
	}



}