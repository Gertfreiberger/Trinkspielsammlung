package wtfisandroid.drinkinggamescollection;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class PolnischesTrinkspiel extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<ImageView> view_image_;
    private ArrayList<Bitmap> all_icons_;
    private ArrayList<Integer> used_icons_;
    private ArrayList<EditText> text_fields_;
    private ArrayList<Button> left_arrows_;
    private ArrayList<Button> right_arrows_;
    private int player_index_;
    private static int unused_image_view_ = 100;
    private static int fixed_player_index_ = 1;
    private static int max_player_index_ = 9;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polnisches_trinkspiel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        view_image_ = new ArrayList<ImageView>();
        all_icons_ = new ArrayList<Bitmap>();
        used_icons_ = new ArrayList<Integer>();
        text_fields_ = new ArrayList<EditText>();
        left_arrows_ = new ArrayList<Button>();
        right_arrows_ = new ArrayList<Button>();
        loadIcons();
        loadImageViews();
        initIcons();
        loadTextFields();
        loadLeftArrows();
        loadRightArrows();

        player_index_ = fixed_player_index_;

        ((Button) findViewById(R.id.polnisch_button_start)).setOnClickListener(this);
        ((Button) findViewById(R.id.polnisch_button_plus)).setOnClickListener(this);
        ((Button) findViewById(R.id.polnisch_button_minus)).setOnClickListener(this);
        ((Button) findViewById(R.id.polnisch_button_help)).setOnClickListener(this);

    }


    public void loadTextFields() {
        text_fields_.add((EditText) findViewById(R.id.polnisch_text_field_player_1));
        text_fields_.add((EditText) findViewById(R.id.polnisch_text_field_player_2));
        text_fields_.add((EditText) findViewById(R.id.polnisch_text_field_player_3));
        text_fields_.add((EditText) findViewById(R.id.polnisch_text_field_player_4));
        text_fields_.add((EditText) findViewById(R.id.polnisch_text_field_player_5));
        text_fields_.add((EditText) findViewById(R.id.polnisch_text_field_player_6));
        text_fields_.add((EditText) findViewById(R.id.polnisch_text_field_player_7));
        text_fields_.add((EditText) findViewById(R.id.polnisch_text_field_player_8));
        text_fields_.add((EditText) findViewById(R.id.polnisch_text_field_player_9));
        text_fields_.add((EditText) findViewById(R.id.polnisch_text_field_player_10));

        for(int i = 1; i <= text_fields_.size(); i++) {
            text_fields_.get(i-1).setText(text_fields_.get(i-1).getText().toString() + i);
        }
    }

    public void loadIcons() {
        all_icons_.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.apple));
        all_icons_.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.basketball));
        all_icons_.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.boy));
        all_icons_.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.boy_1));
        all_icons_.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.cherries));
        all_icons_.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.chick));
        all_icons_.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.cocktail));
        all_icons_.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.crab));
        all_icons_.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.flower));
        all_icons_.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.fox));
        all_icons_.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.ghost));
        all_icons_.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.girl));
        all_icons_.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.hedgehog));
        all_icons_.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.hippopotamus));
        all_icons_.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.koala));
        all_icons_.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.mushroom));
        all_icons_.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.mushroom_1));
        all_icons_.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.pacman));
        all_icons_.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.pig));
        all_icons_.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.pint));
        all_icons_.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.soccer));
        all_icons_.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.star_red));
        all_icons_.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.tiger));
        all_icons_.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.whale));
        all_icons_.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.zebra));
    }

    public void loadImageViews() {
        view_image_.add(((ImageView) findViewById(R.id.polnisch_image_player_1)));
        view_image_.add(((ImageView) findViewById(R.id.polnisch_image_player_2)));
        view_image_.add(((ImageView) findViewById(R.id.polnisch_image_player_3)));
        view_image_.add(((ImageView) findViewById(R.id.polnisch_image_player_4)));
        view_image_.add(((ImageView) findViewById(R.id.polnisch_image_player_5)));
        view_image_.add(((ImageView) findViewById(R.id.polnisch_image_player_6)));
        view_image_.add(((ImageView) findViewById(R.id.polnisch_image_player_7)));
        view_image_.add(((ImageView) findViewById(R.id.polnisch_image_player_8)));
        view_image_.add(((ImageView) findViewById(R.id.polnisch_image_player_9)));
        view_image_.add(((ImageView) findViewById(R.id.polnisch_image_player_10)));
    }

    public void loadLeftArrows() {
        left_arrows_.add((Button) findViewById(R.id.polnisch_button_left_arrow_1));
        left_arrows_.add((Button) findViewById(R.id.polnisch_button_left_arrow_2));
        left_arrows_.add((Button) findViewById(R.id.polnisch_button_left_arrow_3));
        left_arrows_.add((Button) findViewById(R.id.polnisch_button_left_arrow_4));
        left_arrows_.add((Button) findViewById(R.id.polnisch_button_left_arrow_5));
        left_arrows_.add((Button) findViewById(R.id.polnisch_button_left_arrow_6));
        left_arrows_.add((Button) findViewById(R.id.polnisch_button_left_arrow_7));
        left_arrows_.add((Button) findViewById(R.id.polnisch_button_left_arrow_8));
        left_arrows_.add((Button) findViewById(R.id.polnisch_button_left_arrow_9));
        left_arrows_.add((Button) findViewById(R.id.polnisch_button_left_arrow_10));
    }

    public void loadRightArrows() {
        right_arrows_.add((Button) findViewById(R.id.polnisch_button_right_arrow_1));
        right_arrows_.add((Button) findViewById(R.id.polnisch_button_right_arrow_2));
        right_arrows_.add((Button) findViewById(R.id.polnisch_button_right_arrow_3));
        right_arrows_.add((Button) findViewById(R.id.polnisch_button_right_arrow_4));
        right_arrows_.add((Button) findViewById(R.id.polnisch_button_right_arrow_5));
        right_arrows_.add((Button) findViewById(R.id.polnisch_button_right_arrow_6));
        right_arrows_.add((Button) findViewById(R.id.polnisch_button_right_arrow_7));
        right_arrows_.add((Button) findViewById(R.id.polnisch_button_right_arrow_8));
        right_arrows_.add((Button) findViewById(R.id.polnisch_button_right_arrow_9));
        right_arrows_.add((Button) findViewById(R.id.polnisch_button_right_arrow_10));
    }

    public void initIcons() {

        for(int i = 0; i < 10; i++) {
            if(i < 2) {
                view_image_.get(i).setImageBitmap(all_icons_.get(i));
                used_icons_.add(i);
            }
            else {
                used_icons_.add(unused_image_view_);
            }
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.polnisch_button_help:
                startActivity(new Intent(this, PolnischesTrinkspielRules.class));
                break;

            case R.id.polnisch_button_minus:

                if(player_index_ > fixed_player_index_) {
                    changeVisible(View.INVISIBLE);
                    player_index_--;
                }
                break;

            case R.id.polnisch_button_plus:

                if(player_index_ < max_player_index_) {
                    player_index_++;
                    changeVisible(View.VISIBLE);
                }
                break;

            case R.id.polnisch_button_start:

                ArrayList<String> players = new ArrayList<String>();
                ArrayList<Bitmap> icons = new ArrayList<Bitmap>();
                boolean check = false;

                for(int i = 0; i <= player_index_; i++) {

                    text_fields_.get(i).getText().toString().trim();
                    if(!text_fields_.get(i).getText().toString().isEmpty()){
                        for(int j = 0; j < players.size(); j++)
                        {
                            if(i != j && players.get(j).equals(text_fields_.get(i).getText().toString())) {

                                nameError(getResources().getString(R.string.polnisch_message_name_twice));
                                check = true;
                                break;
                            }
                        }   
                    }else{
                        check = true;
                        nameError(getResources().getString(R.string.polnisch_message_name_empty));
                    }
                    

                    if(check) {
                       break;
                    }

                    players.add(text_fields_.get(i).getText().toString());
                    icons.add(all_icons_.get(used_icons_.get(i)));
                }

                if(!check) {
                    Intent intent_start = new Intent(this, PolnischGame.class);
                    intent_start.putStringArrayListExtra("players_name", players);
                    intent_start.putParcelableArrayListExtra("icons", icons);
                    startActivity(intent_start);
                }
                break;

            default:
        }
    }

    public void nameError(String message) {
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setMessage(message);
        dlg.setTitle(R.string.title_activity_polnisches_trinkspiel);
        dlg.setPositiveButton(R.string.polnisch_button_name_twice, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dlg.setCancelable(true);
        dlg.create().show();
    }


    public void arrowClicked(View v) {

        Button butt = (Button) v;
        String[] arrows = butt.getText().toString().split("_");
        nextIcon(((arrows[0].equals("l"))), Integer.parseInt(arrows[1])-1);
    }

    public void nextIcon(boolean dircetion, int index) {

        int bitmap_position = used_icons_.get(index);
        boolean found = true;

        if(dircetion) {
            while(found) {

                if(bitmap_position == 0) {
                    bitmap_position = all_icons_.size() - 1;
                }
                else {
                    bitmap_position--;
                }

                if(used_icons_.contains(bitmap_position)) {
                    continue;
                }

                found = false;
            }
        }
        else {
            while(found) {

                if(bitmap_position == (all_icons_.size()-1)) {
                    bitmap_position = 0;
                }
                else {
                    bitmap_position++;
                }

                if(used_icons_.contains(bitmap_position)) {
                    continue;
                }

                found = false;
            }
        }

        view_image_.get(index).setImageBitmap(all_icons_.get(bitmap_position));
        used_icons_.set(index, bitmap_position);

    }

    public void changeVisible(int visibility) {

        text_fields_.get(player_index_).setVisibility(visibility);
        text_fields_.get(player_index_).setText(R.string.polnisch_textfield_player);
        text_fields_.get(player_index_).setText(text_fields_.get(player_index_).getText().toString() + Integer.toString(player_index_+1));
        left_arrows_.get(player_index_).setVisibility(visibility);
        right_arrows_.get(player_index_).setVisibility(visibility);
        view_image_.get(player_index_).setVisibility(visibility);

        if(visibility == View.VISIBLE) {
            for(int i = 0; i < all_icons_.size(); i++) {

                if(!used_icons_.contains(i)) {
                    view_image_.get(player_index_).setImageBitmap(all_icons_.get(i));
                    used_icons_.set(player_index_, i);
                    break;
                }
            }
        }
        else {
            used_icons_.set(player_index_, unused_image_view_);
        }
    }

}
