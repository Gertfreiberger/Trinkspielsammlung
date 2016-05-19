package wtfisandroid.drinkinggamescollection.testUI;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import wtfisandroid.drinkinggamescollection.Maexchen;
import wtfisandroid.drinkinggamescollection.PolnischesTrinkspiel;

public class PolnischesTrinkspielUItests extends ActivityInstrumentationTestCase2<PolnischesTrinkspiel> {

    private Solo mypolnischSolo;

    public PolnischesTrinkspielUItests() {
        super(PolnischesTrinkspiel.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        mypolnischSolo = new Solo(getInstrumentation(), getActivity());
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testButtons() {
        Button button_start = (Button) mypolnischSolo.getCurrentActivity().findViewById(R.id.);

    }



}
