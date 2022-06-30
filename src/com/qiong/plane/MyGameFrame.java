package com.qiong.plane;

import javax.xml.crypto.Data;
import java.awt.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;

import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;

public class MyGameFrame extends Frame {

    Image planeImg = GameUtil.getImage("images/飞机改.jpg");
    Image bg = GameUtil.getImage("images/background.png");

    Date startTime = new Date();
    Date endTime;

    private Image offScreenImage = null;

    //以下为解决双闪
    Plane plane = new Plane(planeImg, 300, 300, 10);

    ArrayList<Shell> shellList = new ArrayList<Shell>();

    public void update(Graphics g) {
        if(offScreenImage == null)
            offScreenImage = this.createImage(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);//这是游戏窗口的宽度和高度
        Graphics gOff = offScreenImage.getGraphics();
        paint(gOff);
        g.drawImage(offScreenImage, 0, 0, null);
    }


    public void paint(Graphics g){
        g.drawImage(bg,0,0,Constant.GAME_WIDTH,Constant.GAME_HEIGHT, null);
        plane.drawMyself(g);
        for (int i = 0; i < shellList.size(); i++){
            Shell b = shellList.get(i);
            b.draw(g);
            boolean p = b.getRect().intersects(plane.getRect());
            if(p){
                plane.live = false;
                endTime = new Date();
            }
        }
        if(!plane.live){
            if(endTime==null){
                endTime = new Date();
            }
            int period = (int)((endTime.getTime()-startTime.getTime())/1000);
            printInfo(g, "时间："+period+"秒", 50, 120, 260, Color. white );
        }
    }

    public void printInfo(Graphics g,String str,int size,int x,int y,Color color) {
        Color c = g.getColor();
        g.setColor(color);
        Font f = new Font("宋体", Font.BOLD, size);
        g.setFont(f);
        g.drawString(str, x, y);
        g.setColor(c);
    }

    public void launchFrame() {
        setTitle("作品展示");
        setVisible(true);
        setSize(500, 500);
        setLocation(300, 300);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        new PaintThread().start();
        addKeyListener(new KeyMonitor());
        for(int i=0;i<100;i++){
            Shell b = new Shell();
            shellList.add(b);
        }
    }

    //重画窗口的线程
    class PaintThread extends Thread{
        public void run(){
            while(true){
                repaint();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    class KeyMonitor extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            plane.addDirection(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            plane.minusDirection(e);
        }
    }

    public static void main(String[] args) {
        MyGameFrame f = new MyGameFrame();
        f.launchFrame();

    }
}

