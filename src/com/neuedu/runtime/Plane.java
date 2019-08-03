package com.neuedu.runtime;

import com.neuedu.base.BaseSprite;
import com.neuedu.base.Drawable;
import com.neuedu.base.Moveable;
import com.neuedu.constant.FrameConstant;
import com.neuedu.main.GameFrame;
import com.neuedu.util.DataStore;
import com.neuedu.util.ImageMap;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Plane extends BaseSprite implements Drawable, Moveable {

    private Image image;

    private boolean up,right,down,left;
    private boolean fire;
    private int speed=FrameConstant.GAME_SPEED*8;
    private int index=0;

    public Plane() {
        this(FrameConstant.FRAME_WIDTH/2-ImageMap.get("my01").getWidth(null)/2,

                650,//改进
                ImageMap.get("my01"));
    }

    public Plane(int x, int y, Image image) {
        super(x, y);
        this.image = image;
    }

    @Override
    public void draw(Graphics g) {
        //调用move方法
        move();
        borderTesting();
        fire();
        g.drawImage(image,getX(),getY()+30,image.getWidth(null),image.getHeight(null),null);




        //子弹控制器,if是为了没有开火时不进行循环
        if (fire){
            index++;
            if(index>5){
                index=0;
            }
        }

    }

    private int zidan;

    /**
     * 开火方法
     * 判断开关是否打开
     * 创建一个子弹对象放入到gameFrame集合中
     */
    public void fire(){
        if (fire&&index==0&&zidan==1){
            GameFrame gameFrame=DataStore.get("gameFrame");
            gameFrame.bulletList.add(new Bullet(
                    getX()+image.getWidth(null)/2-ImageMap.get("mb01").getWidth(null)/2,
                    getY()-ImageMap.get("mb01").getHeight(null),
                    ImageMap.get("mb01")
            ));
        }
        if (fire&&index==0&&zidan==2){
            GameFrame gameFrame=DataStore.get("gameFrame");
            gameFrame.bulletList.add(new Bullet(
                    getX()+image.getWidth(null)/2-ImageMap.get("mb02").getWidth(null)/2,
                    getY()-ImageMap.get("mb02").getHeight(null),
                    ImageMap.get("mb02")
            ));
        }
    }

    @Override
    public void move() {
        if (up){
            setY(getY()-speed);
        }
        if (right){
            setX(getX()+speed);
        }
        if (down){
            setY(getY()+speed);
        }
        if (left){
            setX(getX()-speed);
        }

    }

    public void borderTesting(){
        if(getX()<0){
            setX(0);
        }
        if(getX()>FrameConstant.FRAME_WIDTH-image.getWidth(null)){
            setX(FrameConstant.FRAME_WIDTH-image.getWidth(null));
        }
        if(getY()<0){
            setY(0);
        }
        if(getY()>650){
            setY(650);
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_W){
            up=true;
        }
        if (e.getKeyCode()==KeyEvent.VK_D){
            right=true;
        }
        if (e.getKeyCode()==KeyEvent.VK_S){
            down=true;
        }
        if (e.getKeyCode()==KeyEvent.VK_A){
            left=true;
        }
        if (e.getKeyCode()==KeyEvent.VK_J){
            fire=true;
            if(GameFrame.bulletkey==1){
                zidan=1;
            }
            if (GameFrame.bulletkey==2){
                zidan=2;
            }

        }



    }
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_W){
            up=false;
        }
        if (e.getKeyCode()==KeyEvent.VK_D){
            right=false;
        }
        if (e.getKeyCode()==KeyEvent.VK_S){
            down=false;
        }
        if (e.getKeyCode()==KeyEvent.VK_A){
            left=false;
        }
        if (e.getKeyCode()==KeyEvent.VK_J){
            fire=false;
        }


    }

    //有bug，有偏移
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(getX()+20,getY()+20,image.getWidth(null)/3,image.getHeight(null)/3);
    }
}
