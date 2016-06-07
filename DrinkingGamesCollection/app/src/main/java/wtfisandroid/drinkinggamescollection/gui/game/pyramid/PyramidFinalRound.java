package wtfisandroid.drinkinggamescollection.gui.game.pyramid;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.LogManager;

import wtfisandroid.drinkinggamescollection.R;
import wtfisandroid.drinkinggamescollection.data.Gamecard;
import wtfisandroid.drinkinggamescollection.logic.Utilities;

public class PyramidFinalRound extends AppCompatActivity {

	private static final String TAG = "anna";
	private SharedPreferences sharedPref;
	private Utilities utilities;
	private Vibrator vib;
	private Resources resources;
	private ImageView firstChoice;
	private ImageView secondChoice;
	private Gamecard gameCard = new Gamecard();
	private Gamecard randomCard;
	private HashMap<Integer, Gamecard> gameDeck;
	private HashMap<Integer, ImageView> cardViews = new HashMap<Integer, ImageView>();
	private int index=0;
	private int deckIndex=0;
	private int height;
	private int width;
	private ImageView Card1;
	private ImageView Card2;
	private ImageView Card3;
	private ImageView Card4;
	private ImageView Card5;
	private TextView currentPlayer;
	private ArrayList<String> players=new ArrayList<String>();
	private int countPlayers=0;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pyramid_final_round);

		firstChoice = (ImageView) findViewById(R.id.ibPyramidFinalHigherButton);
		secondChoice = (ImageView) findViewById(R.id.ibPyramidFinalLowerButton);

		utilities = new Utilities(getApplicationContext());
		sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


		currentPlayer= (TextView) findViewById(R.id.tvFinalPlayer);


		 Card1 = (ImageView) findViewById(R.id.ivPyramideFinalRoundCard1);
		 Card2 = (ImageView) findViewById(R.id.ivPyramideFinalRoundCard2);
		 Card3 = (ImageView) findViewById(R.id.ivPyramideFinalRoundCard3);
		 Card4 = (ImageView) findViewById(R.id.ivPyramideFinalRoundCard4);
		 Card5 = (ImageView) findViewById(R.id.ivPyramideFinalRoundCard5);


		gameCard.generatePyramidGameDeck();
		gameDeck = Gamecard.getAllCards();

		players.add("anna");
		players.add("Manfred");
		players.add("Stru");
		prepareGame(false);


	}


	private void drink() {
		if ( sharedPref.getBoolean(Utilities.VIBRATE_PREFERENCE_KEY, false) ) {
			vib = (Vibrator) PyramidFinalRound.this.getSystemService(Context.VIBRATOR_SERVICE);
			vib.vibrate(1000);
		}
		String toastText = utilities.getEmojiByUnicode(0x1F37A) + "drink" + utilities.getEmojiByUnicode(0x1F37B);
		Toast drinkToast = Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT);
		drinkToast.setGravity(Gravity.CENTER, 0, 0);
		drinkToast.show();
		ViewGroup group = (ViewGroup) drinkToast.getView();
		TextView messageTextView = (TextView) group.getChildAt(0);
		messageTextView.setTextSize(40);
	}


	private void win() {
		if ( sharedPref.getBoolean(Utilities.VIBRATE_PREFERENCE_KEY, false) ) {
			vib = (Vibrator) PyramidFinalRound.this.getSystemService(Context.VIBRATOR_SERVICE);
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

	public void onClickLower(View v)
	{
		Log.d("anna"," deck:"+deckIndex);
		cardViews.get(deckIndex).getLayoutParams().height = height;
		cardViews.get(deckIndex).getLayoutParams().width = width;
		cardViews.get(deckIndex).requestLayout();
		randomCard = gameDeck.get(index);
		cardViews.get(deckIndex).setImageResource(randomCard.getImageID());
		cardViews.get(deckIndex).requestLayout();
		Log.d("anna","rand: "+randomCard.getCardValue()+"akt: "+gameDeck.get(deckIndex).getCardValue()+" deckindex: "+deckIndex);

		if ( randomCard.getCardValue() >= gameDeck.get(deckIndex).getCardValue() ) {
			drink();
			gameDeck.get(deckIndex).setCardValue(randomCard.getCardValue());
			deckIndex=0;
			cardViews.get(deckIndex).getLayoutParams().height = height*2;
			cardViews.get(deckIndex).getLayoutParams().width = width*2;
			cardViews.get(deckIndex).requestLayout();
		}
		else
		{
			if(deckIndex==4)
			{
				//win();
				prepareGame(true);
			}
			else
			{
				gameDeck.get(deckIndex).setCardValue(randomCard.getCardValue());
				deckIndex++;
				cardViews.get(deckIndex).getLayoutParams().height = height*2;
				cardViews.get(deckIndex).getLayoutParams().width = width*2;
				cardViews.get(deckIndex).requestLayout();
			}


		}
		if(index>51)
		{
			index=0;
			prepareGame(false);
		}
		else
			index++;



	}

	public void onClickHigher(View v)
	{

		cardViews.get(deckIndex).getLayoutParams().height = height;
		cardViews.get(deckIndex).getLayoutParams().width = width;
		cardViews.get(deckIndex).requestLayout();
		randomCard = gameDeck.get(index);
		cardViews.get(deckIndex).setImageResource(randomCard.getImageID());
		cardViews.get(deckIndex).requestLayout();

		Log.d("anna","rand: "+randomCard.getCardValue()+"akt: "+gameDeck.get(deckIndex).getCardValue()+" deckindex: "+deckIndex);
		if ( randomCard.getCardValue() <= gameDeck.get(deckIndex).getCardValue() ) {
			drink();
			gameDeck.get(deckIndex).setCardValue(randomCard.getCardValue());
			deckIndex=0;
			cardViews.get(deckIndex).getLayoutParams().height = height*2;
			cardViews.get(deckIndex).getLayoutParams().width = width*2;
			cardViews.get(deckIndex).requestLayout();
		}
		else {
			if(deckIndex==4)
			{
				//win();
				prepareGame(true);
			}
			else
			{
				gameDeck.get(deckIndex).setCardValue(randomCard.getCardValue());
				deckIndex++;
				cardViews.get(deckIndex).getLayoutParams().height = height*2;
				cardViews.get(deckIndex).getLayoutParams().width = width*2;
				cardViews.get(deckIndex).requestLayout();
			}

		}
		if(index>51)
		{
			index=0;
			prepareGame(false);
		}
		else
			index++;
	}

	private void prepareGame(boolean nextPlayer)
	{
		if(countPlayers<players.size())
		{
			if(nextPlayer)
				countPlayers++;
			Log.d("anna","player: "+players.get(countPlayers));
			gameDeck = gameCard.shuffleDeck(gameDeck);

			deckIndex=0;
			index=5;
			currentPlayer.setText(players.get(countPlayers));
			currentPlayer.requestLayout();
			Card1.setImageResource(gameDeck.get(0).getImageID());
			Card2.setImageResource(gameDeck.get(1).getImageID());
			Card3.setImageResource(gameDeck.get(2).getImageID());
			Card4.setImageResource(gameDeck.get(3).getImageID());
			Card5.setImageResource(gameDeck.get(4).getImageID());


			cardViews.put(0,Card1);
			cardViews.put(1,Card2);
			cardViews.put(2,Card3);
			cardViews.put(3,Card4);
			cardViews.put(4,Card5);

			height=	cardViews.get(0).getDrawable().getIntrinsicHeight();
			width=cardViews.get(0).getDrawable().getIntrinsicWidth();

			Log.d("anna","imageview:"+ cardViews.get(0));
			cardViews.get(0).getLayoutParams().height = height*2;
			cardViews.get(0).getLayoutParams().width = width*2;
			cardViews.get(0).requestLayout();

		}
		else
		{
			//end Game
		}


	}
}
