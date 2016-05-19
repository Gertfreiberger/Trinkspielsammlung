package wtfisandroid.drinkinggamescollection.logic;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.AudioManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AnimationUtils;

import java.util.Locale;

public class Utilities {

	private static final String TAG = "utilities";

	private final Context context;

	public final static String SOUND_PREFERENCE_KEY = "sound";
	public final static String VIBRATE_PREFERENCE_KEY = "vibrate";
	public final static String LANGUAGE_PREFERENCE_KEY = "language";
	public final static String SHOW_NAMES_PREFERENCE_KEY = "show_names";

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
	 * @param sound Id of the soundeffect
	 */
	public void playSound(float volume, int sound) {
		AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		am.playSoundEffect(sound, volume);
	}


	/**
	 * @param lang The shortcut of the desired language
	 */
	public void setLanguage(String lang) {
		switch ( lang ){
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
		if ( view != null ) {
			view.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_out));
			view.setVisibility(View.INVISIBLE);
		}
	}

	public void fadeIn(View view) {
		if ( view != null ) {
			view.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in));
			view.setVisibility(View.VISIBLE);
		}
	}

}
