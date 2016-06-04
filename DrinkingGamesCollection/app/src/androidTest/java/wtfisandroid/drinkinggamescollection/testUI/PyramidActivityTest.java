package wtfisandroid.drinkinggamescollection.testUI;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import wtfisandroid.drinkinggamescollection.gui.game.pyramid.PyramidActivity;

public class PyramidActivityTest extends ActivityInstrumentationTestCase2<PyramidActivity> {

	private Solo mySolo;

	public PyramidActivityTest() {
		super(PyramidActivity.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		mySolo = new Solo(getInstrumentation(), getActivity());
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void  testStartDialog() {

	}
}