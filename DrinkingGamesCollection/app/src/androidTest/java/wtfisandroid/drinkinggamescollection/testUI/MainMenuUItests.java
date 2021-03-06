package wtfisandroid.drinkinggamescollection.testUI;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import com.robotium.solo.Solo;

import wtfisandroid.drinkinggamescollection.activities.MainMenu;
import wtfisandroid.drinkinggamescollection.R;


public class MainMenuUITests extends ActivityInstrumentationTestCase2<MainMenu> {

    private Solo mySolo;


     public MainMenuUITests() {
        super(MainMenu.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        mySolo = new Solo(getInstrumentation(), getActivity());
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testButtons() {

        Button button_test = (Button) mySolo.getCurrentActivity().findViewById(R.id.button_maexchen);
        mySolo.clickOnButton(button_test.getText().toString());
        mySolo.goBack();

        button_test = (Button) mySolo.getCurrentActivity().findViewById(R.id.button_polnisches_trinkspiel);
        mySolo.clickOnButton(button_test.getText().toString());
        mySolo.scrollDown();
        mySolo.goBack();

        button_test = (Button) mySolo.getCurrentActivity().findViewById(R.id.button_settings);
        mySolo.clickOnButton(button_test.getText().toString());
        mySolo.goBack();

        button_test = (Button) mySolo.getCurrentActivity().findViewById(R.id.button_manual);
        mySolo.clickOnButton(button_test.getText().toString());
        mySolo.sleep(3000);
        mySolo.goBack();

        button_test = (Button) mySolo.getCurrentActivity().findViewById(R.id.button_pyramid);
        mySolo.clickOnButton(button_test.getText().toString());
        mySolo.goBack();
        mySolo.goBack();
        mySolo.goBack();

        button_test = (Button) mySolo.getCurrentActivity().findViewById(R.id.button_i_have_never_ever);
        mySolo.clickOnButton(button_test.getText().toString());


    }

    public void testButtonClose(){
        Button button_test = (Button) mySolo.getCurrentActivity().findViewById(R.id.button_close);
        mySolo.clickOnButton(button_test.getText().toString());
    }
}
