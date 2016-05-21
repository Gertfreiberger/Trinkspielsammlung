package wtfisandroid.drinkinggamescollection;

import android.content.Intent;
import android.graphics.Bitmap;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polnisch_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        players_ = new ArrayList<Player>();
        ArrayList<Bitmap> icons = this.getIntent().getParcelableArrayListExtra("icons");
        createPlayers(this.getIntent().getStringArrayListExtra("players_name"), icons);
        
        ((TextView) findViewById(R.id.textView)).setText(players_.get(0).getName());
        ((ImageView) findViewById(R.id.imageView)).setImageBitmap(players_.get(0).getIcon());
    }

    public void createPlayers(ArrayList<String> names, ArrayList<Bitmap> icons) {
        for(int i = 0; i< names.size(); i++) {
            players_.add(new Player(names.get(i)));
            players_.get(i).setField(start_field_);
            players_.get(i).setIcon(icons.get(i));
        }
    }
}
