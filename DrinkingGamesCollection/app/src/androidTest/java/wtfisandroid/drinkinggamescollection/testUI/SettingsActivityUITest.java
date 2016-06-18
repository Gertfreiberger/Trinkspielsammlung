package wtfisandroid.drinkinggamescollection.testUI;

import android.content.res.Resources;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import java.util.Arrays;
import java.util.List;

import wtfisandroid.drinkinggamescollection.R;
import wtfisandroid.drinkinggamescollection.activities.games.settings.SettingsActivity;

public class SettingsActivityUITest extends ActivityInstrumentationTestCase2<SettingsActivity> {

	private Solo solo;
	private Resources resources;

	public SettingsActivityUITest() {
		super(SettingsActivity.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
		resources = solo.getCurrentActivity().getResources();
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testSettings() {
		solo.clickOnText(resources.getString((R.string.general)));
		solo.goBack();
		solo.clickOnText(resources.getString((R.string.pyramid)));
		solo.goBack();
		solo.clickOnText(resources.getString((R.string.maexchen)));
		solo.goBack();
	}

	public void testGeneralSettings() {
		solo.clickOnText(resources.getString((R.string.general)));
		solo.clickOnText(resources.getString((R.string.sound)));
		solo.clickOnText(resources.getString((R.string.sound)));
		solo.clickOnText(resources.getString((R.string.vibrate)));
		solo.clickOnText(resources.getString((R.string.vibrate)));
		solo.clickOnText(resources.getString((R.string.language)));
		solo.goBack();
		solo.clickOnText(resources.getString((R.string.language)));
		String[] languages = resources.getStringArray(R.array.availabeLanguages);
		List<String> languageList = Arrays.asList(languages);
		solo.clickOnText(languageList.get(0));
		solo.clickOnText(resources.getString((R.string.language)));
		solo.clickOnText(languageList.get(1));
		solo.clickOnText(resources.getString((R.string.language)));
		solo.clickOnText(languageList.get(1));
		solo.clickOnText(resources.getString((R.string.language)));
		solo.clickOnText(languageList.get(0));
		solo.goBack();
	}

	public void testPyramidSettings() {
		solo.clickOnText(resources.getString((R.string.pyramid)));
		solo.clickOnText(resources.getString((R.string.player_count)));
		solo.clearEditText(0);
		solo.enterText(0, "9999");
		solo.clickOnView(solo.getView(android.R.id.button1));
		solo.clickOnText(resources.getString((R.string.player_count)));
		solo.clearEditText(0);
		solo.enterText(0, "0");
		solo.clickOnView(solo.getView(android.R.id.button1));
		solo.clickOnText(resources.getString((R.string.player_count)));
		solo.clearEditText(0);
		solo.enterText(0, "6");
		solo.clickOnView(solo.getView(android.R.id.button1));
		solo.clickOnText(resources.getString((R.string.player_count)));
		solo.clearEditText(0);
		solo.enterText(0, "1");
		solo.clickOnView(solo.getView(android.R.id.button1));
		solo.clickOnText(resources.getString((R.string.player_count)));
		solo.clearEditText(0);
		solo.enterText(0, "14");
		solo.clickOnView(solo.getView(android.R.id.button1));
		solo.clickOnText(resources.getString((R.string.player_count)));
		solo.clearEditText(0);
		solo.enterText(0, "2");
		solo.clickOnView(solo.getView(android.R.id.button1));
		solo.goBack();
	}

	public void testExportDatabase() {
		solo.clickOnText(resources.getString((R.string.i_have_never_ever)));
		solo.clickOnText(resources.getString((R.string.export)));
		solo.sleep(2000);
	}


}