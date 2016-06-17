package wtfisandroid.drinkinggamescollection;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import wtfisandroid.drinkinggamescollection.features.Dice;
import wtfisandroid.drinkinggamescollection.features.Player;
import wtfisandroid.drinkinggamescollection.features.PolnischField;

public class PolnischGame extends AppCompatActivity {

    private ArrayList<Player> players_;
    private static int start_field_ = 1;
    private static int last_field_ = 72;
    private int current_player_;
    private MediaPlayer sound_dices_;
    private boolean round_blocked_;
    private Dice dice_;
    private ImageView current_player_icon_;
    private TextView current_player_name_;
    private ArrayList<PolnischField>  fields_;
    private Drawable border_red_;
    private int dest_field_;
    private int begin_field_;
    private static int tick_time_ = 1000;
    private ScrollView scroll_vertical_;
    private HorizontalScrollView scroll_horizontal_;
    private GridLayout grid_;
    private int field_width_;
    private int field_height_;
    private final int wait_send_to_field_ = 5 * tick_time_;
    private int throwing_times_;
    private int field_65_player_count_;
    private boolean send_start_;
    private boolean field_59_;
    private boolean field_62_69_;
    private boolean field_34_;
    private boolean field_36_;
    private boolean field_65_;
    private boolean field_65_allow_dice_;
    private int back_move_62_69_;
    private Button skip_player_;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polnisch_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        skip_player_ = (Button) findViewById(R.id.polnisch_button_skip_player);
        field_65_player_count_ = 0;
        current_player_ = 0;
        throwing_times_ = 0;
        send_start_ = false;
        field_59_ = false;
        field_62_69_ = false;
        field_34_ = false;
        field_36_ = false;
        field_65_ = false;
        field_65_allow_dice_ = true;
        back_move_62_69_ = 0;
        current_player_icon_ = (ImageView) findViewById(R.id.polnisch_image_active_player_icon);
        current_player_name_ = (TextView) findViewById(R.id.polnisch_text_field_active_player);
        players_ = new ArrayList<Player>();
        createPlayers(this.getIntent().getStringArrayListExtra("players_name"), this.getIntent().getIntegerArrayListExtra("icons"));
        
        current_player_name_.setText(players_.get(current_player_).getName());
        current_player_icon_.setImageBitmap(players_.get(current_player_).getIcon());

        ArrayList<Bitmap> pictures = new ArrayList<Bitmap>();
        loadDicePictures(pictures);
        dice_ = new Dice(pictures);
        sound_dices_ = MediaPlayer.create(this,R.raw.throwing_dice_3_seconds);
        round_blocked_ = false;

        fields_ = new ArrayList<PolnischField>();
        loadFields();

        border_red_ = getResources().getDrawable(R.drawable.border_red);

        scroll_vertical_ = (ScrollView) findViewById(R.id.polnisch_scroll_view_vertical);
        scroll_horizontal_ = (HorizontalScrollView) findViewById(R.id.polnisch_scroll_view_horizontal);
        grid_ = (GridLayout) findViewById(R.id.polnisch_grid);

        loadRowsAndCollums();

        field_height_ = fields_.get(start_field_).getHeight();
        field_width_ = fields_.get(start_field_).getWidth();

