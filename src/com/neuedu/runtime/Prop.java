package com.neuedu.runtime;

import com.neuedu.base.BaseSprite;
import com.neuedu.base.Drawable;
import com.neuedu.base.Moveable;
import com.neuedu.constant.FrameConstant;
import com.neuedu.main.GameFrame;
import com.neuedu.util.DataStore;

import java.awt.*;

/**
 * 创建道具类
 */
public class Prop extends BaseSprite implements Drawable, Moveable {
    private Image image;
    private int speed= FrameConstant.GAME_SPEED;

    public Prop() {
    }

    public Prop(int x, int y, Image image) {
        super(x, y);
        this.image = image;
    }

    @Override
    public void draw(Graphics g) {
        move();
        borderTesting();
        g.drawImage(image,getX(),getY(),image.getWidth(null),image.getHeight(null),null);
    }

    private boolean right=true;
    @Override
    public void move() {
        if (right){
            setY(getY()+speed);
            setX(getX()+speed);
        }else{
            setX(getX()-speed);
            setY(getY()+speed);
        }

    }
    //边缘检测
    public void borderTesting(){
        //超出下边框时移除该道具
        if (getY()>FrameConstant.FRAME_HEIGHT){
            GameFrame gameFrame= DataStore.get("gameFrame");
            gameFrame.propList.remove(this);
        }
        if (getX()>FrameConstant.FRAME_WIDTH-image.getWidth(null)){
            right=false;
        }
        if (getX()<0){
            right=true;
        }
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(getX(),getY(),image.getWidth(null),image.getHeight(null));
    }
    //血包与我方飞机碰撞之后移除该血包
    public void collisionTesting(Plane plane){
        GameFrame gameFrame=DataStore.get("gameFrame");
        if (plane.getRectangle().intersects(this.getRectangle())){
            gameFrame.propList.remove(this);
            GameFrame.blood+=1;
        }
    }
}
