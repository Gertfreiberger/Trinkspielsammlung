package wtfisandroid.drinkinggamescollection.gui.game.pyramid;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import wtfisandroid.drinkinggamescollection.R;
import wtfisandroid.drinkinggamescollection.data.Gamecard;
import wtfisandroid.drinkinggamescollection.data.Playerhand;
import wtfisandroid.drinkinggamescollection.logic.Utilities;

public class PyramidSecondRoundActivity extends AppCompatActivity {

	private static final String TAG = "pyramid_second_round";

	private SharedPreferences sharedPref;
	private Utilities utilities;
	private ImageView pyramid01;
	private ImageView pyramid02;
	private ImageView pyramid03;
	private ImageView pyramid04;
	private ImageView pyramid05;
	private ImageView pyramid06;
	private ImageView pyramid07;
	private ImageView pyramid08;
	private ImageView pyramid09;
	private ImageView pyramid10;
	private ImageView pyramid11;
	private ImageView pyramid12;
	private ImageView pyramid13;
	private ImageView pyramid14;
	private ImageView pyramid15;
	private HashMap<String, ImageView> pyramids = new HashMap<>();
	private int pyramid_index = 0;
	private ImageView pyramid_card;
	private RotateAnimation anim_rotate;
	private ImageView cardToShowView;
	private HashMap<Integer, Gamecard> gameDeck;
	private ImageView next_card_view;
	private HashMap<String, Playerhand> playerHands;
	private int pyramidRow = 1;
	private int roundNumber = 1;
	private Resources resources;
	private Gamecard cardToShow;
	private String finalPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		utilities = new Utilities(getApplicationContext());
		sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String currentLanguage = sharedPref.getString(Utilities.LANGUAGE_PREFERENCE_KEY, Locale.getDefault().getDisplayLanguage());
		utilities.setLanguage(currentLanguage);
		resources = getResources();
		setContentView(R.layout.activity_pyramid_second_round);

		gameDeck = (HashMap<Integer, Gamecard>) getIntent().getSerializableExtra("gameDeck");
		playerHands = (HashMap<String, Playerhand>) getIntent().getSerializableExtra("playerHands");

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

		if ( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP ) {
			Fade enterTransition = new Fade();
			enterTransition.setDuration(2000);
			getWindow().setEnterTransition(enterTransition);
			Fade fade = new Fade();
			fade.setDuration(2000);
			getWindow().setEnterTransition(fade);
		}

		findViews();

