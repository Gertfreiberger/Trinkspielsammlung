package wtfisandroid.drinkinggamescollection.gui.game.pyramid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;

import wtfisandroid.drinkinggamescollection.R;
import wtfisandroid.drinkinggamescollection.data.Gamecard;
import wtfisandroid.drinkinggamescollection.data.Playerhand;
import wtfisandroid.drinkinggamescollection.logic.Utilities;

public class PyramidActivity extends AppCompatActivity {

    private static final String TAG = "pyramidactivity";

    private SharedPreferences sharedPref;
    private Utilities utilities;
    private Gamecard gameCard = new Gamecard();
    private HashMap<Integer, Gamecard> gameDeck;
    private Resources resources;
    private ImageView first_choice;
    private Vibrator vib;
    private TextView textview_round;
    private TextView textview_player;
    private String player1_name;
    private String player2_name;
    private String player3_name;
    private String player4_name;
    private TextView textview_player1_name;
    private TextView textview_player2_name;
    private TextView textview_player3_name;
    private TextView textview_player4_name;
    private int round_number = 1;
    private int player_number = 0;
    private int current_card_number = 0;
    private HashMap<String, String> player_names = new HashMap<>();
    private HashMap<String, ImageView> player_cards = new HashMap<>();
    private String[] rounds;
    private ImageView second_choice;
    private Gamecard current_card;
    private ImageView player1_card1;
    private ImageView player1_card2;
    private ImageView player1_card3;
    private ImageView player1_card4;
    private ImageView player2_card1;
    private ImageView player2_card2;
    private ImageView player2_card3;
    private ImageView player2_card4;
    private ImageView player3_card1;
    private ImageView player3_card2;
    private ImageView player3_card3;
    private ImageView player3_card4;
    private ImageView player4_card1;
    private ImageView player4_card2;
    private ImageView player4_card3;
    private ImageView player4_card4;
    private Playerhand player1_hand;
    private Playerhand player2_hand;
    private Playerhand player3_hand;
    private Playerhand player4_hand;
    private HashMap<String, Playerhand> player_hands = new HashMap<>();
    private Playerhand current_player_hand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        utilities = new Utilities(getApplicationContext());
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String currentLanguage = sharedPref.getString(Utilities.LANGUAGE_PREFERENCE_KEY, Locale.getDefault().getDisplayLanguage());
        utilities.setLanguage(currentLanguage);
        resources = getResources();
        setContentView(R.layout.content_pyramid_activity);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setItems(R.array.pyramid_rounds, null)
                .setIcon(ResourcesCompat.getDrawable(resources, R.drawable.ic_logo, null))
                .setTitle(R.string.pyramid_dialog_title)
                .setPositiveButton(R.string.start, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startTheGame();
                    }
                })
                .setNegativeButton(R.string.back, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

        findViews();

        player1_name = sharedPref.getString("player_name1", "Player1");
        player2_name = sharedPref.getString("player_name2", "Player2");
        player3_name = sharedPref.getString("player_name3", "Player3");
        player4_name = sharedPref.getString("player_name4", "Player4");

        textview_player1_name.setText(player1_name);
        textview_player2_name.setText(player2_name);
        textview_player3_name.setText(player3_name);
        textview_player4_name.setText(player4_name);

        player_names.put("Player1", player1_name);
        player_names.put("Player2", player2_name);
        player_names.put("Player3", player3_name);
        player_names.put("Player4", player4_name);

        player1_hand = new Playerhand(1, player1_name);
        player2_hand = new Playerhand(2, player2_name);
        player3_hand = new Playerhand(3, player3_name);
        player4_hand = new Playerhand(4, player4_name);

        player_hands.put("Player1", player1_hand);
        player_hands.put("Player2", player2_hand);
        player_hands.put("Player3", player3_hand);
        player_hands.put("Player4", player4_hand);

        rounds = resources.getStringArray(R.array.pyramid_rounds);

        if ( !sharedPref.getBoolean(Utilities.SHOW_NAMES_PREFERENCE_KEY, true) ) {

            utilities.fadeOut(textview_player1_name);
            utilities.fadeOut(textview_player2_name);
            utilities.fadeOut(textview_player3_name);
            utilities.fadeOut(textview_player4_name);
        }

        // Hide the status bar.
        if ( Build.VERSION.SDK_INT < 16 ) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else { // Jellybean and up
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            // Remember that you should never show the action bar if the
            // status bar is hidden, so hide that too if necessary.
            ActionBar actionBar = getSupportActionBar();
            if ( actionBar != null ) {
                actionBar.hide();
            }
        }

        gameCard.generatePyramidGameDeck();
        gameDeck = Gamecard.getAllCards();
        gameDeck = gameCard.shuffleDeck(gameDeck);

        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
            Slide slide = new Slide();
            slide.setDuration(2000);
            getWindow().setExitTransition(slide);
        }
    }

    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.
     * <p/>
     * <p>This is only called once, the first time the options menu is
     * displayed.  To update the menu every time it is displayed, see
     * {@link #onPrepareOptionsMenu}.
     * <p/>
     * <p>The default implementation populates the menu with standard system
     * menu items.  These are placed in the {@link Menu#CATEGORY_SYSTEM} group so that
     * they will be correctly ordered with application-defined menu items.
     * Deriving classes should always call through to the base implementation.
     * <p/>
     * <p>You can safely hold on to <var>menu</var> (and any items created
     * from it), making modifications to it as desired, until the next
     * time onCreateOptionsMenu() is called.
     * <p/>
     * <p>When you add items to the menu, you can implement the Activity's
     * {@link #onOptionsItemSelected} method to handle them there.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_pyramid, menu);
        return true;
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     * <p/>
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.</p>
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if ( sharedPref.getBoolean(Utilities.SOUND_PREFERENCE_KEY, false) ) {
            utilities.playSound(1);
        }
        switch ( item.getItemId() ) {
            case R.id.new_game:
                break;
            case R.id.back:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        if ( sharedPref.getBoolean(Utilities.SOUND_PREFERENCE_KEY, false) ) {
            utilities.playSound(1, AudioManager.FX_KEYPRESS_RETURN);
        }
        super.onBackPressed();
    }

    private void findViews() {
        textview_round = (TextView) findViewById(R.id.tvRound);
        textview_player = (TextView) findViewById(R.id.tvPlayer);

        textview_player1_name = (TextView) findViewById(R.id.tvPlayer1);
        textview_player2_name = (TextView) findViewById(R.id.tvPlayer2);
        textview_player3_name = (TextView) findViewById(R.id.tvPlayer3);
        textview_player4_name = (TextView) findViewById(R.id.tvPlayer4);

        first_choice = (ImageView) findViewById(R.id.ivFirstChoice);
        second_choice = (ImageView) findViewById(R.id.ivSecondChoice);

        player1_card1 = (ImageView) findViewById(R.id.ivPlayer1FirstCard);
        player1_card2 = (ImageView) findViewById(R.id.ivPlayer1SecondCard);
        player1_card3 = (ImageView) findViewById(R.id.ivPlayer1ThirdCard);
        player1_card4 = (ImageView) findViewById(R.id.ivPlayer1FourthCard);
        player2_card1 = (ImageView) findViewById(R.id.ivPlayer2FirstCard);
        player2_card2 = (ImageView) findViewById(R.id.ivPlayer2SecondCard);
        player2_card3 = (ImageView) findViewById(R.id.ivPlayer2ThirdCard);
        player2_card4 = (ImageView) findViewById(R.id.ivPlayer2FourthCard);
        player3_card1 = (ImageView) findViewById(R.id.ivPlayer3FirstCard);
        player3_card2 = (ImageView) findViewById(R.id.ivPlayer3SecondCard);
        player3_card3 = (ImageView) findViewById(R.id.ivPlayer3ThirdCard);
        player3_card4 = (ImageView) findViewById(R.id.ivPlayer3FourthCard);
        player4_card1 = (ImageView) findViewById(R.id.ivPlayer4FirstCard);
        player4_card2 = (ImageView) findViewById(R.id.ivPlayer4SecondCard);
        player4_card3 = (ImageView) findViewById(R.id.ivPlayer4ThirdCard);
        player4_card4 = (ImageView) findViewById(R.id.ivPlayer4FourthCard);

        player_cards.put("Player1Card1", player1_card1);
        player_cards.put("Player1Card2", player1_card2);
        player_cards.put("Player1Card3", player1_card3);
        player_cards.put("Player1Card4", player1_card4);
        player_cards.put("Player2Card1", player2_card1);
        player_cards.put("Player2Card2", player2_card2);
        player_cards.put("Player2Card3", player2_card3);
        player_cards.put("Player2Card4", player2_card4);
        player_cards.put("Player3Card1", player3_card1);
        player_cards.put("Player3Card2", player3_card2);
        player_cards.put("Player3Card3", player3_card3);
        player_cards.put("Player3Card4", player3_card4);
        player_cards.put("Player4Card1", player4_card1);
        player_cards.put("Player4Card2", player4_card2);
        player_cards.put("Player4Card3", player4_card3);
        player_cards.put("Player4Card4", player4_card4);

        utilities.fadeOut(first_choice);
    }

    private void startTheGame() {
        if ( sharedPref.getBoolean(Utilities.SOUND_PREFERENCE_KEY, false) )
            utilities.playSound(1);

        if ( sharedPref.getBoolean(Utilities.VIBRATE_PREFERENCE_KEY, false) ) {
            vib = (Vibrator) PyramidActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
            vib.vibrate(500);
        }

        utilities.fadeIn(first_choice);
        utilities.fadeIn(second_choice);
        utilities.fadeOut(player1_card1);
        utilities.fadeOut(player1_card2);
        utilities.fadeOut(player1_card3);
        utilities.fadeOut(player1_card4);
        utilities.fadeOut(player2_card1);
        utilities.fadeOut(player2_card2);
        utilities.fadeOut(player2_card3);
        utilities.fadeOut(player2_card4);
        utilities.fadeOut(player3_card1);
        utilities.fadeOut(player3_card2);
        utilities.fadeOut(player3_card3);
        utilities.fadeOut(player3_card4);
        utilities.fadeOut(player4_card1);
        utilities.fadeOut(player4_card2);
        utilities.fadeOut(player4_card3);
        utilities.fadeOut(player4_card4);

        execute_round();

    }

    private void execute_round() {
        if ( player_number >= 4 ) {
            round_number++;
            player_number = 1;
            if ( round_number > 4 ) {
                goToNextLevel();
                return;
            }

        } else
            player_number++;

        switch ( round_number ) {
            case 1:
                first_choice.setImageResource(R.drawable.ic_round_1_first_choice);
                second_choice.setImageResource(R.drawable.ic_round_1_second_choice);
                break;
            case 2:
                first_choice.setImageResource(R.drawable.ic_round_2_first_choice);
                second_choice.setImageResource(R.drawable.ic_round_2_second_choice);
                break;
            case 3:
                first_choice.setImageResource(R.drawable.ic_round_3_first_choice);
                second_choice.setImageResource(R.drawable.ic_round_3_second_choice);
                break;
            case 4:
                first_choice.setImageResource(R.drawable.ic_round_4_first_choice);
                second_choice.setImageResource(R.drawable.ic_round_4_second_choice);
                break;
        }

        String player_name = player_names.get("Player" + player_number);
        if ( textview_player != null ) {
            textview_player.setText(resources.getString(R.string.player) + ": " + player_name);
        }
        if ( textview_round != null ) {
            textview_round.setText(rounds[round_number - 1]);
        }
        Log.d(TAG, "execute_round() called with: " + gameDeck);
        current_card = gameDeck.get(current_card_number);
        Log.d(TAG, "" + current_card_number);
        current_card_number++;
        current_player_hand = player_hands.get("Player" + player_number);
    }

    public void onClickFirstChoice(View v) {
        if ( sharedPref.getBoolean(Utilities.SOUND_PREFERENCE_KEY, false) ) {
            utilities.playSound(1);
        }
        Gamecard first_card;
        Gamecard second_card;
        Gamecard third_card;
        switch ( round_number ) {
            case 1:
                String color = current_card.getCardColor();
                if ( !color.equalsIgnoreCase(Gamecard.SPADES) && !color.equalsIgnoreCase(Gamecard.CLUBS) )
                    drink();
                break;
            case 2:
                first_card = current_player_hand.getPlayerCards().get(0);
                if ( current_card.getCardValue() <= first_card.getCardValue() )
                    drink();
                break;
            case 3:
                int current_card_value = current_card.getCardValue();
                first_card = current_player_hand.getPlayerCards().get(0);
                int first_card_value = first_card.getCardValue();
                second_card = current_player_hand.getPlayerCards().get(1);
                int second_card_value = second_card.getCardValue();

                if ( current_card_value < first_card_value && current_card_value > second_card_value || current_card_value > first_card_value && current_card_value < second_card_value )
                    drink();
                break;
            case 4:
                String current_card_color = current_card.getCardColor();
                first_card = current_player_hand.getPlayerCards().get(0);
                String first_card_color = first_card.getCardColor();
                second_card = current_player_hand.getPlayerCards().get(1);
                String second_card_color = second_card.getCardColor();
                third_card = current_player_hand.getPlayerCards().get(2);
                String third_card_color = third_card.getCardColor();

                if ( !current_card_color.equalsIgnoreCase(first_card_color) && !current_card_color.equalsIgnoreCase(second_card_color) && !current_card_color.equalsIgnoreCase(third_card_color) )
                    drink();
                break;
            default:
                break;
        }
        setCard();
    }

    public void onClickSecondChoice(View v) {
        if ( sharedPref.getBoolean(Utilities.SOUND_PREFERENCE_KEY, false) ) {
            utilities.playSound(1);
        }
        Gamecard first_card;
        Gamecard second_card;
        Gamecard third_card;
        switch ( round_number ) {
            case 1:
                String color = current_card.getCardColor();
                if ( !color.equalsIgnoreCase(Gamecard.HEARTS) && !color.equalsIgnoreCase(Gamecard.DIAMONDS) )
                    drink();
                break;
            case 2:
                first_card = current_player_hand.getPlayerCards().get(0);
                if ( current_card.getCardValue() >= first_card.getCardValue() )
                    drink();
                break;
            case 3:
                int current_card_value = current_card.getCardValue();
                first_card = current_player_hand.getPlayerCards().get(0);
                int first_card_value = first_card.getCardValue();
                second_card = current_player_hand.getPlayerCards().get(1);
                int second_card_value = second_card.getCardValue();

                if ( current_card_value > first_card_value && current_card_value < second_card_value || current_card_value < first_card_value && current_card_value > second_card_value )
                    drink();
                break;
            case 4:
                String current_card_color = current_card.getCardColor();
                first_card = current_player_hand.getPlayerCards().get(0);
                String first_card_color = first_card.getCardColor();
                second_card = current_player_hand.getPlayerCards().get(1);
                String second_card_color = second_card.getCardColor();
                third_card = current_player_hand.getPlayerCards().get(2);
                String third_card_color = third_card.getCardColor();

                if ( current_card_color.equalsIgnoreCase(first_card_color) || current_card_color.equalsIgnoreCase(second_card_color) || current_card_color.equalsIgnoreCase(third_card_color) )
                    drink();
                break;
            default:
                break;
        }

        setCard();
    }

    private void setCard() {
        Log.d(TAG, "onClickSecondChoice() called with: " + "v = [" + "Player" + player_number + "Card" + round_number + "]");
        ImageView card = player_cards.get("Player" + player_number + "Card" + round_number);
        if ( card != null ) {
            utilities.fadeIn(card);
            card.setImageResource(current_card.getImageID());
            current_player_hand.addCard(current_card);
        }
        execute_round();
    }

    private void drink() {
        if ( sharedPref.getBoolean(Utilities.VIBRATE_PREFERENCE_KEY, false) ) {
            vib = (Vibrator) PyramidActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
            vib.vibrate(1000);
        }
        Toast.makeText(getApplicationContext(), "Drink!!!!!!!!!!!!!!!! ;)", Toast.LENGTH_SHORT).show();
    }

    private void goToNextLevel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setIcon(ResourcesCompat.getDrawable(resources, R.drawable.ic_logo, null))
                .setTitle(R.string.first_round_finished)
                .setNeutralButton(R.string.go_to_next_level, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(), PyramidSecondRoundActivity.class);

                        intent.putExtra("gameDeck", gameDeck);
                        intent.putExtra("playerHands", player_hands);
                        startActivity(intent);
                        finish();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

