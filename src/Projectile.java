import java.awt.Graphics;
import javax.swing.ImageIcon;

public class Projectile {
    private int x, y;
    private int speed;
    private int directionX;
    private ImageIcon image;

    public Projectile(int startX, int startY, String imagePath, int directionX) {
        this.x = startX;
        this.y = startY;
        this.speed = 50; // Adjust speed as needed
        this.directionX = directionX;
        this.image = new ImageIcon(imagePath); // Load the specified projectile image
    }

    public void move() {
        x += speed * directionX; // Move projectile to the left (for enemy projectiles)
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
        return x < 0; // Check if the projectile is off-screen
    }
}

