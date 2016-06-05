package wtfisandroid.drinkinggamescollection.gui.game.pyramid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import wtfisandroid.drinkinggamescollection.R;
import wtfisandroid.drinkinggamescollection.data.Gamecard;
import wtfisandroid.drinkinggamescollection.data.Playerhand;
import wtfisandroid.drinkinggamescollection.logic.Utilities;

public class PyramidActivity extends AppCompatActivity {

	private static final String TAG = "pyramidactivity";
	private static final String KEY_PLAYER = "PlayerCard";
	private static final String KEY_PLAYERCARD = "Player";

	private SharedPreferences sharedPref;
	private Utilities utilities;
	private Gamecard gameCard = new Gamecard();
	private HashMap<Integer, Gamecard> gameDeck;
	private Resources resources;
	private ImageView firstChoice;
	private Vibrator vib;
	private int roundNumber = 1;
	private int playerCount;
	private int currentPlayerNumber = 0;
	private int currentCardNumber = 0;
	private HashMap<String, String> playerNames = new HashMap<>();
	private HashMap<String, ImageView> playerCards = new HashMap<>();
	private String[] rounds;
	private ImageView secondChoice;
	private Gamecard currentCard;
	private HashMap<String, Playerhand> playerHands = new HashMap<>();
	private Playerhand playerHand;
	private Toolbar toolbar;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Hide the status bar.
//		if ( Build.VERSION.SDK_INT < 16 ) {
//			getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//		} else { // Jellybean and up
//			View decorView = getWindow().getDecorView();
//			int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
//			decorView.setSystemUiVisibility(uiOptions);
//		}
		utilities = new Utilities(getApplicationContext());
		sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String currentLanguage = sharedPref.getString(Utilities.LANGUAGE_PREFERENCE_KEY, Locale.getDefault().getDisplayLanguage());
		utilities.setLanguage(currentLanguage);
		resources = getResources();
		setContentView(R.layout.activity_pyramid);
//		setContentView(R.layout.content_pyramid_activity);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setItems(R.array.pyramid_rounds, null)
						.setIcon(ResourcesCompat.getDrawable(resources, R.drawable.ic_logo, null))
						.setTitle(R.string.pyramid_dialog_title)
						.setOnCancelListener(new DialogInterface.OnCancelListener() {
							@Override
							public void onCancel(DialogInterface dialog) {
								onBackPressed();
							}
						})
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
		dialog.setCanceledOnTouchOutside(false);

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		toolbar.setBackgroundResource(R.drawable.ic_background_gameroom);
		toolbar.setLogo(R.drawable.ic_logo);
		toolbar.setTitleTextColor(Color.BLACK);
		toolbar.setSubtitleTextColor(Color.BLUE);

		prepareGame();

