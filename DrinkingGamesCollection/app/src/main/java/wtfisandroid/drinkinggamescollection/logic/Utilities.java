package wtfisandroid.drinkinggamescollection.logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.AudioManager;
import android.os.Build;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;

import wtfisandroid.drinkinggamescollection.R;

public class Utilities {

	private static final String TAG = "utilities";
	public static final String ACTION = "action";
	public static final String ADD_STATEMENTS = "add_statements";
	public static final String DELETE_STATEMENTS = "delete_statements";

	private final Context context;

	public final static String SOUND_PREFERENCE_KEY = "sound";
	public final static String VIBRATE_PREFERENCE_KEY = "vibrate";
	public final static String LANGUAGE_PREFERENCE_KEY = "language";
	public final static String GAMEDECK_GAME_KEY = "gameDeck";
	public final static String PLAYERHANDS_GAME_KEY = "playerHands";
	public final static String FINAL_PLAYER = "final_player";
	public static final String KEY_PLAYERCARD = "PlayerCard";
	public static final String KEY_PLAYER = "Player";
	public static final String FIRST_RUN_PREFERENCE_KEY = "first_run";
	public static final String PYRAMID_PLAYER_NAME_PREFERENCE_KEY = "player_name";
	public static final String PYRAMID_PLAYER_COUNT_PREFERENCE_KEY = "pyramid_player_count";
	public static final String PREF_WORK = "pref_key_i_have_never_ever_category_work";
	public static final String PREF_SCHOOL = "pref_key_i_have_never_ever_category_school";
	public static final String PREF_LOVE = "pref_key_i_have_never_ever_category_love";
	public static final String PREF_DRINKING = "pref_key_i_have_never_ever_category_drinking";
	public static final String PREF_ADULT = "pref_key_i_have_never_ever_category_adult";
	public static final String PREF_LAW = "pref_key_i_have_never_ever_category_law";

	private final SharedPreferences sharedPref;
	private Resources resources;
	private Vibrator vib;

	public Utilities(Context context) {
		this.context = context;
		this.resources = context.getResources();
		this.sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		this.vib = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
	}

	/**
	 * Plays a Click-Effect with the given volume.
	 *
	 * @param volume Volume of the soundeffect
	 */
	public void playSound(float volume) {
		AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		am.playSoundEffect(AudioManager.FX_KEY_CLICK, volume);
	}

	/**
	 * Plays a Click-Effect with the given volume.
	 *
	 * @param volume Volume of the soundeffect
	 * @param sound  Id of the soundeffect
	 */
	public void playSound(float volume, int sound) {
		AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		am.playSoundEffect(sound, volume);
	}


	/**
	 * @param lang The shortcut of the desired language
	 */
	public void setLanguage(String lang) {
		switch ( lang ) {
			case "Deutsch":
				lang = "de";
				break;
			case "English":
				lang = "en";
				break;
			default:
				lang = "en";
				break;
		}
		Locale locale = new Locale(lang);
		Locale.setDefault(locale);
		Resources res = context.getResources();
		DisplayMetrics dm = res.getDisplayMetrics();
		Configuration conf = res.getConfiguration();
		conf.locale = locale;
		res.updateConfiguration(conf, dm);
	}

	public void fadeOut(View view) {
		if ( view != null && view.getVisibility() != View.INVISIBLE ) {
			Animation animationFadeOut = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
			view.startAnimation(animationFadeOut);
			view.setVisibility(View.INVISIBLE);
		}
	}

	public void fadeIn(View view) {
		if ( view != null && view.getVisibility() != View.VISIBLE ) {
			Animation animationFadeIn = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
			view.startAnimation(animationFadeIn);
			view.setVisibility(View.VISIBLE);
		}
	}

	public void fadeOut(View view, int duration) {
		if ( view != null && view.getVisibility() != View.INVISIBLE ) {
			Animation animationFadeOut = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
			animationFadeOut.setDuration(duration);
			view.startAnimation(animationFadeOut);
			view.setVisibility(View.INVISIBLE);
		}
	}

	public void fadeIn(View view, int duration) {
		if ( view != null && view.getVisibility() != View.VISIBLE ) {
			Animation animationFadeIn = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
			animationFadeIn.setDuration(duration);
			view.startAnimation(animationFadeIn);
			view.setVisibility(View.VISIBLE);
		}
	}

	public String getEmojiByUnicode(int unicode) {
		return new String(Character.toChars(unicode));
	}

	public static String streamToString(InputStream in) {
		if ( in == null )
			return "";

		Writer writer = new StringWriter();
		char[] buffer = new char[1024];
		try {
			Reader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			int n;
			while ( (n = reader.read(buffer)) != -1 ) {
				writer.write(buffer, 0, n);
			}
		} catch ( IOException ex ) {
			ex.printStackTrace();
		}
		return writer.toString();
	}

