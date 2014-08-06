package gameoflife.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Carlos Gutierrez
 */
public class MainFrame extends JFrame implements Observer{
   private Board board;
   private int boardSize = 500;
   private JMenuBar menubar;
   private JLabel message;
   private JTextArea clusterReport;
   private JScrollPane clusterReportScroll;
   private JLabel statusLabel;
   private JButton startbtn;
   private JButton stopbtn;
   private JButton resetbtn;
   private JButton clustersButton;
   private JMenuItem rand;
   private JMenu ops;
   private JMenu help;
   private static final String INSTRUCTIONS = "Click on the squares to select them and create your own patterns!\n"+
           "(try a vertical line of 10 cells, you will be amazed)\n"+
           "or select the \"Fill randomly\" option in the menu.\n Enjoy!";
   private static final String ABOUT = "Created by Carlos Gutierres \"coneramu@gmail.com\"\n"+
           "Skype user: carlos.gutierrez.cone \n";
    
   public MainFrame() {
    super("Game Of Life");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    BorderLayout bord = new BorderLayout();
    setLayout(bord);
    addMenu();
    addOptions();
    addStatusBar();
    pack();
    setLocationRelativeTo(null);
    setResizable(false);
    setVisible(true);
   }
   
   private void addStatusBar(){
       JPanel statusPanel = new JPanel();
       statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
       add(statusPanel, BorderLayout.SOUTH);
       statusPanel.setPreferredSize(new Dimension(this.getWidth(), 16));
       statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
       statusLabel = new JLabel("...");
       statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
       statusPanel.add(statusLabel);
   }

   /**
    * Adding the menu bar
    */
   private void addMenu(){
        menubar = new JMenuBar();
        ops = new JMenu("Options");
        help = new JMenu("Help");
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
        rand = new JMenuItem("Fill Randomly");
        rand.addActionListener(e->{
            board.fillRandomly();
        });
        ops.add(rand);
        JMenuItem inst = new JMenuItem("Instructions");
        inst.addActionListener(e->{
            JOptionPane.showMessageDialog(this, INSTRUCTIONS);
        });
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(e->{
            JOptionPane.showMessageDialog(this, ABOUT);
        });
        help.add(inst);
        help.add(about);
        menubar.add(ops);
        menubar.add(help);
        setJMenuBar(menubar);
   }
   
   /**
    * Changing the number and size of the cells
    * @param side The new value for the cell sides length
    * @param amount The amount that will be displayed in 
    * the message zone
    */
   private void setNewBounds(int side, String amount){
       Cell.sideValue = side;
       message.setText("Number of cells changed to "+amount);
       board.reset();
   }
   
   /**
    * Adding the tool bar
    */
   private void addOptions(){
        board = new Board(boardSize);
        board.addObserver(this);
        startbtn = new JButton("Start");
        stopbtn = new JButton("Stop");
        resetbtn = new JButton("Reset");
        clustersButton = new JButton("Cluster report");
        startbtn.addActionListener(e->{
            board.startGame();
        });
        stopbtn.addActionListener(e->{
            board.stopGame();
        });
        resetbtn.addActionListener(e->{
            board.reset();
        });
        
        clusterReport = new JTextArea(16, 20);
        clusterReport.setEditable(false);
        clusterReportScroll = new JScrollPane(clusterReport);
        clusterReportScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        clustersButton.addActionListener(e->{
            board.findClusters();
            clusterReport.setText(board.getMessageString());
            clusterReport.setSelectionStart(0);
            clusterReport.setSelectionEnd(0); 
            JOptionPane.showMessageDialog(this, clusterReportScroll, "Cluster Report", JOptionPane.INFORMATION_MESSAGE);
        });
        message = new JLabel("Welcome!");
        //Giving it a little margin as a separation from the buttons
        message.setBorder(new EmptyBorder(10, 10, 10, 10));
        JToolBar bar = new JToolBar();
        bar.setFloatable(false);
        bar.add(startbtn);
        bar.add(stopbtn);
        bar.add(resetbtn);
        bar.add(clustersButton);
        bar.add(message);
        add("North", bar);
        add("Center", board.getPanel());
        stopbtn.setEnabled(false);
   }

   
   /**
    * Accions taken when the game state is changed
    * @param o
    * @param arg 
    */
    @Override
    public void update(Observable o, Object arg) {
        switch(board.getGameState()){
            case STARTED:
                startbtn.setEnabled(false);
                stopbtn.setEnabled(true);
                resetbtn.setEnabled(false);
                ops.setEnabled(false);
                clustersButton.setEnabled(false);
                message.setText(String.valueOf(arg));
                break;
            case STOPPED:
                startbtn.setEnabled(true);
                stopbtn.setEnabled(false);
                resetbtn.setEnabled(true);
                ops.setEnabled(true);
                clustersButton.setEnabled(true);
                message.setText(String.valueOf(arg));
            case STARTED_WITH_STATUS:
            case STOPPED_WITH_STATUS:
                statusLabel.setText(String.valueOf(arg));
        }
    }
}
