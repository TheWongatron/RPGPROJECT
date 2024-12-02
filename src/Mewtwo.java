import javax.swing.ImageIcon;

public class Mewtwo extends Enemy {
    public Mewtwo(){
        super();
    }
    public Mewtwo(int x, int y){
        super(x,y,200,200, 40, 150, 50, 20, new ImageIcon("mewtwo.png"), new Shadow(x,y));
    }
}
