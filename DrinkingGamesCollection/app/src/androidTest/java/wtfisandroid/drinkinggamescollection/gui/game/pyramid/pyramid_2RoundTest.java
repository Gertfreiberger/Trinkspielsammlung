package wtfisandroid.drinkinggamescollection.gui.game.pyramid;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

/**
 * Created by Superuser on 03.06.2016.
 */
public class pyramid_2RoundTest extends ActivityInstrumentationTestCase2<pyramid_2Round> {

    private Solo solo;

    public pyramid_2RoundTest() {
        super(pyramid_2Round.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void tearDown() throws Exception {

    }

    public void testShow() {

    }
}