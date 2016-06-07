package wtfisandroid.drinkinggamescollection.gui.game.pyramid;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

/**
 * Created by Superuser on 03.06.2016.
 */
public class pyramidSecondRoundTest extends ActivityInstrumentationTestCase2<PyramidSecondRound> {

    private Solo solo;

    public pyramidSecondRoundTest() {
        super(PyramidSecondRound.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void tearDown() throws Exception {

    }

    public void testShow() {

    }
}