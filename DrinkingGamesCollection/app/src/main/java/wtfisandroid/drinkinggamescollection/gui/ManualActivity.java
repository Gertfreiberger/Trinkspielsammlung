package wtfisandroid.drinkinggamescollection.gui;

import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import wtfisandroid.drinkinggamescollection.R;
import wtfisandroid.drinkinggamescollection.logic.Utilities;
import wtfisandroid.drinkinggamescollection.logic.page_transformations.DepthPageTransformer;
import wtfisandroid.drinkinggamescollection.logic.page_transformations.ZoomOutPageTransformer;

public class ManualActivity extends AppCompatActivity {

	private static final String TAG = "manual";
	private List<String> tab_titles;
	private SharedPreferences sharedPref;
	private Utilities utilities;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		utilities = new Utilities(getApplicationContext());
		sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String currentLanguage = sharedPref.getString(Utilities.LANGUAGE_PREFERENCE_KEY, Locale.getDefault().getDisplayLanguage());
		utilities.setLanguage(currentLanguage);
		setContentView(R.layout.activity_manual);
		ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
		TabsPagerAdapter mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(mAdapter);
		Random rand = new Random();
		int animation = rand.nextInt(2) + 1;
		if ( animation == 1 )
			viewPager.setPageTransformer(true, new DepthPageTransformer());
		else
			viewPager.setPageTransformer(true, new ZoomOutPageTransformer());

		String[] tabs = getResources().getStringArray(R.array.manualTabs);
		tab_titles = Arrays.asList(tabs);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_manual, menu);
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
		if ( sharedPref.getBoolean(Utilities.SOUND_PREFERENCE_KEY, false) ) {
			utilities.playSound(1, AudioManager.FX_KEYPRESS_RETURN);
		}
		super.onBackPressed();
	}

	public class TabsPagerAdapter extends FragmentPagerAdapter {

		private int NUM_ITEMS = 3;

		public TabsPagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		/**
		 * Return a unique identifier for the item at the given position.
		 * <p/>
		 * <p>The default implementation returns the given position.
		 * Subclasses should override this method if the positions of items can change.</p>
		 *
		 * @param position Position within this adapter
		 * @return Unique identifier for the item at position
		 */
		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;
			switch ( position ) {
				case 0:
					fragment = new GeneralFragment();
					break;
				case 1:
					fragment = new IHaveNeverEverFragment();
					break;
				case 2:
					fragment = new PyramidFragment();
					break;
				default:
					break;
			}
			return fragment;
		}

		/**
		 * Return the number of views available.
		 */
		@Override
		public int getCount() {
			return NUM_ITEMS;
		}

		/**
		 * This method may be called by the ViewPager to obtain a title string
		 * to describe the specified page. This method may return null
		 * indicating no title for this page. The default implementation returns
		 * null.
		 *
		 * @param position The position of the title requested
		 * @return A title for the requested page
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			return tab_titles.get(position);
		}
	}

	public static class GeneralFragment extends Fragment {

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.manual, container, false);
			WebView webView = (WebView) rootView.findViewById(R.id.wv_manual);
			AssetManager asset = getActivity().getAssets();
			webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
			webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
			if ( Build.VERSION.SDK_INT >= 19 ) {
				// chromium, enable hardware acceleration
				webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
			} else {
				// older android version, disable hardware acceleration
				webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
			}
			Resources resources = getActivity().getApplicationContext().getResources();
			Utilities util = new Utilities(getContext());
			try {

				InputStream is = asset.open("www/manual_general.html", AssetManager.ACCESS_BUFFER);
				String html = util.streamToString(is);

				webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
				is.close();
			} catch ( IOException e ) {
				e.printStackTrace();
			}
			return rootView;
		}
	}

	public static class IHaveNeverEverFragment extends Fragment {

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.manual, container, false);
			WebView webView = (WebView) rootView.findViewById(R.id.wv_manual);
			AssetManager asset = getActivity().getAssets();
			webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
			webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
			if ( Build.VERSION.SDK_INT >= 19 ) {
				// chromium, enable hardware acceleration
				webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
			} else {
				// older android version, disable hardware acceleration
				webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
			}
			Resources resources = getActivity().getApplicationContext().getResources();
			Utilities util = new Utilities(getContext());
			try {

				InputStream is = asset.open("www/manual_i_have_never_ever.html", AssetManager.ACCESS_BUFFER);
				String html = util.streamToString(is);

				webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
				is.close();
			} catch ( IOException e ) {
				e.printStackTrace();
			}
			return rootView;
		}
	}

	public static class PyramidFragment extends Fragment {

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.manual, container, false);
			WebView webView = (WebView) rootView.findViewById(R.id.wv_manual);
			webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
			webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
			if ( Build.VERSION.SDK_INT >= 19 ) {
				// chromium, enable hardware acceleration
				webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
			} else {
				// older android version, disable hardware acceleration
				webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
			}
			AssetManager asset = getActivity().getAssets();
			Resources resources = getActivity().getApplicationContext().getResources();
			Utilities util = new Utilities(getContext());
			try {

				InputStream is = asset.open("www/manual_pyramid.html", AssetManager.ACCESS_BUFFER);
				String html = util.streamToString(is);

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


			return rootView;
		}
	}

}
