package wtfisandroid.drinkinggamescollection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {


    private Button button_mainmenu_maexchen_;
    private Button button_mainmenu_kingscup_;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button_mainmenu_maexchen_ = (Button) findViewById(R.id.button_maexchen);
        button_mainmenu_kingscup_ = (Button) findViewById(R.id.button_kingscup);

        button_mainmenu_maexchen_.setOnClickListener(this);
        button_mainmenu_kingscup_.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        Button clicked_button = (Button) v;

        switch(clicked_button.getId()) {
            case R.id.button_maexchen:
                Intent intent_maexchen_menu = new Intent(this, Maexchen.class);
                startActivity(intent_maexchen_menu);
                break;

            case R.id.button_kingscup:
                Intent intent_kingscup_menu = new Intent(this, Kingscup.class);
                startActivity(intent_kingscup_menu);
                break;

            default:

        }

    }
}
