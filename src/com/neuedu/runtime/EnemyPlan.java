package com.neuedu.runtime;

import com.neuedu.base.BaseSprite;
import com.neuedu.base.Drawable;
import com.neuedu.base.Moveable;
import com.neuedu.constant.FrameConstant;
import com.neuedu.main.GameFrame;
import com.neuedu.util.DataStore;
import com.neuedu.util.ImageMap;

import java.awt.*;
import java.util.Random;

public class EnemyPlan extends BaseSprite implements Drawable, Moveable {

    private Image image;
    private int speed= FrameConstant.GAME_SPEED*2;
    //生成随机数，减少敌方飞机子弹数量
    private Random random=new Random();

    public EnemyPlan() {
        this(0,0, ImageMap.get("ep01"));
    }

    public EnemyPlan(int x, int y, Image image) {
        super(x, y);
        this.image = image;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image,getX(),getY(),image.getWidth(null),image.getHeight(null),null);
        move();
        borderTesting();
        fire();


    }
    public void fire(){
        GameFrame gameFrame=DataStore.get("gameFrame");
        //生成随机数，控制敌方子弹数量
        if(random.nextInt(1000)>990){
            gameFrame.enemyBulletList.add(new EnemyBullet(
                    getX()+image.getWidth(null)/2-ImageMap.get("epb01").getWidth(null)/2,
                    getY()+image.getHeight(null),
                    ImageMap.get("epb01")));
        }

    }

    @Override
    public void move() {
        setY(getY()+speed);
    }
    public void borderTesting(){
        if (getY()>FrameConstant.FRAME_HEIGHT){
            GameFrame gameFrame= DataStore.get("gameFrame");
            gameFrame.enemyPlanList.remove(this);
        }
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(getX(),getY(),image.getWidth(null),image.getHeight(null));
    }
}
