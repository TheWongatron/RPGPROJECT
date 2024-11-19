import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


    public class Main extends JFrame{
    private static final int WIDTH =1800;
    private static final int HEIGHT=1600;


        public Main () {
            super("Pokemon RPG");
            setSize(WIDTH, HEIGHT);
            Game play = new Game();
            ((Component) play).setFocusable(true);
        

        getContentPane().add(play);
        setVisible(true);
        addWindowListener(new WindowListener(){

            @Override
            public void windowActivated(WindowEvent e) {
                

            }

            @Override
            public void windowClosed(WindowEvent e) {
                

            }

            @Override
            public void windowClosing(WindowEvent e) {
                
                play.writeToFile();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                

            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                

            }

            @Override
            public void windowIconified(WindowEvent e) {
                

            }

            @Override
            public void windowOpened(WindowEvent e) {
                
                play.createFile();
                play.readFile();            }

        });
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public static void main(String[] args) {
    @SuppressWarnings("unused")
    Main run = new Main();


    }
}




