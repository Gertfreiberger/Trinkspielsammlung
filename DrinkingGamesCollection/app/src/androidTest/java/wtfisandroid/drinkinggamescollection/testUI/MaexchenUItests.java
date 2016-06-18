package wtfisandroid.drinkinggamescollection.testUI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.robotium.solo.Solo;

import wtfisandroid.drinkinggamescollection.activities.games.maexchen.Maexchen;
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

    public void testDiceImage() {
        ImageView dice_left = (ImageView) myMaexchenSolo.getCurrentActivity().findViewById(R.id.imageView_dice_left);
        ImageView dice_right = (ImageView) myMaexchenSolo.getCurrentActivity().findViewById(R.id.imageView_dice_right);
        Button button_left = (Button) myMaexchenSolo.getCurrentActivity().findViewById(R.id.maexchen_button_left);
        View cup = myMaexchenSolo.getView(R.id.maexchen_imageView_cup);
        Bitmap left = ((BitmapDrawable)dice_left.getDrawable()).getBitmap();
        Bitmap right = ((BitmapDrawable)dice_right.getDrawable()).getBitmap();
        int check = 0;
        int number_of_throwings = 5;

        myMaexchenSolo.clickOnView(cup);

        for(int i = 0; i < number_of_throwings; i++) {
            myMaexchenSolo.clickOnView(cup);
            myMaexchenSolo.sleep(200);

            if(((BitmapDrawable) dice_left.getDrawable()).getBitmap().sameAs(left) &&
               ((BitmapDrawable) dice_right.getDrawable()).getBitmap().sameAs(right)) {
                check++;
            }
        }
        assertEquals(true, check != number_of_throwings);

        myMaexchenSolo.clickOnButton(button_left.getText().toString());
        check = 0;
        left = ((BitmapDrawable)dice_left.getDrawable()).getBitmap();
        right = ((BitmapDrawable)dice_right.getDrawable()).getBitmap();
        for(int i = 0; i < number_of_throwings; i++) {
            myMaexchenSolo.clickOnView(cup);
            myMaexchenSolo.sleep(200);

            if(((BitmapDrawable) dice_left.getDrawable()).getBitmap().sameAs(left) &&
                    ((BitmapDrawable) dice_right.getDrawable()).getBitmap().sameAs(right)) {
                check++;
            }
        }
        assertEquals(true, check == number_of_throwings);
    }

    public void testDices() {
        TextView view_result = (TextView) myMaexchenSolo.getCurrentActivity().findViewById(R.id.maexchen_textview_dice);
        Button button_left = (Button) myMaexchenSolo.getCurrentActivity().findViewById(R.id.maexchen_button_left);
        Button button_right = (Button) myMaexchenSolo.getCurrentActivity().findViewById(R.id.maexchen_button_right);
        View cup = myMaexchenSolo.getView(R.id.maexchen_imageView_cup);
        String result = view_result.getText().toString();

        throwingDices(true, cup, result, view_result);

        myMaexchenSolo.clickOnButton(button_left.getText().toString());
        result = view_result.getText().toString();
        throwingDices(false, cup, result, view_result);

        myMaexchenSolo.clickOnButton(button_left.getText().toString());
        result = view_result.getText().toString();
        throwingDices(true, cup, result, view_result);

        myMaexchenSolo.clickOnButton(button_right.getText().toString());
        result = view_result.getText().toString();
        throwingDices(false, cup, result, view_result);

        myMaexchenSolo.clickOnButton(button_right.getText().toString());
        result = view_result.getText().toString();
        throwingDices(false, cup, result, view_result);

        myMaexchenSolo.clickOnButton(button_right.getText().toString());
        result = view_result.getText().toString();
        throwingDices(true, cup, result, view_result);

        myMaexchenSolo.clickOnButton(button_right.getText().toString());
        result = view_result.getText().toString();
        throwingDices(false, cup, result, view_result);

        myMaexchenSolo.clickOnButton(button_left.getText().toString());
        result = view_result.getText().toString();
        throwingDices(true, cup, result, view_result);

        myMaexchenSolo.clickOnButton(button_left.getText().toString());
        result = view_result.getText().toString();
        throwingDices(false, cup, result, view_result);

        myMaexchenSolo.clickOnButton(button_right.getText().toString());
        result = view_result.getText().toString();
        throwingDices(false, cup, result, view_result);
    }

    public void testButtonTextAndVisible() {

        int sleep_time = 500;
        Button button_left = (Button) myMaexchenSolo.getCurrentActivity().findViewById(R.id.maexchen_button_left);
        Button button_right = (Button) myMaexchenSolo.getCurrentActivity().findViewById(R.id.maexchen_button_right);
        ImageView cup = (ImageView) myMaexchenSolo.getCurrentActivity().findViewById(R.id.maexchen_imageView_cup);
        TextView result = (TextView) myMaexchenSolo.getCurrentActivity().findViewById(R.id.maexchen_textview_dice);

        assertEquals(true, button_left.getText().toString().equals(myMaexchenSolo.getCurrentActivity().getResources().getString(R.string.maexchen_button_reveal)));
        assertEquals(true, button_right.getText().toString().equals(myMaexchenSolo.getCurrentActivity().getResources().getString(R.string.maexchen_button_next)));
        assertEquals(true, cup.getVisibility() == View.VISIBLE);
        assertEquals(true, result.getVisibility() == View.INVISIBLE);

        myMaexchenSolo.clickOnButton(button_left.getText().toString());
        myMaexchenSolo.sleep(sleep_time);
        assertEquals(true, button_left.getText().toString().equals(myMaexchenSolo.getCurrentActivity().getResources().getString(R.string.maexchen_button_throw_again)));
        assertEquals(true, button_right.getText().toString().equals(myMaexchenSolo.getCurrentActivity().getResources().getString(R.string.maexchen_button_next)));
        assertEquals(true, cup.getVisibility() == View.INVISIBLE);
        assertEquals(true, result.getVisibility() == View.VISIBLE);


        myMaexchenSolo.clickOnButton(button_left.getText().toString());
        myMaexchenSolo.sleep(sleep_time);
        assertEquals(true, button_right.getText().toString().equals(myMaexchenSolo.getCurrentActivity().getResources().getString(R.string.maexchen_button_next)));
        assertEquals(true, button_left.getVisibility() == View.INVISIBLE);
        assertEquals(true, cup.getVisibility() == View.VISIBLE);
        assertEquals(true, result.getVisibility() == View.INVISIBLE);


        myMaexchenSolo.clickOnButton(button_right.getText().toString());
        myMaexchenSolo.sleep(sleep_time);
        assertEquals(true, button_left.getText().toString().equals(myMaexchenSolo.getCurrentActivity().getResources().getString(R.string.maexchen_button_throw)));
        assertEquals(true, button_right.getText().toString().equals(myMaexchenSolo.getCurrentActivity().getResources().getString(R.string.maexchen_button_reveal)));
        assertEquals(true, cup.getVisibility() == View.VISIBLE);
        assertEquals(true, result.getVisibility() == View.INVISIBLE);


        myMaexchenSolo.clickOnButton(button_right.getText().toString());
        myMaexchenSolo.sleep(sleep_time);
        assertEquals(true, button_right.getText().toString().equals(myMaexchenSolo.getCurrentActivity().getResources().getString(R.string.maexchen_button_new_turn)));
        assertEquals(true, button_left.getVisibility() == View.INVISIBLE);
        assertEquals(true, cup.getVisibility() == View.INVISIBLE);
        assertEquals(true, result.getVisibility() == View.VISIBLE);


        myMaexchenSolo.clickOnButton(button_right.getText().toString());
        myMaexchenSolo.sleep(sleep_time);
        assertEquals(true, button_left.getText().toString().equals(myMaexchenSolo.getCurrentActivity().getResources().getString(R.string.maexchen_button_reveal)));
        assertEquals(true, button_right.getText().toString().equals(myMaexchenSolo.getCurrentActivity().getResources().getString(R.string.maexchen_button_next)));
        assertEquals(true, cup.getVisibility() == View.VISIBLE);
        assertEquals(true, result.getVisibility() == View.INVISIBLE);


        myMaexchenSolo.clickOnButton(button_right.getText().toString());
        myMaexchenSolo.sleep(sleep_time);
        assertEquals(true, button_left.getText().toString().equals(myMaexchenSolo.getCurrentActivity().getResources().getString(R.string.maexchen_button_throw)));
        assertEquals(true, button_right.getText().toString().equals(myMaexchenSolo.getCurrentActivity().getResources().getString(R.string.maexchen_button_reveal)));
        assertEquals(true, cup.getVisibility() == View.VISIBLE);
        assertEquals(true, result.getVisibility() == View.INVISIBLE);


        myMaexchenSolo.clickOnButton(button_left.getText().toString());
        myMaexchenSolo.sleep(sleep_time);
        assertEquals(true, button_left.getText().toString().equals(myMaexchenSolo.getCurrentActivity().getResources().getString(R.string.maexchen_button_reveal)));
        assertEquals(true, button_right.getText().toString().equals(myMaexchenSolo.getCurrentActivity().getResources().getString(R.string.maexchen_button_next)));
        assertEquals(true, cup.getVisibility() == View.VISIBLE);
        assertEquals(true, result.getVisibility() == View.INVISIBLE);


        myMaexchenSolo.clickOnButton(button_left.getText().toString());
        myMaexchenSolo.sleep(sleep_time);
        assertEquals(true, button_left.getText().toString().equals(myMaexchenSolo.getCurrentActivity().getResources().getString(R.string.maexchen_button_throw_again)));
        assertEquals(true, button_right.getText().toString().equals(myMaexchenSolo.getCurrentActivity().getResources().getString(R.string.maexchen_button_next)));
        assertEquals(true, cup.getVisibility() == View.INVISIBLE);
        assertEquals(true, result.getVisibility() == View.VISIBLE);


        myMaexchenSolo.clickOnButton(button_right.getText().toString());
        myMaexchenSolo.sleep(sleep_time);
        assertEquals(true, button_left.getText().toString().equals(myMaexchenSolo.getCurrentActivity().getResources().getString(R.string.maexchen_button_throw)));
        assertEquals(true, button_right.getText().toString().equals(myMaexchenSolo.getCurrentActivity().getResources().getString(R.string.maexchen_button_reveal)));
        assertEquals(true, cup.getVisibility() == View.VISIBLE);
        assertEquals(true, result.getVisibility() == View.INVISIBLE);

    }

    public void testCupClick() {
        View cup = myMaexchenSolo.getView(R.id.maexchen_imageView_cup);
        myMaexchenSolo.clickOnView(cup);
    }

    public void throwingDices(boolean check, View cup, String result, TextView view_result) {

        int number_of_throws = 5;
        int same_result = 0;
        for(int i = 0; i < number_of_throws; i++) {

            myMaexchenSolo.clickOnView(cup);
            myMaexchenSolo.sleep(200);

            if(result.equals(view_result.getText().toString())) {
                same_result++;
            }
        }
        assertEquals(check, same_result != number_of_throws);
    }

    public void testSound() {
        AudioManager audio_manager = (AudioManager) myMaexchenSolo.getCurrentActivity().getSystemService(Context.AUDIO_SERVICE);
        View cup = myMaexchenSolo.getView(R.id.maexchen_imageView_cup);
        myMaexchenSolo.clickOnView(cup);
        myMaexchenSolo.sleep(200);
        if(!audio_manager.isMusicActive()) {
            assertEquals(true,false);
        }
    }


}
