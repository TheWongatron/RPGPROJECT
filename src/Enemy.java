import javax.swing.*;

public class Enemy extends Characters {
    private boolean canShoot; // Whether the enemy can shoot
    private long lastShootTime; // Time of the last shot
    private static final int SHOOT_COOLDOWN = 2000; // Cooldown period in milliseconds

    // Default constructor
    public Enemy() {
        super();
        this.canShoot = true;
        this.lastShootTime = System.currentTimeMillis();
    }

    // Parameterized constructor
    public Enemy(int x, int y, int w, int h, int speed, int hea, int dam, int st, ImageIcon pic, Weapons weap) {
        super(x, y, w, h, speed, hea, dam, st, pic, weap);
        this.canShoot = true;
        this.lastShootTime = System.currentTimeMillis();
    }

    // Getter for shooting readiness
    public boolean isReadyToShoot() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime >= SHOOT_COOLDOWN) {
            canShoot = true; // Ready to shoot if cooldown period has passed
        }
        return canShoot;
    }

    // Reset the shooting cooldown
    public void resetShootCooldown() {
        canShoot = false;
        lastShootTime = System.currentTimeMillis();
    }
}
