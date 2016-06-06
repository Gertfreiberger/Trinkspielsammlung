package wtfisandroid.drinkinggamescollection.logic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
		String sql = "CREATE TABLE " + TABLE_STATEMENTS + "("
						+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_STATEMENT + " TEXT,"
						+ KEY_CATEGORY + " TEXT" + ")";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATEMENTS);
		onCreate(db);
	}

	public void addStatement(IHaveNeverEverStatement statement) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_STATEMENT, statement.getStatement());
		values.put(KEY_CATEGORY, statement.getCategory());

		Log.d(TAG, "addStatement() called with: " + "statement = [" + statement + "]");

		db.insert(TABLE_STATEMENTS, null, values);
		db.close();
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
