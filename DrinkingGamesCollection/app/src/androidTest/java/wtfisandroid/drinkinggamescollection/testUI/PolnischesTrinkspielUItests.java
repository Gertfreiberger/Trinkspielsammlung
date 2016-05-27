package wtfisandroid.drinkinggamescollection.testUI;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

        mypolnischSolo.clickOnButton(button_plus.getText().toString());
        mypolnischSolo.clickOnButton(button_minus.getText().toString());
        mypolnischSolo.clickOnButton(button_help.getText().toString());
        mypolnischSolo.goBack();

        for(int i = 0; i < 8; i++){
            mypolnischSolo.clickOnButton(button_plus.getText().toString());
        }

        String left_arrow = "l_";
        String right_arrow = "r_";

        for(int i = 1; i  < 11;i++) {
            mypolnischSolo.clickOnButton(left_arrow + i);
            mypolnischSolo.clickOnButton(right_arrow + i);
        }
        mypolnischSolo.scrollUp();
        mypolnischSolo.clickOnButton(button_start.getText().toString());
    }

    public void testButtonsGame(){
        navigateToGame();

        Button button_help = (Button) mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_help_game);
        Button button_skip_player = (Button) mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_skip_player);
        View image_dice = mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_image_dice);

        mypolnischSolo.clickOnButton(button_help.getText().toString());
        mypolnischSolo.goBack();

        mypolnischSolo.clickOnButton(button_skip_player.getText().toString());
        mypolnischSolo.clickOnView(image_dice);
        mypolnischSolo.sleep(8000);
    }

    public void testHelpPages(){
        Button button_help = (Button) mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_help);

        mypolnischSolo.clickOnButton(button_help.getText().toString());
        mypolnischSolo.sleep(500);
        String text = ((TextView)mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_rule_headline)).getText().toString();
        assertEquals(true,text.equals(mypolnischSolo.getCurrentActivity().getResources().getString(R.string.polnisch_rule_headline)));
        text = ((TextView)mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_game_field_headline)).getText().toString();
        assertEquals(true,text.equals(mypolnischSolo.getCurrentActivity().getResources().getString(R.string.polnisch_game_field_headline)));
        text = ((TextView)mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_game_field)).getText().toString();
        assertEquals(true,text.equals(mypolnischSolo.getCurrentActivity().getResources().getString(R.string.polnisch_game_field)));
        text = ((TextView)mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_game_rule_headline)).getText().toString();
        assertEquals(true,text.equals(mypolnischSolo.getCurrentActivity().getResources().getString(R.string.polnisch_game_rule_headline)));
        text = ((TextView)mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_game_rules)).getText().toString();
        assertEquals(true,text.equals(mypolnischSolo.getCurrentActivity().getResources().getString(R.string.polnisch_game_rules)));


        mypolnischSolo.scrollDown();
        mypolnischSolo.goBack();

        navigateToGame();

        button_help = (Button) mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_help_game);
        mypolnischSolo.clickOnButton(button_help.getText().toString());
        mypolnischSolo.sleep(500);

        text = ((TextView)mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_rule_headline)).getText().toString();
        assertEquals(true,text.equals(mypolnischSolo.getCurrentActivity().getResources().getString(R.string.polnisch_rule_headline)));
        text = ((TextView)mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_game_field_headline)).getText().toString();
        assertEquals(true,text.equals(mypolnischSolo.getCurrentActivity().getResources().getString(R.string.polnisch_game_field_headline)));
        text = ((TextView)mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_game_field)).getText().toString();
        assertEquals(true,text.equals(mypolnischSolo.getCurrentActivity().getResources().getString(R.string.polnisch_game_field)));
        text = ((TextView)mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_game_rule_headline)).getText().toString();
        assertEquals(true,text.equals(mypolnischSolo.getCurrentActivity().getResources().getString(R.string.polnisch_game_rule_headline)));
        text = ((TextView)mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_game_rules)).getText().toString();
        assertEquals(true,text.equals(mypolnischSolo.getCurrentActivity().getResources().getString(R.string.polnisch_game_rules)));
    }

    public void testSkipPlayer(){
        navigateToGame();

        TextView current_player = (TextView) mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_text_field_active_player);
        ImageView current_icon = (ImageView) mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_image_active_player_icon);
        Button skip_player = (Button) mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_skip_player);

        String name = current_player.getText().toString();
        Bitmap icon = ((BitmapDrawable)current_icon.getDrawable()).getBitmap();

        assertEquals(true, current_player.getText().toString().equals(name));
        assertEquals(true,((BitmapDrawable)current_icon.getDrawable()).getBitmap().sameAs(icon));

        mypolnischSolo.clickOnButton(skip_player.getText().toString());
        mypolnischSolo.sleep(300);

        assertEquals(false, current_player.getText().toString().equals(name));
        assertEquals(false,((BitmapDrawable)current_icon.getDrawable()).getBitmap().sameAs(icon));
    }

    public void testIconChange() {
        ImageView icon = (ImageView) mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_image_player_1);
        Bitmap icon_picture = ((BitmapDrawable)icon.getDrawable()).getBitmap();

        mypolnischSolo.clickOnButton("l_1");
        mypolnischSolo.sleep(200);
        assertEquals(false,icon_picture.sameAs(((BitmapDrawable)icon.getDrawable()).getBitmap()));
        mypolnischSolo.clickOnButton("r_1");
        mypolnischSolo.sleep(200);
        assertEquals(true,icon_picture.sameAs(((BitmapDrawable)icon.getDrawable()).getBitmap()));
        mypolnischSolo.clickOnButton("r_1");
        mypolnischSolo.sleep(200);
        assertEquals(false,icon_picture.sameAs(((BitmapDrawable)icon.getDrawable()).getBitmap()));
    }

    public void testVisibilityandInitText() {
        View minus = mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_minus);
        View plus = mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_plus);
        ImageView icon_3 = (ImageView) mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_image_player_3);
        ImageView icon_4 = (ImageView) mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_image_player_4);
        ImageView icon_1 = (ImageView) mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_image_player_1);


        assertEquals(View.INVISIBLE, mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_text_field_player_3).getVisibility());
        assertEquals(View.INVISIBLE,mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_left_arrow_3).getVisibility());
        assertEquals(View.INVISIBLE,mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_right_arrow_3).getVisibility());
        assertEquals(View.INVISIBLE,mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_text_field_player_3).getVisibility());
        assertEquals(true,(((BitmapDrawable)icon_1.getDrawable()).getBitmap()).sameAs(BitmapFactory.decodeResource(mypolnischSolo.getCurrentActivity().getResources(),R.mipmap.apple)));

        mypolnischSolo.clickOnView(plus);
        mypolnischSolo.sleep(200);

        assertEquals(View.VISIBLE,mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_text_field_player_3).getVisibility());
        assertEquals(View.VISIBLE,mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_left_arrow_3).getVisibility());
        assertEquals(View.VISIBLE,mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_right_arrow_3).getVisibility());
        assertEquals(View.VISIBLE,mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_text_field_player_3).getVisibility());
        assertEquals("Spieler3",((EditText)mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_text_field_player_3)).getText().toString());
        assertEquals(true, (((BitmapDrawable)icon_3.getDrawable()).getBitmap()).sameAs(BitmapFactory.decodeResource(mypolnischSolo.getCurrentActivity().getResources(),R.mipmap.boy)));

        mypolnischSolo.clickOnButton("l_1");
        mypolnischSolo.clickOnView(plus);
        mypolnischSolo.sleep(200);

        assertEquals(View.VISIBLE, mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_text_field_player_4).getVisibility());
        assertEquals(View.VISIBLE,mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_left_arrow_4).getVisibility());
        assertEquals(View.VISIBLE,mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_right_arrow_4).getVisibility());
        assertEquals(View.VISIBLE,mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_text_field_player_4).getVisibility());
        assertEquals("Spieler4",((EditText)mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_text_field_player_4)).getText().toString());
        assertEquals(true,(((BitmapDrawable)icon_4.getDrawable()).getBitmap()).sameAs(BitmapFactory.decodeResource(mypolnischSolo.getCurrentActivity().getResources(),R.mipmap.apple)));


        for(int i = 0; i < 8; i++){
            mypolnischSolo.clickOnView(plus);

        }

        for(int i = 0; i < 12; i++){
            mypolnischSolo.clickOnView(minus);
        }


        assertEquals(View.INVISIBLE, mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_text_field_player_3).getVisibility());
        assertEquals(View.INVISIBLE,mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_left_arrow_3).getVisibility());
        assertEquals(View.INVISIBLE,mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_right_arrow_3).getVisibility());
        assertEquals(View.INVISIBLE,mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_text_field_player_3).getVisibility());

        assertEquals(View.VISIBLE, mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_text_field_player_2).getVisibility());
        assertEquals(View.VISIBLE,mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_left_arrow_2).getVisibility());
        assertEquals(View.VISIBLE,mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_right_arrow_2).getVisibility());
        assertEquals(View.VISIBLE,mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_text_field_player_2).getVisibility());

        mypolnischSolo.clickOnButton("r_1");
        mypolnischSolo.sleep(200);
        assertEquals(true,(((BitmapDrawable)icon_1.getDrawable()).getBitmap()).sameAs(BitmapFactory.decodeResource(mypolnischSolo.getCurrentActivity().getResources(),R.mipmap.apple)));
    }

    public void testStartChecks() {
        Button button_start = (Button) mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_start);
        Button button_plus = (Button) mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_plus);

        EditText text_field_1 = (EditText) mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_text_field_player_1);
        EditText text_field_6 = (EditText) mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_text_field_player_6);
        EditText text_field_9 = (EditText) mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_text_field_player_9);

        for(int i = 0; i < 8; i++) {
            mypolnischSolo.clickOnButton(button_plus.getText().toString());
        }

        mypolnischSolo.clearEditText(text_field_6);
        mypolnischSolo.enterText(text_field_6, "Moritz");
        mypolnischSolo.clearEditText(text_field_1);
        mypolnischSolo.enterText(text_field_1, "Moritz");
        mypolnischSolo.scrollUp();
        mypolnischSolo.sleep(200);

        mypolnischSolo.clickOnButton(button_start.getText().toString());
        mypolnischSolo.sleep(200);
        assertEquals(true,mypolnischSolo.searchText(mypolnischSolo.getCurrentActivity().getResources().getString(R.string.polnisch_message_name_twice)));
        mypolnischSolo.goBack();

        mypolnischSolo.clearEditText(text_field_6);
        mypolnischSolo.enterText(text_field_6, "Klaus");
        mypolnischSolo.clearEditText(text_field_9);
        mypolnischSolo.enterText(text_field_9, "   ");
        mypolnischSolo.scrollUp();
        mypolnischSolo.sleep(200);

        mypolnischSolo.clickOnButton(button_start.getText().toString());
        mypolnischSolo.sleep(200);
        assertEquals(true,mypolnischSolo.searchText(mypolnischSolo.getCurrentActivity().getResources().getString(R.string.polnisch_message_name_empty)));
        mypolnischSolo.goBack();

        mypolnischSolo.clearEditText(text_field_9);
        mypolnischSolo.enterText(text_field_9, "Penny");
        mypolnischSolo.scrollUp();
        mypolnischSolo.sleep(200);
        mypolnischSolo.clickOnButton(button_start.getText().toString());
        mypolnischSolo.sleep(200);
        mypolnischSolo.getText("Moritz");






    }

    public void navigateToGame() {
        Button button_start = (Button) mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_start);

        mypolnischSolo.clickOnButton(button_start.getText().toString());
        mypolnischSolo.sleep(5000);
    }

}
