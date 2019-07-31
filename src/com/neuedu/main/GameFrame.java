package com.neuedu.main;

import com.neuedu.constant.FrameConstant;
import com.neuedu.runtime.*;
import com.neuedu.util.ImageMap;


import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameFrame extends Frame {

    public boolean gameOver=false;
    //创建背景对象
    private Background bg=new Background();
    //创建飞机对象
    private Plane plane=new Plane();

    //创建我方子弹  集合
    public final List<Bullet> bulletList=new CopyOnWriteArrayList<>();//防止并发问题使用CopyOnWriteArrayList

    //创建敌方飞机集合
    public final List<EnemyPlan> enemyPlanList=new CopyOnWriteArrayList<>();

    //创建敌方子弹  集合
    public final List<EnemyBullet> enemyBulletList=new CopyOnWriteArrayList<>();
    @Override
    public void paint(Graphics g) {

        if (!gameOver){
            bg.draw(g);
            plane.draw(g);
            for (Bullet bullet : bulletList) {
                bullet.draw(g);
            }
//        g.setColor(Color.red);
//        g.drawString(""+enemyBulletList.size(),100,100);

            for (EnemyBullet enemyBullet : enemyBulletList) {
                enemyBullet.draw(g);
            }
            for (EnemyPlan enemyPlan : enemyPlanList) {
                enemyPlan.draw(g);
            }

            for (Bullet bullet : bulletList) {
                bullet.collisionTesting(enemyPlanList);
            }
            for (EnemyBullet enemyBullet : enemyBulletList) {
                enemyBullet.collisionTesting(plane);
            }



        }

    }

    /**
     * 初始化窗口
     */
    public void init(){
        //设置好尺寸
        setSize(FrameConstant.FRAME_WIDTH,FrameConstant.FRAME_HEIGHT);

        enableInputMethods(false);
        //设置窗口居中
        setLocationRelativeTo(null);
        //禁止窗口拉大
        setResizable(false);

        //X关闭窗口
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        //添加键盘间监听
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                plane.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                plane.keyReleased(e);
            }
        });




        enemyPlanList.add(new EnemyPlan(300,60, ImageMap.get("ep01")));
        enemyPlanList.add(new EnemyPlan(200,60, ImageMap.get("ep01")));
        enemyPlanList.add(new EnemyPlan(600,60, ImageMap.get("ep01")));
        enemyPlanList.add(new EnemyPlan(100,60, ImageMap.get("ep01")));
        enemyPlanList.add(new EnemyPlan(500,60, ImageMap.get("ep01")));
        enemyPlanList.add(new EnemyPlan(400,60, ImageMap.get("ep01")));
        //窗口始终打开状态
        setVisible(true);


        new Thread(){
            @Override
            public void run() {
                while (true){
                    repaint();
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();
    }

    private Image offScreenImage = null;//创建缓冲区
    @Override
    public void update(Graphics g) {
        if(offScreenImage == null) {
            offScreenImage = this.createImage(FrameConstant.FRAME_WIDTH,FrameConstant.FRAME_HEIGHT);
        }
        Graphics gOff = offScreenImage.getGraphics();//创建离线图片的实例，在图片缓冲区绘图

        paint(gOff);
        g.drawImage(offScreenImage, 0, 0, null);//将缓冲图片绘制到窗口目标
    }

}
