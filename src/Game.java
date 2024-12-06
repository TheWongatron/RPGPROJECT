import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.*;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Random;



public class Game extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener{    
    private BufferedImage back;
    private int key, x, y;
    private Characters player;
    private final ArrayList <Characters> charList;
    private final ArrayList<Projectile> enemyProjectiles;
    private String screen;
    private final Timer gameTimer; // Add a single Timer for updates
    private Font customFont;
    private final File saveFile;
    private final Queue <Enemy> enemies;
    private final ImageIcon startBg;
    private final ImageIcon selectionBg;
    private final ImageIcon gameBg;
    private final ImageIcon logo;
    private final ImageIcon winBg;
    private final ImageIcon loseBg;
    private final Timer enemyAttackTimer;
    private static final int MOVE_SPEED = 50;
    private final ArrayList<Projectile> projectiles; // List to hold projectiles



    public Game() {
        new Thread(this).start();
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        saveFile=new File("savedfile.txt");
        key =-1;
        x=0;
        y=0;
        charList = setCharList();
        for(final Characters c: charList){
            System.out.println(c);
        }
        startBg = new ImageIcon("startscreen.jpg");
        selectionBg = new ImageIcon("startscreen.jpg");
        gameBg = new ImageIcon("cave.png");
        logo = new ImageIcon("pokemon.png");
        winBg = new ImageIcon("win.jpg");
        loseBg = new ImageIcon("lose.jpg");
        screen="start";
        enemyProjectiles = new ArrayList<>();
        enemyAttackTimer = new Timer(3000, e -> triggerEnemyAttacks());
        enemyAttackTimer.start();


        
        
        enemies = setEs();
        System.out.println(enemies.size());
        projectiles = new ArrayList<>();
        // Load the custom font
        try {
        // Load the custom font once
        customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Pokemon Classic.ttf")).deriveFont(70f);
        final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(customFont);
        } catch (IOException | FontFormatException e) {
        e.printStackTrace();
    }
        gameTimer = new Timer(30, e -> {
            updateProjectiles();  // Move and handle projectiles
            repaint();            // Redraw the panel
        });
        gameTimer.start();
    }
    private void updateGameState() {
        // Check if all enemies are defeated
        if (enemies.isEmpty() && !"win".equals(screen)) {
            screen = "win"; // Switch to the win screen
            repaint();
        }
        
    }
    

    public void createFile(){
        try {
            if(saveFile.createNewFile()){
                System.out.println("Successfully created file!");
            }
            else {
                System.out.println("File already exists!");
            }
        } catch (final IOException e) {
            
            e.printStackTrace();
        }
    }
    public void readFile(){
        Scanner sc;
        try {
            sc = new Scanner(saveFile);
            while(sc.hasNext()){
                System.out.println(sc.nextInt());
        
            }
        } 
        catch (final FileNotFoundException e) {
            
            e.printStackTrace();
        }
    }
    public void writeToFile(){
        try {
        final FileWriter myWriter = new FileWriter(saveFile);

        //write whatever you want to save
        if(enemies.isEmpty()){
            myWriter.write("win!");
        }
        else {
            myWriter.write("Mewtwo has "+enemies.size()+" health left");
            myWriter.close();
        System.out.println("Successfully wrote to file");
        }
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }
    private void updateProjectiles() {
        // Handle player projectiles
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile proj = projectiles.get(i);
            proj.move(); 
    
            // Check for collisions with enemies
            for (Enemy enemy : enemies) {
                if (proj.getX() >= enemy.getX() && proj.getX() <= (enemy.getX() + enemy.getW()) &&
                    proj.getY() >= enemy.getY() && proj.getY() <= (enemy.getY() + enemy.getH())) {
                    enemies.remove(enemy); // Remove the enemy on collision
                    projectiles.remove(i); // Remove the projectile on collision
                    i--; 
                    updateGameState();
                    break; 
                }
            }
    
            // Remove projectile if it goes off screen
            if (proj.isOffScreen(getWidth())) {
                projectiles.remove(i);
                i--; 
            }
        }
    
        // Handle enemy projectiles
        for (int i = 0; i < enemyProjectiles.size(); i++) {
            Projectile proj = enemyProjectiles.get(i);
            proj.move(); 
    
            // Check for collisions with the player
            if (proj.getX() >= player.getX() && proj.getX() <= (player.getX() + player.getW()) &&
                proj.getY() >= player.getY() && proj.getY() <= (player.getY() + player.getH())) {
                // Reduce player's health by 5
                player.setHealth(player.getHealth() - 20);
                // Handle damage to the player here
                enemyProjectiles.remove(i);
                i--;

                if (player.getHealth() <= 0) {
                    player.setHealth(0); // Ensure health doesn't go below zero
                    screen = "lose";     // Switch to the lose screen
                    repaint();
                }
            }
    
            // Remove projectile if it goes off screen
            if (proj.isOffScreen(getWidth())) {
                enemyProjectiles.remove(i);
                i--;
            }
        }
    }
    
