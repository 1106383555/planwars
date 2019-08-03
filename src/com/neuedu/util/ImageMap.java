package com.neuedu.util;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ImageMap {
    private static final Map<String, Image> map=new HashMap<>();
    static {
        //背景图片
        map.put("bg01",ImageUtil.getImage("com\\neuedu\\imgs\\bg\\bg1.jpg"));
        //我方飞机
        map.put("my01",ImageUtil.getImage("com\\neuedu\\imgs\\plane\\my_1.png"));
        //我方子弹
        map.put("mb01",ImageUtil.getImage("com\\neuedu\\imgs\\bullet\\my_1.png"));
        map.put("mb02",ImageUtil.getImage("com\\neuedu\\imgs\\bullet\\my_2.png"));
        //敌方飞机
        map.put("ep01",ImageUtil.getImage("com\\neuedu\\imgs\\plane\\ep_1.png"));
        map.put("ep02",ImageUtil.getImage("com\\neuedu\\imgs\\plane\\ep_2.png"));
        map.put("ep03",ImageUtil.getImage("com\\neuedu\\imgs\\plane\\ep_3.png"));
        //敌方子弹
        map.put("epb01",ImageUtil.getImage("com\\neuedu\\imgs\\bullet\\epb_1.png"));
        map.put("epb02",ImageUtil.getImage("com\\neuedu\\imgs\\bullet\\epb_2.png"));
        map.put("epb03",ImageUtil.getImage("com\\neuedu\\imgs\\bullet\\epb_3.png"));
        //血包
        map.put("blood1",ImageUtil.getImage("com\\neuedu\\imgs\\prop\\blood1.png"));

        //终极boos
        map.put("boos1",ImageUtil.getImage("com\\neuedu\\imgs\\boos\\boos1.png"));
        map.put("boos2",ImageUtil.getImage("com\\neuedu\\imgs\\boos\\boos2.png"));
    }
    public static Image get(String key){
        return map.get(key);
    }
}
