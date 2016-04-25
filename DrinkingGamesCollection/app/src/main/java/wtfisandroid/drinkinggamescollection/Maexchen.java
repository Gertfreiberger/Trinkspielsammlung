package wtfisandroid.drinkinggamescollection;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import wtfisandroid.drinkinggamescollection.features.Dice;

public class Maexchen extends AppCompatActivity implements View.OnClickListener, SensorEventListener {

    private ImageView dice_image_left_;
    private ImageView dice_image_right_;
    private ImageView cup_;
    private Button button_left_;
    private Button button_right_;
    private Button button_help_;
    private StatesMaexchen state_maexchen_;
    private TextView dice_result_;
    private Dice dice_left_;
    private Dice dice_right_;
    private SensorManager sensor_manager_;
    private Sensor sensor_accelerometer_;
    private long last_update_;
    private float last_x_;
    private float last_y_;
    private float last_z_;
    private static final int SHAKE_THRESHOLD_ = 50;
    private static final int WAITING_SHAKE_ = 100;

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
        cup_ = (ImageView) findViewById(R.id.maexchen_imageView_cup);

        ArrayList<Bitmap> pictures = new ArrayList<Bitmap>();
        createDicePictures(pictures);

        dice_left_ = new Dice(pictures);
        dice_right_ = new Dice(pictures);
        dice_image_left_ = (ImageView) findViewById(R.id.imageView_dice_left);
        dice_image_right_ = (ImageView) findViewById(R.id.imageView_dice_right);
        dice_result_ = (TextView) findViewById(R.id.maexchen_textview_dice);
        dice_result_.setVisibility(View.INVISIBLE );

        dice_left_.wuerfeln(dice_image_left_);
        dice_right_.wuerfeln(dice_image_right_);
        showThrowResult(dice_left_.getLastThrow() + 1, dice_right_.getLastThrow() + 1);

        last_update_ = 0;
        sensor_manager_ = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor_accelerometer_ = sensor_manager_.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensor_manager_.registerListener(this, sensor_accelerometer_, SensorManager.SENSOR_DELAY_NORMAL);
        
        button_help_.setOnClickListener(this);
        button_left_.setOnClickListener(this);
        button_right_.setOnClickListener(this);
    }

    public void createDicePictures(ArrayList<Bitmap> pictures) {
        pictures.add(BitmapFactory.decodeResource(getResources(), R.mipmap.dice_1));
        pictures.add(BitmapFactory.decodeResource(getResources(), R.mipmap.dice_2));
        pictures.add(BitmapFactory.decodeResource(getResources(), R.mipmap.dice_3));
        pictures.add(BitmapFactory.decodeResource(getResources(), R.mipmap.dice_4));
        pictures.add(BitmapFactory.decodeResource(getResources(), R.mipmap.dice_5));
        pictures.add(BitmapFactory.decodeResource(getResources(), R.mipmap.dice_6));
    }

    // For the Buttons events

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
                onPause();
                state_maexchen_ = StatesMaexchen.THROW_RESULT;
                button_left_.setText(R.string.maexchen_button_throw_again);
                cup_.setVisibility(View.INVISIBLE);
                dice_result_.setVisibility(View.VISIBLE);
                break;

            case THROW_RESULT:
                onResume();
                state_maexchen_ = StatesMaexchen.SECOND_THROW;
                button_left_.setVisibility(View.INVISIBLE);
                cup_.setVisibility(View.VISIBLE);
                dice_result_.setVisibility(View.INVISIBLE);
                break;

            case TRUST:
                onResume();
                state_maexchen_ = StatesMaexchen.FIRST_THROW;
                button_left_.setText(R.string.maexchen_button_reveal);
                button_right_.setText(R.string.maexchen_button_next);
                dice_result_.setVisibility(View.INVISIBLE);
                cup_.setVisibility(View.VISIBLE);

                break;

            default:

        }
    }

    public void buttonRightClicked() {

        switch (state_maexchen_) {
            case FIRST_THROW:
                onPause();
                state_maexchen_ = StatesMaexchen.TRUST;
                button_left_.setText(R.string.maexchen_button_throw);
                button_right_.setText(R.string.maexchen_button_reveal);
                break;

            case THROW_RESULT:
                onPause();
                state_maexchen_ = StatesMaexchen.TRUST;
                button_left_.setText(R.string.maexchen_button_throw);
                button_right_.setText(R.string.maexchen_button_reveal);
                cup_.setVisibility(View.VISIBLE);
                dice_result_.setVisibility(View.INVISIBLE);
                break;

            case SECOND_THROW:
                onPause();
                state_maexchen_ = StatesMaexchen.TRUST;
                button_left_.setVisibility(View.VISIBLE);
                button_left_.setText(R.string.maexchen_button_throw);
                button_right_.setText(R.string.maexchen_button_reveal);
                break;

            case TRUST:
                onPause();
                state_maexchen_ = StatesMaexchen.UNCOVER;
                button_left_.setVisibility(View.INVISIBLE);
                button_right_.setText(R.string.maexchen_button_new_turn);
                cup_.setVisibility(View.INVISIBLE);
                dice_result_.setVisibility(View.VISIBLE);
                break;

            case UNCOVER:
                onResume();
                state_maexchen_ = StatesMaexchen.FIRST_THROW;
                button_left_.setVisibility(View.VISIBLE);
                button_left_.setText(R.string.maexchen_button_reveal);
                cup_.setVisibility(View.VISIBLE);
                dice_result_.setVisibility(View.INVISIBLE);
                break;

            default:

        }
    }

    // For the Sensor events

    public void onSensorChanged(SensorEvent event) {

        Sensor active_sensor = event.sensor;

        if(active_sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x_acceleration = event.values[0];
            float y_acceleration = event.values[1];
            float z_acceleration = event.values[2];

            long current_time = System.currentTimeMillis();

            if((current_time - last_update_) > WAITING_SHAKE_) {
                last_update_ = current_time;

                float speed = Math.abs(x_acceleration + y_acceleration + z_acceleration - last_x_ -last_y_ -last_z_);

                if(speed > SHAKE_THRESHOLD_) {
                    dice_left_.wuerfeln(dice_image_left_);
                    dice_right_.wuerfeln(dice_image_right_);
                    showThrowResult(dice_left_.getLastThrow() + 1, dice_right_.getLastThrow() + 1);
                }

                last_x_ = x_acceleration;
                last_y_ = y_acceleration;
                last_z_ = z_acceleration;
            }
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onPause() {
        super.onPause();
        sensor_manager_.unregisterListener(this);
    }

    public void onResume() {
        super.onResume();
        sensor_manager_.registerListener(this, sensor_accelerometer_, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void showThrowResult(int left_throw,int right_throw) {

        if(left_throw == right_throw) {
            dice_result_.setText(Integer.toString(left_throw) + "ser pasch");
        }
        else if((left_throw == 1 && right_throw == 2) || (left_throw == 2 && right_throw == 1)) {
            dice_result_.setText("Mäxchen");
        }
        else if(left_throw < right_throw) {
            dice_result_.setText(Integer.toString(right_throw) + Integer.toString(left_throw));
        }
        else {
            dice_result_.setText(Integer.toString(left_throw) + Integer.toString(right_throw));
        }

    }

}
