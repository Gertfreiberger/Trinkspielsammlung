package wtfisandroid.drinkinggamescollection.activities.games.pyramid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Vector;

import wtfisandroid.drinkinggamescollection.R;
import wtfisandroid.drinkinggamescollection.data.Gamecard;
import wtfisandroid.drinkinggamescollection.data.PlayerHand;
import wtfisandroid.drinkinggamescollection.logic.Utilities;

public class PyramidSecondRound extends AppCompatActivity {
	private static final String TAG = "TESTING";

	//private static final String TAG = "testing";

	private int id;
	private int index;

	/* for accessing GUI attributes */
	protected Button bDeal;
	protected Button bNext;
	protected ImageView view;
	protected TextView tvPlayerName;


	private int drinkCount = 1;
	private int currentPlayer = 1;

	public int pyramidIndex = 0;

	private HashMap<Integer, Gamecard> gameDeck;

	private Vector<ImageView> pyramidCards;
	private Vector<ImageView> playerCards;
	private RotateAnimation anim_rotate;
	private HashMap<String, PlayerHand> playerHands;
	private Utilities utilities;
	private SharedPreferences sharedPref;
	private Resources resources;
	private int playerCount;

	private int currentCardValue;
	private PlayerHand currentPlayerHand;
	private ArrayList<String> finalPlayer = new ArrayList<>();
	public Gamecard currentCard;
	private int currentCardIndex;

	/* Animations */
	private ScaleAnimation animPlayerCards;
	private ScaleAnimation animPyramidCards;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		utilities = new Utilities(getApplicationContext());
		sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String currentLanguage = sharedPref.getString(Utilities.LANGUAGE_PREFERENCE_KEY, Locale.getDefault().getDisplayLanguage());
		utilities.setLanguage(currentLanguage);
		resources = getResources();
		setContentView(R.layout.activity_pyramid_2_round);

		if ( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP ) {
			Slide slide = new Slide();
			slide.setDuration(1000);
			getWindow().setEnterTransition(slide);
			getWindow().setReturnTransition(slide);
		}

		playerCount = Integer.valueOf(sharedPref.getString(Utilities.PYRAMID_PLAYER_COUNT_PREFERENCE_KEY, "2"));

		Toast.makeText(getApplicationContext(), resources.getString(R.string.hello_second_pyramid_round), Toast.LENGTH_SHORT).show();

		/* Initialize scale animation for showing player hand */
		animPlayerCards = new ScaleAnimation(1, 1.0f, 0, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animPlayerCards.setDuration(500);
		animPlayerCards.setFillAfter(true);

		/* Initialize scale animation if card matches */
		animPyramidCards = new ScaleAnimation(0, 1.0f, 0, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animPyramidCards.setDuration(500);
		animPyramidCards.setFillAfter(true);

		/* Initialize scale animation for flipping pyramid card */
		anim_rotate = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		anim_rotate.setDuration(300);
		anim_rotate.setInterpolator(new LinearInterpolator());
		anim_rotate.setAnimationListener(new Animation.AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
			}
		});

		tvPlayerName = (TextView) findViewById(R.id.PlayerHand);