		final Animation anim_fade_in = new AlphaAnimation(0, 1);
		anim_fade_in.setInterpolator(new DecelerateInterpolator());
		anim_fade_in.setDuration(200);
		anim_fade_in.setAnimationListener(new Animation.AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				if ( pyramid_index < 15 ) {
					pyramid_index++;
					pyramid_card = pyramids.get("pyramid" + pyramid_index);
					if ( pyramid_card != null ) {
						pyramid_card.setVisibility(View.VISIBLE);
						pyramid_card.startAnimation(anim_fade_in);
					}
				}
			}
		});

		anim_rotate = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		anim_rotate.setDuration(200);
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
				distributeDrinks();
			}
		});

		pyramid_index = 1;
		pyramid_card = pyramids.get("pyramid" + pyramid_index);

		if ( pyramid_card != null ) {
			pyramid_card.startAnimation(anim_fade_in);
			pyramid_card.setVisibility(View.VISIBLE);
			pyramid_card.setEnabled(true);
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
		pyramid01 = (ImageView) findViewById(R.id.ivPyramide01);
		pyramid02 = (ImageView) findViewById(R.id.ivPyramide02);
		pyramid03 = (ImageView) findViewById(R.id.ivPyramide03);
		pyramid04 = (ImageView) findViewById(R.id.ivPyramide04);
		pyramid05 = (ImageView) findViewById(R.id.ivPyramide05);
		pyramid06 = (ImageView) findViewById(R.id.ivPyramide06);
		pyramid07 = (ImageView) findViewById(R.id.ivPyramide07);
		pyramid08 = (ImageView) findViewById(R.id.ivPyramide08);
		pyramid09 = (ImageView) findViewById(R.id.ivPyramide09);
		pyramid10 = (ImageView) findViewById(R.id.ivPyramide10);
		pyramid11 = (ImageView) findViewById(R.id.ivPyramide11);
		pyramid12 = (ImageView) findViewById(R.id.ivPyramide12);
		pyramid13 = (ImageView) findViewById(R.id.ivPyramide13);
		pyramid14 = (ImageView) findViewById(R.id.ivPyramide14);
		pyramid15 = (ImageView) findViewById(R.id.ivPyramide15);
		initImageViews();

	}

	private void initImageViews() {
		pyramids.put("pyramid1", pyramid01);
		pyramids.put("pyramid2", pyramid02);
		pyramids.put("pyramid3", pyramid03);
		pyramids.put("pyramid4", pyramid04);
		pyramids.put("pyramid5", pyramid05);
		pyramids.put("pyramid6", pyramid06);
		pyramids.put("pyramid7", pyramid07);
		pyramids.put("pyramid8", pyramid08);
		pyramids.put("pyramid9", pyramid09);
		pyramids.put("pyramid10", pyramid10);
		pyramids.put("pyramid11", pyramid11);
		pyramids.put("pyramid12", pyramid12);
		pyramids.put("pyramid13", pyramid13);
		pyramids.put("pyramid14", pyramid14);
		pyramids.put("pyramid15", pyramid15);

		for ( int i = 0; i <= pyramids.size(); i++ ) {
			final ImageView card = pyramids.get("pyramid" + i);
			if ( card != null ) {
				card.setVisibility(View.INVISIBLE);
				final int finalI = i;
				card.setEnabled(false);
				card.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						card.setClickable(false);
						card.startAnimation(anim_rotate);
						cardToShowView = card;
						cardToShow = gameDeck.get(finalI);
						if ( finalI < pyramids.size() )
							next_card_view = pyramids.get("pyramid" +( finalI + 1));
					}

				});
			}
		}
	}

	private void distributeDrinks() {
		cardToShowView.setImageResource(cardToShow.getImageID());
		List<String> distributors = new ArrayList<>();
		for (int i = 0; i < playerHands.size(); i++) {
			Playerhand playerHand = playerHands.get("Player" + (i + 1));
			Iterator it =  playerHand.getPlayerCards().iterator();
			while (it.hasNext()) {
				Gamecard card = (Gamecard) it.next();
				if ( card.getCardValue() == cardToShow.getCardValue()) {
					it.remove();
					distributors.add(playerHand.getPlayerName());
				}
			}
		}

		if ( !distributors.isEmpty() ) {

			List<Integer> counts = new ArrayList<>();
			int count;
			for ( int i = 1; i <= playerHands.size(); i++ ) {
				count = Collections.frequency(distributors, playerHands.get("Player" + i).getPlayerName());
				if ( count > 0 ) {
					counts.add(count);
				}
			}

			Set<String> clearedList = new HashSet<>();
			clearedList.addAll(distributors);
			distributors.clear();
			distributors.addAll(clearedList);

			String[] array = new String[distributors.size()];

			for ( int i = 0; i < distributors.size(); i++ ) {
				array[i] = distributors.get(i) + ": " + resources.getString(R.string.distribute) + " " + (counts.get(i) * pyramidRow) + " " + resources.getString(R.string.drinks);
			}

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setCancelable(false)
							.setItems(array, null)
							.setIcon(ResourcesCompat.getDrawable(resources, R.drawable.ic_logo, null))
							.setTitle(R.string.distribute_drinks)
							.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									dialog.dismiss();
									if ( pyramidRow == 5 )
										findFinalPlayer();
								}
							});

			AlertDialog dialog = builder.create();
			dialog.show();
		} else {
			if ( pyramidRow == 5 )
				findFinalPlayer();
		}


		if ( next_card_view != null ) {
			next_card_view.setEnabled(true);
			next_card_view.setClickable(true);
		}

		if ( roundNumber == 5|| roundNumber == 9 || roundNumber == 12 || roundNumber == 14 || roundNumber  == 15 )
			pyramidRow++;

		roundNumber++;
	}

	private void findFinalPlayer() {
		int maxCards = -1;
		for ( int i = 1; i <= playerHands.size(); i++ ) {
			if ( playerHands.get("Player" + i).getPlayerCards().size() > maxCards ) {
				finalPlayer = playerHands.get("Player" + i).getPlayerName();
			}
		}
		goToNextLevel();
	}

	private void goToNextLevel() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(false)
						.setIcon(ResourcesCompat.getDrawable(resources, R.drawable.ic_logo, null))
						.setTitle(R.string.second_round_finished)
						.setMessage(resources.getString(R.string.final_player_message) + finalPlayer)
						.setNeutralButton(R.string.go_to_Final_level, new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int id) {
								Intent intent = new Intent(getApplicationContext(), PyramidFinalRound.class);
								intent.putExtra(Utilities.FINAL_PLAYER, finalPlayer);
								startActivity(intent);
								finish();
							}
						});

		AlertDialog dialog = builder.create();
		dialog.show();
	}
}
