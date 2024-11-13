import javax.swing.*;
public class Enemy extends Characters{
    public Enemy(){
        super();
    }
    public Enemy(int x, int y, int w, int h, int speed, int hea, int dam, int st, ImageIcon pic, Weapons weap){
        super(x,y,w,h,speed,hea,dam,st,pic, weap);
    }
}
