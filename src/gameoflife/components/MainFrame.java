/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameoflife.components;

import static gameoflife.components.Board.GameState;
import gameoflife.control.Algorithm;
import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

/**
 *
 * @author toshiba
 */
public class MainFrame extends JFrame implements Observer{
   private Board board;
   private int boardSize = 500;
   private JMenuBar menubar;
   private Thread golthread;
   private JLabel message;
   private JButton startbtn;
   private JButton stopbtn;
   private JButton resetbtn;
    
   public MainFrame() {
    super("Game Of Life");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    BorderLayout bord = new BorderLayout();
    setLayout(bord);
    addMenu();
    addOptions();
    pack();
    setLocationRelativeTo(null);
    setResizable(false);
    setVisible(true);
   }

   private void addMenu(){
        menubar = new JMenuBar();
        JMenu ops = new JMenu("Options");
        JMenu ncells= new JMenu("Number of cells");
        JMenuItem n50 = new JMenuItem("500");
        JMenuItem n40 = new JMenuItem("400"); 
        JMenuItem n30 = new JMenuItem("300"); 
        JMenuItem n20 = new JMenuItem("200"); 
        JMenuItem n10 = new JMenuItem("100");
        n50.addActionListener(e->{
            setNewBounds(10, "500");
        });
        n40.addActionListener(e->{
            setNewBounds(12, "400");
        });
        n30.addActionListener(e->{
            setNewBounds(16, "300");
        });
        n20.addActionListener(e->{
            setNewBounds(25, "200");
        });
        n10.addActionListener(e->{
            setNewBounds(50, "100");
        });
        ncells.add(n50);
        ncells.add(n40);
        ncells.add(n30);
        ncells.add(n20);
        ncells.add(n10);
        ops.add(ncells);
        menubar.add(ops);
        setJMenuBar(menubar);
   }
   
   private void setNewBounds(int side, String amount){
       Cell.sideValue = side;
       message.setText("  Number of cells changed to "+amount);
       board.reset();
   }
   
   private void addOptions(){
        board = new Board(boardSize);
        board.addObserver(this);
        startbtn = new JButton("Start");
        stopbtn = new JButton("Stop");
        resetbtn = new JButton("Reset");
        startbtn.addActionListener(e->{
            board.startGame();
        });
        stopbtn.addActionListener(e->{
            board.stopGame();
        });
        resetbtn.addActionListener(e->{
            board.reset();
        });
        message = new JLabel("  Welcome!");
        JToolBar bar = new JToolBar();
        bar.setFloatable(false);
        bar.add(startbtn);
        bar.add(stopbtn);
        bar.add(resetbtn);
        bar.add(message);
        add("North", bar);
        add("Center", board.getPanel());
        stopbtn.setEnabled(false);
   }

    @Override
    public void update(Observable o, Object arg) {
        message.setText("   "+String.valueOf(arg));
        switch(board.getGameState()){
            case STARTED:
                startbtn.setEnabled(false);
                stopbtn.setEnabled(true);
                resetbtn.setEnabled(false);
                break;
            case STOPPED:
                startbtn.setEnabled(true);
                stopbtn.setEnabled(false);
                resetbtn.setEnabled(true);
        }
    }
}
