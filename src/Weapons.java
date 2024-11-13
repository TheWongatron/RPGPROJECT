import java.awt.Graphics;
import javax.swing.ImageIcon;

public class Weapons {
    int dam, durability, dps, x, y; // Add y for position
    public ImageIcon pic;
    
    public Weapons() {
        super();
    }
    
    public Weapons(int d, int dur, int dp, ImageIcon p) {
        dam = d;
        durability = dur;
        dps = dp;
        pic = p;
    }

    public void setX(int i) {
        x = i;
    }
    
    public void setY(int i) {
        y = i; // Add a method to set y coordinate
    }
    
    public int getDamage() {
        return dam;
    }

    public int getDurability() {
        return durability;
    }

    public int getDPS() {
        return dps;
    }

    public ImageIcon getPic() {
        return pic;
    }

    // Method to draw the weapon's image at specified coordinates
    public void draw(Graphics g, int x, int y) {
        if (pic != null) {
            g.drawImage(pic.getImage(), x, y, null); // Draw the weapon's image
        }
    }
}