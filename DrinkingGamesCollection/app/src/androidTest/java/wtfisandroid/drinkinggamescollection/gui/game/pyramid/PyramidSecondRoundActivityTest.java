package wtfisandroid.drinkinggamescollection.gui.game.pyramid;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

/**
 * Created by Superuser on 20.05.2016.
 */
public class PyramidSecondRoundActivityTest extends ActivityInstrumentationTestCase2<PyramidSecondRoundActivity> {

    private Solo solo;

    public PyramidSecondRoundActivityTest() {
        super(PyramidSecondRoundActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void tearDown() throws Exception {

    }

    public void testShow() {

    }
}