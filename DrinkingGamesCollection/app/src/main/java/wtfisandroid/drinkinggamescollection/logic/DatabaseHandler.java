package wtfisandroid.drinkinggamescollection.logic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import wtfisandroid.drinkinggamescollection.data.IHaveNeverEverStatement;

/**
 * Created by Manfred on 20.05.2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "i_have_never_ever";
	private static final String TABLE_STATEMENTS = "statements";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_STATEMENT = "statement";
	private static final String KEY_CATEGORY = "category";
	private static final String TAG = " DatabaseHandler";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public void executeSQLScript(InputStream sql) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		SQLiteDatabase db = this.getWritableDatabase();
		byte buf[] = new byte[1024];
		int len;

		try {
			while ( (len = sql.read(buf)) != -1 ) {
				outputStream.write(buf, 0, len);
			}
			outputStream.close();
			sql.close();
			String[] createScript = outputStream.toString().split(";");
			for ( int i = 0; i < createScript.length; i++ ) {
				String sqlStatement = createScript[i].trim();
				if ( sqlStatement.length() > 0 ) {
					db.execSQL(sqlStatement + ";");
				}
			}
		} catch ( IOException e ) {
			e.printStackTrace();
		} catch ( SQLException e ) {
			e.printStackTrace();
		}
	}


	public List<IHaveNeverEverStatement> getAllStatements() {
		List<IHaveNeverEverStatement> statements = new ArrayList<>();

		String selectQuery = "SELECT  * FROM " + TABLE_STATEMENTS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if ( cursor.moveToFirst() ) {
			do {
				IHaveNeverEverStatement contact = new IHaveNeverEverStatement();
				contact.setId(Integer.parseInt(cursor.getString(0)));
				contact.setM_statement(cursor.getString(1));
				contact.setM_category(cursor.getString(2));
				statements.add(contact);
			} while ( cursor.moveToNext() );
		}

		return statements;
	}

	public void addStatement(IHaveNeverEverStatement statement) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_STATEMENT, statement.getStatement());
		values.put(KEY_CATEGORY, statement.getCategory());

		db.insert(TABLE_STATEMENTS, null, values);
		db.close();
	}

	public int updateStatement(IHaveNeverEverStatement statement) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_STATEMENT, statement.getStatement());
		values.put(KEY_CATEGORY, statement.getCategory());

		// updating row
		return db.update(TABLE_STATEMENTS, values, KEY_ID + " = ?",
						new String[]{ String.valueOf(statement.getId()) });
	}

	public void deleteStatement(IHaveNeverEverStatement statement) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_STATEMENTS, KEY_ID + " = ?",
						new String[]{ String.valueOf(statement.getId()) });
		db.close();
	}

	public int getStatementsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_STATEMENTS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		return cursor.getCount();
	}
}
