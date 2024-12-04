import javax.swing.ImageIcon;
public class Thunder extends Ranged {
    public Thunder(){
        super();
    }

    public Thunder(int x,int y){
        super(x-100,y-200,100,2,159, new ImageIcon("pikachuattack.png"));
    }

    public String toString(){
        return "Thunder";
    }
}
