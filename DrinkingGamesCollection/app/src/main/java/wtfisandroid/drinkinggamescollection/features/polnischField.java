package wtfisandroid.drinkinggamescollection.features;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import wtfisandroid.drinkinggamescollection.R;

public class polnischField {

    private ArrayList<Player> players_;
    private int field_number_;
    private LinearLayout field_;
    private ArrayList<ImageView> field_icons_from_player_;
    private int icon_position_;
    private Drawable border_;


    public polnischField(int field_number, LinearLayout field, ImageView left, ImageView middle, ImageView right) {

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
        field_icons_from_player_.get(icon_position_).setImageBitmap(player.getIcon());
        field_icons_from_player_.get(icon_position_).setVisibility(View.VISIBLE);
        if(icon_position_ < 3) {
            icon_position_++;
        }
        else {
            icon_position_ = 0;
        }
    }

    public void playerLeave(Player player) {
        players_.remove(players_.indexOf(player));

        for (int i = 0; i <field_icons_from_player_.size(); i++) {

            if(((BitmapDrawable)field_icons_from_player_.get(i).getDrawable()).getBitmap().sameAs(player.getIcon())) {
                field_icons_from_player_.get(i).setVisibility(View.INVISIBLE);
                icon_position_ = i;
            }
        }
    }



}