    public void shootProjectile() {
        if (player == null) return; // Ensure player is selected
    
        final int startX = player.getX() + player.getW() - 400; // Position just outside the player
        final int startY = player.getY() + player.getH() / 10;  // Center vertically
        String projectileImage = "";
    
        // Determine the projectile image based on the character
        if (player instanceof Pikachu) {
            projectileImage = "pikachuattack.png";
        } else if (player instanceof Venasaur) {
            projectileImage = "venesaurattack.png";
        } else if (player instanceof Blastoise) {
            projectileImage = "blastoiseattack.png";
        } else if (player instanceof Charizard) {
            projectileImage = "charizardattack.png";
        }
    
        projectiles.add(new Projectile(startX, startY, projectileImage,-1));
    }
    public void enemyShoot(Enemy enemy) {
        if (enemy == null) return;
    
        int startX = enemy.getX() + 50; // Position slightly in front of the enemy
        int startY = enemy.getY() + enemy.getH() / 10; // Center vertically
        String projectileImage = "shadow.png"; // Enemy's attack image
    
        enemyProjectiles.add(new Projectile(startX, startY, projectileImage,1));
    }
    
    public void triggerEnemyAttacks() {
        for (Enemy enemy : enemies) {
            if (enemy != null && enemy.isReadyToShoot()) {
                enemyShoot(enemy); // Make the enemy shoot
                enemy.resetShootCooldown(); // Reset its shooting cooldown
                break; // Stop after one enemy shoots
            }
        }
    }
    
    
    
    
    
