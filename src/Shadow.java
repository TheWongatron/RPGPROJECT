import javax.swing.ImageIcon;
public class Shadow extends Ranged {
    public Shadow(){
        super();
    }

    public Shadow(int x,int y){
        super(x-100,y-200,100,2,159, new ImageIcon("shadow.png"));
    }

    public String toString(){
        return "Shadow";
    }
}
