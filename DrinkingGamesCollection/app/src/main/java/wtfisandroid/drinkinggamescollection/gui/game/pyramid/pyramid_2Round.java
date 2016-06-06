package wtfisandroid.drinkinggamescollection.gui.game.pyramid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Vector;

import wtfisandroid.drinkinggamescollection.R;
import wtfisandroid.drinkinggamescollection.data.Gamecard;
import wtfisandroid.drinkinggamescollection.data.Playerhand;

public class pyramid_2Round extends AppCompatActivity {

    private ImageView view;
    private int pyramidRow = 1;
    private int currentPlayer = 1;

    private Animation anim_slide;
    private Playerhand playerhand;
    private Gamecard gameCard = new Gamecard();
    private HashMap<Integer, Gamecard> gameDeck;

    private Vector<ImageView> views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pyramid_2_round);
        Toast.makeText(getApplicationContext(), "Willkommen in der zweiten Runde", Toast.LENGTH_SHORT).show();

        gameCard.generatePyramidGameDeck();
        gameDeck = Gamecard.getAllCards();
        gameDeck = gameCard.shuffleDeck(gameDeck);

        anim_slide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide);
        views = new Vector<ImageView>();
        setPyramidCards();

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        //if (hasFocus)
           //startAnimation.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void setPyramidCards() {

        int id;
        int index = 0;
        getPyramidViews();
        for (; index < views.size(); index++) {
            id = gameDeck.get(index).getImageID();
            views.elementAt(index).startAnimation(anim_slide);
            views.elementAt(index).setBackgroundResource(id);
        }
        //stop all animations
    }

    public void getPyramidViews() {

        int id;
        int index = 1;
        String name;
        try {
            for (; index < 16; index++) {
                name = "ivPyramide" + index;
                id = R.id.class.getField(name).getInt(null);
                view = (ImageView) findViewById(id);
                views.add(view);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public void showPyramidCard() {
        int index = 14;
        int cardValue = 0;
        for (; index > 0; index--) {
            //id = gameDeck.get(index).getImageID();
            cardValue = gameDeck.get(index).getCardValue();
            views.elementAt(index).setBackgroundResource(R.drawable.gamecard_2_of_clubs);
            checkPlayerHand(cardValue);
        }
    }

    public void nextPlayer() {

    }

    public void checkPlayerHand(int cardValue) {
        int index = 0;
        for (; index > 4; index++) {
            //if ()
        }
    }
}