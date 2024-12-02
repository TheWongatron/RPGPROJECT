import javax.swing.ImageIcon;

public class Venasaur extends Characters {
    public Venasaur(){
        super();
    }
    public Venasaur(int x, int y){
        super(x,y,200,200, 10, 150, 35, 15, new ImageIcon("venesaur.png"), new Leaf(x,y));
    }
    public String toString(){
        return "Venasaur"; //change to other character when done
    }
}
