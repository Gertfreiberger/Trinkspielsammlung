package wtfisandroid.drinkinggamescollection.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import wtfisandroid.drinkinggamescollection.R;

public class Gamecard implements Parcelable {

	private static final String TAG = "Gamecard";
	public static final String CLUBS = "Clubs";
	public static final String DIAMONDS = "Diamonds";
	public static final String SPADES = "Spades";
	public static final String HEARTS = "Hearts";
	public static final String TWO = "Two";
	public static final String THREE = "Three";
	public static final String FOUR = "Four";
	public static final String FIVE = "Five";
	public static final String SIX = "Six";
	public static final String SEVEN = "Seven";
	public static final String EIGHT = "Eight";
	public static final String NINE = "Nine";
	public static final String TEN = "Ten";
	public static final String JACK = "Jack";
	public static final String QUEEN = "Queen";
	public static final String KING = "King";
	public static final String ACE = "Ace";

	private int cardID = -1;
	private int cardValue = 0;
	private String cardColor = null;
	private String cardName = null;
	private int imageID = 0;

	private static HashMap<Integer, Gamecard> allCards = new HashMap<Integer, Gamecard>();

	public Gamecard() {
		super();
	}

	protected Gamecard(Parcel in) {
		cardID = in.readInt();
		cardValue = in.readInt();
		cardColor = in.readString();
		cardName = in.readString();
		imageID = in.readInt();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(cardID);
		dest.writeInt(cardValue);
		dest.writeString(cardColor);
		dest.writeString(cardName);
		dest.writeInt(imageID);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<Gamecard> CREATOR = new Creator<Gamecard>() {
		@Override
		public Gamecard createFromParcel(Parcel in) {
			return new Gamecard(in);
		}

		@Override
		public Gamecard[] newArray(int size) {
			return new Gamecard[size];
		}
	};

	/**
	 * Returns a string containing a concise, human-readable description of this
	 * object. Subclasses are encouraged to override this method and provide an
	 * implementation that takes into account the object's type and data. The
	 * default implementation is equivalent to the following expression:
	 * <pre>
	 *   getClass().getName() + '@' + Integer.toHexString(hashCode())</pre>
	 * <p>See <a href="{@docRoot}reference/java/lang/Object.html#writing_toString">Writing a useful
	 * {@code toString} method</a>
	 * if you intend implementing your own {@code toString} method.
	 *
	 * @return a printable representation of this object.
	 */
	@Override
	public String toString() {
		return "Gamecard{" +
						"cardID=" + cardID +
						", cardValue=" + cardValue +
						", cardColor='" + cardColor + '\'' +
						", cardName='" + cardName + '\'' +
						", imageID=" + imageID +
						'}';
	}

	/**
	 * @param cardID
	 * @param cardColor
	 * @param cardName
	 * @param cardValue
	 */
	public Gamecard(int cardID, String cardColor, String cardName, int cardValue, int imageID) {
		super();
		this.cardID = cardID;
		this.cardColor = cardColor;
		this.cardName = cardName;
		this.imageID = imageID;
		this.cardValue = cardValue;
		allCards.put(cardID, this);
	}

	/**
	 * @return the cardID
	 */
	public int getCardID() {
		return cardID;
	}

	/**
	 * @param cardID the cardID to set
	 */
	public void setCardID(int cardID) {
		this.cardID = cardID;
	}

	/**
	 * @return the cardColor
	 */
	public String getCardColor() {
		return cardColor;
	}

	/**
	 * @param cardColor the cardColor to set
	 */
	public void setCardColor(String cardColor) {
		this.cardColor = cardColor;
	}

	/**
	 * @return the cardName
	 */
	public String getCardName() {
		return cardName;
	}

	/**
	 * @param cardName the cardName to set
	 */
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	/**
	 * @return the image
	 */
	public int getImageID() {
		return imageID;
	}

	/**
	 * @param image the image to set
	 */
	public void setImageID(int image) {
		this.imageID = image;
	}

	/**
	 * @return the cardValue
	 */
	public int getCardValue() {
		return cardValue;
	}

	/**
	 * @param cardValue the cardValue to set
	 */
	public void setCardValue(int cardValue) {
		this.cardValue = cardValue;
	}

	/**
	 * @return the allCards
	 */
	public static HashMap<Integer, Gamecard> getAllCards() {
		return allCards;
	}

	public void generatePyramidGameDeck() {
		new Gamecard(0, CLUBS, TWO, 2, R.drawable.gamecard_2_of_clubs);
		new Gamecard(1, DIAMONDS, TWO, 2, R.drawable.gamecard_2_of_diamonds);
		new Gamecard(2, HEARTS, TWO, 2, R.drawable.gamecard_2_of_hearts);
		new Gamecard(3, SPADES, TWO, 2, R.drawable.gamecard_2_of_spades);
		new Gamecard(4, CLUBS, THREE, 3, R.drawable.gamecard_3_of_clubs);
		new Gamecard(5, DIAMONDS, THREE, 3, R.drawable.gamecard_3_of_diamonds);
		new Gamecard(6, HEARTS, THREE, 3, R.drawable.gamecard_3_of_hearts);
		new Gamecard(7, SPADES, THREE, 3, R.drawable.gamecard_3_of_spades);
		new Gamecard(8, CLUBS, FOUR, 4, R.drawable.gamecard_4_of_clubs);
		new Gamecard(9, DIAMONDS, FOUR, 4, R.drawable.gamecard_4_of_diamonds);
		new Gamecard(10, HEARTS, FOUR, 4, R.drawable.gamecard_4_of_hearts);
		new Gamecard(11, SPADES, FOUR, 4, R.drawable.gamecard_4_of_spades);
		new Gamecard(12, CLUBS, FIVE, 5, R.drawable.gamecard_5_of_clubs);
		new Gamecard(13, DIAMONDS, FIVE, 5, R.drawable.gamecard_5_of_diamonds);
		new Gamecard(14, HEARTS, FIVE, 5, R.drawable.gamecard_5_of_hearts);
		new Gamecard(15, SPADES, FIVE, 5, R.drawable.gamecard_5_of_spades);
		new Gamecard(16, CLUBS, SIX, 6, R.drawable.gamecard_6_of_clubs);
		new Gamecard(17, DIAMONDS, SIX, 6, R.drawable.gamecard_6_of_diamonds);
		new Gamecard(18, HEARTS, SIX, 6, R.drawable.gamecard_6_of_hearts);
		new Gamecard(19, SPADES, SIX, 6, R.drawable.gamecard_6_of_spades);
		new Gamecard(20, CLUBS, SEVEN, 7, R.drawable.gamecard_7_of_clubs);
		new Gamecard(21, DIAMONDS, SEVEN, 7, R.drawable.gamecard_7_of_diamonds);
		new Gamecard(22, HEARTS, SEVEN, 7, R.drawable.gamecard_7_of_hearts);
		new Gamecard(23, SPADES, SEVEN, 7, R.drawable.gamecard_7_of_spades);
		new Gamecard(24, CLUBS, EIGHT, 8, R.drawable.gamecard_8_of_clubs);
		new Gamecard(25, DIAMONDS, EIGHT, 8, R.drawable.gamecard_8_of_diamonds);
		new Gamecard(26, HEARTS, EIGHT, 8, R.drawable.gamecard_8_of_hearts);
		new Gamecard(27, SPADES, EIGHT, 8, R.drawable.gamecard_8_of_spades);
		new Gamecard(28, CLUBS, NINE, 9, R.drawable.gamecard_9_of_clubs);
		new Gamecard(29, DIAMONDS, NINE, 9, R.drawable.gamecard_9_of_diamonds);
		new Gamecard(30, HEARTS, NINE, 9, R.drawable.gamecard_9_of_hearts);
		new Gamecard(31, SPADES, NINE, 9, R.drawable.gamecard_9_of_spades);
		new Gamecard(32, CLUBS, TEN, 10, R.drawable.gamecard_10_of_clubs);
		new Gamecard(33, DIAMONDS, TEN, 10, R.drawable.gamecard_10_of_diamonds);
		new Gamecard(34, HEARTS, TEN, 10, R.drawable.gamecard_10_of_hearts);
		new Gamecard(35, SPADES, TEN, 10, R.drawable.gamecard_10_of_spades);
		new Gamecard(36, CLUBS, JACK, 11, R.drawable.gamecard_jack_of_clubs);
		new Gamecard(37, DIAMONDS, JACK, 11, R.drawable.gamecard_jack_of_diamonds);
		new Gamecard(38, HEARTS, JACK, 11, R.drawable.gamecard_jack_of_hearts);
		new Gamecard(39, SPADES, JACK, 11, R.drawable.gamecard_jack_of_spades);
		new Gamecard(40, CLUBS, QUEEN, 12, R.drawable.gamecard_jack_of_clubs);
		new Gamecard(41, DIAMONDS, QUEEN, 12, R.drawable.gamecard_queen_of_diamonds);
		new Gamecard(42, HEARTS, QUEEN, 12, R.drawable.gamecard_queen_of_hearts);
		new Gamecard(43, SPADES, QUEEN, 12, R.drawable.gamecard_queen_of_spades);
		new Gamecard(44, CLUBS, KING, 13, R.drawable.gamecard_king_of_clubs);
		new Gamecard(45, DIAMONDS, KING, 13, R.drawable.gamecard_king_of_diamonds);
		new Gamecard(46, HEARTS, KING, 13, R.drawable.gamecard_king_of_hearts);
		new Gamecard(47, SPADES, KING, 13, R.drawable.gamecard_king_of_spades);
		new Gamecard(48, CLUBS, ACE, 14, R.drawable.gamecard_jack_of_clubs);
		new Gamecard(49, DIAMONDS, ACE, 14, R.drawable.gamecard_ace_of_diamonds);
		new Gamecard(50, HEARTS, ACE, 14, R.drawable.gamecard_ace_of_hearts);
		new Gamecard(51, SPADES, ACE, 14, R.drawable.gamecard_ace_of_spades);
	}

	public HashMap<Integer, Gamecard> shuffleDeck(HashMap<Integer, Gamecard> gameDeck) {
		LinkedHashMap<Integer, Gamecard> shuffledDeck = new LinkedHashMap<Integer, Gamecard>();
		try {
			Random rand = new Random();
			int randNumber;
			boolean add;
			List<Integer> order = new ArrayList<Integer>();
			int size = gameDeck.size();
			for (; order.size() < size; ) {
				randNumber = rand.nextInt(size);
				add = true;
				for ( int j = 0; j < order.size(); j++ ) {
					if ( order.get(j) == randNumber ) {
						add = false;
					}
				}
				if ( add ) {
					order.add(randNumber);
				}
			}
			int i = 0;
			for ( Iterator<Integer> iterator = order.iterator(); iterator.hasNext(); ) {
				Integer integer = iterator.next();
				shuffledDeck.put(i, gameDeck.get(integer));
				i++;
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return shuffledDeck;
	}


}
