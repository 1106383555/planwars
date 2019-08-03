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
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameFrame extends Frame {


    private static int score = 0;
    public static int blood = 3;
    public static int boosblood = 50;

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        GameFrame.score = score;
    }

    public boolean gameOver = false;
    //创建背景对象
    private Background bg = new Background();
    //创建飞机对象
    private Plane plane = new Plane();

    Random random = new Random();

    //创建我方子弹  集合
    public final List<Bullet> bulletList = new CopyOnWriteArrayList<>();//防止并发问题使用CopyOnWriteArrayList

    //创建敌方飞机集合
    public final List<EnemyPlan> enemyPlanList = new CopyOnWriteArrayList<>();

    //创建敌方子弹  集合
    public final List<EnemyBullet> enemyBulletList = new CopyOnWriteArrayList<>();

    //创建道具集合
    public final List<Prop> propList = new CopyOnWriteArrayList<>();
    public final List<Upb> upbList = new CopyOnWriteArrayList<>();

    //创建终极boos
    private Boos boos = new Boos();
    private Ending ending=new Ending();

    public static int bulletkey=1;

    @Override
    public void paint(Graphics g) {

        if (!gameOver) {
            bg.draw(g);

            plane.draw(g);
            boos.draw(g);



            for (Bullet bullet : bulletList) {
                bullet.draw(g);
            }
            for (Upb upb : upbList) {
                upb.draw(g);
            }
            g.setFont(new Font("楷体", 0, 30));
            g.setColor(Color.red);
            g.drawString("得分：" + score, 80, 100);
            g.drawString("生命值：" + blood, 80, 130);
            g.drawString("Boss生命值：" + boosblood, 80, 160);

            for (EnemyBullet enemyBullet : enemyBulletList) {
                enemyBullet.draw(g);

            }
            for (EnemyPlan enemyPlan : enemyPlanList) {
                enemyPlan.draw(g);

            }
            for (Prop prop : propList) {
                prop.draw(g);
            }
            /**
             *碰撞检测
             */
            //我方子弹与敌方飞机的碰撞
            for (Bullet bullet : bulletList) {
                bullet.collisionTesting(enemyPlanList);
            }
//            敌方子弹与我方飞机的碰撞
            for (EnemyBullet enemyBullet : enemyBulletList) {
                enemyBullet.collisionTesting(plane);
            }
            //敌方飞机与我方飞机的碰撞
            for (EnemyPlan enemyPlan : enemyPlanList) {
                enemyPlan.collisionTesting(plane);
            }
            //血量道具与我方飞机的碰撞
            for (Prop prop : propList) {
                prop.collisionTesting(plane);
            }


            //子弹升级道具与我方飞机的碰撞
            for (Upb upb : upbList) {
                upb.collisionTesting(plane);

            }
            //终极Boss与我方飞机与子弹的碰撞
            if (Boos.isSw()) {
                boos.collisionTesting(plane);
                boos.collisionTesting(bulletList);
            }
        }
        if (gameOver){
            ending.draw(g);
        }
    }

    /**
     * 初始化窗口
     */
    public void init() {
        //设置好尺寸
        setSize(FrameConstant.FRAME_WIDTH, FrameConstant.FRAME_HEIGHT);

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
        //游戏初始时添加敌方飞机
        for (int i = 0; i < 150; i++) {
            enemyPlanList.add(new EnemyPlan(random.nextInt(650), 20 - random.nextInt(9000), 1));
            enemyPlanList.add(new EnemyPlan(random.nextInt(650), 20 - random.nextInt(9000), 2));
            enemyPlanList.add(new EnemyPlan(random.nextInt(650), 20 - random.nextInt(9000), 3));
        }

        //添加道具
        for (int i = 0; i < 50; i++) {
            propList.add(new Prop(random.nextInt(650), -random.nextInt(8000), ImageMap.get("blood1")));
        }
        for (int i = 0; i < 5; i++) {
            upbList.add(new Upb(random.nextInt(650), -random.nextInt(1000), ImageMap.get("upb")));

        }


        //窗口始终打开状态
        setVisible(true);


        new Thread() {
            @Override
            public void run() {
                while (true) {
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

    //解决闪屏问题
    private Image offScreenImage = null;//创建缓冲区

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(FrameConstant.FRAME_WIDTH, FrameConstant.FRAME_HEIGHT);
        }
        Graphics gOff = offScreenImage.getGraphics();//创建离线图片的实例，在图片缓冲区绘图

        paint(gOff);
        g.drawImage(offScreenImage, 0, 0, null);//将缓冲图片绘制到窗口目标
    }

}
