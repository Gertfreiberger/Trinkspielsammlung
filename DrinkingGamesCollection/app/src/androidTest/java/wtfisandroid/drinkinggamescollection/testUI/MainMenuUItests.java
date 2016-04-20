package wtfisandroid.drinkinggamescollection.testUI;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import com.robotium.solo.Solo;
import wtfisandroid.drinkinggamescollection.MainMenu;
import wtfisandroid.drinkinggamescollection.R;


public class MainMenuUItests extends ActivityInstrumentationTestCase2<MainMenu> {

    private Solo mySolo;


    public MainMenuUItests() {
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
        button_test = (Button) mySolo.getCurrentActivity().findViewById(R.id.button_kingscup);
        mySolo.clickOnButton(button_test.getText().toString());
        mySolo.goBack();
    }








}
