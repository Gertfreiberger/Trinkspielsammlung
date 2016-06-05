package wtfisandroid.drinkinggamescollection.logic;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.AudioManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;

public class Utilities {

	private static final String TAG = "utilities";

	private final Context context;

	public final static String SOUND_PREFERENCE_KEY = "sound";
	public final static String VIBRATE_PREFERENCE_KEY = "vibrate";
	public final static String LANGUAGE_PREFERENCE_KEY = "language";
	public final static String GAMEDECK_GAME_KEY = "gameDeck";
	public final static String PLAYERHANDS_GAME_KEY = "playerHands";
	public final static String FINAL_PLAYER = "final_player";
	public static final String PYRAMID_PLAYER_COUNT_PREFERENCE_KEY = "pyramid_player_count";

	public Utilities(Context context) {
		this.context = context;
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
		} catch ( IOException ex ){
			ex.printStackTrace();
		}
		return writer.toString();
	}
}
