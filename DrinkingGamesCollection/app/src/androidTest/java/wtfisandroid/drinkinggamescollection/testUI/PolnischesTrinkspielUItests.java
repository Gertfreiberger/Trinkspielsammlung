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

        String left_arrow = "l_";
        String right_arrow = "r_";

        for(int i = 1; i  < 11;i++) {
            mypolnischSolo.clickOnButton(left_arrow + i);
            mypolnischSolo.clickOnButton(right_arrow + i);
        }
    }

    public void testIconChange() {
        ImageView icon = (ImageView) mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_image_player_1);
        Bitmap icon_picture = ((BitmapDrawable)icon.getDrawable()).getBitmap();

        mypolnischSolo.clickOnButton("l_1");
        assert(icon_picture.sameAs(((BitmapDrawable)icon.getDrawable()).getBitmap()));
        mypolnischSolo.clickOnButton("r_1");
        assert(!icon_picture.sameAs(((BitmapDrawable)icon.getDrawable()).getBitmap()));
        mypolnischSolo.clickOnButton("r_1");
        assert(icon_picture.sameAs(((BitmapDrawable)icon.getDrawable()).getBitmap()));
    }

    public void testVisibilityandInitText() {
        View minus = mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_minus);
        View plus = mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_plus);
        ImageView icon_3 = (ImageView) mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_image_player_3);
        ImageView icon_4 = (ImageView) mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_image_player_4);
        ImageView icon_1 = (ImageView) mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_image_player_1);


        assert(mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_text_field_player_3).getVisibility() != View.INVISIBLE);
        assert(mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_left_arrow_3).getVisibility() != View.INVISIBLE);
        assert(mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_right_arrow_3).getVisibility() != View.INVISIBLE);
        assert(mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_text_field_player_3).getVisibility() != View.INVISIBLE);
        assert(!(((BitmapDrawable)icon_1.getDrawable()).getBitmap()).sameAs(BitmapFactory.decodeResource(mypolnischSolo.getCurrentActivity().getResources(),R.mipmap.apple)));

        mypolnischSolo.clickOnView(plus);
        mypolnischSolo.sleep(200);

        assert(mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_text_field_player_3).getVisibility() != View.VISIBLE);
        assert(mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_left_arrow_3).getVisibility() != View.VISIBLE);
        assert(mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_right_arrow_3).getVisibility() != View.VISIBLE);
        assert(mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_text_field_player_3).getVisibility() != View.VISIBLE);
        assert(((EditText)mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_text_field_player_3)).getText().toString() != "Spieler3");
        assert(!(((BitmapDrawable)icon_3.getDrawable()).getBitmap()).sameAs(BitmapFactory.decodeResource(mypolnischSolo.getCurrentActivity().getResources(),R.mipmap.boy)));


        mypolnischSolo.clickOnView(plus);
        mypolnischSolo.clickOnButton("l_1");
        mypolnischSolo.sleep(200);

        assert(mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_text_field_player_4).getVisibility() != View.VISIBLE);
        assert(mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_left_arrow_4).getVisibility() != View.VISIBLE);
        assert(mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_right_arrow_4).getVisibility() != View.VISIBLE);
        assert(mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_text_field_player_4).getVisibility() != View.VISIBLE);
        assert(((EditText)mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_text_field_player_3)).getText().toString() != "Spieler4");
        assert(!(((BitmapDrawable)icon_4.getDrawable()).getBitmap()).sameAs(BitmapFactory.decodeResource(mypolnischSolo.getCurrentActivity().getResources(),R.mipmap.apple)));


        mypolnischSolo.clickOnView(plus);
        mypolnischSolo.sleep(200);
        mypolnischSolo.clickOnView(plus);
        mypolnischSolo.sleep(200);
        mypolnischSolo.clickOnView(plus);
        mypolnischSolo.sleep(200);
        mypolnischSolo.clickOnView(plus);
        mypolnischSolo.sleep(200);
        mypolnischSolo.clickOnView(plus);
        mypolnischSolo.sleep(200);
        mypolnischSolo.clickOnView(plus);
        mypolnischSolo.sleep(200);
        mypolnischSolo.clickOnView(plus);
        mypolnischSolo.sleep(200);
        mypolnischSolo.clickOnView(plus);
        mypolnischSolo.sleep(200);

        mypolnischSolo.clickOnView(minus);
        mypolnischSolo.sleep(200);
        mypolnischSolo.clickOnView(minus);
        mypolnischSolo.sleep(200);
        mypolnischSolo.clickOnView(minus);
        mypolnischSolo.sleep(200);
        mypolnischSolo.clickOnView(minus);
        mypolnischSolo.sleep(200);
        mypolnischSolo.clickOnView(minus);
        mypolnischSolo.sleep(200);
        mypolnischSolo.clickOnView(minus);
        mypolnischSolo.sleep(200);
        mypolnischSolo.clickOnView(minus);
        mypolnischSolo.sleep(200);
        mypolnischSolo.clickOnView(minus);
        mypolnischSolo.sleep(200);
        mypolnischSolo.clickOnView(minus);
        mypolnischSolo.sleep(200);
        mypolnischSolo.clickOnView(minus);
        mypolnischSolo.sleep(200);

        assert(mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_text_field_player_3).getVisibility() != View.INVISIBLE);
        assert(mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_left_arrow_3).getVisibility() != View.INVISIBLE);
        assert(mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_right_arrow_3).getVisibility() != View.INVISIBLE);
        assert(mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_text_field_player_3).getVisibility() != View.INVISIBLE);

        assert(mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_text_field_player_2).getVisibility() != View.VISIBLE);
        assert(mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_left_arrow_2).getVisibility() != View.VISIBLE);
        assert(mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_button_right_arrow_2).getVisibility() != View.VISIBLE);
        assert(mypolnischSolo.getCurrentActivity().findViewById(R.id.polnisch_text_field_player_2).getVisibility() != View.VISIBLE);

        mypolnischSolo.clickOnButton("r_1");
        mypolnischSolo.sleep(200);
        assert(!(((BitmapDrawable)icon_1.getDrawable()).getBitmap()).sameAs(BitmapFactory.decodeResource(mypolnischSolo.getCurrentActivity().getResources(),R.mipmap.apple)));
    }

}
