import java.awt.Graphics;
import javax.swing.ImageIcon;

public class Projectile {
    private int x, y;
    private int speed;
    private ImageIcon image;
    
    
        public Projectile(int startX, int startY) {
            this.x = startX;
            this.y = startY;
            this.speed = 50; // Adjust speed as needed
            this.image = new ImageIcon("blastoiseattack.png"); // Load your projectile image

    }

    public void move() {
        x -= speed; // Move projectile to the right
    }

    public void draw(Graphics g) {
        g.drawImage(image.getImage(), x, y, null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isOffScreen(int width) {
        return x > width; // Check if the projectile is off-screen
    }
}
