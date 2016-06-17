package wtfisandroid.drinkinggamescollection.data;

/**
 * Created by Manfred on 20.05.2016.
 */
public class IHaveNeverEverStatement {

	private int id = -1;
	private String m_statement = null;
	private String M_category = null;

	public IHaveNeverEverStatement(int id, String statement, String category) {
		this.id = id;
		this.m_statement = statement;
		this.M_category = category;
	}

	public IHaveNeverEverStatement() {

	}

	public IHaveNeverEverStatement(String statement, String category) {
		this.m_statement = statement;
		this.M_category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatement() {
		return m_statement;
	}

	public void setM_statement(String m_statement) {
		this.m_statement = m_statement;
	}

	public String getCategory() {
		return M_category;
	}

	public void setM_category(String m_category) {
		this.M_category = m_category;
	}

	@Override
	public String toString() {
		return "IHaveNeverEverStatement{" +
						"id=" + id +
						", statement='" + m_statement + '\'' +
						", category='" + M_category + '\'' +
						'}';
	}
}
