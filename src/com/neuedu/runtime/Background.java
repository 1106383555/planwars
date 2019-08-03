package com.neuedu.runtime;

import com.neuedu.base.BaseSprite;
import com.neuedu.base.Drawable;
import com.neuedu.base.Moveable;
import com.neuedu.constant.FrameConstant;
import com.neuedu.util.ImageMap;
import com.neuedu.util.ImageUtil;

import java.awt.*;

public class Background extends BaseSprite implements Moveable, Drawable {


    private Image image;

    public Background() {
        //调用自己有参的构造函数
        this(0, FrameConstant.FRAME_HEIGHT -ImageMap.get("bg01").getHeight(null), ImageMap.get("bg01"));
    }

    public Background(int x, int y, Image image) {
        super(x, y);
        this.image = image;
    }

    @Override
    public void move() {
        setY(getY()+FrameConstant.GAME_SPEED);
    }

    @Override
    public void draw(Graphics g) {
        move();
        g.drawImage(image,getX(),getY(),image.getWidth(null),image.getHeight(null),null);
    }
}
