package wtfisandroid.drinkinggamescollection.testUI;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import wtfisandroid.drinkinggamescollection.activities.ManualActivity;

public class ManualActivityUITest extends ActivityInstrumentationTestCase2<ManualActivity> {

	private Solo solo;

	public ManualActivityUITest() {
		super(ManualActivity.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testManual() {
		solo.drag(300,0,100,100,1);
		solo.sleep(5000);
		solo.drag(0,300, 100, 100,1);
		solo.drag(300,0,100,100,1);
		solo.drag(300,0,100,100,1);
		solo.drag(300,0,100,100,1);
		solo.drag(0,300, 100, 100,1);
		solo.drag(300,0,100,100,1);
		solo.drag(0,300, 100, 100,1);
		solo.drag(300,0,100,100,1);
	}


}