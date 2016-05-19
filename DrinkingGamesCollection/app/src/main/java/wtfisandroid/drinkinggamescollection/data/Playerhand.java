package wtfisandroid.drinkinggamescollection.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Playerhand implements Parcelable{

	private int playerID = -1;
	private String playerName = null;
	private List<Gamecard> playerCards = new ArrayList<>();

	public Playerhand(int playerID, String playerName) {
		this.playerName = playerName;
		this.playerID = playerID;
	}

	protected Playerhand(Parcel in) {
		playerID = in.readInt();
		playerName = in.readString();
		playerCards = in.createTypedArrayList(Gamecard.CREATOR);
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(playerID);
		dest.writeString(playerName);
		dest.writeTypedList(playerCards);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<Playerhand> CREATOR = new Creator<Playerhand>() {
		@Override
		public Playerhand createFromParcel(Parcel in) {
			return new Playerhand(in);
		}

		@Override
		public Playerhand[] newArray(int size) {
			return new Playerhand[size];
		}
	};

	public List<Gamecard> getPlayerCards() {
		return playerCards;
	}

	public String getPlayerName() {
		return playerName;
	}

	public int getPlayerID() {
		return playerID;
	}

	public List<Gamecard> addCard(Gamecard card) {
		playerCards.add(card);
		return playerCards;
	}

	@Override
	public String toString() {
		return "Playerhand{" +
						"playerID=" + playerID +
						", player_cards=" + playerCards +
						'}';
	}

}
