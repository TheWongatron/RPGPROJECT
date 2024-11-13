import javax.swing.ImageIcon;

public class Charizard extends Characters {
    public Charizard(){
        super();
    }
    public Charizard(int x, int y){
        super(x,y,200,200, 35, 85, 40, 10, new ImageIcon("charizard.png"), new Fire(x,y));
      
    }
    public String toString(){
        return "Charizard"; //change to other character when done
    }
}
