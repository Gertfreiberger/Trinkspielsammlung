package wtfisandroid.drinkinggamescollection.data;

import android.graphics.Bitmap;

public class Player {

    private String name_;
    private int field_;
    private Bitmap icon_;
    private int used_icon_field_;
    private boolean pause_;

    public Player(String name) {
        name_ = name;
        used_icon_field_ = 100;
        pause_ = false;
    }

    public void setField(int field) {
        field_ = field;
    }

    public void setUsedIconField(int icon_number) {
        used_icon_field_ = icon_number;
    }

    public void setName(String name) {
        name_ = name;
    }

    public void setIcon(Bitmap icon) {
        icon_ = icon;
    }

    public void setPause(boolean pause) {
        pause_ = pause;
    }

    public int getField() {
        return field_;
    }

    public String getName() {
        return name_;
    }

    public Bitmap getIcon() {
        return icon_;
    }

    public boolean getPause() {
        return pause_;
    }

    public int getUsedIconField() {
        return used_icon_field_;
    }



}
