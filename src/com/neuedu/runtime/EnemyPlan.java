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
    private Image image2;
    private Image image3;
    private int type;
    private int speed= FrameConstant.GAME_SPEED*2;
    //生成随机数，减少敌方飞机子弹数量
    private Random random=new Random();

    public EnemyPlan() {
        this(0,0,1);
    }

    public EnemyPlan(int x, int y,int type) {
        super(x, y);
        this.type=type;
        this.image = ImageMap.get("ep01");
        this.image2 = ImageMap.get("ep02");
        this.image3 = ImageMap.get("ep03");

    }

    @Override
    public void draw(Graphics g) {
        move();
        borderTesting();
        //控制敌方飞机出现在屏幕上时才发射子弹
        if (getY()>30){
            fire();
        }
        if (type==1){
            g.drawImage(image,getX(),getY(),image.getWidth(null),image.getHeight(null),null);
        }if(type==2){
            g.drawImage(image2,getX(),getY(),image.getWidth(null),image.getHeight(null),null);
        }if (type==3){
            g.drawImage(image3,getX(),getY(),image.getWidth(null),image.getHeight(null),null);

        }


    }
    public void fire(){
        GameFrame gameFrame=DataStore.get("gameFrame");
        //生成随机数，控制敌方子弹数量
        if(random.nextInt(1000)>990){
            if (type==1){
                gameFrame.enemyBulletList.add(new EnemyBullet(
                        getX()+image.getWidth(null)/2-ImageMap.get("epb01").getWidth(null)/2,
                        getY()+image.getHeight(null),
                        ImageMap.get("epb01")));
            }
            if (type==2){
                gameFrame.enemyBulletList.add(new EnemyBullet(
                        getX()+image.getWidth(null)/2-ImageMap.get("epb02").getWidth(null)/2,
                        getY()+image.getHeight(null),
                        ImageMap.get("epb02")));
            }
            if(type==3){
                gameFrame.enemyBulletList.add(new EnemyBullet(
                        getX()+image.getWidth(null)/2-ImageMap.get("epb03").getWidth(null)/2,
                        getY()+image.getHeight(null),
                        ImageMap.get("epb03")));
            }

        }

    }

    private boolean right=true;
    @Override
    public void move() {
        if (type==1){
            setY(getY()+speed);
        }
        if (type==2){
            if (right){
                setX(getX()+speed);
                setY(getY()+speed);
            }else{
                setX(getX()-speed);
                setY(getY()+speed);
            }

//            if (getX()>FrameConstant.FRAME_WIDTH-image2.getWidth(null)){
//                setX(getX()-speed);
//                setY(getY()+speed);
//            }
//            if (getX()<image2.getWidth(null)-FrameConstant.FRAME_WIDTH){
//                setX(getX()+speed);
//                setY(getY()+speed);
//            }
        }
        if (type==3){
            if (right){
                setX(getX()-speed);
                setY(getY()+speed);
            }else{
                setX(getX()+speed);
                setY(getY()+speed);
            }

//            if (getX()<image3.getWidth(null)-FrameConstant.FRAME_WIDTH){
//                setX(getX()+speed);
//                setY(getY()+speed);
//            }
//            if (getX()>FrameConstant.FRAME_WIDTH-image3.getWidth(null)){
//                setX(getX()-speed);
//                setY(getY()+speed);
//            }
        }
    }
    public void borderTesting(){

            if (getY()>FrameConstant.FRAME_HEIGHT){
                GameFrame gameFrame= DataStore.get("gameFrame");
                gameFrame.enemyPlanList.remove(this);
            }
            if (type==2){
                if (getX()>FrameConstant.FRAME_WIDTH-image.getWidth(null)){
                    right=false;
                }
                if (getX()<0){
                    right=true;
                }
            }
            if(type==3){
                if (getX()<0){
                    right=false;
                }
                if (getX()>FrameConstant.FRAME_WIDTH-image.getWidth(null)){
                    right=true;
                }
            }
    }

    @Override
    public Rectangle getRectangle() {
        if (type==1){
            return new Rectangle(getX(),getY(),image.getWidth(null),image.getHeight(null));
        }
        if (type==2){
            return new Rectangle(getX(),getY(),image2.getWidth(null),image2.getHeight(null));
        }
        else {
            return new Rectangle(getX(),getY(),image3.getWidth(null),image3.getHeight(null));

        }
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
