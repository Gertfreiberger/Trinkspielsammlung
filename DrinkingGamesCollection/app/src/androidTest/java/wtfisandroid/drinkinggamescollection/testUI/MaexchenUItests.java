package wtfisandroid.drinkinggamescollection.testUI;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.robotium.solo.Solo;

import org.w3c.dom.Text;

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

        Button image_button_help = (Button) myMaexchenSolo.getCurrentActivity().findViewById(R.id.maexchen_button_help);
        myMaexchenSolo.clickOnButton(image_button_help.getText().toString());
        myMaexchenSolo.goBack();
    }

    public void testButtonLeftAndRight() {
        Button button_left = (Button) myMaexchenSolo.getCurrentActivity().findViewById(R.id.maexchen_button_left);
        myMaexchenSolo.clickOnButton(button_left.getText().toString());
        Button button_right = (Button) myMaexchenSolo.getCurrentActivity().findViewById(R.id.maexchen_button_right);
        myMaexchenSolo.clickOnButton(button_right.getText().toString());
    }

    public void testDices() {
        TextView view_result = (TextView) myMaexchenSolo.getCurrentActivity().findViewById(R.id.maexchen_textview_dice);
        assert(view_result.getText().toString().equals(R.string.maexchen_textView_result_init) == true);
    }

    public void testAcceleotmeterDisable() {
        TextView view_result = (TextView) myMaexchenSolo.getCurrentActivity().findViewById(R.id.maexchen_textview_dice);
        Button button_left = (Button) myMaexchenSolo.getCurrentActivity().findViewById(R.id.maexchen_button_left);
        myMaexchenSolo.clickOnButton(button_left.getText().toString());

    }

}