		if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
			Slide slide = new Slide();
			slide.setDuration(2000);
			getWindow().setExitTransition(slide);
		}

		dialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_pyramid, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if ( sharedPref.getBoolean(Utilities.SOUND_PREFERENCE_KEY, false) ) {
			utilities.playSound(1);
		}
		switch ( item.getItemId() ) {
			case R.id.new_game:
				recreate();
				break;
			case R.id.back:
				finish();
				break;
			case android.R.id.home:
				Toast.makeText(getApplicationContext(), toolbar.getTitle() + "; " + toolbar.getSubtitle(), Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		if ( sharedPref.getBoolean(Utilities.SOUND_PREFERENCE_KEY, false) ) {
			utilities.playSound(1, AudioManager.FX_KEYPRESS_RETURN);
		}
		super.onBackPressed();
	}

	private void prepareGame() {

		firstChoice = (ImageView) findViewById(R.id.ivFirstChoice);
		secondChoice = (ImageView) findViewById(R.id.ivSecondChoice);

		ImageView playerCard1 = (ImageView) findViewById(R.id.ivPyramidFirstRoundPlayerCard1);
		ImageView playerCard2 = (ImageView) findViewById(R.id.ivPyramidFirstRoundPlayerCard2);
		ImageView playerCard3 = (ImageView) findViewById(R.id.ivPyramidFirstRoundPlayerCard3);
		ImageView playerCard4 = (ImageView) findViewById(R.id.ivPyramidFirstRoundPlayerCard4);

		playerCards.put(KEY_PLAYERCARD + "1", playerCard1);
		playerCards.put(KEY_PLAYERCARD + "2", playerCard2);
		playerCards.put(KEY_PLAYERCARD + "3", playerCard3);
		playerCards.put(KEY_PLAYERCARD + "4", playerCard4);

		for ( Map.Entry<String, ImageView> entry : playerCards.entrySet() ) {
			ImageView value = entry.getValue();
			utilities.fadeOut(value);
		}

		String player1_name = sharedPref.getString(Utilities.PYRAMID_PLAYER_NAME_PREFERENCE_KEY + "1", KEY_PLAYER + "1");
		String player2_name = sharedPref.getString(Utilities.PYRAMID_PLAYER_NAME_PREFERENCE_KEY + "2", KEY_PLAYER + "2");
		String player3_name = sharedPref.getString(Utilities.PYRAMID_PLAYER_NAME_PREFERENCE_KEY + "3", KEY_PLAYER + "3");
		String player4_name = sharedPref.getString(Utilities.PYRAMID_PLAYER_NAME_PREFERENCE_KEY + "4", KEY_PLAYER + "4");
		playerCount = Integer.valueOf(sharedPref.getString(Utilities.PYRAMID_PLAYER_COUNT_PREFERENCE_KEY, "4"));

		playerNames.put(KEY_PLAYER + "1", player1_name);
		playerNames.put(KEY_PLAYER + "2", player2_name);
		playerNames.put(KEY_PLAYER + "3", player3_name);
		playerNames.put(KEY_PLAYER + "4", player4_name);

		playerHands.put(KEY_PLAYER + "1", new Playerhand(1, player1_name));
		playerHands.put(KEY_PLAYER + "2", new Playerhand(2, player2_name));
		playerHands.put(KEY_PLAYER + "3", new Playerhand(3, player3_name));
		playerHands.put(KEY_PLAYER + "4", new Playerhand(4, player4_name));

		rounds = resources.getStringArray(R.array.pyramid_rounds);
		handler = new Handler();

		gameCard.generatePyramidGameDeck();
		gameDeck = Gamecard.getAllCards();
		gameDeck = gameCard.shuffleDeck(gameDeck);

	}

	private void startTheGame() {
		if ( sharedPref.getBoolean(Utilities.SOUND_PREFERENCE_KEY, false) )
			utilities.playSound(1);

		if ( sharedPref.getBoolean(Utilities.VIBRATE_PREFERENCE_KEY, false) ) {
			vib = (Vibrator) PyramidActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
			vib.vibrate(500);
		}

		utilities.fadeIn(firstChoice);
		utilities.fadeIn(secondChoice);

		execute_round();
	}

	private void execute_round() {
		if ( currentPlayerNumber >= playerCount ) {
			roundNumber++;
			currentPlayerNumber = 1;
			if ( roundNumber > 4 ) {
				goToNextLevel();
				return;
			}
		} else
			currentPlayerNumber++;

		StateListDrawable firstChoiceState = new StateListDrawable();
		StateListDrawable secondChoiceState = new StateListDrawable();
		switch ( roundNumber ) {
			case 1:
				firstChoiceState.addState(new int[]{ android.R.attr.state_pressed }, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_black_card_pressed, null));
				firstChoiceState.addState(new int[]{}, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_black_card, null));
				secondChoiceState.addState(new int[]{ android.R.attr.state_pressed }, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_red_card_pressed, null));
				secondChoiceState.addState(new int[]{}, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_red_card, null));
				break;
			case 2:
				firstChoiceState.addState(new int[]{ android.R.attr.state_pressed }, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_higher_pressed, null));
				firstChoiceState.addState(new int[]{}, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_higher, null));
				secondChoiceState.addState(new int[]{ android.R.attr.state_pressed }, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_lower_pressed, null));
				secondChoiceState.addState(new int[]{}, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_lower, null));
				break;
			case 3:
				firstChoiceState.addState(new int[]{ android.R.attr.state_pressed }, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_within_pressed, null));
				firstChoiceState.addState(new int[]{}, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_within, null));
				secondChoiceState.addState(new int[]{ android.R.attr.state_pressed }, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_outside_pressed, null));
				secondChoiceState.addState(new int[]{}, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_outside, null));
				break;
			case 4:
				firstChoiceState.addState(new int[]{ android.R.attr.state_pressed }, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_in_possesion_pressed, null));
				firstChoiceState.addState(new int[]{}, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_in_possesion, null));
				secondChoiceState.addState(new int[]{ android.R.attr.state_pressed }, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_not_in_possesion_pressed, null));
				secondChoiceState.addState(new int[]{}, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_not_in_possesion, null));
				break;
			default:
				Log.d(TAG, "execute_round() called with: " + "RoundNumber: " + roundNumber);
				break;
		}

		firstChoice.setImageDrawable(firstChoiceState);
		secondChoice.setImageDrawable(secondChoiceState);

		String player_name = playerNames.get(KEY_PLAYER + currentPlayerNumber);
		if ( toolbar != null )
			toolbar.setTitle(resources.getString(R.string.player) + ": " + player_name);

		if ( toolbar != null )
			toolbar.setSubtitle(rounds[roundNumber - 1]);

		playerHand = playerHands.get(KEY_PLAYER + currentPlayerNumber);
		currentCard = gameDeck.get(currentCardNumber);
		currentCardNumber++;

		for ( int i = 0; i < roundNumber - 1; i++ ) {
			ImageView view = playerCards.get(KEY_PLAYERCARD + (i + 1));
			utilities.fadeIn(view, 3000);
			view.setImageResource(playerHand.getPlayerCards().get(i).getImageID());
		}

		firstChoice.setClickable(true);
		secondChoice.setClickable(true);
	}

	public void onClickFirstChoice(View v) {
		if ( sharedPref.getBoolean(Utilities.SOUND_PREFERENCE_KEY, false) ) {
			utilities.playSound(1);
		}
		firstChoice.setClickable(false);
		secondChoice.setClickable(false);
		Gamecard first_card;
		Gamecard second_card;
		Gamecard third_card;
		switch ( roundNumber ) {
			case 1:
				String color = currentCard.getCardColor();
				if ( !color.equalsIgnoreCase(Gamecard.SPADES) && !color.equalsIgnoreCase(Gamecard.CLUBS) )
					drink();
				break;
			case 2:
				first_card = playerHand.getPlayerCards().get(0);
				if ( currentCard.getCardValue() <= first_card.getCardValue() )
					drink();
				break;
			case 3:
				int current_card_value = currentCard.getCardValue();
				first_card = playerHand.getPlayerCards().get(0);
				int first_card_value = first_card.getCardValue();
				second_card = playerHand.getPlayerCards().get(1);
				int second_card_value = second_card.getCardValue();

				if ( current_card_value < first_card_value && current_card_value > second_card_value || current_card_value > first_card_value && current_card_value < second_card_value )
					drink();
				break;
			case 4:
				String current_card_color = null;
				String first_card_color = null;
				String second_card_color = null;
				String third_card_color = null;
				current_card_color = currentCard.getCardColor();
				first_card = playerHand.getPlayerCards().get(0);
				first_card_color = first_card.getCardColor();
				second_card = playerHand.getPlayerCards().get(1);
				second_card_color = second_card.getCardColor();
				third_card = playerHand.getPlayerCards().get(2);
				third_card_color = third_card.getCardColor();

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
		firstChoice.setClickable(false);
		secondChoice.setClickable(false);
		Gamecard first_card;
		Gamecard second_card;
		Gamecard third_card;
		switch ( roundNumber ) {
			case 1:
				String color = currentCard.getCardColor();
				if ( !color.equalsIgnoreCase(Gamecard.HEARTS) && !color.equalsIgnoreCase(Gamecard.DIAMONDS) )
					drink();
				break;
			case 2:
				first_card = playerHand.getPlayerCards().get(0);
				if ( currentCard.getCardValue() >= first_card.getCardValue() )
					drink();
				break;
			case 3:
				int current_card_value = currentCard.getCardValue();
				first_card = playerHand.getPlayerCards().get(0);
				int first_card_value = first_card.getCardValue();
				second_card = playerHand.getPlayerCards().get(1);
				int second_card_value = second_card.getCardValue();

				if ( current_card_value > first_card_value && current_card_value < second_card_value || current_card_value < first_card_value && current_card_value > second_card_value )
					drink();
				break;
			case 4:
				String current_card_color = currentCard.getCardColor();
				first_card = playerHand.getPlayerCards().get(0);
				String first_card_color = first_card.getCardColor();
				second_card = playerHand.getPlayerCards().get(1);
				String second_card_color = second_card.getCardColor();
				third_card = playerHand.getPlayerCards().get(2);
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
		ImageView card = playerCards.get(KEY_PLAYERCARD + roundNumber);
		if ( card != null && currentCard != null ) {
			utilities.fadeIn(card);
			card.setImageResource(currentCard.getImageID());
			playerHand.addCard(currentCard);
		}
//		currentCard = null;

		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				for ( Map.Entry<String, ImageView> entry : playerCards.entrySet() ) {
					ImageView value = entry.getValue();
					utilities.fadeOut(value);
				}
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						execute_round();
					}
				}, 1000);
			}
		}, 2000);

	}

	private void drink() {
		if ( sharedPref.getBoolean(Utilities.VIBRATE_PREFERENCE_KEY, false) ) {
			vib = (Vibrator) PyramidActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
			vib.vibrate(1000);
		}
		String toastText = utilities.getEmojiByUnicode(0x1F37A) + resources.getString(R.string.pyramid_drink_message) + utilities.getEmojiByUnicode(0x1F37B);
		Toast drinkToast = Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT);
		drinkToast.setGravity(Gravity.CENTER, 0, 0);
		drinkToast.show();
		ViewGroup group = (ViewGroup) drinkToast.getView();
		TextView messageTextView = (TextView) group.getChildAt(0);
		messageTextView.setTextSize(40);
	}

	private void goToNextLevel() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(false)
						.setIcon(ResourcesCompat.getDrawable(resources, R.drawable.ic_logo, null))
						.setTitle(R.string.first_round_finished)
						.setNeutralButton(R.string.go_to_next_level, new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int id) {
								Intent intent = new Intent(getApplicationContext(), PyramidSecondRoundActivity.class);

								intent.putExtra(Utilities.GAMEDECK_GAME_KEY, gameDeck);
								intent.putExtra(Utilities.PLAYERHANDS_GAME_KEY, playerHands);
								startActivity(intent);
								finish();
							}
						});

		AlertDialog dialog = builder.create();
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}
}

