import javax.swing.ImageIcon;
public class Fire extends Ranged {
    public Fire(){
        super();
    }

    public Fire(int x,int y){
        super(x-100,y-200,100,2,159, new ImageIcon("charizardattack.png"));
    }

    public String toString(){
        return "Fire";
    }
}
