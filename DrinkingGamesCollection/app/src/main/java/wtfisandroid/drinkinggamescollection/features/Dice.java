package wtfisandroid.drinkinggamescollection.features;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

import wtfisandroid.drinkinggamescollection.R;


public class Dice {

    private ArrayList<Bitmap> dice_pictures_;
    private int last_throw_;
    private Random throw_dice_;
    private int dice_sides_;

    public Dice(ArrayList<Bitmap> pictures) {
        last_throw_ = 0;
        throw_dice_ = new Random();
        dice_sides_ = 5;

        dice_pictures_ = pictures;
    }

    public int getLastThrow() {
        return last_throw_;
    }

    public void wuerfeln(ImageView image) {
        last_throw_ = throw_dice_.nextInt(dice_sides_);
        image.setImageBitmap(dice_pictures_.get(last_throw_));
    }
}
