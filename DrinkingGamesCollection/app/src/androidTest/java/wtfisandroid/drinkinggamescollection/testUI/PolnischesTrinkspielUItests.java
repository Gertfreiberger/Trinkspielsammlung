package wtfisandroid.drinkinggamescollection.testUI;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import com.robotium.solo.Solo;

import wtfisandroid.drinkinggamescollection.Maexchen;
import wtfisandroid.drinkinggamescollection.PolnischesTrinkspiel;
import wtfisandroid.drinkinggamescollection.R;

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
        Button button_start = (Button) mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_start);
        Button button_plus = (Button) mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_plus);
        Button button_minus= (Button) mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_minus);
        Button button_help = (Button) mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_help);

        mypolnischSolo.clickOnButton(button_start.getText().toString());
        mypolnischSolo.clickOnButton(button_plus.getText().toString());
        mypolnischSolo.clickOnButton(button_minus.getText().toString());
        mypolnischSolo.clickOnButton(button_help.getText().toString());

        String left_arrow = "Linker Pfeil ";
        String right_arrow = "Rechter Pfeil ";

        for(int i = 1; i  < 11;i++) {
            mypolnischSolo.clickOnButton(left_arrow + i);
            mypolnischSolo.clickOnButton(right_arrow + i);
        }
    }



}
