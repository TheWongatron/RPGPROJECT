import javax.swing.ImageIcon;
public class Leaf extends Ranged {
    public Leaf(){
        super();
    }

    public Leaf(int x,int y){
        super(x-100,y-200,100,2,159, new ImageIcon("venesaurattack.png"));
    }

    public String toString(){
        return "Leaf";
    }
}
