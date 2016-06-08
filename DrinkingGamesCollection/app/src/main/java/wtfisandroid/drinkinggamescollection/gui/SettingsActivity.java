package wtfisandroid.drinkinggamescollection.gui;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.AudioManager;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.List;
import java.util.Locale;

import wtfisandroid.drinkinggamescollection.R;
import wtfisandroid.drinkinggamescollection.logic.Utilities;

public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

	private static final String TAG = "settings";

	private SharedPreferences sharedPref;
	private Utilities utilities;
	private String currentLanguage;

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

		private Resources m_resources;
		private SharedPreferences m_preference;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			m_resources = getResources();

			String settings = getArguments().getString("settings");
			if ( settings.equalsIgnoreCase("general") ) {
				addPreferencesFromResource(R.xml.options_general);
			} else if ( settings.equalsIgnoreCase("pyramid") ) {
				addPreferencesFromResource(R.xml.options_pyramide);
				final PreferenceScreen screen = getPreferenceScreen();
				final PreferenceCategory category = new PreferenceCategory(screen.getContext());

				category.setTitle(m_resources.getString(R.string.player_names));
				screen.addPreference(category);
				m_preference = PreferenceManager.getDefaultSharedPreferences(screen.getContext());
				int player_number = Integer.valueOf(m_preference.getString(Utilities.PYRAMID_PLAYER_COUNT_PREFERENCE_KEY, "2"));
				for ( int i = 0; i < player_number; i++ ) {
					EditTextPreference player_name = new EditTextPreference(screen.getContext());
					player_name.setKey(Utilities.PYRAMID_PLAYER_NAME_PREFERENCE_KEY + (i + 1));
					player_name.setTitle(m_resources.getString(R.string.player) + " " + (i + 1));
					String playerName = m_preference.getString(Utilities.PYRAMID_PLAYER_NAME_PREFERENCE_KEY + (i + 1), m_resources.getString(R.string.player) + " " + (i + 1));
					player_name.setText(playerName);
					player_name.setSummary(playerName);
					category.addPreference(player_name);
				}

				final EditTextPreference playerCount = (EditTextPreference) findPreference(Utilities.PYRAMID_PLAYER_COUNT_PREFERENCE_KEY);

				playerCount.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
					@Override
					public boolean onPreferenceChange(Preference preference, Object newVal) {
						boolean updatePlayerCount = true;
						int count = Integer.valueOf((String) newVal);
						int player_number = Integer.valueOf(m_preference.getString(Utilities.PYRAMID_PLAYER_COUNT_PREFERENCE_KEY, "2"));
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

						m_preference = PreferenceManager.getDefaultSharedPreferences(screen.getContext());
						SharedPreferences.Editor editor = m_preference.edit();
						editor.putString(Utilities.PYRAMID_PLAYER_COUNT_PREFERENCE_KEY, Integer.toString(count));
						editor.commit();

						int counter;

						if ( count > player_number ) {
							counter = player_number + 1;

							for (; counter <= count; counter++ ) {
								EditTextPreference player_name = new EditTextPreference(screen.getContext());
								player_name.setKey(Utilities.PYRAMID_PLAYER_NAME_PREFERENCE_KEY + (counter));
								player_name.setTitle(m_resources.getString(R.string.player) + " " + (counter));
								String playerName = m_preference.getString(Utilities.PYRAMID_PLAYER_NAME_PREFERENCE_KEY + counter, m_resources.getString(R.string.player) + " " + counter);
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

			} else if ( settings.equalsIgnoreCase("maexchen") ) {
				addPreferencesFromResource(R.xml.options_maexchen);
			}
		}

	}
}
