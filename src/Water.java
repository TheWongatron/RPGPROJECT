import javax.swing.ImageIcon;
public class Water extends Ranged {
    public Water(){
        super();
    }

    public Water(int x,int y){
        super(x-100,y-200,100,2,159, new ImageIcon("blastoiseattack.png"));
    }

    public String toString(){
        return "Water";
    }
}
