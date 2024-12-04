import javax.swing.ImageIcon;

public class Pikachu extends Characters {
    public Pikachu(){
        super();
    }
    public Pikachu(int x, int y){
        super(x,y,250,200, 50, 50, 25, 5, new ImageIcon("pikachu.png"), new Thunder(x,y));
    }
    public String toString(){
        return "Pikachu"; //change to other character when done
    }
}
