package wtfisandroid.drinkinggamescollection.testUI;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ImageButton;

import com.robotium.solo.Solo;
import wtfisandroid.drinkinggamescollection.Maexchen;
import wtfisandroid.drinkinggamescollection.MainMenu;
import wtfisandroid.drinkinggamescollection.R;


public class MaexchenUItests extends ActivityInstrumentationTestCase2<Maexchen> {

    private Solo myMaexchenSolo;


    public MaexchenUItests() {
        super(Maexchen.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        myMaexchenSolo = new Solo(getInstrumentation(), getActivity());
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }


    public void testMaexchenButtonHelp() {

        ImageButton image_button_help = (ImageButton) myMaexchenSolo.getCurrentActivity().findViewById(R.id.maexchen_button_help);
        myMaexchenSolo.clickOnImageButton(image_button_help.getId());
        myMaexchenSolo.goBack();
    }

    public void testButtonLeftAndRight() {
        Button button_left = (Button) myMaexchenSolo.getCurrentActivity().findViewById(R.id.maexchen_button_left);
        myMaexchenSolo.clickOnButton(button_left.getText().toString());
        Button button_right = (Button) myMaexchenSolo.getCurrentActivity().findViewById(R.id.maexchen_button_right);
        myMaexchenSolo.clickOnButton(button_right.getText().toString());
    }

}
