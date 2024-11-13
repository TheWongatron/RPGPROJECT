import javax.swing.ImageIcon;
public class Gatherer extends Enemy {
    public Gatherer(){
        super();
    }
    public String toString(){
        return "Gatherer";
    }
    public Gatherer(int x, int y){
        super(x, y, 50, 50, 2, 172, 2000, 5, new ImageIcon("Gatherer.png"), new Fire(x,y)); 
    }
}
