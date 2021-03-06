package wtfisandroid.drinkinggamescollection.activities.games.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.List;
import java.util.Locale;

import wtfisandroid.drinkinggamescollection.R;
import wtfisandroid.drinkinggamescollection.logic.DatabaseHandler;
import wtfisandroid.drinkinggamescollection.logic.Utilities;

import static android.content.Intent.createChooser;

public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

	private static final String TAG = "settings";

	private SharedPreferences sharedPref;
	private Utilities utilities;
	private String currentLanguage;
	private static final int FILE_SELECT_REQUEST = 777;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		utilities = new Utilities(getApplicationContext());
		sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		currentLanguage = sharedPref.getString(Utilities.LANGUAGE_PREFERENCE_KEY, Locale.getDefault().getDisplayLanguage());
		utilities.setLanguage(currentLanguage);
	}

	/**
	 * Called when the activity needs its list of headers build.  By
	 * implementing this and adding at least one item to the list, you
	 * will cause the activity to run in its modern fragment mode.  Note
	 * that this function may not always be called; for example, if the
	 * activity has been asked to display a particular fragment without
	 * the header list, there is no need to build the headers.
	 * <p/>
	 * <p>Typical implementations will use {@link #loadHeadersFromResource}
	 * to fill in the list from a resource.
	 *
	 * @param target The list in which to place the headers.
	 */
	@Override
	public void onBuildHeaders(List<Header> target) {
		utilities = new Utilities(getApplicationContext());
		sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		currentLanguage = sharedPref.getString(Utilities.LANGUAGE_PREFERENCE_KEY, Locale.getDefault().getDisplayLanguage());
		utilities.setLanguage(currentLanguage);
		loadHeadersFromResource(R.xml.options_header, target);
		super.onBuildHeaders(target);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if ( key.equalsIgnoreCase(Utilities.VIBRATE_PREFERENCE_KEY) ) {
			if ( sharedPref.getBoolean(Utilities.VIBRATE_PREFERENCE_KEY, false) ) {
				Vibrator vib = (Vibrator) SettingsActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
				vib.vibrate(999);
			}
		} else if ( key.equalsIgnoreCase(Utilities.SOUND_PREFERENCE_KEY) ) {
			if ( sharedPref.getBoolean(Utilities.SOUND_PREFERENCE_KEY, false) ) {
				utilities.playSound(1);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if ( sharedPref.getBoolean(Utilities.SOUND_PREFERENCE_KEY, false) ) {
			utilities.playSound(1);
		}
		switch ( item.getItemId() ) {
			case R.id.back:
				finish();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		if ( sharedPref.getBoolean(Utilities.SOUND_PREFERENCE_KEY, false) )
			utilities.playSound(1, AudioManager.FX_KEYPRESS_RETURN);

		super.onBackPressed();
	}

	/**
	 * Subclasses should override this method and verify that the given fragment is a valid type
	 * to be attached to this activity. The default implementation returns <code>true</code> for
	 * apps built for <code>android:targetSdkVersion</code> older than
	 * {@link Build.VERSION_CODES#KITKAT}. For later versions, it will throw an exception.
	 *
	 * @param fragmentName the class name of the Fragment about to be attached to this activity.
	 * @return true if the fragment class name is valid for this Activity and false otherwise.
	 */
	@Override
	protected boolean isValidFragment(String fragmentName) {
		return SettingsFragment.class.getName().equals(fragmentName);
	}

	@Override
	protected void onResume() {
		super.onResume();
		PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).unregisterOnSharedPreferenceChangeListener(this);
	}

	public static class SettingsFragment extends PreferenceFragment {

		private Resources resources;
		private static File fileWithinMyDir;
		DatabaseHandler db;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			resources = getResources();

			String settings = getArguments().getString("settings");
			if ( settings != null && settings.equalsIgnoreCase("general") ) {
				showGeneralSettings();
			} else if ( settings != null && settings.equalsIgnoreCase("pyramid") ) {
				showPyramidSettings();
			} else if ( settings != null && settings.equalsIgnoreCase("i_have_never_ever") ) {
				db = new DatabaseHandler(getActivity().getApplicationContext());
				showIHaveNeverEverSettings();
			}

		}

		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			switch ( requestCode ) {
				case FILE_SELECT_REQUEST:
					if ( resultCode == RESULT_OK ) {
						//TODO import  chosen Database file
						db.importDatabase(data.getData());
					}
					break;
			}
		}

		private void showGeneralSettings() {
			addPreferencesFromResource(R.xml.options_general);
			Preference resetSettings = findPreference("reset_settings");
			resetSettings.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

				@Override
				public boolean onPreferenceClick(Preference preference) {
					PreferenceScreen screen = getPreferenceScreen();
					SharedPreferences sharePref = PreferenceManager.getDefaultSharedPreferences(screen.getContext());
					SharedPreferences.Editor editor = sharePref.edit();
					editor.clear();
					editor.putBoolean(Utilities.FIRST_RUN_PREFERENCE_KEY, false);

					SwitchPreference sound = (SwitchPreference) findPreference("sound");
					SwitchPreference vibrate = (SwitchPreference) findPreference("vibrate");
					sound.setChecked(true);
					vibrate.setChecked(true);
					editor.commit();
					View view = getView();
					if ( view != null ) {
						Snackbar snackbar = Snackbar.make(view, R.string.settings_reset_success, Snackbar.LENGTH_LONG);
						snackbar.show();
					} else if ( view != null )
						Snackbar.make(view, R.string.settings_reset_error, Snackbar.LENGTH_LONG).show();


					return false;
				}
			});
		}

		private void showPyramidSettings() {
			addPreferencesFromResource(R.xml.options_pyramide);
			final PreferenceScreen screen = getPreferenceScreen();
			final SharedPreferences sharePref = PreferenceManager.getDefaultSharedPreferences(screen.getContext());
			final PreferenceCategory category = new PreferenceCategory(screen.getContext());

			category.setTitle(resources.getString(R.string.player_names));
			screen.addPreference(category);
			int player_number = Integer.valueOf(sharePref.getString(Utilities.PYRAMID_PLAYER_COUNT_PREFERENCE_KEY, "2"));
			for ( int i = 0; i < player_number; i++ ) {
				EditTextPreference player_name = new EditTextPreference(screen.getContext());
				player_name.setKey(Utilities.PYRAMID_PLAYER_NAME_PREFERENCE_KEY + (i + 1));
				player_name.setTitle(resources.getString(R.string.player) + " " + (i + 1));
				String playerName = sharePref.getString(Utilities.PYRAMID_PLAYER_NAME_PREFERENCE_KEY + (i + 1), resources.getString(R.string.player) + " " + (i + 1));
				player_name.setText(playerName);
				player_name.setSummary(playerName);
				category.addPreference(player_name);
				player_name.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
					@Override
					public boolean onPreferenceChange(Preference preference, Object newVal) {
						preference.setSummary(newVal.toString());
						return true;
					}

				});
			}

			final EditTextPreference playerCount = (EditTextPreference) findPreference(Utilities.PYRAMID_PLAYER_COUNT_PREFERENCE_KEY);

			playerCount.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
				@Override
				public boolean onPreferenceChange(Preference preference, Object newVal) {
					boolean updatePlayerCount = true;
					int count = Integer.valueOf((String) newVal);
					int player_number = Integer.valueOf(sharePref.getString(Utilities.PYRAMID_PLAYER_COUNT_PREFERENCE_KEY, "2"));
					if ( count < 2 ) {
						playerCount.setText("2");
						updatePlayerCount = false;
						count = 2;
					} else if ( count > 9 ) {
						playerCount.setText("9");
						count = 9;
						updatePlayerCount = false;
					}
					playerCount.getEditor().apply();
					playerCount.getEditor().commit();

					SharedPreferences.Editor editor = sharePref.edit();
					editor.putString(Utilities.PYRAMID_PLAYER_COUNT_PREFERENCE_KEY, Integer.toString(count));
					editor.apply();

					int counter;

					if ( count > player_number ) {
						counter = player_number + 1;

						for (; counter <= count; counter++ ) {
							EditTextPreference player_name = new EditTextPreference(screen.getContext());
							player_name.setKey(Utilities.PYRAMID_PLAYER_NAME_PREFERENCE_KEY + (counter));
							player_name.setTitle(resources.getString(R.string.player) + " " + (counter));
							String playerName = sharePref.getString(Utilities.PYRAMID_PLAYER_NAME_PREFERENCE_KEY + counter, resources.getString(R.string.player) + " " + counter);
							player_name.setText(playerName);
							player_name.setSummary(playerName);
							category.addPreference(player_name);
						}
					} else if ( count < player_number ) {
						counter = player_number;

						for (; counter > count; counter-- ) {
							EditTextPreference etPlayerName = (EditTextPreference) findPreference(Utilities.PYRAMID_PLAYER_NAME_PREFERENCE_KEY + (counter));
							if ( etPlayerName != null ) {
								category.removePreference(etPlayerName);
							}
						}
					}

					return updatePlayerCount;
				}

			});
		}

		private void showIHaveNeverEverSettings() {
			addPreferencesFromResource(R.xml.options_i_have_never_ever);

			Preference importSql = findPreference("import_sql");
			importSql.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

				@Override
				public boolean onPreferenceClick(Preference preference) {
					try {
						if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ) {
							Intent intent = new Intent();
							intent.setType("*/*");
							intent.setAction(Intent.ACTION_GET_CONTENT);
							startActivityForResult(intent, FILE_SELECT_REQUEST);
						} else {
							Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
							intent.setType("*/*");
							startActivityForResult(intent, FILE_SELECT_REQUEST);
						}
					} catch ( android.content.ActivityNotFoundException ex ) {
						try {
							Toast.makeText(getActivity(), resources.getString(R.string.install_file_explorer), Toast.LENGTH_SHORT).show();
							startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=file explorer")));
						} catch ( android.content.ActivityNotFoundException activityNotFoundException ) {
							startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/search?q=file%20explorer&c=apps&hl=en")));
						}
					}
					return false;
				}
			});

			Preference exportPrefs = findPreference("export_sql");
			exportPrefs.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

				@Override
				public boolean onPreferenceClick(Preference preference) {
					fileWithinMyDir = db.exportDB();
					View view = getView();
					if ( fileWithinMyDir != null && view != null ) {
						Snackbar snackbar = Snackbar.make(view, R.string.db_exported_success, Snackbar.LENGTH_LONG);
						snackbar.setAction(R.string.share, new ShareListener());
						snackbar.show();
					} else if ( view != null )
						Snackbar.make(view, R.string.db_exported_error, Snackbar.LENGTH_LONG).show();

					return false;
				}
			});

			Preference addStatements = findPreference("add_statements");
			addStatements.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

				@Override
				public boolean onPreferenceClick(Preference preference) {

					Intent intent = new Intent(getActivity(), AddStatementActivity.class);
					startActivity(intent);

					return false;
				}
			});

			Preference deleteStatements = findPreference("delete_statements");
			deleteStatements.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

				@Override
				public boolean onPreferenceClick(Preference preference) {

					Intent intent = new Intent(getActivity(), DeleteStatementActivity.class);
					startActivity(intent);
					return false;
				}
			});

			Preference resetStatements = findPreference("reset_statements");
			resetStatements.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

				@Override
				public boolean onPreferenceClick(Preference preference) {
					View view = getView();
					if ( db.init() && view != null ) {
						Snackbar snackbar = Snackbar.make(view, R.string.db_reset_success, Snackbar.LENGTH_LONG);
						snackbar.show();
					} else if ( view != null )
						Snackbar.make(view, R.string.db_reset_error, Snackbar.LENGTH_LONG).show();


					return false;
				}
			});
		}

		public class ShareListener implements View.OnClickListener {

			@Override
			public void onClick(View v) {
				if ( fileWithinMyDir.exists() ) {
					Intent intentShareFile = new Intent(Intent.ACTION_SEND);
					intentShareFile.setType("file/*");
					intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + fileWithinMyDir.getPath()));

					intentShareFile.putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.sharing_sqlite_dump_subject));
					intentShareFile.putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.sharing_sqlite_dump_body));
					startActivity(createChooser(intentShareFile, resources.getString(R.string.sharing_sqlite_dump)));
				}
			}
		}
	}

}
