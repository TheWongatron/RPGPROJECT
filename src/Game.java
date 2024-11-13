import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.*;

public class Game extends JPanel implements Runnable, KeyListener, MouseListener,
    
    MouseMotionListener{    
    private BufferedImage back;
    private int key, x, y;
    private Characters player;
    private ArrayList <Characters> charList;
    private String screen;
    private ArrayList <Ranged> rangedWeap;
    private Timer gameTimer; // Add a single Timer for updates
    private Font customFont;

    private Queue <Enemy> enemies;
    private ImageIcon startBg;
    private ImageIcon selectionBg;
    private ImageIcon gameBg;
    private ImageIcon logo;
    private static final int MOVE_SPEED = 50;
    private ArrayList<Projectile> projectiles; // List to hold projectiles


    public Game() {
        new Thread(this).start();
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        key =-1;
        x=0;
        y=0;
        charList = setCharList();
        for(Characters c: charList){
            System.out.println(c);
        }
        startBg = new ImageIcon("startscreen.jpg");
        selectionBg = new ImageIcon("startscreen.jpg");
        gameBg = new ImageIcon("cave.png");
        logo = new ImageIcon("pokemon.png");
        screen="start";
        rangedWeap = new ArrayList <Ranged>();
        enemies = setEs();
        System.out.println(enemies.size());
        projectiles = new ArrayList<>();
        gameTimer = new Timer(30, e -> {
            updateProjectiles();  // Move and handle projectiles
            repaint();            // Redraw the panel
        });
        gameTimer.start();
    }

    
    private void updateProjectiles() {
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile proj = projectiles.get(i);
            proj.move(); // Move the projectile
            
            // Check for collisions with enemies
            for (Enemy enemy : enemies) {
                if (proj.getX() >= enemy.getX() && proj.getX() <= (enemy.getX() + enemy.getW()) &&
                    proj.getY() >= enemy.getY() && proj.getY() <= (enemy.getY() + enemy.getH())) {
                    enemies.remove(enemy); // Remove the enemy on collision
                    projectiles.remove(i); // Remove the projectile on collision
                    i--; // Adjust index due to removal
                    break; // Exit the loop after a hit
                }
            }

            // Remove projectile if it goes off screen
            if (proj.isOffScreen(getWidth())) {
                projectiles.remove(i);
                i--; // Adjust index after removal
            }
        }
    }

    public void shootProjectile() {
        int startX = player.getX() + player.getW() - 400; // Position just outside the player
        int startY = player.getY() + player.getH() / 8; // Center vertically
        projectiles.add(new Projectile(startX, startY));
    }

    public Queue <Enemy> setEs(){
        Queue <Enemy> temp = new LinkedList <>();
        temp.add(new Mewtwo(100,100));
        temp.add(new Mewtwo(200,200));
        temp.add(new Mewtwo(300,300));
        return temp;
    }

    public void drawSelectionScreen(Graphics g2d) {
        // Draw the selection background first
        g2d.drawImage(selectionBg.getImage(), 0, 0, getWidth(), getHeight(), this);
        
        // Then draw the player's character
        player.drawChar(g2d);
        
        // Display selected message and player stats
        g2d.setFont(new Font("Cuckoo", Font.BOLD, 50));
        String selectedMessage = "You selected: " + player.toString();
        g2d.drawString(selectedMessage, 410, 700);
        
        g2d.setFont(new Font("Nunito", Font.BOLD, 30));
        String speed = "Speed: " + player.getSpeed();
        String damage = "Strength: " + player.getDamage();
        String health = "Health: " + player.getHealth();
        String stam = "Stamina: " + player.getStam();
        int statsX = 430;
        int statsY = 750;
        g2d.drawString(speed, statsX, statsY);
        statsY += 30;
        g2d.drawString(damage, statsX, statsY);
        statsY += 30;
        g2d.drawString(health, statsX, statsY);
        statsY += 30;
        g2d.drawString(stam, statsX, statsY);
    }

    public void run() {
        try {
            while (true) {
                Thread.currentThread().sleep(5);
                updateProjectiles(); // Call to update projectiles
                repaint();
            }
        
            catch (Exception e) {
            e.printStackTrace();
        }
    }
    

public ArrayList <Characters> setCharList(){
    ArrayList <Characters> temp = new ArrayList <>();
    temp.add(new Pikachu(100,380));
    temp.add(new Venasaur(500,400));
    temp.add(new Blastoise(900,360));
    temp.add(new Charizard(1300,360));
    return temp;
}

public void paint(Graphics g){
    Graphics2D twoDgraph = (Graphics2D) g;
    if( back ==null)
back=(BufferedImage)( (createImage(getWidth(), getHeight())));
Graphics g2d = back.createGraphics();
g2d.clearRect(0,0,getSize().width, getSize().height);

drawScreen(g2d);
twoDgraph.drawImage(back, null, 0, 0);
if (customFont != null) {
    System.out.println("Custom font applied");
    g2d.setFont(customFont);
}
}






public void drawStartScreen (Graphics g2d){
    g2d.drawImage(startBg.getImage(), 0,0,getWidth(), getHeight(), this);
    g2d.drawImage(startBg.getImage(), 0,0,getWidth(), getHeight(), this);
    g2d.drawImage(logo.getImage(), 450 ,0, 800, 400, this);
    g2d.drawString("Choose Your Character",600,800);
    g2d.drawString("Press the number to select",600,850);
    g2d.setFont( new Font("chiller", Font.BOLD, 50));
    g2d.drawString("1",350,500);
    g2d.drawString("2",720,500);
    g2d.drawString("3",1120,500);
    g2d.drawString("4",1500,500);
    g2d.setFont( new Font("hack", Font.BOLD, 100));
    for(Characters c: charList){
        System.out.println(c);
        c.drawChar(g2d);
        }
}


public void attack(){
    if(player.getWeapon() instanceof Ranged){
        //rangedWeap.add(new Ranged(player.getWeapon().getDamage(), player.getWeapon().getDurability(), player.getWeapon().getDPS(), player.getWeapon().getPic()));
    }
    else{
        //code in the check for collision for melee weapons
    }
}

//DO NOT DELETE
@Override
public void keyTyped(KeyEvent e) {
// TODO Auto-generated method stub
}
//DO NOT DELETE
@Override
public void keyPressed(KeyEvent e) {
    key = e.getKeyCode();
    System.out.println(key);

    // Handle character selection
    if (key >= KeyEvent.VK_1 && key <= KeyEvent.VK_4) {
        screen = "selection";
        player = charList.get(key - KeyEvent.VK_1); // Get character based on key pressed
    } else if (key == KeyEvent.VK_ENTER) {
        screen = "gameplay";
        repaint();
    } if (key == KeyEvent.VK_SPACE) {
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




//else if (key==65){
   // screen = "gameplay"
    //attack();
//}


//DO NOT DELETE
@Override
public void keyReleased(KeyEvent e) {
}
@Override
public void mouseDragged(MouseEvent arg0) {
// TODO Auto-generated method stub
}
@Override
public void mouseMoved(MouseEvent arg0) {
// TODO Auto-generated method stub
x=arg0.getX();
y=arg0.getY();
}

    private void drawScreen(Graphics g2d) {
    switch (screen) {
        case "start":
        drawStartScreen(g2d);
        break;
            case "selection":
                drawSelectionScreen(g2d);
                break;
        case "gameplay":
                drawGameScreen(g2d);

    }
    }
    public void drawGameScreen(Graphics g2d) {
        g2d.drawImage(gameBg.getImage(), 0, 0, getWidth(), getHeight(), this);
        player.drawChar(g2d);
    
        // Draw projectiles
        for (Projectile proj : projectiles) {
            proj.draw(g2d);
        }
    
        // Draw enemies
        if (enemies.peek() != null)
            (enemies.peek()).drawChar(g2d); 
    }
    
@Override
public void mouseClicked(MouseEvent arg0) {
// TODO Auto-generated method stub
//check to see if on start screen
//for loop to check through al mainChars
//if mousecollision is true
//player = loop.get(i)
    enemies.remove();
}

@Override
public void mouseEntered(MouseEvent arg0) {
// TODO Auto-generated method stub
System.out.println("entered");
}

 

@Override
public void mouseExited(MouseEvent arg0) {
// TODO Auto-generated method stub
System.out.println("exited");
}
@Override
public void mousePressed(MouseEvent arg0) {
// TODO Auto-generated method stub
System.out.println("you clicked at"+ arg0.getY());
x=arg0.getX();
y=arg0.getY();
}
@Override
public void mouseReleased(MouseEvent arg0) {
// TODO Auto-generated method stub
}
}