package com.neuedu.runtime;

import com.neuedu.base.BaseSprite;
import com.neuedu.base.Drawable;
import com.neuedu.base.Moveable;
import com.neuedu.constant.FrameConstant;
import com.neuedu.main.GameFrame;
import com.neuedu.util.DataStore;
import com.neuedu.util.ImageMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Boos extends BaseSprite implements Drawable, Moveable {

    private List<Image> imageList = new ArrayList<>();//不会被并发访问，所以没问题

    private int speed = FrameConstant.GAME_SPEED * 2;

    public Boos() {
        for (int i = 1; i < 3; i++) {
            imageList.add(ImageMap.get("boos" + i));
        }
    }

    private int index = 0;
    private static boolean sw = false;

    public static void setSw(boolean sw) {
        Boos.sw = sw;
    }

    public static boolean isSw() {
        return sw;
    }

    @Override
    public void draw(Graphics g) {
        move();
        borderTesting();
        if (sw) {
            g.drawImage(imageList.get(index++ / 10),
                    getX(), getY(), imageList.get(0).getWidth(null),
                    imageList.get(0).getHeight(null), null);
        }
        if (index >= 20) {
            index = 0;
        }
    }



    @Override
    public void move() {
        if (sw) {
            if (right&&top) {
                setX(getX() + speed * 3);
                setY(getY() + speed * 3);
            } if(right&&!top) {
                setX(getX() + speed * 3);
                setY(getY() - speed * 3);
            }
            if (!right&&top){
                setX(getX() - speed * 3);
                setY(getY() + speed * 3);
            }
            if (!right&&!top){
                setX(getX() - speed * 3);
                setY(getY() - speed * 3);
            }
        }
    }
    private boolean right = true;
    private boolean top = true;
    public void borderTesting() {
        if (getX() > FrameConstant.FRAME_WIDTH - imageList.get(0).getWidth(null)) {
            right = false;
        }
        if (getX() < 0) {
            right = true;
        }
        if(getY()>FrameConstant.FRAME_HEIGHT-imageList.get(0).getHeight(null)){
            top=false;
        }
        if (getY()<30){
            top=true;
        }
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(getX(),getY(),imageList.get(0).getWidth(null),imageList.get(0).getHeight(null));
    }
    public void collisionTesting(Plane plane){
        GameFrame gameFrame= DataStore.get("gameFrame");
        if(plane.getRectangle().intersects(this.getRectangle())){
            gameFrame.gameOver=true;
        }
    }

    public void collisionTesting(List<Bullet> bulletList){
        GameFrame gameFrame= DataStore.get("gameFrame");
        for (Bullet bullet : bulletList) {
            if (bullet.getRectangle().intersects(this.getRectangle())){
                bulletList.remove(bullet);
                GameFrame.boosblood--;
                if (GameFrame.boosblood<0){
                    gameFrame.gameOver=true;

                }
            }
        }
    }

}
