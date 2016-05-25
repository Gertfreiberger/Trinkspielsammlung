package wtfisandroid.drinkinggamescollection.features;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import wtfisandroid.drinkinggamescollection.R;

public class PolnischField {

    private ArrayList<Player> players_;
    private int field_number_;
    private LinearLayout field_;
    private ArrayList<ImageView> field_icons_from_player_;
    private int icon_position_;
    private Drawable border_;
    private int row_;
    private int collum_;
    private static int divide_number_ = 9;


    public PolnischField(int field_number, LinearLayout field, ImageView left, ImageView middle, ImageView right) {

        players_ = new ArrayList<Player>();
        field_icons_from_player_ = new ArrayList<ImageView>();
        field_icons_from_player_.add(left);
        field_icons_from_player_.add(middle);
        field_icons_from_player_.add(right);
        field_number_ = field_number;
        field_ = field;
        icon_position_ = 0;
        border_ = field_.getBackground();
    }

    public void changeBorderRed(Drawable red_border) {
       field_.setBackground(red_border);
    }

    public void changeBorderBlack() {
        field_.setBackground(border_);
    }

    public void playerArrived(Player player) {
        players_.add(player);
        player.setField(field_number_);
        field_icons_from_player_.get(icon_position_).setImageBitmap(player.getIcon());
        field_icons_from_player_.get(icon_position_).setVisibility(View.VISIBLE);

        if(players_.size() > 3) {

            for(int i = 0; i < players_.size(); i++) {

                if(players_.get(i).getUsedIconField() == icon_position_){
                    players_.get(i).setUsedIconField(100);
                }
            }
        }

        player.setUsedIconField(icon_position_);

        if(icon_position_ < 2) {
            icon_position_++;
        }
        else {
            icon_position_ = 0;
        }

    }

    public void playerLeave(Player player) {
        players_.remove(players_.indexOf(player));

        if(player.getUsedIconField() != 100){
            if(players_.size() < 3){
                field_icons_from_player_.get(player.getUsedIconField()).setVisibility(View.INVISIBLE);
            }
            else {

                for(int i = 0; i < players_.size(); i++){

                    if(players_.get(i).getUsedIconField() == 100){
                        field_icons_from_player_.get(player.getUsedIconField()).setImageBitmap(players_.get(i).getIcon());
                        players_.get(i).setUsedIconField(player.getUsedIconField());
                    }
                }
            }
            icon_position_ = player.getUsedIconField();
        }
    }

    public int getFieldNumber() {
        return field_number_;
    }

    public int getHeight() {
        return field_.getLayoutParams().height;
    }

    public int getWidth() {
        return field_.getLayoutParams().width;
    }

    public LinearLayout getField() {
        return field_;
    }

    public void setRowAndCollum(int cell_index) {
        row_ = cell_index / divide_number_;
        collum_ = cell_index % divide_number_;
    }

    public int getRow() {
        return row_;
    }

    public int getCollum() {
        return collum_;
    }


}
