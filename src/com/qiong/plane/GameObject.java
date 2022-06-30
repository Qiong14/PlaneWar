package com.qiong.plane;

import java.awt.*;

public class GameObject {

    Image img;
    double x,y;
    int speed;
    int width,height;

    public GameObject(Image img, double x, double y, int speed, int width, int height) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.width = width;
        this.height = height;
    }

    public GameObject(Image img, double x, double y) {
        this.img = img;
        this.x = x;
        this.y = y;
        if(img!=null){
            this.width = img.getWidth(null);
            this.height = img.getHeight(null);
        }
    }

    public GameObject(){

    }

    public void drawMyself(Graphics g){
        g.drawImage(img,(int)x,(int)y,width,height,null);
    }

    //返回物体矩形，用于碰撞体积检测
    public Rectangle getRect(){
        return new Rectangle((int)x, (int)y, width, height);
    }

}