	public void drink() {
		if ( sharedPref.getBoolean(Utilities.VIBRATE_PREFERENCE_KEY, false) ) {
			vib.vibrate(1000);
		}
		String toastText = getEmojiByUnicode(0x1F37A) + resources.getString(R.string.pyramid_drink_message) + getEmojiByUnicode(0x1F37B);
		Toast drinkToast = Toast.makeText(context, toastText, Toast.LENGTH_SHORT);
		drinkToast.setGravity(Gravity.CENTER, 0, 0);
		drinkToast.show();
		ViewGroup group = (ViewGroup) drinkToast.getView();
		TextView messageTextView = (TextView) group.getChildAt(0);
		messageTextView.setTextSize(30);
	}

	public String getFileContents(InputStream inputStream) {

		final StringBuilder stringBuilder = new StringBuilder();
		try {
			final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

			boolean done = false;

			while ( !done ) {
				final String line = reader.readLine();
				done = (line == null);

				if ( line != null ) {
					stringBuilder.append(line);
				}
			}

			reader.close();
			inputStream.close();
		} catch ( FileNotFoundException e ) {
			e.printStackTrace();
		} catch ( IOException e ) {
			e.printStackTrace();
		}

		return stringBuilder.toString();
	}

	public void generateIHaveNeverEverManual(WebView webView) {
		webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
		webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		if ( Build.VERSION.SDK_INT >= 19 ) {
			// chromium, enable hardware acceleration
			webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		} else {
			// older android version, disable hardware acceleration
			webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
		AssetManager asset = context.getAssets();
		Resources resources = context.getResources();
		try {
			Utilities util = new Utilities(context);
			InputStream is = asset.open("www/manual_i_have_never_ever.html", AssetManager.ACCESS_BUFFER);
			String html = util.streamToString(is);

			html = html.replace("$GAME_PLAY_HEADER", resources.getString(R.string.pyramid_manual_general_info_header));
			html = html.replace("$GAME_PLAY__TEXT", resources.getString(R.string.never_ever_text));
			webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
			is.close();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}


	public void generatePyramidManual(WebView webView) {
		webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
		webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		if ( Build.VERSION.SDK_INT >= 19 ) {
			// chromium, enable hardware acceleration
			webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		} else {
			// older android version, disable hardware acceleration
			webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
		AssetManager asset = context.getAssets();
		Resources resources = context.getResources();
		try {

			InputStream is = asset.open("www/manual_pyramid.html", AssetManager.ACCESS_BUFFER);
			String html = streamToString(is);

			html = html.replace("$GENERAL_INFORMATION_HEADER", resources.getString(R.string.pyramid_manual_general_info_header));
			html = html.replace("$GENERAL_INFORMATION_TEXT", resources.getString(R.string.pyramid_manual_general_info));
			html = html.replace("$FIRST_ROUND_HEADER", resources.getString(R.string.pyramid_manual_first_round_header));
			html = html.replace("$FIRST_ROUND_TEXT", resources.getString(R.string.pyramid_manual_first_round));
			html = html.replace("$FIRST_FIRST_ROUND_HEADER", resources.getString(R.string.pyramid_manual_first_first_round_header));
			html = html.replace("$FIRST_FIRST_ROUND_TEXT", resources.getString(R.string.pyramid_manual_first_first_round));
			html = html.replace("$FIRST_SECOND_ROUND_HEADER", resources.getString(R.string.pyramid_manual_first_second_round_header));
			html = html.replace("$FIRST_SECOND_ROUND_TEXT", resources.getString(R.string.pyramid_manual_first_second_round));
			html = html.replace("$FIRST_THIRD_ROUND_HEADER", resources.getString(R.string.pyramid_manual_first_third_round_header));
			html = html.replace("$FIRST_THIRD_ROUND_TEXT", resources.getString(R.string.pyramid_manual_first_third_round));
			html = html.replace("$FIRST_FOURTH_ROUND_HEADER", resources.getString(R.string.pyramid_manual_first_fourth_round_header));
			html = html.replace("$FIRST_FOURTH_ROUND_TEXT", resources.getString(R.string.pyramid_manual_first_fourth_round));
			html = html.replace("$SECOND_ROUND_HEADER", resources.getString(R.string.pyramid_manual_second_round_header));
			html = html.replace("$SECOND_ROUND_TEXT", resources.getString(R.string.pyramid_manual_second_round));
			html = html.replace("$FINAL_ROUND_HEADER", resources.getString(R.string.pyramid_manual_final_round_header));
			html = html.replace("$FINAL_ROUND_TEXT", resources.getString(R.string.pyramid_manual_final_round));
			webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
			is.close();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
}
