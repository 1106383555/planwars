package com.neuedu.runtime;

import com.neuedu.base.BaseSprite;
import com.neuedu.base.Drawable;
import com.neuedu.base.Moveable;
import com.neuedu.constant.FrameConstant;
import com.neuedu.main.GameFrame;
import com.neuedu.util.DataStore;
import com.neuedu.util.ImageMap;

import java.awt.*;
import java.util.List;

public class Bullet extends BaseSprite implements Drawable, Moveable {

    private int speed= FrameConstant.GAME_SPEED*6;

    private Image image;

    public Bullet() {
//        //无意义
//        this(0,0, ImageMap.get("mb01"));
    }

    public Bullet(int x, int y, Image image) {
        super(x, y);
        this.image = image;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image,getX(),getY(),image.getWidth(null),image.getHeight(null),null);
        move();
        borderTesting();
    }

    @Override
    public void move() {
        setY(getY()-speed);

    }

    public void borderTesting(){
        if (getY()<30-image.getHeight(null)){
            GameFrame gameFrame=DataStore.get("gameFrame");
            gameFrame.bulletList.remove(this);
        }
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(getX(),getY(),image.getWidth(null),image.getHeight(null));
    }

    public void collisionTesting(List<EnemyPlan> enemyPlanList){
        for (EnemyPlan enemyPlan : enemyPlanList) {
            if (enemyPlan.getRectangle().intersects(this.getRectangle())){
                enemyPlanList.remove(enemyPlan);
            }

        }

    }
}
