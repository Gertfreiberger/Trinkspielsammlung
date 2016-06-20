package wtfisandroid.drinkinggamescollection.logic;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import wtfisandroid.drinkinggamescollection.R;
import wtfisandroid.drinkinggamescollection.data.IHaveNeverEverStatement;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static final String TAG = " DatabaseHandler";

	private static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "i_have_never_ever";
	public static final String TABLE_STATEMENTS = "statements";

	public static final String KEY_STATEMENT_ID = "statementId";
	public static final String KEY_STATEMENT = "statement";
	public static final String KEY_CATEGORY = "category";
	public static final String QUERY_ALL_LANGUAGES = "SELECT * FROM LANGUAGES;";
	public static final String QUERY_ALL_CATEGORIES = "SELECT * FROM `categories` ORDER BY `categories`.`catergorieName`  DESC";
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

	public void executeMultipleSQLScript(InputStream sql) {
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
				Log.d(TAG, "executeMultipleSQLScript()  " + "sql = [" + sqlStatement + "]");
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
				IHaveNeverEverStatement statement = new IHaveNeverEverStatement();
				statement.setId(Integer.parseInt(cursor.getString(0)));
				statement.setStatement(cursor.getString(1));
				statement.setCategory(cursor.getString(2));
				statement.setLanguage(cursor.getString(3));
				statements.add(statement);
			} while ( cursor.moveToNext() );
		}
		cursor.close();

		return statements;
	}

	public void addStatement(IHaveNeverEverStatement statement) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		if ( statement.getId() != -1 ) {
			values.put(KEY_STATEMENT_ID, statement.getId());
		} else {
			values.put(KEY_STATEMENT_ID, System.currentTimeMillis() / 1000);
		}

		values.put(KEY_STATEMENT, statement.getStatement());
		values.put(KEY_CATEGORY, statement.getCategory());
		values.put(KEY_LANGUAGE, statement.getLanguage());

		long k = db.insert(TABLE_STATEMENTS, null, values);
		Log.d(TAG, "getAllStatements() long = " + k + " with: " + "getAllStatements = [" + getAllStatements("Select  * from statements") + "]");
		db.close();
	}

	public void deleteStatement(String statement) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_STATEMENTS, KEY_STATEMENT + " = ?", new String[]{ statement });
		db.close();
	}

	public int getStatementsCount(String language) {
		String countQuery = "SELECT  * FROM " + TABLE_STATEMENTS + " WHERE LANGUAGE = '" + language + "' ";
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
		if ( !backupDB.exists() )
			backupDB.getParentFile().mkdirs();

		try {
			source = new FileInputStream(currentDB).getChannel();
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
		cursor.close();

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
		cursor.close();

		return languages;
	}

	public boolean init() {
		boolean successful = false;
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
			if ( c.moveToFirst() ) {
				while ( !c.isAfterLast() ) {
					db.execSQL("DROP TABLE IF EXISTS " + c.getString(0));
					c.moveToNext();
				}
			}

			InputStream insertsStream = context.getAssets().open("databases/i_have_never_ever.sql");
			executeMultipleSQLScript(insertsStream);
			successful = true;
		} catch ( IOException | SQLException e ) {
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		return successful;
	}

	public void importDatabase(Uri uri) {
		try {
			Log.d(TAG, "File Uri: " + uri.toString());
			// Get the path
			String path = getPath(context, uri);
			Log.d(TAG, "File Path: " + path);
			// Get the file instance
			File backupDB = new File(path);

			File currentDB = new File("/data/" + context.getPackageName() + "/databases/" + getDatabaseName());

			FileChannel source = null;
			FileChannel destination = null;
			Log.d(TAG, "importDatabase() called with: " + "inStream = [" + backupDB + "]");
			source = new FileInputStream(backupDB).getChannel();
			destination = new FileOutputStream(currentDB).getChannel();
			destination.transferFrom(source, 0, source.size());
			source.close();
			destination.close();
			Toast toast1 = Toast.makeText(context, ("Restore ok"), Toast.LENGTH_SHORT);
			toast1.show();
		} catch ( IOException e ) {
			Toast toast = Toast.makeText(context, ("Error1!"), Toast.LENGTH_LONG);
			toast.show();
			e.printStackTrace();
		} catch ( URISyntaxException e ) {
			Toast toast = Toast.makeText(context, ("Error2!"), Toast.LENGTH_LONG);
			toast.show();
			e.printStackTrace();
		}
	}

	public static String getPath(Context context, Uri uri) throws URISyntaxException {
		if ( "content".equalsIgnoreCase(uri.getScheme()) ) {
			String[] projection = { "_data" };
			Cursor cursor = null;

			try {
				cursor = context.getContentResolver().query(uri, projection, null, null, null);
				int column_index = cursor.getColumnIndexOrThrow("_data");
				if ( cursor.moveToFirst() ) {
					return cursor.getString(column_index);
				}
			} catch ( Exception e ) {
				// Eat it
			}
		} else if ( "file".equalsIgnoreCase(uri.getScheme()) ) {
			return uri.getPath();
		}

		return null;
	}
}