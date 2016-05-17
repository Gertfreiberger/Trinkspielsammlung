package wtfisandroid.drinkinggamescollection.testUI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.Image;
import android.provider.MediaStore;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
        assert(check == number_of_throwings);

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
        assert(!(check == number_of_throwings));
    }

    public void testDices() {
        TextView view_result = (TextView) myMaexchenSolo.getCurrentActivity().findViewById(R.id.maexchen_textview_dice);
        Button button_left = (Button) myMaexchenSolo.getCurrentActivity().findViewById(R.id.maexchen_button_left);
        Button button_right = (Button) myMaexchenSolo.getCurrentActivity().findViewById(R.id.maexchen_button_right);
        View cup = myMaexchenSolo.getView(R.id.maexchen_imageView_cup);
        String result = view_result.getText().toString();

        throwingDices(true, cup, result, view_result);

        myMaexchenSolo.clickOnButton(button_left.getText().toString());
        throwingDices(false, cup, result, view_result);

        myMaexchenSolo.clickOnButton(button_left.getText().toString());
        throwingDices(true, cup, result, view_result);

        myMaexchenSolo.clickOnButton(button_right.getText().toString());
        throwingDices(false, cup, result, view_result);

        myMaexchenSolo.clickOnButton(button_right.getText().toString());
        throwingDices(false, cup, result, view_result);

        myMaexchenSolo.clickOnButton(button_right.getText().toString());
        throwingDices(true, cup, result, view_result);

        myMaexchenSolo.clickOnButton(button_right.getText().toString());
        throwingDices(false, cup, result, view_result);

        myMaexchenSolo.clickOnButton(button_left.getText().toString());
        throwingDices(true, cup, result, view_result);

        myMaexchenSolo.clickOnButton(button_left.getText().toString());
        throwingDices(false, cup, result, view_result);

        myMaexchenSolo.clickOnButton(button_right.getText().toString());
        throwingDices(false, cup, result, view_result);
    }

    public void testButtonTextAndVisible() {

        Button button_left = (Button) myMaexchenSolo.getCurrentActivity().findViewById(R.id.maexchen_button_left);
        Button button_right = (Button) myMaexchenSolo.getCurrentActivity().findViewById(R.id.maexchen_button_right);
        ImageView cup = (ImageView) myMaexchenSolo.getCurrentActivity().findViewById(R.id.maexchen_imageView_cup);
        TextView result = (TextView) myMaexchenSolo.getCurrentActivity().findViewById(R.id.maexchen_textview_dice);

        assert(button_left.getText().toString().equals(R.string.maexchen_button_reveal) == false);
        assert(button_right.getText().toString().equals(R.string.maexchen_button_next) == false);
        assert(cup.getVisibility() != View.VISIBLE);
        assert(result.getVisibility() != View.INVISIBLE);

        myMaexchenSolo.clickOnButton(button_left.getText().toString());
        assert(button_left.getText().toString().equals(R.string.maexchen_button_throw_again) == false);
        assert(button_right.getText().toString().equals(R.string.maexchen_button_next) == false);
        assert(cup.getVisibility() != View.INVISIBLE);
        assert(result.getVisibility() != View.VISIBLE);

        myMaexchenSolo.sleep(300);
        myMaexchenSolo.clickOnButton(button_left.getText().toString());
        assert(button_right.getText().toString().equals(R.string.maexchen_button_next) == false);
        assert(button_left.getVisibility() == View.VISIBLE);
        assert(cup.getVisibility() != View.VISIBLE);
        assert(result.getVisibility() != View.INVISIBLE);

        myMaexchenSolo.sleep(300);
        myMaexchenSolo.clickOnButton(button_right.getText().toString());
        assert(button_left.getText().toString().equals(R.string.maexchen_button_throw) == false);
        assert(button_right.getText().toString().equals(R.string.maexchen_button_reveal) == false);
        assert(cup.getVisibility() != View.VISIBLE);
        assert(result.getVisibility() != View.INVISIBLE);

        myMaexchenSolo.sleep(300);
        myMaexchenSolo.clickOnButton(button_right.getText().toString());
        assert(button_right.getText().toString().equals(R.string.maexchen_button_new_turn) == false);
        assert(button_left.getVisibility() == View.VISIBLE);
        assert(cup.getVisibility() != View.INVISIBLE);
        assert(result.getVisibility() != View.VISIBLE);

        myMaexchenSolo.sleep(300);
        myMaexchenSolo.clickOnButton(button_right.getText().toString());
        assert(button_left.getText().toString().equals(R.string.maexchen_button_reveal) == false);
        assert(button_right.getText().toString().equals(R.string.maexchen_button_next) == false);
        assert(cup.getVisibility() != View.VISIBLE);
        assert(result.getVisibility() != View.INVISIBLE);

        myMaexchenSolo.sleep(300);
        myMaexchenSolo.clickOnButton(button_right.getText().toString());
        assert(button_left.getText().toString().equals(R.string.maexchen_button_throw) == false);
        assert(button_right.getText().toString().equals(R.string.maexchen_button_reveal) == false);
        assert(cup.getVisibility() != View.VISIBLE);
        assert(result.getVisibility() != View.INVISIBLE);

        myMaexchenSolo.sleep(300);
        myMaexchenSolo.clickOnButton(button_left.getText().toString());
        assert(button_left.getText().toString().equals(R.string.maexchen_button_reveal) == false);
        assert(button_right.getText().toString().equals(R.string.maexchen_button_next) == false);
        assert(cup.getVisibility() != View.VISIBLE);
        assert(result.getVisibility() != View.INVISIBLE);

        myMaexchenSolo.sleep(300);
        myMaexchenSolo.clickOnButton(button_left.getText().toString());
        assert(button_left.getText().toString().equals(R.string.maexchen_button_throw_again) == false);
        assert(button_right.getText().toString().equals(R.string.maexchen_button_next) == false);
        assert(cup.getVisibility() != View.INVISIBLE);
        assert(result.getVisibility() != View.VISIBLE);

        myMaexchenSolo.sleep(300);
        myMaexchenSolo.clickOnButton(button_right.getText().toString());
        assert(button_left.getText().toString().equals(R.string.maexchen_button_throw) == false);
        assert(button_right.getText().toString().equals(R.string.maexchen_button_reveal) == false);
        assert(cup.getVisibility() != View.VISIBLE);
        assert(result.getVisibility() != View.INVISIBLE);

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
        assert((same_result == number_of_throws) == check);
        result = view_result.getText().toString();
    }

    public void testSound() {
        AudioManager audio_manager = (AudioManager) myMaexchenSolo.getCurrentActivity().getSystemService(Context.AUDIO_SERVICE);
        View cup = (View) myMaexchenSolo.getView(R.id.maexchen_imageView_cup);
        myMaexchenSolo.clickOnView(cup);
        if(!audio_manager.isMusicActive()) {
            assert(true);
        }
    }


}