		bDeal = (Button) findViewById(R.id.bDeal);
		bNext = (Button) findViewById(R.id.bNext);
		bDeal.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				clearButtons();
				playerCards.elementAt(currentCardIndex).setScaleY(1.0f);
				playerCards.elementAt(currentCardIndex).setScaleX(1.0f);
				currentPlayerHand.getPlayerCards().remove(currentCardIndex);
				utilities.drink();
				hidePlayerCards(currentPlayerHand);
				checkPlayerHand(currentCardValue, playerHands.get(Utilities.KEY_PLAYER + currentPlayer));
			}
		});
		bDeal.setClickable(false);
		bNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				clearButtons();
				playerCards.elementAt(currentCardIndex).setScaleY(1.0f);
				playerCards.elementAt(currentCardIndex).setScaleX(1.0f);
				hidePlayerCards(currentPlayerHand);
				checkPlayerHand(currentCardValue, playerHands.get(Utilities.KEY_PLAYER + currentPlayer));
			}
		});
		bNext.setClickable(false);

		gameDeck = (HashMap<Integer, Gamecard>) getIntent().getSerializableExtra((Utilities.GAMEDECK_GAME_KEY));
		playerHands = (HashMap<String, PlayerHand>) getIntent().getSerializableExtra(Utilities.PLAYERHANDS_GAME_KEY);

		pyramidCards = new Vector<>();
		playerCards = new Vector<>();
		getPlayerCards();
		setPyramidCards();
		showPyramidCard();


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
				Intent intent = new Intent(getApplicationContext(), PyramidActivity.class);
				startActivity(intent);
				break;
			case R.id.back:
				onBackPressed();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		if ( sharedPref.getBoolean(Utilities.SOUND_PREFERENCE_KEY, false) ) {
			utilities.playSound(1, AudioManager.FX_KEYPRESS_RETURN);
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(resources.getString(R.string.clos_game_message));
		builder.setCancelable(true);

		builder.setPositiveButton(
						resources.getString(R.string.yes),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								finish();
							}
						});

		builder.setNegativeButton(
						resources.getString(R.string.no),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

		AlertDialog alert = builder.create();
		alert.show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void setPyramidCards() {
		index = 0;
		getPyramidViews();
		for (; index < pyramidCards.size(); index++ ) {
			view = pyramidCards.elementAt(index);
			view.startAnimation(animPyramidCards);
			view.setBackgroundResource(R.drawable.card_back);
		}
	}

	public void getPyramidViews() {
		index = 1;
		String name;
		try {
			for (; index < 16; index++ ) {
				name = "ivPyramide" + index;
				id = R.id.class.getField(name).getInt(null);
				view = (ImageView) findViewById(id);
				pyramidCards.add(view);
			}
		} catch ( IllegalAccessException e ) {
			e.printStackTrace();
		} catch ( NoSuchFieldException e ) {
			e.printStackTrace();
		}
	}

	public void setPlayerCards(PlayerHand playerHand) {
		index = 0;
		clearCards();
		/* set text from textView of player hand*/
		tvPlayerName.setText("Spielerkarten: "
				+ sharedPref.getString(Utilities.PYRAMID_PLAYER_NAME_PREFERENCE_KEY + currentPlayer,
				playerHand.getPlayerName()));
		index = 0;
		for (; index < playerHand.getPlayerCards().size(); index++ ) {
			view = playerCards.elementAt(index);
			id = playerHand.getPlayerCards().get(index).getImageID();
			view.startAnimation(animPlayerCards);
			view.setBackgroundResource(id);
		}
	}

	public void hidePlayerCards(PlayerHand playerHand) {
		index = 0;
		clearCards();
		/* set text from textView of player hand*/
		tvPlayerName.setText("Spielerkarten: ");
		for (; index < playerHand.getPlayerCards().size(); index++ ) {
			view = playerCards.elementAt(index);
			view.startAnimation(animPlayerCards);
			view.setBackgroundResource(R.drawable.card_back);
		}
	}

	public void getPlayerCards() {
		index = 1;
		String name;
		try {
			for (; index < 5; index++ ) {
				name = Utilities.KEY_PLAYERCARD + index;
				id = R.id.class.getField(name).getInt(null);
				view = (ImageView) findViewById(id);
				playerCards.add(view);
			}
		} catch ( IllegalAccessException e ) {
			e.printStackTrace();
		} catch ( NoSuchFieldException e ) {
			e.printStackTrace();
		}
	}

	public void clearCards() {
		index = 0;
		/* Remove all images of the player hand for new arrangement */
		int index = 0;
		for (; index < 4; index++) {
			playerCards.elementAt(index).setBackgroundResource(android.R.color.transparent);
		}
	}

	public void showPyramidCard() {
		if ( pyramidIndex < 15 ) {
			drinkCount = getDrinkCount();
			pyramidCards.elementAt(pyramidIndex).setClickable(true);
			pyramidCards.elementAt(pyramidIndex).setBackgroundResource(R.drawable.card_back_focus);
			pyramidCards.elementAt(pyramidIndex).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Gamecard card = gameDeck.get(pyramidIndex + (playerCount * 4));
					id = card.getImageID();
					currentCardValue = gameDeck.get(pyramidIndex + (playerCount * 4)).getCardValue();
					currentPlayerHand = playerHands.get(Utilities.KEY_PLAYER + 1);
					v.setClickable(false);
					v.startAnimation(anim_rotate);
					v.setBackgroundResource(id);
					pyramidIndex++;
					checkPlayerHand(currentCardValue, currentPlayerHand);
				}
			});
		} else {
			findFinalPlayer();
			android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);

			builder.setCancelable(false)
							.setIcon(ResourcesCompat.getDrawable(resources, R.drawable.ic_logo, null))
							.setTitle(R.string.second_round_finished)
							.setMessage(resources.getString(R.string.final_player_message) + finalPlayer)
							.setNeutralButton(R.string.go_to_Final_level, new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog, int id) {
									Intent intent = new Intent(getApplicationContext(), PyramidFinalRound.class);
									intent.putStringArrayListExtra(Utilities.FINAL_PLAYER, finalPlayer);
									startActivity(intent);
									finish();
								}
							});

			android.support.v7.app.AlertDialog dialog = builder.create();
			dialog.show();
		}
	}

	public void checkPlayerHand(int cardValue, PlayerHand playerHand) {
		index = 0;
		boolean match = false;
		if ( currentPlayer <= playerCount ) {
			for (; index < playerHand.getPlayerCards().size(); index++ ) {
				if ( cardValue == playerHand.getPlayerCards().get(index).getCardValue() ) {
					match = true;

					/* when matchin set buttons clickable and red */
					bDeal.setClickable(true);
					bNext.setClickable(true);
					bDeal.setText("Verteile (" + drinkCount + " Schluck)");
					bDeal.setBackgroundColor(Color.RED);
					bNext.setBackgroundColor(Color.RED);

					/* scale matching card */
					view = playerCards.elementAt(index);
					view.setScaleY(1.2f);
					view.setScaleX(1.2f);

					currentCardIndex = index;
					currentCard = playerHand.getPlayerCards().get(index);
					currentPlayerHand = playerHands.get(Utilities.KEY_PLAYER + currentPlayer);
					setPlayerCards(currentPlayerHand);
					break;
				}
			}
			currentPlayer++;
			if ( !match && currentPlayer <= playerCount ) {
				checkPlayerHand(cardValue, playerHands.get(Utilities.KEY_PLAYER + currentPlayer));
			} else if ( !match ) {
				currentPlayer = 1;
				showPyramidCard();
			}
		} else {
			currentPlayer = 1;
			showPyramidCard();
		}
	}

	public int getDrinkCount() {
		if (pyramidIndex < 5)
			return 1;
		else if (pyramidIndex < 9)
			return 2;
		else if (pyramidIndex < 12)
			return 3;
		else if (pyramidIndex < 14)
			return 4;
		else
			return 5;
	}

	public void clearButtons() {
		bDeal.setClickable(false);
		bNext.setClickable(false);
		bDeal.setBackgroundColor(Color.GRAY);
		bNext.setBackgroundColor(Color.GRAY);
		bDeal.setText("Austeilen");
	}

	private void findFinalPlayer() {
		int maxCards = 0;
		for ( int i = 1; i <= playerCount; i++ ) {
			if ( playerHands.get("Player" + i).getPlayerCards().size() >= maxCards )
				maxCards = playerHands.get("Player" + i).getPlayerCards().size();
		}
		for (int i = 1; i <= playerCount; i++) {
			if (playerHands.get("Player" + i).getPlayerCards().size() == maxCards)
				finalPlayer.add(playerHands.get("Player" + i).getPlayerName());
		}
	}
}