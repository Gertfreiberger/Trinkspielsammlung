package wtfisandroid.drinkinggamescollection;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class Maexchen extends AppCompatActivity implements View.OnClickListener {

    private ImageView dice_left_;
    private ImageView dice_right_;
    private ImageView cup_;
    private Button button_left_;
    private Button button_right_;
    private Button button_help_;
    private StatesMaexchen state_maexchen_;
    private TextView dice_result_;
    private Random number_generator_;

    public enum StatesMaexchen {
        FIRST_THROW, THROW_RESULT , SECOND_THROW, TRUST, UNCOVER
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maexchen_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        state_maexchen_ = StatesMaexchen.FIRST_THROW;

        button_left_ = (Button) findViewById(R.id.maexchen_button_left);
        button_right_ = (Button) findViewById(R.id.maexchen_button_right);
        button_help_ = (Button) findViewById(R.id.maexchen_button_help);

        dice_left_ = (ImageView) findViewById(R.id.imageView_dice_left);
        dice_right_ = (ImageView) findViewById(R.id.imageView_dice_right);
        cup_ = (ImageView) findViewById(R.id.maexchen_imageView_cup);

        dice_result_ = (TextView) findViewById(R.id.maexchen_textview_dice);

        number_generator_ = new Random();
        Bitmap left_dice = BitmapFactory.decodeResource(getResources(),R.mipmap.dice_1);
        Bitmap right_dice = BitmapFactory.decodeResource(getResources(),R.mipmap.dice_2);
        dice_left_.setImageBitmap(left_dice);
        dice_right_.setImageBitmap(right_dice);
        
        button_help_.setOnClickListener(this);
        button_left_.setOnClickListener(this);
        button_right_.setOnClickListener(this);
    }

    public void onClick(View v) {
        Button clicked_button = (Button) v;

        switch (clicked_button.getId()) {
            case R.id.maexchen_button_help:
                Intent rule_page = new Intent(this, MaexchenRules.class);
                startActivity(rule_page);
                break;

            case R.id.maexchen_button_left:
                buttonLeftClicked();
                break;

            case R.id.maexchen_button_right:
                buttonRightClicked();
                break;

            default:

        }
    }


    public void buttonLeftClicked() {

        switch (state_maexchen_) {
            case FIRST_THROW:
                state_maexchen_ = StatesMaexchen.THROW_RESULT;
                button_left_.setText(R.string.maexchen_button_throw_again);
                cup_.setVisibility(View.INVISIBLE);
                break;

            case THROW_RESULT:
                state_maexchen_ = StatesMaexchen.SECOND_THROW;
                button_left_.setVisibility(View.INVISIBLE);
                cup_.setVisibility(View.VISIBLE);
                break;

            case TRUST:
                state_maexchen_ = StatesMaexchen.FIRST_THROW;
                button_left_.setText(R.string.maexchen_button_reveal);
                button_right_.setText(R.string.maexchen_button_next);
                break;

            default:

        }
    }

    public void buttonRightClicked() {

        switch (state_maexchen_) {
            case FIRST_THROW:
                state_maexchen_ = StatesMaexchen.TRUST;
                button_left_.setText(R.string.maexchen_button_throw);
                button_right_.setText(R.string.maexchen_button_reveal);
                break;

            case THROW_RESULT:
                state_maexchen_ = StatesMaexchen.TRUST;
                button_left_.setText(R.string.maexchen_button_throw);
                button_right_.setText(R.string.maexchen_button_reveal);
                break;

            case SECOND_THROW:
                state_maexchen_ = StatesMaexchen.TRUST;
                button_left_.setVisibility(View.VISIBLE);
                button_left_.setText(R.string.maexchen_button_throw);
                button_right_.setText(R.string.maexchen_button_reveal);
                break;

            case TRUST:
                state_maexchen_ = StatesMaexchen.UNCOVER;
                button_left_.setVisibility(View.INVISIBLE);
                button_right_.setText(R.string.maexchen_button_next);
                break;

            case UNCOVER:
                state_maexchen_ = StatesMaexchen.FIRST_THROW;
                button_left_.setVisibility(View.VISIBLE);
                button_left_.setText(R.string.maexchen_button_reveal);

                break;

            default:

        }
    }

}
