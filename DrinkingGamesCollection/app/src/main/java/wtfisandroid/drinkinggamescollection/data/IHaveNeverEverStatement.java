package wtfisandroid.drinkinggamescollection.data;

/**
 * Created by Manfred on 20.05.2016.
 */
public class IHaveNeverEverStatement {

	private int id = -1;
	private String mStatement = null;
	private String mCategory = null;
	private String mLanguage;

	public IHaveNeverEverStatement() {

	}

	public IHaveNeverEverStatement(String statement, String category) {
		this.id = -1;
		this.mStatement = statement;
		this.mCategory = category;
	}

	public IHaveNeverEverStatement(int id, String statement, String category) {
		this.id = id;
		this.mStatement = statement;
		this.mCategory = category;
	}

	public IHaveNeverEverStatement(int id, String statement) {
		this.id = id;
		this.mStatement = statement;
		this.mCategory = "Other";
	}

	public IHaveNeverEverStatement(String statement) {
		this.id = -1;
		this.mStatement = statement;
		this.mCategory = "Other";
	}

	public IHaveNeverEverStatement(String statement, String category, String language) {
		this.id = -1;
		this.mStatement = statement;
		this.mCategory = category;
		this.mLanguage = language;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatement() {
		return mStatement;
	}

	public void setStatement(String mStatement) {
		this.mStatement = mStatement;
	}

	public String getCategory() {
		return mCategory;
	}

	public void setCategory(String mCategory) {
		this.mCategory = mCategory;
	}

	public String getLanguage() {
		return mLanguage;
	}

	public void setLanguage(String mLanguage) {
		this.mLanguage = mLanguage;
	}

	@Override
	public String toString() {
		return "IHaveNeverEverStatement{" +
						"mLanguage='" + mLanguage + '\'' +
						", id=" + id +
						", mStatement='" + mStatement + '\'' +
						", mCategory='" + mCategory + '\'' +
						'}';
	}
}
