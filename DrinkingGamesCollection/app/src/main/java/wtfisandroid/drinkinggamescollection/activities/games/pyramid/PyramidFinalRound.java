package wtfisandroid.drinkinggamescollection.activities.games.pyramid;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.StateListDrawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ContentFrameLayout;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import wtfisandroid.drinkinggamescollection.R;
import wtfisandroid.drinkinggamescollection.data.Gamecard;
import wtfisandroid.drinkinggamescollection.logic.Utilities;

public class PyramidFinalRound extends AppCompatActivity {//TODO Add Options menu ("new Game" And "BAck")

	private static final String TAG = "PyramidFinalRound";
	private SharedPreferences sharedPref;
	private Utilities utilities;
	private Resources resources;
	private ImageView firstChoice;
	private ImageView secondChoice;
	private Gamecard gameCard = new Gamecard();
	private Gamecard randomCard;
	private HashMap<Integer, Gamecard> gameDeck;
	private HashMap<Integer, ImageView> cardViews = new HashMap<>();
	private int index = 0;
	private int deckIndex = 0;
	private int height;
	private int width;
	private ImageView Card1;
	private ImageView Card2;
	private ImageView Card3;
	private ImageView Card4;
	private ImageView Card5;
	private TextView currentPlayer;
	private ArrayList<String> players = new ArrayList<String>();
	private int countPlayers = 0;
	private boolean backToPyramid = false;
	private long back_pressed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pyramid_final_round);

		utilities = new Utilities(getApplicationContext());
		sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

		Card1 = (ImageView) findViewById(R.id.ivPyramideFinalRoundCard1);
		Card2 = (ImageView) findViewById(R.id.ivPyramideFinalRoundCard2);
		Card3 = (ImageView) findViewById(R.id.ivPyramideFinalRoundCard3);
		Card4 = (ImageView) findViewById(R.id.ivPyramideFinalRoundCard4);
		Card5 = (ImageView) findViewById(R.id.ivPyramideFinalRoundCard5);
		currentPlayer = (TextView) findViewById(R.id.tvFinalPlayer);
		firstChoice = (ImageView) findViewById(R.id.ibPyramidFinalHigherButton);
		secondChoice = (ImageView) findViewById(R.id.ibPyramidFinalLowerButton);

		StateListDrawable firstChoiceState = new StateListDrawable();
		StateListDrawable secondChoiceState = new StateListDrawable();
		firstChoiceState.addState(new int[]{ android.R.attr.state_pressed }, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_higher_pressed, null));
		firstChoiceState.addState(new int[]{}, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_higher, null));
		secondChoiceState.addState(new int[]{ android.R.attr.state_pressed }, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_lower_pressed, null));
		secondChoiceState.addState(new int[]{}, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_lower, null));

		resources = getResources();
		gameCard.generatePyramidGameDeck();
		gameDeck = Gamecard.getAllCards();

		Intent intent = getIntent();
		players = intent.getStringArrayListExtra(Utilities.FINAL_PLAYER);
		prepareGame(false);
	}

	private void win() {
		if ( sharedPref.getBoolean(Utilities.VIBRATE_PREFERENCE_KEY, false) ) {
			Vibrator vib = (Vibrator) PyramidFinalRound.this.getSystemService(Context.VIBRATOR_SERVICE);
			vib.vibrate(1000);
		}
		String toastText = utilities.getEmojiByUnicode(0x1F37A) + "WIN" + utilities.getEmojiByUnicode(0x1F37B);
		Toast drinkToast = Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT);
		drinkToast.setGravity(Gravity.CENTER, 0, 0);
		drinkToast.show();
		ViewGroup group = (ViewGroup) drinkToast.getView();
		TextView messageTextView = (TextView) group.getChildAt(0);
		messageTextView.setTextSize(40);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if ( sharedPref.getBoolean(Utilities.SOUND_PREFERENCE_KEY, false) ) {
			utilities.playSound(1);
		}
		switch ( item.getItemId() ) {
			case R.id.new_game:
				if ( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP ) {
					ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(PyramidFinalRound.this);
					Intent intent = new Intent(PyramidFinalRound.this, PyramidActivity.class);
					startActivity(intent, options.toBundle());
				} else
					startActivity(new Intent(getApplicationContext(), PyramidActivity.class));
				finish();
				break;
			case R.id.help:
				ContentFrameLayout rootLayout = (ContentFrameLayout) findViewById(android.R.id.content);
				View.inflate(this, R.layout.manual, rootLayout);
				backToPyramid = true;
				WebView webView = (WebView) findViewById(R.id.wv_manual);
				utilities.generatePyramidManual(webView);
				break;
			case R.id.back:
				finish();
				break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		if ( sharedPref.getBoolean(Utilities.SOUND_PREFERENCE_KEY, false) )
			utilities.playSound(1, AudioManager.FX_KEYPRESS_RETURN);

		if ( backToPyramid ) {
			ContentFrameLayout rootLayout = (ContentFrameLayout) findViewById(android.R.id.content);
			if ( rootLayout != null )
				rootLayout.removeView(findViewById(R.id.wv_manual));

			backToPyramid = false;
		} else if ( back_pressed + 2000 > System.currentTimeMillis() ) {
			finish();
		} else {
			Toast.makeText(getBaseContext(), getString(R.string.close_message), Toast.LENGTH_SHORT).show();
			back_pressed = System.currentTimeMillis();
		}
	}

	public void onClickLower(View v) {//TODO Add pressed state
		Log.d("anna", " deck:" + deckIndex);
		cardViews.get(deckIndex).getLayoutParams().height = height;
		cardViews.get(deckIndex).getLayoutParams().width = width;
		cardViews.get(deckIndex).requestLayout();
		randomCard = gameDeck.get(index);
		cardViews.get(deckIndex).setImageResource(randomCard.getImageID());
		cardViews.get(deckIndex).requestLayout();
		Log.d("anna", "rand: " + randomCard.getCardValue() + "akt: " + gameDeck.get(deckIndex).getCardValue() + " deckindex: " + deckIndex);

		if ( randomCard.getCardValue() >= gameDeck.get(deckIndex).getCardValue() ) {
			utilities.drink();
			gameDeck.get(deckIndex).setCardValue(randomCard.getCardValue());
			deckIndex = 0;
			cardViews.get(deckIndex).getLayoutParams().height = height * 2;
			cardViews.get(deckIndex).getLayoutParams().width = width * 2;
			cardViews.get(deckIndex).requestLayout();
		} else {
			if ( deckIndex == 4 ) {
				//win();
				prepareGame(true);
			} else {
				gameDeck.get(deckIndex).setCardValue(randomCard.getCardValue());
				deckIndex++;
				cardViews.get(deckIndex).getLayoutParams().height = height * 2;
				cardViews.get(deckIndex).getLayoutParams().width = width * 2;
				cardViews.get(deckIndex).requestLayout();
			}

		}
		if ( index > 51 ) {
			index = 0;
			prepareGame(false);
		} else
			index++;

	}

	public void onClickHigher(View v) { //TODO Add pressed state

		cardViews.get(deckIndex).getLayoutParams().height = height;
		cardViews.get(deckIndex).getLayoutParams().width = width;
		cardViews.get(deckIndex).requestLayout();
		randomCard = gameDeck.get(index);
		cardViews.get(deckIndex).setImageResource(randomCard.getImageID());
		cardViews.get(deckIndex).requestLayout();

		Log.d("anna", "rand: " + randomCard.getCardValue() + "akt: " + gameDeck.get(deckIndex).getCardValue() + " deckindex: " + deckIndex);
		if ( randomCard.getCardValue() <= gameDeck.get(deckIndex).getCardValue() ) {
			utilities.drink();
			gameDeck.get(deckIndex).setCardValue(randomCard.getCardValue());
			deckIndex = 0;
			cardViews.get(deckIndex).getLayoutParams().height = height * 2;
			cardViews.get(deckIndex).getLayoutParams().width = width * 2;
			cardViews.get(deckIndex).requestLayout();
		} else {
			if ( deckIndex == 4 ) {
				//win();
				prepareGame(true);
			} else {
				gameDeck.get(deckIndex).setCardValue(randomCard.getCardValue());
				deckIndex++;
				cardViews.get(deckIndex).getLayoutParams().height = height * 2;
				cardViews.get(deckIndex).getLayoutParams().width = width * 2;
				cardViews.get(deckIndex).requestLayout();
			}

		}
		if ( index > 51 ) {
			index = 0;
			prepareGame(false);
		} else
			index++;
	}

	private void prepareGame(boolean nextPlayer) {
		if ( nextPlayer )
			countPlayers++;
		if ( countPlayers < players.size() ) {

			Log.d("anna", "player: " + players.get(countPlayers));
			gameDeck = gameCard.shuffleDeck(gameDeck);

			deckIndex = 0;
			index = 5;
			currentPlayer.setText(players.get(countPlayers));
			currentPlayer.requestLayout();
			Card1.setImageResource(gameDeck.get(0).getImageID());
			Card2.setImageResource(gameDeck.get(1).getImageID());
			Card3.setImageResource(gameDeck.get(2).getImageID());
			Card4.setImageResource(gameDeck.get(3).getImageID());
			Card5.setImageResource(gameDeck.get(4).getImageID());

			cardViews.put(0, Card1);
			cardViews.put(1, Card2);
			cardViews.put(2, Card3);
			cardViews.put(3, Card4);
			cardViews.put(4, Card5);

			height = cardViews.get(0).getDrawable().getIntrinsicHeight();
			width = cardViews.get(0).getDrawable().getIntrinsicWidth();

			Log.d("anna", "imageview:" + cardViews.get(0));
			cardViews.get(0).getLayoutParams().height = height * 2;
			cardViews.get(0).getLayoutParams().width = width * 2;
			cardViews.get(0).requestLayout();

		} else {
			goToMainMenu();
		}

	}


	private void goToMainMenu() {//TODO New Game Button to dialog
		firstChoice.setClickable(false);
		secondChoice.setClickable(false);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(false)
						.setIcon(ResourcesCompat.getDrawable(resources, R.drawable.ic_logo, null))
						.setTitle("Final round finished")
						.setNeutralButton("Go to Main", new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int id) {

								if ( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP ) {
									ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(PyramidFinalRound.this);
									Slide slide = new Slide();
									slide.setDuration(1000);
									getWindow().setExitTransition(slide);
								}
								finish();
							}
						});

		AlertDialog dialog = builder.create();
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();

	}
}
