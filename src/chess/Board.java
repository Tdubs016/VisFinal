/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

/**
 *
 * @author Typan
 */
public class Board {
    private Border greenBorder = BorderFactory.createLineBorder(Color.green);
    private Border yellowBorder = BorderFactory.createLineBorder(Color.yellow);
    JFrame board;
    JPanel spot[][] = new JPanel[8][8];
   
    Board(){
    board = new JFrame("Chess");
    board.setSize(600, 600);
    board.setLayout(new GridLayout(8, 8));
    LinkedList<piece> piecelist = new LinkedList<>();
    for (int i = 0; i < 8; i++){
        piece bpawn = new piece("bpawn", 6, i, true, piecelist);
    }
    piece bking = new piece("bking", 7, 4, true, piecelist);
    piece bqueen = new piece("bqueen", 7, 3, true, piecelist);
    piece bbishop1 = new piece("bbishop", 7, 2, true, piecelist);
    piece bbishop2 = new piece("bbishop", 7, 5, true, piecelist);
    piece bknight1 = new piece("bknight", 7, 1, true, piecelist);
    piece bknight2 = new piece("bknight", 7, 6, true, piecelist);
    piece brook1 = new piece("brook", 7, 0, true, piecelist);
    piece brook2 = new piece("brook", 7, 7, true, piecelist);
    for (int i = 0; i < 8; i++){
        piece wpawn = new piece("wpawn", 1, i, false, piecelist);
    }
    piece wking = new piece("wking", 0, 4, true, piecelist);
    piece wqueen = new piece("wqueen", 0, 3, true, piecelist);
    piece wbishop1 = new piece("wbishop", 0, 2, true, piecelist);
    piece wbishop2 = new piece("wbishop", 0, 5, true, piecelist);
    piece wknight1 = new piece("wknight", 0, 1, true, piecelist);
    piece wknight2 = new piece("wknight", 0, 6, true, piecelist);
    piece wrook1 = new piece("wrook", 0, 0, true, piecelist);
    piece wrook2 = new piece("wrook", 0, 7, true, piecelist);
    
        for (int row = 0; row < 8; row++){
            for (int collum = 0; collum <8; collum++){
           spot[row][collum] = new JPanel();
           spot[row][collum].setEnabled(false);
           
          
           
           if ((row + collum) % 2 == 0) {
                spot[row][collum].setBackground(Color.lightGray);
            } else {
                spot[row][collum].setBackground(Color.white);
            }   
          
            spot[row][collum].addMouseListener(new MouseAdapter() {
                           
                          public void mouseEntered(MouseEvent evt) {
                            
                            JPanel parent = (JPanel)evt.getSource();
                            Component[] components = parent.getComponents();
                            
                            for (Component comp: components){
                                if(comp.getClass().equals(JLabel.class)){
                                    parent.setBorder(greenBorder);
                                    parent.revalidate();
                                }
                            }
                            
                         }

                         public void mouseExited(MouseEvent evt) {
                           JPanel parent = (JPanel)evt.getSource();
                             Component[] components = parent.getComponents();
                            
                            for (Component comp: components){
                                if(comp.getClass().equals(JLabel.class)){
                                    parent.setBorder(null);
                                    parent.revalidate();
                                }
                            }
                         }
                         public void mouseClicked(MouseEvent evt) {
                           JPanel parent = (JPanel)evt.getSource();
                          
                           for (int row = 0; row < 8; row++){
                            for (int collum = 0; collum <8; collum++){
                                spot[row][collum].setBorder(null);
                            }
                           }
                           
                           
                           int collum = parent.getX()/66;
                           int row = parent.getY()/66;
                            Component[] components = spot[row][collum].getComponents();
                           System.out.println("Collum: " + collum);
                           System.out.println("Row: " + row);
                           
                           
                             for (Component comp: components){
                                 
                                 if(comp.getClass().equals(JLabel.class)){
                                     for(piece p: piecelist){
                                         if(p.piecex == row && p.piecey == collum){
                                             System.out.println(p.name);
                                            if(p.name == "bpawn"){
                                                spot[row-1][collum].setBorder(yellowBorder);
                                                spot[row-2][collum].setBorder(yellowBorder);    
                                            }
                                            if (p.name == "bknight"){
//                                               try {
//                                                spot[row-1][collum+2].setBorder(yellowBorder);
//                                               } catch (Exception e){
//                                               
//                                               } finally {
//                                               
//                                                spot[row-2][collum+1].setBorder(yellowBorder);
//                                                spot[row-2][collum-1].setBorder(yellowBorder);
//                                                spot[row-1][collum-2].setBorder(yellowBorder);
//                                                       }
                                            }
                                         }
                                     }
                                 }
                                 
                            
                             
                             }
                          
                          }
                           
                         
                         
                          });
            
           
           
           
           board.add(spot[row][collum]);
           
            }
        }
     int size = 60;
     for(piece p: piecelist){
           if(p.name == "bpawn"){
               JLabel piece = new JLabel();
              ImageIcon imageIcon = new ImageIcon(new ImageIcon("images/black pieces/blackpawn.png").getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT));
              piece.setIcon(imageIcon);
               spot[p.piecex][p.piecey].add(piece);
           }
           if(p.name == "wpawn"){
               JLabel piece = new JLabel();
              ImageIcon imageIcon = new ImageIcon(new ImageIcon("images/white pieces/whitepawn.png").getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT));
              piece.setIcon(imageIcon);
               spot[p.piecex][p.piecey].add(piece);
           }
           if(p.name == "bking"){
               JLabel piece = new JLabel();
              ImageIcon imageIcon = new ImageIcon(new ImageIcon("images/black pieces/blackking.png").getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT));
              piece.setIcon(imageIcon);
               spot[p.piecex][p.piecey].add(piece);
           }
           if(p.name == "wking"){
               JLabel piece = new JLabel();
              ImageIcon imageIcon = new ImageIcon(new ImageIcon("images/white pieces/whiteking.png").getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT));
              piece.setIcon(imageIcon);
               spot[p.piecex][p.piecey].add(piece);
           }
           if(p.name == "bqueen"){
               JLabel piece = new JLabel();
              ImageIcon imageIcon = new ImageIcon(new ImageIcon("images/black pieces/blackqueen.png").getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT));
              piece.setIcon(imageIcon);
               spot[p.piecex][p.piecey].add(piece);
           }
           if(p.name == "wqueen"){
               JLabel piece = new JLabel();
              ImageIcon imageIcon = new ImageIcon(new ImageIcon("images/white pieces/whitequeen.png").getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT));
              piece.setIcon(imageIcon);
               spot[p.piecex][p.piecey].add(piece);
           }
           if(p.name == "bbishop"){
               JLabel piece = new JLabel();
              ImageIcon imageIcon = new ImageIcon(new ImageIcon("images/black pieces/blackbishop.png").getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT));
              piece.setIcon(imageIcon);
               spot[p.piecex][p.piecey].add(piece);
           }
           if(p.name == "wbishop"){
               JLabel piece = new JLabel();
              ImageIcon imageIcon = new ImageIcon(new ImageIcon("images/white pieces/whitebishop.png").getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT));
              piece.setIcon(imageIcon);
               spot[p.piecex][p.piecey].add(piece);
           }
           if(p.name == "bknight"){
               JLabel piece = new JLabel();
              ImageIcon imageIcon = new ImageIcon(new ImageIcon("images/black pieces/blackknight.png").getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT));
              piece.setIcon(imageIcon);
               spot[p.piecex][p.piecey].add(piece);
           }
           if(p.name == "wknight"){
               JLabel piece = new JLabel();
              ImageIcon imageIcon = new ImageIcon(new ImageIcon("images/white pieces/whiteknight.png").getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT));
              piece.setIcon(imageIcon);
               spot[p.piecex][p.piecey].add(piece);
           }
           if(p.name == "brook"){
               JLabel piece = new JLabel();
              ImageIcon imageIcon = new ImageIcon(new ImageIcon("images/black pieces/blackrook.png").getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT));
              piece.setIcon(imageIcon);
               spot[p.piecex][p.piecey].add(piece);
           }
           if(p.name == "wrook"){
               JLabel piece = new JLabel();
              ImageIcon imageIcon = new ImageIcon(new ImageIcon("images/white pieces/whiterook.png").getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT));
              piece.setIcon(imageIcon);
               spot[p.piecex][p.piecey].add(piece);
           }
           }   
                
                
       
    // board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    board.setVisible(true);
    
    }
     
 
    
}


