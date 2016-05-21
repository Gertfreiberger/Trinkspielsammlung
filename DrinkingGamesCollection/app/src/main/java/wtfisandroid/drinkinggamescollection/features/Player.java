package wtfisandroid.drinkinggamescollection.features;

import android.graphics.Bitmap;

public class Player {

    private String name_;
    private int field_;
    private Bitmap icon_;

    public Player(String name) {
        name_ = name;
    }

    public void setField(int field) {
        field_ = field;
    }

    public void setName(String name) {
        name_ = name;
    }

    public void setIcon(Bitmap icon) {
        icon_ = icon;
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



}
