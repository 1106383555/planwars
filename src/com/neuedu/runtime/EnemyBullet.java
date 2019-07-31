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

public class EnemyBullet extends BaseSprite implements Drawable, Moveable {

    private Image image;
    private int speed= FrameConstant.GAME_SPEED*8;


    public EnemyBullet() {
    }

    public EnemyBullet(int x, int y, Image image) {
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
        setY(getY()+speed);
    }
    public void borderTesting(){
        if (getY()>FrameConstant.FRAME_HEIGHT){
            GameFrame gameFrame= DataStore.get("gameFrame");
            gameFrame.enemyBulletList.remove(this);
        }
    }
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(getX(),getY(),image.getWidth(null),image.getHeight(null));
    }


    public void collisionTesting(Plane plane){
        GameFrame gameFrame=DataStore.get("gameFrame");
        if (plane.getRectangle().intersects(this.getRectangle())){
            //敌方子弹与我方飞机碰撞时移除子弹
            gameFrame.enemyBulletList.remove(this);
            gameFrame.gameOver=true;

        }

    }
}
