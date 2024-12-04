import javax.swing.ImageIcon;
import java.awt.*;
public class Ranged extends Weapons {
    public Ranged(){
        super();
    }
    public Ranged(int x, int y,int dam, int dur, int dp, ImageIcon pic){
        super(dam,dur,dp,pic);
    }
    public void drawAtt(Graphics g2d){
        g2d.drawImage(pic.getImage(), x,y, null);
    }


    
}
