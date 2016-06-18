package wtfisandroid.drinkinggamescollection.logic;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import wtfisandroid.drinkinggamescollection.R;
import wtfisandroid.drinkinggamescollection.data.IHaveNeverEverStatement;

/**
 * Created by Manfred on 20.05.2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

	private static final String TAG = " DatabaseHandler";

	private static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "i_have_never_ever";
	public static final String TABLE_STATEMENTS = "statements";

	public static final String KEY_STATEMENT_ID = "id";
	public static final String KEY_STATEMENT = "statement";
	public static final String KEY_CATEGORY = "category";
	public static final String QUERY_ALL_LANGUAGES = "SELECT * FROM LANGUAGES;";
	public static final String QUERY_ALL_CATEGORIES = "SELECT * FROM CATEGORIES;";
	public static final String KEY_LANGUAGE = "language";


	private final Context context;
	private final Resources resource;

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
		resource = context.getResources();
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
			for ( String aCreateScript : createScript ) {
				String sqlStatement = aCreateScript.trim();
				Log.d(TAG, "executeSQLScript() called with: " + "sql = [" + sqlStatement + "]");
				if ( sqlStatement.length() > 0 ) {
					db.execSQL(sqlStatement + ";");
				}
			}
		} catch ( IOException | SQLException e ) {
			e.printStackTrace();
		}
	}

	public List<IHaveNeverEverStatement> getAllStatements(String selectQuery) {
		List<IHaveNeverEverStatement> statements = new ArrayList<>();

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if ( cursor.moveToFirst() ) {
			do {
				IHaveNeverEverStatement contact = new IHaveNeverEverStatement();
				contact.setId(Integer.parseInt(cursor.getString(0)));
				contact.setStatement(cursor.getString(1));
				contact.setCategory(cursor.getString(2));
				statements.add(contact);
			} while ( cursor.moveToNext() );
		}

		return statements;
	}

	public void addStatement(IHaveNeverEverStatement statement) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		if (statement.getId() != -1 ) {
			values.put(KEY_STATEMENT_ID, statement.getId());
		} else {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
			String time = format.format(new Date().getTime());
			Log.d(TAG, "addStatement() called with: " + "time = [" + time + "]");
			values.put(KEY_STATEMENT_ID, time);
		}

		values.put(KEY_STATEMENT, statement.getStatement());
		values.put(KEY_CATEGORY, statement.getCategory());
		values.put(KEY_LANGUAGE, statement.getLanguage());

		db.insert(TABLE_STATEMENTS, null, values);
		db.close();
	}

	public int updateStatement(IHaveNeverEverStatement statement) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_STATEMENT, statement.getStatement());
		values.put(KEY_CATEGORY, statement.getCategory());

		// updating row
		return db.update(TABLE_STATEMENTS, values, KEY_STATEMENT_ID + " = ?",
						new String[]{ String.valueOf(statement.getId()) });
	}

	public void deleteStatement(IHaveNeverEverStatement statement) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_STATEMENTS, KEY_STATEMENT_ID + " = ?",
						new String[]{ String.valueOf(statement.getId()) });
		db.close();
	}

	public int getStatementsCount(String language) {
		String countQuery = "SELECT  * FROM " + TABLE_STATEMENTS + " WHERE LANGUAGE = '" + language + "' ";
		Log.d(TAG, "getStatementsCount() called with: " + "language = [" + countQuery + "]");
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int count = cursor.getCount();
		cursor.close();

		return count;
	}

	public File exportDB() {
		File exportedFile = null;
		File sd = Environment.getExternalStorageDirectory();
		File data = Environment.getDataDirectory();
		FileChannel source;
		FileChannel destination;
		String currentDBPath = "/data/" + context.getPackageName() + "/databases/" + getDatabaseName();
		String directory = resource.getString(R.string.app_name).replace(" ", "_");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", java.util.Locale.getDefault());
		String currentDateAndTime = format.format(new Date());
		String backupDBPath = directory + File.separator + DATABASE_NAME + "_" + currentDateAndTime + ".sql";
		File currentDB = new File(data, currentDBPath);
		File backupDB = new File(sd, backupDBPath);
		if ( !backupDB.exists() ) {
			backupDB.getParentFile().mkdirs();
		}
		try {
			source = new FileInputStream(currentDB).getChannel();
			Log.d(TAG, "source() called with: " + source);
			destination = new FileOutputStream(backupDB).getChannel();
			destination.transferFrom(source, 0, source.size());
			source.close();
			destination.close();
			exportedFile = backupDB;
		} catch ( IOException e ) {
			Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		return exportedFile;
	}

	public List<String> getAllCategories() {
		List<String> cats = new ArrayList<>();

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(QUERY_ALL_CATEGORIES, null);

		if ( cursor.moveToFirst() ) {
			do {
				cats.add(cursor.getString(0));
			} while ( cursor.moveToNext() );
		}

		return cats;
	}

	public List<String> getAllLanguages() {
		List<String> languages = new ArrayList<>();

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(QUERY_ALL_LANGUAGES, null);

		if ( cursor.moveToFirst() ) {
			do {
				languages.add(cursor.getString(0));
			} while ( cursor.moveToNext() );
		}

		return languages;
	}
}
