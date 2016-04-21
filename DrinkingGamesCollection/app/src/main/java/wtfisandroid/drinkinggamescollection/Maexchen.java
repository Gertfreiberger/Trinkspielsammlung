package wtfisandroid.drinkinggamescollection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
    private Button button_left_;
    private Button button_right_;
    private ImageButton button_help_;
    private TextView dice_result_;
    private Random number_generator_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maexchen_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        button_left_ = (Button) findViewById(R.id.maexchen_button_left);
        button_right_ = (Button) findViewById(R.id.maexchen_button_right);
        button_help_ = (ImageButton) findViewById(R.id.maexchen_button_help);
        dice_left_ = (ImageView) findViewById(R.id.imageView_dice_left);
        dice_right_ = (ImageView) findViewById(R.id.imageView_dice_right);
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


    }

}
