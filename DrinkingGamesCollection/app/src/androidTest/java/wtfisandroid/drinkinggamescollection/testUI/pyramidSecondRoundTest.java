package wtfisandroid.drinkinggamescollection.testUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.TextView;

import com.robotium.solo.Solo;

import java.util.HashMap;
import java.util.Random;

import wtfisandroid.drinkinggamescollection.R;
import wtfisandroid.drinkinggamescollection.data.Gamecard;
import wtfisandroid.drinkinggamescollection.data.PlayerHand;
import wtfisandroid.drinkinggamescollection.gui.game.pyramid.PyramidSecondRound;
import wtfisandroid.drinkinggamescollection.logic.Utilities;

/**
 * Created by Superuser on 03.06.2016.
 */
public class pyramidSecondRoundTest extends ActivityInstrumentationTestCase2<PyramidSecondRound> {

    private static final String TAG = "TEST";

    private Solo solo;

    /* Game Deck and Player Hand */
    private Gamecard gameCard;
    private static HashMap<Integer, Gamecard> gameDeck;
    private static HashMap<String, PlayerHand> playerHands = new HashMap<>();

    private Integer playerCount;
    private SharedPreferences sharedPref;

    public pyramidSecondRoundTest() {
        super(PyramidSecondRound.class);
    }

    public void setUp() throws Exception {

        super.setUp();

        /* getting Player count from shared preferences */
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getInstrumentation().getTargetContext());
        playerCount = Integer.valueOf(sharedPref.getString(Utilities.PYRAMID_PLAYER_COUNT_PREFERENCE_KEY, "4"));

        /* generate Game Deck */
        gameCard = new Gamecard();
        gameCard.generatePyramidGameDeck();
        gameDeck = Gamecard.getAllCards();
        gameDeck = gameCard.shuffleDeck(gameDeck);

        /* generate new PlayerHand(s) to the number of player */
        int temp = 0;
        String name;
        for (int i = 0; i < playerCount; i++) {
            name = "Fritz_" + i;
            playerHands.put(Utilities.KEY_PLAYER + (i+1), new PlayerHand((i+1), name));

            playerHands.get(Utilities.KEY_PLAYER + (i+1)).addCard(gameDeck.get(i+temp));
            temp++;
            playerHands.get(Utilities.KEY_PLAYER + (i+1)).addCard(gameDeck.get(i+temp));
            temp++;
            playerHands.get(Utilities.KEY_PLAYER + (i+1)).addCard(gameDeck.get(i+temp));
            temp++;
            playerHands.get(Utilities.KEY_PLAYER + (i+1)).addCard(gameDeck.get(i+temp));
            temp++;
        }

        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public PyramidSecondRound getActivity() {
        /* set extra for activity */
        Intent intent = new Intent();
        intent.putExtra(Utilities.GAMEDECK_GAME_KEY, gameDeck);
        intent.putExtra(Utilities.PLAYERHANDS_GAME_KEY, playerHands);
        setActivityIntent(intent);
        return super.getActivity();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    /* Go through all 15 pyramid cards and ignore matching */

    public void testThroughPyramidCards() throws Exception {
        int id;
        String name;
        for ( int i = 0; i < 15; i++ ) {
            name = "ivPyramide" + i;
            try {
                id = R.id.class.getField(name).getInt(null);
                solo.clickOnView(solo.getView(id));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            solo.sleep(1000);
        }
    }

    /* Go through all 15 pyramid cards and if matching distribute drinks automatically */

    public void testThroughPyramidWithDeal() throws Exception {
        int id;
        String name;
        for ( int i = 0; i < 15; i++ ) {
            name = "ivPyramide" + i;
            try {
                id = R.id.class.getField(name).getInt(null);
                solo.clickOnView(solo.getView(id));
                while (solo.getView(R.id.bDeal).isClickable())
                    solo.clickOnView(solo.getView(R.id.bDeal));
                solo.sleep(500);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }

     /* Go through all 15 pyramid cards and if matching go ahead without distributing drinks automatically */

    public void testThroughPyramidWithNexxt() throws Exception {
        int id;
        String name;
        for ( int i = 0; i < 15; i++ ) {
            name = "ivPyramide" + i;
            try {
                id = R.id.class.getField(name).getInt(null);
                solo.clickOnView(solo.getView(id));
                while (solo.getView(R.id.bNext).isClickable())
                    solo.clickOnView(solo.getView(R.id.bNext));
                solo.sleep(500);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }


    /* Go through all 15 pyramid cards and if matching randomly distribute drinks or go ahead */

    public void testRandomPyramid() throws Exception {
        int x;
        int id;
        String name;
        Random rand = new Random();
        for ( int i = 1; i < 15; i++ ) {
            x = rand.nextInt(2);
            name = "ivPyramide" + i;
            try {
                id = R.id.class.getField(name).getInt(null);
                solo.clickOnView(solo.getView(id));
                switch(x) {
                    case 0: while (solo.getView(R.id.bDeal).isClickable())
                                solo.clickOnView(solo.getView(R.id.bDeal));
                            break;

                    case 1: while (solo.getView(R.id.bNext).isClickable())
                                solo.clickOnView(solo.getView(R.id.bNext));
                            break;

                    default: break;
                }
                solo.sleep(500);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }

    /* If first player card matches distribute drinks to the other players otherwise do nothing */

    public void testFirstGetsAllDrunk() throws Exception {
        int id;
        String name;
        TextView text = (TextView) solo.getView(R.id.PlayerHand);
        for ( int i = 0; i < 15; i++ ) {
            name = "ivPyramide" + i;
            try {
                id = R.id.class.getField(name).getInt(null);
                solo.clickOnView(solo.getView(id));
                while (solo.getView(R.id.bDeal).isClickable()) {
                    if (text.getText().toString().contains(playerHands.get(Utilities.KEY_PLAYER + 1).getPlayerName()))
                        solo.clickOnView(solo.getView(R.id.bDeal));
                    else
                        solo.clickOnView(solo.getView(R.id.bNext));
                }
                solo.sleep(500);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }

    /* If last player card matches distribute drinks to the other players otherwise do nothing */

    public void testLastGetsAllDrunk() throws Exception {
        int id;
        String name;
        TextView text = (TextView) solo.getView(R.id.PlayerHand);
        for ( int i = 0; i < 15; i++ ) {
            name = "ivPyramide" + i;
            try {
                id = R.id.class.getField(name).getInt(null);
                solo.clickOnView(solo.getView(id));
                while (solo.getView(R.id.bDeal).isClickable()) {
                    if (text.getText().toString().contains(playerHands.get(Utilities.KEY_PLAYER + playerCount).getPlayerName()))
                        solo.clickOnView(solo.getView(R.id.bDeal));
                    else
                        solo.clickOnView(solo.getView(R.id.bNext));
                }
                solo.sleep(500);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }
}