        scroll_vertical_.post(new Runnable() {
            @Override
            public void run() {

                new CountDownTimer(tick_time_,tick_time_) {

                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        scroll_vertical_.smoothScrollTo(0,7*field_height_);
                        scroll_horizontal_.smoothScrollTo(0, 0);
                    }
                }.start();
            }
        });
}

    @Override
    public void onBackPressed(){
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setMessage(R.string.polnisch_dialog_alert_back_description);
        dlg.setTitle(R.string.polnisch_dialog_alert_back_title);
        dlg.setPositiveButton(R.string.polnisch_dialog_alert_back_nein, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        dlg.setNegativeButton(R.string.polnisch_dialog_alert_back_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        dlg.create().show();
    }

    public void loadDicePictures(ArrayList<Bitmap> pictures) {
        pictures.add(BitmapFactory.decodeResource(getResources(), R.mipmap.dice_1));
        pictures.add(BitmapFactory.decodeResource(getResources(), R.mipmap.dice_2));
        pictures.add(BitmapFactory.decodeResource(getResources(), R.mipmap.dice_3));
        pictures.add(BitmapFactory.decodeResource(getResources(), R.mipmap.dice_4));
        pictures.add(BitmapFactory.decodeResource(getResources(), R.mipmap.dice_5));
        pictures.add(BitmapFactory.decodeResource(getResources(), R.mipmap.dice_6));
    }

    public void createPlayers(ArrayList<String> names, ArrayList<Integer> icons) {

        ArrayList<Bitmap> all_icons = loadIcons();

        for(int i = 0; i< names.size(); i++) {
            players_.add(new Player(names.get(i)));
            players_.get(i).setField(start_field_);
            players_.get(i).setIcon(all_icons.get(icons.get(i)));
        }
    }

    public ArrayList<Bitmap> loadIcons() {
        ArrayList<Bitmap> all_icons = new ArrayList<Bitmap>();

        all_icons.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.apple));
        all_icons.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.basketball));
        all_icons.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.boy));
        all_icons.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.boy_1));
        all_icons.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.cherries));
        all_icons.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.chick));
        all_icons.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.cocktail));
        all_icons.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.crab));
        all_icons.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.flower));
        all_icons.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.fox));
        all_icons.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.ghost));
        all_icons.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.girl));
        all_icons.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.hedgehog));
        all_icons.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.hippopotamus));
        all_icons.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.koala));
        all_icons.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.mushroom));
        all_icons.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.mushroom_1));
        all_icons.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.pacman));
        all_icons.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.pig));
        all_icons.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.pint));
        all_icons.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.soccer));
        all_icons.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.star_red));
        all_icons.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.tiger));
        all_icons.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.whale));
        all_icons.add((Bitmap) BitmapFactory.decodeResource(getResources(), R.mipmap.zebra));
        return  all_icons;
    }

    public void loadFields() {

        ArrayList<ImageView> image_views = loadImageViewsOfGame();
        ArrayList<LinearLayout> layouts = loadLayoutsOfGame();


        for(int i = 1, j = 0; i <= last_field_; i++, j+=3 ) {

            fields_.add(new PolnischField(i, layouts.get(i-1), image_views.get(j), image_views.get(j+1), image_views.get(j+2)));
        }


        for(int i = 0; i < players_.size(); i++) {
            fields_.get(start_field_ - 1).playerArrived(players_.get(i));
/*
            if(i < 3){
                fields_.get(start_field_ - 1 + 63).playerArrived(players_.get(i));
            }
            else if(i == 3) {
                fields_.get(start_field_ - 1 + 6).playerArrived(players_.get(i));
            }
            else if(i == 4) {
                fields_.get(start_field_ - 1 + 7).playerArrived(players_.get(i));
            }
            else if(i == 5) {
                fields_.get(start_field_ - 1 + 50).playerArrived(players_.get(i));
            }
            else if(i == 6) {
                fields_.get(start_field_ - 1 + 18).playerArrived(players_.get(i));
            }
            else if(i == 7) {
                fields_.get(start_field_ - 1 + 38).playerArrived(players_.get(i));
            }
            else if(i == 8) {
                fields_.get(start_field_ - 1 + 14).playerArrived(players_.get(i));
            }
            else if(i == 9) {
                fields_.get(start_field_ - 1 + 43).playerArrived(players_.get(i));
            }*/
        }

    }

    public void loadRowsAndCollums() {

        for(int i = 0; i < fields_.size(); i++) {
            fields_.get(i).setRowAndCollum(grid_.indexOfChild(fields_.get(i).getField()));
        }
    }

    public ArrayList<ImageView> loadImageViewsOfGame() {

        ArrayList<ImageView> image_views = new ArrayList<ImageView>();

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_1));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_1));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_1));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_2));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_2));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_2));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_3));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_3));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_3));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_4));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_4));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_4));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_5));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_5));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_5));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_6));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_6));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_6));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_7));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_7));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_7));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_8));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_8));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_8));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_9));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_9));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_9));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_10));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_10));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_10));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_11));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_11));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_11));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_12));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_12));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_12));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_13));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_13));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_13));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_14));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_14));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_14));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_15));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_15));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_15));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_16));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_16));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_16));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_17));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_17));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_17));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_18));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_18));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_18));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_19));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_19));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_19));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_20));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_20));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_20));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_21));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_21));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_21));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_22));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_22));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_22));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_23));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_23));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_23));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_24));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_24));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_24));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_25));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_25));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_25));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_26));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_26));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_26));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_27));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_27));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_27));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_28));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_28));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_28));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_29));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_29));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_29));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_30));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_30));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_30));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_31));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_31));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_31));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_32));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_32));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_32));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_33));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_33));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_33));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_34));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_34));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_34));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_35));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_35));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_35));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_36));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_36));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_36));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_37));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_37));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_37));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_38));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_38));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_38));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_39));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_39));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_39));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_40));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_40));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_40));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_41));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_41));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_41));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_42));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_42));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_42));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_43));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_43));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_43));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_44));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_44));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_44));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_45));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_45));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_45));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_46));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_46));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_46));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_47));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_47));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_47));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_48));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_48));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_48));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_49));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_49));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_49));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_50));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_50));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_50));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_51));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_51));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_51));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_52));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_52));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_52));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_53));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_53));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_53));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_54));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_54));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_54));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_55));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_55));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_55));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_56));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_56));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_56));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_57));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_57));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_57));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_58));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_58));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_58));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_59));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_59));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_59));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_60));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_60));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_60));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_61));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_61));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_61));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_62));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_62));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_62));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_63));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_63));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_63));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_64));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_64));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_64));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_65));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_65));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_65));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_66));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_66));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_66));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_67));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_67));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_67));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_68));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_68));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_68));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_69));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_69));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_69));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_70));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_70));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_70));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_71));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_71));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_71));

        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_1_field_72));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_2_field_72));
        image_views.add((ImageView) findViewById(R.id.polnisch_image_icon_3_field_72));

        return image_views;
    }

    public ArrayList<LinearLayout> loadLayoutsOfGame() {

        ArrayList<LinearLayout> layouts = new ArrayList<LinearLayout>();

        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_1));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_2));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_3));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_4));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_5));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_6));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_7));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_8));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_9));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_10));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_11));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_12));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_13));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_14));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_15));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_16));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_17));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_18));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_19));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_20));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_21));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_22));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_23));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_24));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_25));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_26));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_27));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_28));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_29));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_30));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_31));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_32));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_33));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_34));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_35));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_36));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_37));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_38));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_39));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_40));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_41));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_42));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_43));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_44));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_45));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_46));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_47));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_48));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_49));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_50));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_51));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_52));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_53));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_54));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_55));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_56));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_57));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_58));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_59));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_60));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_61));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_62));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_63));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_64));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_65));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_66));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_67));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_68));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_69));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_70));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_71));
        layouts.add((LinearLayout)findViewById(R.id.polnisch_layout_field_72));

        return layouts;
    }

    public void throwDice() {
        dice_.wuerfeln((ImageView)findViewById(R.id.polnisch_image_dice));
        if(!sound_dices_.isPlaying()) {
            sound_dices_.seekTo(0);
            sound_dices_.start();
        }
    }

    public void openHelpPage(View v) {

        Intent help_page = new Intent(this, PolnischesTrinkspielRules.class);
        startActivity(help_page);
    }

    public void skipPlayer(View v) {

        if(!round_blocked_ && !field_65_){
            if(!send_start_ && !field_59_ && !field_62_69_ && (throwing_times_ == 0)) {
                nextPlayer();
            }
        }

        if(!round_blocked_ && field_65_){
            if(!field_65_allow_dice_){
                round_blocked_ = true;
                skip_player_.setVisibility(View.INVISIBLE);
                field_65_player_count_++;
                dest_field_ = players_.get(current_player_).getField() - 2;
                if(dest_field_ < 0 ){
                    dest_field_ = 0;
                }
                sendPlayer(current_player_);

                if(field_65_player_count_ == players_.size()){
                    field_65_ = false;
                    skip_player_.setText(getResources().getString(R.string.polnisch_skip_player));
                }
            }
        }
    }

    public void newRound(View v) {
        if(!round_blocked_ && (!field_65_ || field_65_allow_dice_)) {

            round_blocked_ = true;
            throwDice();

            if(throwing_times_ == 0){

                int time_ticks = 0;
                if(players_.get(current_player_).getField() + dice_.getLastThrow() >= last_field_){
                    time_ticks = last_field_ - players_.get(current_player_).getField() + 2;
                }
                else {
                    time_ticks = (dice_.getLastThrow()+3);
                }
                final int time_to_wait = time_ticks * tick_time_;

                if(field_65_){
                    field_65_allow_dice_ = false;
                    skip_player_.setVisibility(View.VISIBLE);
                }

                movePlayer(dice_.getLastThrow()+1, time_to_wait, true);
            }
            else {
                throwing_times_--;

                if(send_start_){

                    if(field_59_){

                        if((dice_.getLastThrow()+1) == 1){
                            dest_field_ = 0;
                            sendPlayer(current_player_);
                        }
                        else {
                            nextPlayer();
                            round_blocked_ = false;
                        }
                    }
                    else {
                        field_65_allow_dice_ = false;
                        dest_field_ = 0;
                        sendPlayer(current_player_);
                    }

                    if(throwing_times_ == 0){
                        field_65_allow_dice_ = false;
                        skip_player_.setVisibility(View.VISIBLE);
                        send_start_ = false;
                        field_59_ = false;
                    }
                }
                else if(field_62_69_){

                    back_move_62_69_ += (dice_.getLastThrow() + 1);

                    if(throwing_times_ == 0){
                        field_62_69_ = false;
                        final int back_steps = (back_move_62_69_ + 2) * tick_time_;
                        movePlayer(back_move_62_69_, back_steps, false);
                        back_move_62_69_ = 0;
                        field_65_allow_dice_ = false;
                    }
                    else {
                        round_blocked_ = false;
                    }
                }
                else {
                    if(throwing_times_ == 0 && field_65_){
                        field_65_allow_dice_ = false;
                        skip_player_.setVisibility(View.VISIBLE);
                    }
                    nextPlayer();
                    round_blocked_ = false;
                }
            }
        }

    }

    public void movePlayer(int number_of_fields_to_go, final int time_to_wait, final boolean front_or_back) {
        skip_player_.setVisibility(View.INVISIBLE);
        begin_field_ = players_.get(current_player_).getField()-1;
        if(front_or_back){
            dest_field_ = begin_field_ + number_of_fields_to_go;
        }
        else {

            dest_field_ = begin_field_ - number_of_fields_to_go;
        }

        if(dest_field_ >= last_field_) {
            dest_field_ = last_field_-1;
        }

        scrollingToField(begin_field_);

        new CountDownTimer(time_to_wait, tick_time_) {

            public void onTick(long millisUntilFinished) {

                long sec = millisUntilFinished/1000 + 1;

                if(sec == time_to_wait/1000) {
                    scrollingToField(begin_field_);
                    fields_.get(begin_field_).changeBorderRed(border_red_);
                    fields_.get(begin_field_).playerLeave(players_.get(current_player_));
                }
                else if(front_or_back) {
                    fields_.get(begin_field_).changeBorderBlack();
                    scrollingToField(begin_field_ + 1);
                    fields_.get(begin_field_ + 1).changeBorderRed(border_red_);
                    begin_field_++;
                }
                else {
                    fields_.get(begin_field_).changeBorderBlack();
                    scrollingToField(begin_field_ - 1);
                    fields_.get(begin_field_ - 1).changeBorderRed(border_red_);
                    begin_field_--;
                }

            }

            public void onFinish() {
                fields_.get(dest_field_).playerArrived(players_.get(current_player_));
                fields_.get(dest_field_).changeBorderBlack();

                if(dest_field_ == (last_field_-1) ) {
                    winGame();
                }

                if(actionField()){
                    nextPlayer();
                    skip_player_.setVisibility(View.VISIBLE);
                    round_blocked_ = false;
                }
            }
        }.start();



    }

    public void nextPlayer() {

        if(current_player_ == (players_.size()-1)) {
            current_player_ = 0;
        }
        else {
            current_player_++;
        }

        current_player_name_.setText(players_.get(current_player_).getName());
        current_player_icon_.setImageBitmap(players_.get(current_player_).getIcon());

        if(players_.get(current_player_).getPause() && !send_start_ && !field_59_ && (throwing_times_ == 0 && !field_65_)){
            players_.get(current_player_).setPause(false);
            nextPlayer();
        }
    }

    public void winGame() {
        final Dialog winner = new Dialog(PolnischGame.this);
        winner.setContentView(R.layout.dialog_winner);
        winner.setCanceledOnTouchOutside(false);

        TextView player_name = (TextView) winner.findViewById(R.id.polnisch_dialog_winner_player_name);
        ImageView player_icon = (ImageView) winner.findViewById(R.id.polnisch_dialog_winner_player_icon);
        Button button_end = (Button) winner.findViewById(R.id.polnisch_dialog_winner_button);

        player_name.setText(players_.get(current_player_).getName());
        player_icon.setImageBitmap(players_.get(current_player_).getIcon());

        button_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        winner.show();
    }

    public void scrollingToField(final int field_index) {

        scroll_vertical_.post(new Runnable() {
            @Override
            public void run() {

                int collum = fields_.get(field_index).getCollum();
                int row = fields_.get(field_index).getRow();

                int x_pos;
                int y_pos;

                if(collum == 0) {
                    x_pos = collum;
                }
                else if(collum == 1){
                    x_pos = (field_width_/2);
                }
                else {
                    x_pos = (collum-1)*field_width_ + (field_width_/2);

                }

                if(row == 0){
                    y_pos = row;
                }
                else if(row == 1){
                    y_pos = (field_height_/2);
                }
                else {
                    y_pos = (row-1)*field_height_ + (field_height_/2);
                }

                scroll_vertical_.smoothScrollTo(0,y_pos);
                scroll_horizontal_.smoothScrollTo(x_pos, 0);
            }
        });
    }

    public void sendPlayer(final int player){

        begin_field_ = players_.get(player).getField() - 1;

        new CountDownTimer(wait_send_to_field_, tick_time_) {

            public void onTick(long millisUntilFinished) {

                long sec = millisUntilFinished/1000;

                if((sec+3) == wait_send_to_field_/1000) {
                    scrollingToField(begin_field_);
                    fields_.get(begin_field_).changeBorderRed(border_red_);
                    fields_.get(begin_field_).playerLeave(players_.get(player));
                }
                else if((sec+4) == wait_send_to_field_/1000){
                    fields_.get(begin_field_).changeBorderBlack();
                    scrollingToField(dest_field_);
                    fields_.get(dest_field_).changeBorderRed(border_red_);
                }
            }

            public void onFinish() {
                fields_.get(dest_field_).playerArrived(players_.get(player));
                fields_.get(dest_field_).changeBorderBlack();

                if(actionField()){
                    nextPlayer();
                    skip_player_.setVisibility(View.VISIBLE);
                    round_blocked_ = false;
                }

            }
        }.start();
    }

    public void openPlayersToDrink(ArrayList<Player> players_to_show){
        final Dialog players_to_drink = new Dialog(PolnischGame.this);
        players_to_drink.setContentView(R.layout.dialog_polnisch_game);

        Button ok_button = (Button) players_to_drink.findViewById(R.id.polnisch_dialog_button_ok);
        TextView field_description = (TextView) players_to_drink.findViewById(R.id.polnisch_dialog_description_text);

        if(field_34_){
            ok_button.setVisibility(View.INVISIBLE);
            field_description.setText(R.string.polnisch_dialog_choose_player);
        }else {
            field_description.setText(R.string.polnisch_dialog_text_players_drink);
        }

        players_to_drink.setCanceledOnTouchOutside(false);

        ArrayList<ImageView> player_icons = loadDialogImageViews(players_to_drink);
        ArrayList<TextView> player_names = loadDialogTextViews(players_to_drink);


        for(int i = 0; i < players_to_show.size(); i++){
            player_icons.get(i).setImageBitmap(players_to_show.get(i).getIcon());
            player_icons.get(i).setVisibility(View.VISIBLE);
            player_names.get(i).setText(players_to_show.get(i).getName());
            player_names.get(i).setVisibility(View.VISIBLE);


            if(field_34_){
                player_names.get(i).setClickable(true);
                player_names.get(i).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        String player_name = ((TextView) view).getText().toString();
                        field_34_ = false;
                        for(int i = 0; i < players_.size(); i++){
                            if(players_.get(i).getName().equals(player_name)){
                                players_to_drink.dismiss();
                                dest_field_ = 6;
                                sendPlayer(i);
                            }
                        }
                    }
                });
            }
        }

        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                players_to_drink.dismiss();
                if(field_36_){
                    field_36_ = false;
                    dest_field_ = 0;
                    sendPlayer(current_player_);
                }
            }
        });
        players_to_drink.show();
    }

    public ArrayList<ImageView> loadDialogImageViews(Dialog dialog){
        ArrayList<ImageView> icons = new ArrayList<ImageView>();

        icons.add((ImageView) dialog.findViewById(R.id.polnisch_dialog_icon_1));
        icons.add((ImageView) dialog.findViewById(R.id.polnisch_dialog_icon_2));
        icons.add((ImageView) dialog.findViewById(R.id.polnisch_dialog_icon_3));
        icons.add((ImageView) dialog.findViewById(R.id.polnisch_dialog_icon_4));
        icons.add((ImageView) dialog.findViewById(R.id.polnisch_dialog_icon_5));
        icons.add((ImageView) dialog.findViewById(R.id.polnisch_dialog_icon_6));
        icons.add((ImageView) dialog.findViewById(R.id.polnisch_dialog_icon_7));
        icons.add((ImageView) dialog.findViewById(R.id.polnisch_dialog_icon_8));
        icons.add((ImageView) dialog.findViewById(R.id.polnisch_dialog_icon_9));
        icons.add((ImageView) dialog.findViewById(R.id.polnisch_dialog_icon_10));

        return icons;
    }

    public ArrayList<TextView> loadDialogTextViews(Dialog dialog){
        ArrayList<TextView> player_names = new ArrayList<TextView>();

        player_names.add((TextView) dialog.findViewById(R.id.polnisch_dialog_text_1));
        player_names.add((TextView) dialog.findViewById(R.id.polnisch_dialog_text_2));
        player_names.add((TextView) dialog.findViewById(R.id.polnisch_dialog_text_3));
        player_names.add((TextView) dialog.findViewById(R.id.polnisch_dialog_text_4));
        player_names.add((TextView) dialog.findViewById(R.id.polnisch_dialog_text_5));
        player_names.add((TextView) dialog.findViewById(R.id.polnisch_dialog_text_6));
        player_names.add((TextView) dialog.findViewById(R.id.polnisch_dialog_text_7));
        player_names.add((TextView) dialog.findViewById(R.id.polnisch_dialog_text_8));
        player_names.add((TextView) dialog.findViewById(R.id.polnisch_dialog_text_9));
        player_names.add((TextView) dialog.findViewById(R.id.polnisch_dialog_text_10));

        return player_names;
    }

    public void openTwoButtonsDialog(){
        final Dialog two_buttons = new Dialog(PolnischGame.this);
        two_buttons.setContentView(R.layout.dialog_polnisch_2_buttons);
        two_buttons.setCanceledOnTouchOutside(false);

        TextView description_text = (TextView) two_buttons.findViewById(R.id.polnisch_dialog_2_buttons_description_text);
        Button left_button = (Button) two_buttons.findViewById(R.id.polnisch_dialog_2_buttons_button_left);
        Button right_button = (Button) two_buttons.findViewById(R.id.polnisch_dialog_2_buttons_button_right);

        description_text.setText(R.string.polnisch_dialog_choose_drink_or_back);

        left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                two_buttons.dismiss();
                nextPlayer();
                round_blocked_ = false;
            }
        });

        right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                two_buttons.dismiss();
                final int wait_24 = 6 * tick_time_;
                movePlayer(4, wait_24, false);
            }
        });

        two_buttons.show();


    }

    public boolean actionField(){

        switch(dest_field_){

            case 3:
                final int wait_3 = 4*tick_time_;
                movePlayer(2, wait_3, false);
                return false;

            case 5:
                dest_field_ = 32;
                sendPlayer(current_player_);
                return false;

            case 13:
                field_65_allow_dice_ = true;
                throwing_times_ = 1;
                current_player_--;
                send_start_ = true;
                break;

            case 14:
                ArrayList<Player> players_list_14 = new ArrayList<>();

                for(int i = start_field_-1; i < last_field_; i++ ){
                    if(!fields_.get(i).getPlayers().isEmpty()){
                        players_list_14.addAll(fields_.get(i).getPlayers());
                       break;
                    }
                }

                for(int i = last_field_-1; i >= start_field_ - 1 ; i-- ){
                    if(!fields_.get(i).getPlayers().isEmpty()){
                        players_list_14.addAll(fields_.get(i).getPlayers());
                        break;
                    }
                }
                openPlayersToDrink(players_list_14);
                break;

            case 17:
                players_.get(current_player_).setPause(true);
                break;

            case 20:
                field_65_allow_dice_ = true;
                current_player_--;
                break;

            case 21:
                dest_field_ = 0;
                sendPlayer(current_player_);
                return false;

            case 24:
                openTwoButtonsDialog();
                return false;

            case 29:
                dest_field_ = 9;
                sendPlayer(current_player_);
                return false;

            case 31:
                field_65_allow_dice_ = true;
                throwing_times_ = 1;
                current_player_--;
                break;

            case 34:
                field_34_ = true;
                openPlayersToDrink(players_);
                return false;

            case 35:
                dest_field_ = 6;
                sendPlayer(current_player_);
                return false;

            case 36:
                ArrayList<Player> players_list_36 = new ArrayList<>();

                for(int i = start_field_-1; i < last_field_; i++ ){
                    if(!fields_.get(i).getPlayers().isEmpty()){
                        players_list_36.addAll(fields_.get(i).getPlayers());
                        break;
                    }
                }
                field_36_ = true;
                openPlayersToDrink(players_list_36);
                return false;

            case 37:
                ArrayList<Player> players_list_37 = new ArrayList<>();
                for(int i = 0; i < players_.size(); i++)
                {
                    if(players_.get(i).getField() > 38){
                        players_list_37.add(players_.get(i));
                    }
                }

                if(!players_list_37.isEmpty()){
                    openPlayersToDrink(players_list_37);
                }
                break;

            case 42:
                field_65_allow_dice_ = true;
                throwing_times_ = players_.size();
                break;

            case 45:
                final int wait_45 = 4*tick_time_;
                movePlayer(2, wait_45, true);
                return false;

            case 46:
                field_65_allow_dice_ = true;
                throwing_times_ = 1;
                current_player_--;
                break;

            case 49:
                field_65_allow_dice_ = true;
                dest_field_ = 28;
                int same_player = current_player_;
                current_player_--;
                sendPlayer(same_player);
                return false;

            case 55:
                field_65_allow_dice_ = true;
                throwing_times_ = players_.size();
                break;

            case 56:
                dest_field_ = 18;
                sendPlayer(current_player_);
                return false;

            case 59:
                field_65_allow_dice_ = true;
                throwing_times_ = players_.size();
                send_start_ = true;
                field_59_ = true;
                break;

            case 62:
                field_65_allow_dice_ = true;
                throwing_times_ = 3;
                current_player_--;
                field_62_69_ = true;
                break;

            case 65:
                field_65_ = true;
                field_65_allow_dice_ = false;
                skip_player_.setText(getResources().getString(R.string.polnisch_next));
                field_65_player_count_ = 1;
                dest_field_ = players_.get(current_player_).getField() - 2;
                if(dest_field_ < 0 ){
                    dest_field_ = 0;
                }
                sendPlayer(current_player_);
                return false;

            case 66:
                dest_field_ = 32;
                sendPlayer(current_player_);
                return false;

            case 69:
                field_65_allow_dice_ = true;
                throwing_times_ = 5;
                current_player_--;
                field_62_69_ = true;
                break;

            case 70:
                dest_field_ = 0;
                sendPlayer(current_player_);
                return false;

            default:
        }
        return true;
    }
}
