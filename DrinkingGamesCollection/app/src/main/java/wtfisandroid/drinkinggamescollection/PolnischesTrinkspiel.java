package wtfisandroid.drinkinggamescollection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class PolnischesTrinkspiel extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<ImageView> view_image;
    private ArrayList<Bitmap> all_icons;
    private ArrayList<Integer> used_icons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polnisches_trinkspiel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        view_image = new ArrayList<ImageView>();
        all_icons = new ArrayList<Bitmap>();
        used_icons = new ArrayList<Integer>();
        loadIcons();
        loadImageViews();
        initIcons();

        ((Button) findViewById(R.id.polnisch_button_start)).setOnClickListener(this);
        ((Button) findViewById(R.id.polnisch_button_plus)).setOnClickListener(this);
        ((Button) findViewById(R.id.polnisch_button_minus)).setOnClickListener(this);
        ((Button) findViewById(R.id.polnisch_button_help)).setOnClickListener(this);

    }


    public void loadIcons() {
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
    }

    public void loadImageViews() {
        view_image.add(((ImageView) findViewById(R.id.polnisch_image_player_1)));
        view_image.add(((ImageView) findViewById(R.id.polnisch_image_player_2)));
        view_image.add(((ImageView) findViewById(R.id.polnisch_image_player_3)));
        view_image.add(((ImageView) findViewById(R.id.polnisch_image_player_4)));
        view_image.add(((ImageView) findViewById(R.id.polnisch_image_player_5)));
        view_image.add(((ImageView) findViewById(R.id.polnisch_image_player_6)));
        view_image.add(((ImageView) findViewById(R.id.polnisch_image_player_7)));
        view_image.add(((ImageView) findViewById(R.id.polnisch_image_player_8)));
        view_image.add(((ImageView) findViewById(R.id.polnisch_image_player_9)));
        view_image.add(((ImageView) findViewById(R.id.polnisch_image_player_10)));
    }

    public void initIcons() {

        for(int i = 0; i < 10; i++) {
            view_image.get(i).setImageBitmap(all_icons.get(i));
            used_icons.add(i);
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.polnisch_button_help:
                break;

            case R.id.polnisch_button_minus:
                break;

            case R.id.polnisch_button_plus:
                break;

            case R.id.polnisch_button_start:
                break;

            default:
        }
    }

    public void arrowClicked(View v) {

        Button butt = (Button) v;
        String[] arrows = butt.getText().toString().split("_");
        nextIcon(((arrows[0].equals("l"))), Integer.parseInt(arrows[1])-1);
    }

    public void nextIcon(boolean dircetion, int index) {

        int bitmap_position = used_icons.get(index);
        boolean found = true;

        if(dircetion) {
            while(found) {

                if(bitmap_position == 0) {
                    bitmap_position = all_icons.size() - 1;
                }
                else {
                    bitmap_position--;
                }

                if(used_icons.contains(bitmap_position)) {
                    continue;
                }

                found = false;
            }
        }
        else {
            while(found) {

                if(bitmap_position == (all_icons.size()-1)) {
                    bitmap_position = 0;
                }
                else {
                    bitmap_position++;
                }

                if(used_icons.contains(bitmap_position)) {
                    continue;
                }

                found = false;
            }
        }

        view_image.get(index).setImageBitmap(all_icons.get(bitmap_position));
        used_icons.set(index, bitmap_position);

    }

}
