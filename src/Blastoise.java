import javax.swing.ImageIcon;

public class Blastoise extends Characters {
    public Blastoise(){
        super();
    }
    public Blastoise(int x, int y){
        super(x,y,300,250, 25, 100, 30, 30, new ImageIcon("blastoise.png"), new Water(x,y));
    }
    public String toString(){
        return "Blastoise"; //change to other character when done
    }
}