    public Queue <Enemy> setEs(){
        final Queue <Enemy> temp = new LinkedList <>();
        final Random rand = new Random();
        for (int i = 0; i < 100; i++) 
        {
            temp.add(new Mewtwo(rand.nextInt(1000), rand.nextInt(800)));
        }
        return temp;
    }
    public void run() {
        try {
            while (true) {Thread.currentThread();
                Thread.sleep(5);
                updateProjectiles(); // Call to update projectiles
                updateGameState();   // Check for win condition
                repaint();
            }
                
            catch (final Exception e) {
            e.printStackTrace();
        }
    }
    public ArrayList <Characters> setCharList(){
        final ArrayList <Characters> temp = new ArrayList <>();
        temp.add(new Pikachu(100,380));
        temp.add(new Venasaur(500,400));
        temp.add(new Blastoise(900,360));
        temp.add(new Charizard(1300,360));
        return temp;
    }
    public void paint(final Graphics g){
        final Graphics2D twoDgraph = (Graphics2D) g;
        if( back ==null)
    back=(BufferedImage)( (createImage(getWidth(), getHeight())));
    final Graphics g2d = back.createGraphics();
    g2d.clearRect(0,0,getSize().width, getSize().height);

    drawScreen(g2d);
    twoDgraph.drawImage(back, null, 0, 0);
    if (customFont != null) {
        System.out.println("Custom font applied");
        g2d.setFont(customFont);
    }
}
    public void drawStartScreen (final Graphics g2d){
        g2d.drawImage(startBg.getImage(), 0,0,getWidth(), getHeight(), this);
        g2d.drawImage(startBg.getImage(), 0,0,getWidth(), getHeight(), this);
        g2d.drawImage(logo.getImage(), 450 ,0, 800, 400, this);
        g2d.setFont( new Font("Pokemon Classic", Font.BOLD, 15));
        g2d.drawString("Choose Your Character",450,800);
        g2d.drawString("Press the number to select",450,850);
        g2d.setFont( new Font("Pokemon Classic", Font.BOLD, 50));
        g2d.drawString("1",350,500);
        g2d.drawString("2",720,500);
        g2d.drawString("3",1120,500);
        g2d.drawString("4",1500,500);
        g2d.setFont( new Font("hack", Font.BOLD, 100));
        for(final Characters c: charList){
            System.out.println(c);
            c.drawChar(g2d);
            }
    }
    public void drawSelectionScreen(final Graphics g2d) {
        // Draw the selection background first
        g2d.drawImage(selectionBg.getImage(), 0, 0, getWidth(), getHeight(), this);
    
        // Then draw the player's character
        player.drawChar(g2d);
    
        // Display selected message and player stats
        g2d.setFont(new Font("Pokemon Classic", Font.BOLD, 40));
        final String selectedMessage = "You selected: " + player.toString();
        g2d.drawString(selectedMessage, 410, 650);
    
        g2d.setFont(new Font("Pokemon Classic", Font.BOLD, 30));
        final String speed = "Speed: " + player.getSpeed();
        final String damage = "Strength: " + player.getDamage();
        final String health = "Health: " + player.getHealth();
        final String stam = "Stamina: " + player.getStam();
        final int statsX = 430;
        int statsY = 750;
        g2d.drawString(speed, statsX, statsY);
        statsY += 30;
        g2d.drawString(damage, statsX, statsY);
        statsY += 30;
        g2d.drawString(health, statsX, statsY);
        statsY += 30;
        g2d.drawString(stam, statsX, statsY);
    }
    public void drawGameScreen(Graphics g2d) {
        g2d.drawImage(gameBg.getImage(), 0, 0, getWidth(), getHeight(), this);
        player.drawChar(g2d);
    
        // Draw player projectiles
        for (Projectile proj : projectiles) {
            proj.draw(g2d);
        }
    
        // Draw enemy projectiles
        for (Projectile proj : enemyProjectiles) {
            proj.draw(g2d);
        }
    
        // Draw enemies
        if (enemies.peek() != null) {
            enemies.peek().drawChar(g2d);
        }
        // Display player's health in the top-right corner
        g2d.setFont(new Font("Pokemon Classic", Font.BOLD, 30)); // Set the font
        g2d.setColor(Color.WHITE); // Set the text color
        String healthText = "Health: " + player.getHealth(); // Health text
        int textWidth = g2d.getFontMetrics().stringWidth(healthText); // Calculate text width
        g2d.drawString(healthText, getWidth() - textWidth - 20, 40); // Draw text

    }
    
    public void drawWinScreen(final Graphics g2d) {
        g2d.drawImage(winBg.getImage(), 0, 0, getWidth(), getHeight(), this);
        g2d.setFont(new Font("Pokemon Classic", Font.BOLD, 30));
        g2d.setColor(Color.black);
        g2d.drawString("Winner, winner, PokéDinner!", 950, 380);
    }
    public void drawLoseScreen(final Graphics g2d) {
        g2d.drawImage(loseBg.getImage(), 0, 0, getWidth(), getHeight(), this);
        g2d.setFont(new Font("Pokemon Classic", Font.BOLD, 40));
        g2d.setColor(Color.white);
        g2d.drawString("Mewtwo's psychic", 100, 100);
        g2d.drawString("powers were too ", 100, 200);
        g2d.drawString("strong!", 200, 300);
    }
    public void Rangedack(){
        if(player.getWeapon() instanceof Ranged){
            //rangedWeap.add(new Ranged(player.getWeapon().getDamage(), player.getWeapon().getDurability(), player.getWeapon().getDPS(), player.getWeapon().getPic()));
        }
        else{
            //code in the check for collision for melee weapons
        }
    }

    //DO NOT DELETE
    @Override
    public void keyTyped(final KeyEvent e) {

    }
    //DO NOT DELETE
    @Override
    public void keyPressed(final KeyEvent e) {
        key = e.getKeyCode();
        System.out.println(key);

        // Handle character selection
        if (key >= KeyEvent.VK_1 && key <= KeyEvent.VK_4) {
            screen = "selection";
            player = charList.get(key - KeyEvent.VK_1); // Get character based on key pressed
            
        } else if (key == KeyEvent.VK_ENTER) {
            screen = "gameplay";
            if (player != null) {
                player.setX(1300);
                player.setY(600);
            }
            repaint();
        } else if (key == KeyEvent.VK_W) { // Check if "W" key is pressed
            screen = "win";
            repaint();
        } else if (key == KeyEvent.VK_L) { // Check if "L" key is pressed
            screen = "lose";
            repaint(); 
        } if (key == KeyEvent.VK_SPACE){
            System.out.println("Space key pressed");
            shootProjectile();
        }

        // Movement controls
        switch (key) {
            case KeyEvent.VK_UP:
                player.setY(player.getY() - MOVE_SPEED);
                break;
            case KeyEvent.VK_DOWN:
                player.setY(player.getY() + MOVE_SPEED);
                break;
            case KeyEvent.VK_LEFT:
                player.setX(player.getX() - MOVE_SPEED);
                break;
            case KeyEvent.VK_RIGHT:
                player.setX(player.getX() + MOVE_SPEED);
                break;
        }

        // Ensure player remains within screen bounds
        if (player.getX() < 0) player.setX(0);
        if (player.getY() < 0) player.setY(0);
        if (player.getX() > getWidth() - player.getW()) player.setX(getWidth() - player.getW());
        if (player.getY() > getHeight() - player.getH()) player.setY(getHeight() - player.getH());

        repaint();
    }

            







//DO NOT DELETE
@Override
public void keyReleased(final KeyEvent e) {
}
@Override
public void mouseDragged(final MouseEvent arg0) {

}
@Override
public void mouseMoved(final MouseEvent arg0) {

x=arg0.getX();
y=arg0.getY();
}

    private void drawScreen(final Graphics g2d) {
        switch (screen) {
            case "start":
                drawStartScreen(g2d);
                break;
            case "selection":
                drawSelectionScreen(g2d);
                break;
            case "gameplay":
                drawGameScreen(g2d);
                break;
            case "win":
                drawWinScreen(g2d);
                break;
            case "lose":
                drawLoseScreen(g2d);
                break;

    }
    }
   
    
    
@Override
public void mouseClicked(final MouseEvent arg0) {

//check to see if on start screen
//for loop to check through al mainChars
//if mousecollision is true
//player = loop.get(i)
    enemies.remove();
}

@Override
public void mouseEntered(final MouseEvent arg0) {

System.out.println("entered");
}

 

@Override
public void mouseExited(final MouseEvent arg0) {

System.out.println("exited");
}
@Override
public void mousePressed(final MouseEvent arg0) {

System.out.println("you clicked at"+ arg0.getY());
x=arg0.getX();
y=arg0.getY();
}
@Override
public void mouseReleased(final MouseEvent arg0) {

}
}