package wtfisandroid.drinkinggamescollection.gui.game;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.robotium.solo.Solo;

import wtfisandroid.drinkinggamescollection.data.IHaveNeverEverStatement;
import wtfisandroid.drinkinggamescollection.logic.DatabaseHandler;


/**
 * Created by Manfred on 20.05.2016.
 */
public class IHaveNeverActivityTest extends ActivityInstrumentationTestCase2 {

	private static final String TAG = "i_have_never_ever";
	private Solo m_solo;
	private DatabaseHandler db;

	public IHaveNeverActivityTest() {
		super(IHaveNeverEverActivity.class);
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		m_solo = new Solo(getInstrumentation(), getActivity());
		db = new DatabaseHandler(m_solo.getCurrentActivity());
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAddingStatement() {
		db.addStatement(new IHaveNeverEverStatement("Test", "school"));
		db.addStatement(new IHaveNeverEverStatement(4, "I never ever", "work"));
		Log.d(TAG, "testAddingStatement() called with: " + db.getAllStatements() + "");
	}
}