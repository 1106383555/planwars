package com.neuedu.runtime;

import com.neuedu.base.BaseSprite;
import com.neuedu.base.Drawable;
import com.neuedu.util.ImageMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Ending extends BaseSprite implements Drawable {
    private List<Image> imageList = new ArrayList<>();//不会被并发访问，所以没问题

    public Ending() {
        for (int i = 0; i < 9; i++) {
            imageList.add(ImageMap.get("end" + i));
        }
    }
    private int index=0;

    @Override
    public void draw(Graphics g) {
        if(index<54){
            g.drawImage(imageList.get(index++/6),
                    getX()+120, getY()+300, imageList.get(0).getWidth(null),
                    imageList.get(0).getHeight(null), null);
        }
    }
}
