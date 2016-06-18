package wtfisandroid.drinkinggamescollection.testUI;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import com.robotium.solo.Solo;

import java.util.ArrayList;
import java.util.Random;

import wtfisandroid.drinkinggamescollection.R;
import wtfisandroid.drinkinggamescollection.gui.game.pyramid.PyramidFinalRound;
import wtfisandroid.drinkinggamescollection.logic.Utilities;

/**
 * Created by Superuser on 18.06.2016.
 */
public class PyramidFinalRoundTest extends ActivityInstrumentationTestCase2<PyramidFinalRound> {

    private Solo solo;
    private ArrayList<String> finalPlayer = new ArrayList<>();

    public PyramidFinalRoundTest() { super(PyramidFinalRound.class); }

    @Override
    public PyramidFinalRound getActivity() {
        Intent intent = new Intent();
        intent.putStringArrayListExtra(Utilities.FINAL_PLAYER, finalPlayer);
        setActivityIntent(intent);
        return super.getActivity();
    }

    public void setUp() throws Exception {
        super.setUp();
        finalPlayer.add("Player1");
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    /* Go through final round and always decide upper case*/
    //
    public void testAlwaysUp() throws Exception {
        while (solo.getView(R.id.ibPyramidFinalHigherButton).isClickable()) {
            solo.clickOnView(solo.getView(R.id.ibPyramidFinalHigherButton));
            solo.sleep(500);
        }
    }

    /* Go through final round and always decide upper case*/
    //
    public void testAlwaysDown() throws Exception {
        while (solo.getView(R.id.ibPyramidFinalLowerButton).isClickable()) {
            solo.clickOnView(solo.getView(R.id.ibPyramidFinalLowerButton));
            solo.sleep(500);
        }
    }

    /* Go through final round and decide randomly between up and down*/
    //
    public void testRandomChoice() throws Exception {
        int x;
        Random rand = new Random();
        while (solo.getView(R.id.ibPyramidFinalLowerButton).isClickable()) {
            x = rand.nextInt(2);
            switch(x) {
                case 1: solo.clickOnView(solo.getView(R.id.ibPyramidFinalHigherButton));
                        break;

                case 0: solo.clickOnView(solo.getView(R.id.ibPyramidFinalLowerButton));
                        break;
            }
            solo.sleep(500);
        }
    }
}