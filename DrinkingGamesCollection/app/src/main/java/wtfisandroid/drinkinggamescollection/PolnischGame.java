package wtfisandroid.drinkinggamescollection;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import wtfisandroid.drinkinggamescollection.features.Player;

public class PolnischGame extends AppCompatActivity {

    private ArrayList<Player> players_;
    private static int start_field_ = 0;
    private int current_player_;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polnisch_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        current_player_ = 0;
        players_ = new ArrayList<Player>();
        createPlayers(this.getIntent().getStringArrayListExtra("players_name"), this.getIntent().getIntegerArrayListExtra("icons"));
        
        ((TextView) findViewById(R.id.polnisch_text_field_active_player)).setText(players_.get(current_player_).getName());
        ((ImageView) findViewById(R.id.polnisch_image_active_player_icon)).setImageBitmap(players_.get(current_player_).getIcon());
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




}
