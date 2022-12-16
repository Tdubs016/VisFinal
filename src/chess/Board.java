/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
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
    // Borders for the Jpanels
    private Border greenBorder = BorderFactory.createLineBorder(Color.green);
    private Border yellowBorder = BorderFactory.createLineBorder(Color.yellow);
    private Border redBorder = BorderFactory.createLineBorder(Color.red);
   
    JFrame board; 
    JPanel spot[][] = new JPanel[8][8];
    
    
    Board(){
        
   
    
        board = new JFrame("Chess");
        board.setSize(600, 600);
        board.setLayout(new GridLayout(8, 8));
        LinkedList<Piece> piecelist = new LinkedList<>();

        for (int i = 0; i < 8; i++){
            Piece bpawn = new Piece("bpawn", 6, i, true, piecelist);
        }
        Piece bking = new Piece("bking", 7, 4, true, piecelist);
        Piece bqueen = new Piece("bqueen", 7, 3, true, piecelist);
        Piece bbishop1 = new Piece("bbishop", 7, 2, true, piecelist);
        Piece bbishop2 = new Piece("bbishop", 7, 5, true, piecelist);
        Piece bknight1 = new Piece("bknight", 7, 1, true, piecelist);
        Piece bknight2 = new Piece("bknight", 7, 6, true, piecelist);
        Piece brook1 = new Piece("brook", 7, 0, true, piecelist);
        Piece brook2 = new Piece("brook", 7, 7, true, piecelist);
        for (int i = 0; i < 8; i++){
            Piece wpawn = new Piece("wpawn", 1, i, false, piecelist);
        }
        Piece wking = new Piece("wking", 0, 4, false, piecelist);
        Piece wqueen = new Piece("wqueen", 0, 3, false, piecelist);
        Piece wbishop1 = new Piece("wbishop", 0, 2, false, piecelist);
        Piece wbishop2 = new Piece("wbishop", 0, 5, false, piecelist);
        Piece wknight1 = new Piece("wknight", 0, 1, false, piecelist);
        Piece wknight2 = new Piece("wknight", 0, 6, false, piecelist);
        Piece wrook1 = new Piece("wrook", 0, 0, false, piecelist);
        Piece wrook2 = new Piece("wrook", 0, 7, false, piecelist);
    
        for (int row = 0; row < 8; row++){
            for (int collum = 0; collum <8; collum++){
           spot[row][collum] = new JPanel();
           spot[row][collum].setEnabled(false);
           
          
           // sets the color for each jpanel
           if ((row + collum) % 2 == 0) {
                spot[row][collum].setBackground(Color.lightGray);
            } else {
                spot[row][collum].setBackground(Color.white);
            }   
            // adds a mouse listener to each jpanel so we can get functionality with each one
            spot[row][collum].addMouseListener(new MouseAdapter() {
                           
                          public void mouseEntered(MouseEvent evt) {
                            
                            JPanel parent = (JPanel)evt.getSource();
                            Component[] components = parent.getComponents();
                            int collum = parent.getX()/66;
                            int row = parent.getY()/66;
                            
                            for (Component comp: components){
                                if(comp.getClass().equals(JLabel.class)){
                                    for(Piece p: piecelist){
                                        if(p.piecex == row && p.piecey == collum){
                                            //depending on whos turn it is will determine which one's highlight green
                                           if (Playerstats.stats.getturn()==1 && p.black ==true){
                                            parent.setBorder(greenBorder);
                                            parent.revalidate();
                                            }
                                           else if(Playerstats.stats.getturn()==1 && p.black ==false){
                                               if(parent.getBorder()!= redBorder){
                                                parent.setBorder(null);
                                                parent.revalidate();
                                               }
                                           }
                                           if (Playerstats.stats.getturn()==2 && p.black ==false){
                                            parent.setBorder(greenBorder);
                                            parent.revalidate();
                                            }
                                           else if(Playerstats.stats.getturn()==2 && p.black ==true){
                                             if(parent.getBorder()!= redBorder){
                                                parent.setBorder(null);
                                                parent.revalidate();
                                               }
                                           }
                                        }
                                    }
                                }
                            }
                            
                         }

                         public void mouseExited(MouseEvent evt) {
                           JPanel parent = (JPanel)evt.getSource();
                             Component[] components = parent.getComponents();
                            
                            for (Component comp: components){
                                if(comp.getClass().equals(JLabel.class)){
                                    // makes it so it un highlights a jpanel but only if it is not yellow or red 
                                    if(parent.getBorder() !=redBorder && parent.getBorder() !=yellowBorder ){
                                        parent.setBorder(null);
                                        parent.revalidate();
                                    }
                                   
                                }
                            }
                         }
                         public void mousePressed(MouseEvent evt) {
                           JPanel parent = (JPanel)evt.getSource();
                          
                           for (int row = 0; row < 8; row++){
                            for (int collum = 0; collum <8; collum++){
                                spot[row][collum].setBorder(null);
                            }
                           }
                           
                           
                           
                           int collum = parent.getX()/66;
                           int row = parent.getY()/66;
                            Component[] components = spot[row][collum].getComponents();
                          
                           
                             for (Component comp: components){
                                 
                                 if(comp.getClass().equals(JLabel.class)){
                                     for(Piece p: piecelist){
                                         if(p.piecex == row && p.piecey == collum){
                                             
                                             p.picked = true;
                                             // Player 1 turn ---------------------------------------------------------------------------------------------------------------------------------------------------------------
                                             if(Playerstats.stats.getturn() == 1){
                                                 //Movement logic for black pawn --------------------------------------------------------------------------------------------------------------------------------------------
                                                if(p.name == "bpawn"){
                                                    if(p.moves == 0) {
                                                        if(spot[row-1][collum].getComponentCount() == 0){  
                                                            spot[row-1][collum].setBorder(yellowBorder);
                                                        }
                                                        if(spot[row-2][collum].getComponentCount() == 0){  
                                                            spot[row-2][collum].setBorder(yellowBorder);
                                                        }
                                                        try {
                                                        if(spot[row-1][collum-1].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row-1 && pt.piecey == collum-1 && pt.black == false){
                                                                        spot[row-1][collum-1].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                        } catch (Exception e){}
                                                        try{
                                                        if(spot[row-1][collum+1].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row-1 && pt.piecey == collum+1 && pt.black == false){
                                                                        spot[row-1][collum+1].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                        }catch (Exception e){}
                                                    }
                                                    else{
                                                        try{
                                                            if(spot[row-1][collum].getComponentCount() == 0){  
                                                            spot[row-1][collum].setBorder(yellowBorder);
                                                            }
                                                        }catch (Exception e){}
                                                        
                                                        try {
                                                        if(spot[row-1][collum-1].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row-1 && pt.piecey == collum-1 && pt.black == false){
                                                                        spot[row-1][collum-1].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                        } catch (Exception e){}
                                                        try{
                                                        if(spot[row-1][collum+1].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row-1 && pt.piecey == collum+1 && pt.black == false){
                                                                        spot[row-1][collum+1].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                        }catch (Exception e){}
                                                    }

                                                }
                                                //Movement logic for black knight --------------------------------------------------------------------------------------------------------------------------------------------
                                                if (p.name == "bknight"){
                                                   try {
                                                    if(spot[row-1][collum+2].getComponentCount() == 0){  
                                                        spot[row-1][collum+2].setBorder(yellowBorder);
                                                    }
                                                    else if(spot[row-1][collum+2].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row-1 && pt.piecey == collum+2 && pt.black == false){
                                                                        spot[row-1][collum+2].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                   } catch (Exception e){

                                                   } 
                                                   try {
                                                        if(spot[row-2][collum+1].getComponentCount() == 0){  
                                                            spot[row-2][collum+1].setBorder(yellowBorder);
                                                        }
                                                        else if(spot[row-2][collum+1].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row-2 && pt.piecey == collum+1 && pt.black == false){
                                                                        spot[row-2][collum+1].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                   } catch (Exception e){

                                                   } 
                                                   try {
                                                       if(spot[row-2][collum-1].getComponentCount() == 0){  
                                                            spot[row-2][collum-1].setBorder(yellowBorder);
                                                       }
                                                       else if(spot[row-2][collum-1].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row-2 && pt.piecey == collum-1 && pt.black == false){
                                                                        spot[row-2][collum-1].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                   } catch (Exception e){

                                                   } 
                                                   try {
                                                       if(spot[row-1][collum-2].getComponentCount() == 0){  
                                                            spot[row-1][collum-2].setBorder(yellowBorder);
                                                       }
                                                       else if(spot[row-1][collum-2].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row-1 && pt.piecey == collum-2 && pt.black == false){
                                                                        spot[row-1][collum-2].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                   } catch (Exception e){

                                                   } 
                                                   try {
                                                       if(spot[row+1][collum+2].getComponentCount() == 0){  
                                                            spot[row+1][collum+2].setBorder(yellowBorder);
                                                       }
                                                       else if(spot[row+1][collum+2].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row+1 && pt.piecey == collum+2 && pt.black == false){
                                                                        spot[row+1][collum+2].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                   } catch (Exception e){

                                                   } 
                                                   try {
                                                       if(spot[row+2][collum+1].getComponentCount() == 0){  
                                                            spot[row+2][collum+1].setBorder(yellowBorder);
                                                       }
                                                       else if(spot[row+2][collum+1].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row-2 && pt.piecey == collum+1 && pt.black == false){
                                                                        spot[row-2][collum+1].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                   } catch (Exception e){

                                                   } 
                                                   try {
                                                       if(spot[row+2][collum-1].getComponentCount() == 0){  
                                                        spot[row+2][collum-1].setBorder(yellowBorder);
                                                       }
                                                       else if(spot[row+2][collum-1].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row+2 && pt.piecey == collum-1 && pt.black == false){
                                                                        spot[row+2][collum-1].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                   } catch (Exception e){

                                                   } 
                                                   try {
                                                    if(spot[row+1][collum-2].getComponentCount() == 0){  
                                                    spot[row+1][collum-2].setBorder(yellowBorder);
                                                    }
                                                    else if(spot[row+1][collum-2].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row+1 && pt.piecey == collum-2 && pt.black == false){
                                                                        spot[row+1][collum-2].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                   } catch (Exception e){

                                                   } 




                                                   } 
                                                    //Movement logic for black bishop --------------------------------------------------------------------------------------------------------------------------------------------
                                                    if(p.name == "bbishop"){
                                                       
                                                       int next = 1;

                                                       
                                                        try {
                                                                for (int i = 0; i < 8; i++){   
                                                                    if(spot[row-next][collum-next].getComponentCount() == 0){       
                                                                        spot[row-next][collum-next].setBorder(yellowBorder);
                                                                        next++;
                                                                    }
                                                                    else if(spot[row-next][collum-next].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row-next && pt.piecey == collum-next && pt.black == false){
                                                                        spot[row-next][collum-next].setBorder(redBorder);
                                                                        i = 8;
                                                                        }
                                                                    }
                                                                }
                                                                }
                                                       } catch (Exception e){

                                                       } 
                                                           next = 1;
                                                           try {
                                                                for (int i = 0; i < 8; i++){                                                  
                                                                    if(spot[row-next][collum+next].getComponentCount() == 0){       
                                                                        spot[row-next][collum+next].setBorder(yellowBorder);
                                                                        next++;
                                                                    }
                                                                    else if(spot[row-next][collum+next].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row-next && pt.piecey == collum+next && pt.black == false){
                                                                        spot[row-next][collum+next].setBorder(redBorder);
                                                                        i = 8;
                                                                        }
                                                                    }
                                                                }
                                                           }
                                                       } catch (Exception e){

                                                       } 
                                                         next = 1;
                                                           try {
                                                                for (int i = 0; i < 8; i++){                                                  
                                                                    if(spot[row+next][collum+next].getComponentCount() == 0){       
                                                                        spot[row+next][collum+next].setBorder(yellowBorder);
                                                                        next++;
                                                                    }
                                                                    else if(spot[row+next][collum+next].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row+next && pt.piecey == collum+next && pt.black == false){
                                                                        spot[row+next][collum+next].setBorder(redBorder);
                                                                        i = 8;
                                                                        }
                                                                    }
                                                                }
                                                           }
                                                       } catch (Exception e){

                                                       } 
                                                        next = 1;
                                                           try {
                                                                for (int i = 0; i < 8; i++){                                                  
                                                                    if(spot[row+next][collum-next].getComponentCount() == 0){       
                                                                        spot[row+next][collum-next].setBorder(yellowBorder);
                                                                        next++;
                                                                    }
                                                                    else if(spot[row+next][collum-next].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row+next && pt.piecey == collum-next && pt.black == false){
                                                                        spot[row+next][collum-next].setBorder(redBorder);
                                                                        i = 8;
                                                                        }
                                                                    }
                                                                }
                                                           }
                                                       } catch (Exception e){

                                                       } 


                                                }
                                                //Movement logic for black rook --------------------------------------------------------------------------------------------------------------------------------------------    
                                                if(p.name == "brook"){
                                                    int next = 1;
                                                    
                                                    try {
                                                        for (int i = 0; i < 8; i++){ 
                                                            
                                                            if(spot[row][collum+next].getComponentCount() == 0){       
                                                                spot[row][collum+next].setBorder(yellowBorder);
                                                                next++;
                                                            }
                                                            else if(spot[row][collum+next].getComponentCount()> 0){
                                                                for(Piece pt: piecelist){
                                                                    if(pt.piecex == row && pt.piecey == collum+next && pt.black == false){
                                                                    spot[row][collum+next].setBorder(redBorder);
                                                                    i = 8;
                                                                    }
                                                                }
                                                            }
                                                                
                                                        }
                                                    } catch (Exception e){

                                                    }
                                                    next = 1;
                                                    try {
                                                        for (int i = 0; i < 8; i++){   
                                                            if(spot[row][collum-next].getComponentCount() == 0){       
                                                                spot[row][collum-next].setBorder(yellowBorder);
                                                                next++;
                                                            }
                                                            else if(spot[row][collum-next].getComponentCount()> 0){                                                              
                                                                for(Piece pt: piecelist){
                                                                    if(pt.piecex == row && pt.piecey == collum-next && pt.black == false){
                                                                    spot[row][collum-next].setBorder(redBorder);
                                                                    i = 8;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } catch (Exception e){

                                                    }
                                                    next = 1;
                                                    try {
                                                        for (int i = 0; i < 8; i++){   
                                                            if(spot[row+next][collum].getComponentCount() == 0){       
                                                                spot[row+next][collum].setBorder(yellowBorder);
                                                                next++;
                                                            }
                                                            else if(spot[row+next][collum].getComponentCount()> 0){
                                                                for(Piece pt: piecelist){
                                                                    if(pt.piecex == row+next && pt.piecey == collum && pt.black == false){
                                                                    spot[row+next][collum].setBorder(redBorder);
                                                                    i = 8;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } catch (Exception e){

                                                    } 
                                                    next = 1;
                                                    try {
                                                        for (int i = 0; i < 8; i++){   
                                                            if(spot[row-next][collum].getComponentCount() == 0){       
                                                                spot[row-next][collum].setBorder(yellowBorder);
                                                                next++;
                                                            }
                                                            else if(spot[row-next][collum].getComponentCount()> 0){
                                                                for(Piece pt: piecelist){
                                                                    if(pt.piecex == row-next && pt.piecey == collum && pt.black == false){
                                                                    spot[row-next][collum].setBorder(redBorder);
                                                                    i = 8;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } catch (Exception e){

                                                    } 
                                                }

                                                //Movement logic for black queen --------------------------------------------------------------------------------------------------------------------------------------------
                                                if(p.name == "bqueen"){
                                                    int next = 1;
                                                    try {
                                                        for (int i = 0; i < 8; i++){   
                                                            if(spot[row][collum+next].getComponentCount() == 0){       
                                                                spot[row][collum+next].setBorder(yellowBorder);
                                                                next++;
                                                            }
                                                            else if(spot[row][collum+next].getComponentCount()> 0){
                                                                for(Piece pt: piecelist){
                                                                    if(pt.piecex == row && pt.piecey == collum+next && pt.black == false){
                                                                    spot[row][collum+next].setBorder(redBorder);
                                                                    i = 8;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } catch (Exception e){

                                                    }
                                                    next = 1;
                                                    try {
                                                        for (int i = 0; i < 8; i++){   
                                                            if(spot[row][collum-next].getComponentCount() == 0){       
                                                                spot[row][collum-next].setBorder(yellowBorder);
                                                                next++;
                                                            }
                                                            else if(spot[row][collum-next].getComponentCount()> 0){
                                                                for(Piece pt: piecelist){
                                                                    if(pt.piecex == row && pt.piecey == collum-next && pt.black == false){
                                                                    spot[row][collum-next].setBorder(redBorder);
                                                                    i = 8;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } catch (Exception e){

                                                    }
                                                    next = 1;
                                                    try {
                                                        for (int i = 0; i < 8; i++){   
                                                            if(spot[row+next][collum].getComponentCount() == 0){       
                                                                spot[row+next][collum].setBorder(yellowBorder);
                                                                next++;
                                                            }
                                                            else if(spot[row+next][collum].getComponentCount()> 0){
                                                                for(Piece pt: piecelist){
                                                                    if(pt.piecex == row+next && pt.piecey == collum && pt.black == false){
                                                                    spot[row+next][collum].setBorder(redBorder);
                                                                    i = 8;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } catch (Exception e){

                                                    } 
                                                    next = 1;
                                                    try {
                                                        for (int i = 0; i < 8; i++){   
                                                            if(spot[row-next][collum].getComponentCount() == 0){       
                                                                spot[row-next][collum].setBorder(yellowBorder);
                                                                next++;
                                                            }
                                                            else if(spot[row-next][collum].getComponentCount()> 0){
                                                                for(Piece pt: piecelist){
                                                                    if(pt.piecex == row-next && pt.piecey == collum && pt.black == false){
                                                                    spot[row-next][collum].setBorder(redBorder);
                                                                    i = 8;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } catch (Exception e){

                                                    }
                                                    next = 1;
                                                    try {
                                                            for (int i = 0; i < 8; i++){   
                                                                if(spot[row-next][collum-next].getComponentCount() == 0){       
                                                                    spot[row-next][collum-next].setBorder(yellowBorder);
                                                                    next++;
                                                                }
                                                                else if(spot[row-next][collum-next].getComponentCount()> 0){
                                                                for(Piece pt: piecelist){
                                                                    if(pt.piecex == row-next && pt.piecey == collum-next && pt.black == false){
                                                                    spot[row-next][collum-next].setBorder(redBorder);
                                                                    i = 8;
                                                                    }
                                                                }
                                                            }
                                                            }
                                                   } catch (Exception e){

                                                   } 
                                                       next = 1;
                                                       try {
                                                            for (int i = 0; i < 8; i++){                                                  
                                                                if(spot[row-next][collum+next].getComponentCount() == 0){       
                                                                    spot[row-next][collum+next].setBorder(yellowBorder);
                                                                    next++;
                                                                }
                                                                else if(spot[row-next][collum+next].getComponentCount()> 0){
                                                                for(Piece pt: piecelist){
                                                                    if(pt.piecex == row-next && pt.piecey == collum+next && pt.black == false){
                                                                    spot[row-next][collum+next].setBorder(redBorder);
                                                                    i = 8;
                                                                    }
                                                                }
                                                            }
                                                       }
                                                   } catch (Exception e){

                                                   } 
                                                     next = 1;
                                                       try {
                                                            for (int i = 0; i < 8; i++){                                                  
                                                                if(spot[row+next][collum+next].getComponentCount() == 0){       
                                                                    spot[row+next][collum+next].setBorder(yellowBorder);
                                                                    next++;
                                                                }
                                                                else if(spot[row+next][collum+next].getComponentCount()> 0){
                                                                for(Piece pt: piecelist){
                                                                    if(pt.piecex == row+next && pt.piecey == collum+next && pt.black == false){
                                                                    spot[row+next][collum+next].setBorder(redBorder);
                                                                    i = 8;
                                                                    }
                                                                }
                                                            }
                                                       }
                                                   } catch (Exception e){

                                                   } 
                                                    next = 1;
                                                       try {
                                                            for (int i = 0; i < 8; i++){                                                  
                                                                if(spot[row+next][collum-next].getComponentCount() == 0){       
                                                                    spot[row+next][collum-next].setBorder(yellowBorder);
                                                                    next++;
                                                                }
                                                                else if(spot[row+next][collum-next].getComponentCount()> 0){
                                                                for(Piece pt: piecelist){
                                                                    if(pt.piecex == row+next && pt.piecey == collum-next && pt.black == false){
                                                                    spot[row+next][collum-next].setBorder(redBorder);
                                                                    i = 8;
                                                                    }
                                                                }
                                                            }
                                                       }
                                                   } catch (Exception e){

                                                   } 
                                                }

                                                //Movement logic for black king --------------------------------------------------------------------------------------------------------------------------------------------
                                                if(p.name == "bking"){
                                                    try {
                                                        if(spot[row+1][collum].getComponentCount() == 0){  
                                                            spot[row+1][collum].setBorder(yellowBorder);
                                                        }   
                                                        else if(spot[row+1][collum].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row+1 && pt.piecey == collum && pt.black == false){
                                                                        spot[row+1][collum].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                    } 
                                                    catch (Exception e){}
                                                    try {
                                                        if(spot[row+1][collum+1].getComponentCount() == 0){  
                                                            spot[row+1][collum+1].setBorder(yellowBorder);
                                                        }   
                                                        else if(spot[row+1][collum+1].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row+1 && pt.piecey == collum+1 && pt.black == false){
                                                                        spot[row+1][collum+1].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                    } 
                                                    catch (Exception e){}
                                                    try {
                                                        if(spot[row+1][collum-1].getComponentCount() == 0){  
                                                            spot[row+1][collum-1].setBorder(yellowBorder);
                                                        }   
                                                        else if(spot[row+1][collum-1].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row+1 && pt.piecey == collum-1 && pt.black == false){
                                                                        spot[row+1][collum-1].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                    } 
                                                    catch (Exception e){}
                                                    try {
                                                        if(spot[row-1][collum].getComponentCount() == 0){  
                                                            spot[row-1][collum].setBorder(yellowBorder);
                                                        }   
                                                        else if(spot[row-1][collum].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row-1 && pt.piecey == collum && pt.black == false){
                                                                        spot[row-1][collum].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                    } 
                                                    catch (Exception e){}
                                                    try {
                                                        if(spot[row-1][collum+1].getComponentCount() == 0){  
                                                            spot[row-1][collum+1].setBorder(yellowBorder);
                                                        }   
                                                        else if(spot[row-1][collum+1].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row-1 && pt.piecey == collum+1 && pt.black == false){
                                                                        spot[row-1][collum+1].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                    } 
                                                    catch (Exception e){}
                                                    try {
                                                        if(spot[row-1][collum-1].getComponentCount() == 0){  
                                                            spot[row-1][collum-1].setBorder(yellowBorder);
                                                        }   
                                                        else if(spot[row-1][collum-1].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row-1 && pt.piecey == collum-1 && pt.black == false){
                                                                        spot[row-1][collum-1].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                    } 
                                                    catch (Exception e){}
                                                    try {
                                                        if(spot[row][collum+1].getComponentCount() == 0){  
                                                            spot[row][collum+1].setBorder(yellowBorder);
                                                        }   
                                                        else if(spot[row][collum+1].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row && pt.piecey == collum+1 && pt.black == false){
                                                                        spot[row][collum+1].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                    } 
                                                    catch (Exception e){}
                                                    try {
                                                        if(spot[row][collum-1].getComponentCount() == 0){  
                                                            spot[row][collum-1].setBorder(yellowBorder);
                                                        }   
                                                        else if(spot[row][collum-1].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row && pt.piecey == collum-1 && pt.black == false){
                                                                        spot[row][collum-1].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                    } 
                                                    catch (Exception e){}
                                                }
                                             }
                                             
                                             // players 2 turn ----------------------------------------------------------------------------------------------------------------------------------------------------------------
                                             if(Playerstats.stats.getturn() == 2){
                                                 //Movement logic for white pawn --------------------------------------------------------------------------------------------------------------------------------------------
                                                 if(p.name == "wpawn"){
                                                    if(p.moves == 0) {
                                                        if(spot[row+1][collum].getComponentCount() == 0){  
                                                            spot[row+1][collum].setBorder(yellowBorder);
                                                        }
                                                        if(spot[row+2][collum].getComponentCount() == 0){  
                                                            spot[row+2][collum].setBorder(yellowBorder);
                                                        }
                                                        try {
                                                        if(spot[row+1][collum-1].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row-1 && pt.piecey == collum-1 && pt.black == true){
                                                                        spot[row+1][collum-1].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                        } catch (Exception e){}
                                                        try{
                                                        if(spot[row+1][collum+1].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row-1 && pt.piecey == collum+1 && pt.black == true){
                                                                        spot[row+1][collum+1].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                        }catch (Exception e){}
                                                    }
                                                    else{
                                                        try{
                                                            if(spot[row+1][collum].getComponentCount() == 0){  
                                                            spot[row+1][collum].setBorder(yellowBorder);
                                                            }
                                                        }catch (Exception e){}
                                                        
                                                        try {
                                                        if(spot[row+1][collum-1].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row+1 && pt.piecey == collum-1 && pt.black == true){
                                                                        spot[row+1][collum-1].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                        } catch (Exception e){}
                                                        try{
                                                        if(spot[row+1][collum+1].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row+1 && pt.piecey == collum+1 && pt.black == true){
                                                                        spot[row+1][collum+1].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                        }catch (Exception e){}
                                                    }

                                                }
                                                 //Movement logic for white knight --------------------------------------------------------------------------------------------------------------------------------------------
                                                if (p.name == "wknight"){
                                                   try {
                                                    if(spot[row-1][collum+2].getComponentCount() == 0){  
                                                        spot[row-1][collum+2].setBorder(yellowBorder);
                                                    }
                                                    else if(spot[row-1][collum+2].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row-1 && pt.piecey == collum+2 && pt.black == true){
                                                                        spot[row-1][collum+2].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                   } catch (Exception e){

                                                   } 
                                                   try {
                                                        if(spot[row-2][collum+1].getComponentCount() == 0){  
                                                            spot[row-2][collum+1].setBorder(yellowBorder);
                                                        }
                                                        else if(spot[row-2][collum+1].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row-2 && pt.piecey == collum+1 && pt.black == true){
                                                                        spot[row-2][collum+1].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                   } catch (Exception e){

                                                   } 
                                                   try {
                                                       if(spot[row-2][collum-1].getComponentCount() == 0){  
                                                            spot[row-2][collum-1].setBorder(yellowBorder);
                                                       }
                                                       else if(spot[row-2][collum-1].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row-2 && pt.piecey == collum-1 && pt.black == true){
                                                                        spot[row-2][collum-1].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                   } catch (Exception e){

                                                   } 
                                                   try {
                                                       if(spot[row-1][collum-2].getComponentCount() == 0){  
                                                            spot[row-1][collum-2].setBorder(yellowBorder);
                                                       }
                                                       else if(spot[row-1][collum-2].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row-1 && pt.piecey == collum-2 && pt.black == true){
                                                                        spot[row-1][collum-2].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                   } catch (Exception e){

                                                   } 
                                                   try {
                                                       if(spot[row+1][collum+2].getComponentCount() == 0){  
                                                            spot[row+1][collum+2].setBorder(yellowBorder);
                                                       }
                                                       else if(spot[row+1][collum+2].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row+1 && pt.piecey == collum+2 && pt.black == true){
                                                                        spot[row+1][collum+2].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                   } catch (Exception e){

                                                   } 
                                                   try {
                                                       if(spot[row+2][collum+1].getComponentCount() == 0){  
                                                            spot[row+2][collum+1].setBorder(yellowBorder);
                                                       }
                                                       else if(spot[row+2][collum+1].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row-2 && pt.piecey == collum+1 && pt.black == true){
                                                                        spot[row-2][collum+1].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                   } catch (Exception e){

                                                   } 
                                                   try {
                                                       if(spot[row+2][collum-1].getComponentCount() == 0){  
                                                        spot[row+2][collum-1].setBorder(yellowBorder);
                                                       }
                                                       else if(spot[row+2][collum-1].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row+2 && pt.piecey == collum-1 && pt.black == true){
                                                                        spot[row+2][collum-1].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                   } catch (Exception e){

                                                   } 
                                                   try {
                                                    if(spot[row+1][collum-2].getComponentCount() == 0){  
                                                    spot[row+1][collum-2].setBorder(yellowBorder);
                                                    }
                                                    else if(spot[row+1][collum-2].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row+1 && pt.piecey == collum-2 && pt.black == true){
                                                                        spot[row+1][collum-2].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                   } catch (Exception e){

                                                   } 




                                                   } 
                                                    //Movement logic for white bishop --------------------------------------------------------------------------------------------------------------------------------------------
                                                    if(p.name == "wbishop"){
                                                       
                                                       int next = 1;

                                                       
                                                        try {
                                                                for (int i = 0; i < 8; i++){   
                                                                    if(spot[row-next][collum-next].getComponentCount() == 0){       
                                                                        spot[row-next][collum-next].setBorder(yellowBorder);
                                                                        next++;
                                                                    }
                                                                    else if(spot[row-next][collum-next].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row-next && pt.piecey == collum-next && pt.black == true){
                                                                        spot[row-next][collum-next].setBorder(redBorder);
                                                                        i = 8;
                                                                        }
                                                                    }
                                                                }
                                                                }
                                                       } catch (Exception e){

                                                       } 
                                                           next = 1;
                                                           try {
                                                                for (int i = 0; i < 8; i++){                                                  
                                                                    if(spot[row-next][collum+next].getComponentCount() == 0){       
                                                                        spot[row-next][collum+next].setBorder(yellowBorder);
                                                                        next++;
                                                                    }
                                                                    else if(spot[row-next][collum+next].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row-next && pt.piecey == collum+next && pt.black == true){
                                                                        spot[row-next][collum+next].setBorder(redBorder);
                                                                        i = 8;
                                                                        }
                                                                    }
                                                                }
                                                           }
                                                       } catch (Exception e){

                                                       } 
                                                         next = 1;
                                                           try {
                                                                for (int i = 0; i < 8; i++){                                                  
                                                                    if(spot[row+next][collum+next].getComponentCount() == 0){       
                                                                        spot[row+next][collum+next].setBorder(yellowBorder);
                                                                        next++;
                                                                    }
                                                                    else if(spot[row+next][collum+next].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row+next && pt.piecey == collum+next && pt.black == true){
                                                                        spot[row+next][collum+next].setBorder(redBorder);
                                                                        i = 8;
                                                                        }
                                                                    }
                                                                }
                                                           }
                                                       } catch (Exception e){

                                                       } 
                                                        next = 1;
                                                           try {
                                                                for (int i = 0; i < 8; i++){                                                  
                                                                    if(spot[row+next][collum-next].getComponentCount() == 0){       
                                                                        spot[row+next][collum-next].setBorder(yellowBorder);
                                                                        next++;
                                                                    }
                                                                    else if(spot[row+next][collum-next].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row+next && pt.piecey == collum-next && pt.black == true){
                                                                        spot[row+next][collum-next].setBorder(redBorder);
                                                                        i = 8;
                                                                        }
                                                                    }
                                                                }
                                                           }
                                                       } catch (Exception e){

                                                       } 


                                                }
                                                //Movement logic for white rook --------------------------------------------------------------------------------------------------------------------------------------------
                                                if(p.name == "wrook"){
                                                    int next = 1;                                                   
                                                    try {
                                                        for (int i = 0; i < 8; i++){ 
                                                            
                                                            if(spot[row][collum+next].getComponentCount() == 0){       
                                                                spot[row][collum+next].setBorder(yellowBorder);
                                                                next++;
                                                            }
                                                            else if(spot[row][collum+next].getComponentCount()> 0){
                                                                for(Piece pt: piecelist){
                                                                    if(pt.piecex == row && pt.piecey == collum+next && pt.black == true){
                                                                    spot[row][collum+next].setBorder(redBorder);
                                                                    i = 8;
                                                                    }
                                                                }
                                                            }
                                                                
                                                        }
                                                    } catch (Exception e){

                                                    }
                                                    next = 1;
                                                    try {
                                                        for (int i = 0; i < 8; i++){   
                                                            if(spot[row][collum-next].getComponentCount() == 0){       
                                                                spot[row][collum-next].setBorder(yellowBorder);
                                                                next++;
                                                            }
                                                            else if(spot[row][collum-next].getComponentCount()> 0){                                                              
                                                                for(Piece pt: piecelist){
                                                                    if(pt.piecex == row && pt.piecey == collum-next && pt.black == true){
                                                                    spot[row][collum-next].setBorder(redBorder);
                                                                    i = 8;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } catch (Exception e){

                                                    }
                                                    next = 1;
                                                    try {
                                                        for (int i = 0; i < 8; i++){   
                                                            if(spot[row+next][collum].getComponentCount() == 0){       
                                                                spot[row+next][collum].setBorder(yellowBorder);
                                                                next++;
                                                            }
                                                            else if(spot[row+next][collum].getComponentCount()> 0){
                                                                for(Piece pt: piecelist){
                                                                    if(pt.piecex == row+next && pt.piecey == collum && pt.black == true){
                                                                    spot[row+next][collum].setBorder(redBorder);
                                                                    i = 8;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } catch (Exception e){

                                                    } 
                                                    next = 1;
                                                    try {
                                                        for (int i = 0; i < 8; i++){   
                                                            if(spot[row-next][collum].getComponentCount() == 0){       
                                                                spot[row-next][collum].setBorder(yellowBorder);
                                                                next++;
                                                            }
                                                            else if(spot[row-next][collum].getComponentCount()> 0){
                                                                for(Piece pt: piecelist){
                                                                    if(pt.piecex == row-next && pt.piecey == collum && pt.black == true){
                                                                    spot[row-next][collum].setBorder(redBorder);
                                                                    i = 8;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } catch (Exception e){

                                                    } 
                                                }

                                                //Movement logic for white queen --------------------------------------------------------------------------------------------------------------------------------------------
                                                if(p.name == "wqueen"){
                                                    int next = 1;
                                                    try {
                                                        for (int i = 0; i < 8; i++){   
                                                            if(spot[row][collum+next].getComponentCount() == 0){       
                                                                spot[row][collum+next].setBorder(yellowBorder);
                                                                next++;
                                                            }
                                                            else if(spot[row][collum+next].getComponentCount()> 0){
                                                                for(Piece pt: piecelist){
                                                                    if(pt.piecex == row && pt.piecey == collum+next && pt.black == true){
                                                                    spot[row][collum+next].setBorder(redBorder);
                                                                    i = 8;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } catch (Exception e){

                                                    }
                                                    next = 1;
                                                    try {
                                                        for (int i = 0; i < 8; i++){   
                                                            if(spot[row][collum-next].getComponentCount() == 0){       
                                                                spot[row][collum-next].setBorder(yellowBorder);
                                                                next++;
                                                            }
                                                            else if(spot[row][collum-next].getComponentCount()> 0){
                                                                for(Piece pt: piecelist){
                                                                    if(pt.piecex == row && pt.piecey == collum-next && pt.black == true){
                                                                    spot[row][collum-next].setBorder(redBorder);
                                                                    i = 8;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } catch (Exception e){

                                                    }
                                                    next = 1;
                                                    try {
                                                        for (int i = 0; i < 8; i++){   
                                                            if(spot[row+next][collum].getComponentCount() == 0){       
                                                                spot[row+next][collum].setBorder(yellowBorder);
                                                                next++;
                                                            }
                                                            else if(spot[row+next][collum].getComponentCount()> 0){
                                                                for(Piece pt: piecelist){
                                                                    if(pt.piecex == row+next && pt.piecey == collum && pt.black == true){
                                                                    spot[row+next][collum].setBorder(redBorder);
                                                                    i = 8;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } catch (Exception e){

                                                    } 
                                                    next = 1;
                                                    try {
                                                        for (int i = 0; i < 8; i++){   
                                                            if(spot[row-next][collum].getComponentCount() == 0){       
                                                                spot[row-next][collum].setBorder(yellowBorder);
                                                                next++;
                                                            }
                                                            else if(spot[row-next][collum].getComponentCount()> 0){
                                                                for(Piece pt: piecelist){
                                                                    if(pt.piecex == row-next && pt.piecey == collum && pt.black == true){
                                                                    spot[row-next][collum].setBorder(redBorder);
                                                                    i = 8;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } catch (Exception e){

                                                    }
                                                    next = 1;
                                                    try {
                                                            for (int i = 0; i < 8; i++){   
                                                                if(spot[row-next][collum-next].getComponentCount() == 0){       
                                                                    spot[row-next][collum-next].setBorder(yellowBorder);
                                                                    next++;
                                                                }
                                                                else if(spot[row-next][collum-next].getComponentCount()> 0){
                                                                for(Piece pt: piecelist){
                                                                    if(pt.piecex == row-next && pt.piecey == collum-next && pt.black == true){
                                                                    spot[row-next][collum-next].setBorder(redBorder);
                                                                    i = 8;
                                                                    }
                                                                }
                                                            }
                                                            }
                                                   } catch (Exception e){

                                                   } 
                                                       next = 1;
                                                       try {
                                                            for (int i = 0; i < 8; i++){                                                  
                                                                if(spot[row-next][collum+next].getComponentCount() == 0){       
                                                                    spot[row-next][collum+next].setBorder(yellowBorder);
                                                                    next++;
                                                                }
                                                                else if(spot[row-next][collum+next].getComponentCount()> 0){
                                                                for(Piece pt: piecelist){
                                                                    if(pt.piecex == row-next && pt.piecey == collum+next && pt.black == true){
                                                                    spot[row-next][collum+next].setBorder(redBorder);
                                                                    i = 8;
                                                                    }
                                                                }
                                                            }
                                                       }
                                                   } catch (Exception e){

                                                   } 
                                                     next = 1;
                                                       try {
                                                            for (int i = 0; i < 8; i++){                                                  
                                                                if(spot[row+next][collum+next].getComponentCount() == 0){       
                                                                    spot[row+next][collum+next].setBorder(yellowBorder);
                                                                    next++;
                                                                }
                                                                else if(spot[row+next][collum+next].getComponentCount()> 0){
                                                                for(Piece pt: piecelist){
                                                                    if(pt.piecex == row+next && pt.piecey == collum+next && pt.black == true){
                                                                    spot[row+next][collum+next].setBorder(redBorder);
                                                                    i = 8;
                                                                    }
                                                                }
                                                            }
                                                       }
                                                   } catch (Exception e){

                                                   } 
                                                    next = 1;
                                                       try {
                                                            for (int i = 0; i < 8; i++){                                                  
                                                                if(spot[row+next][collum-next].getComponentCount() == 0){       
                                                                    spot[row+next][collum-next].setBorder(yellowBorder);
                                                                    next++;
                                                                }
                                                                else if(spot[row+next][collum-next].getComponentCount()> 0){
                                                                for(Piece pt: piecelist){
                                                                    if(pt.piecex == row+next && pt.piecey == collum-next && pt.black == true){
                                                                    spot[row+next][collum-next].setBorder(redBorder);
                                                                    i = 8;
                                                                    }
                                                                }
                                                            }
                                                       }
                                                   } catch (Exception e){

                                                   } 
                                                }

                                                //Movement logic for white king --------------------------------------------------------------------------------------------------------------------------------------------
                                                if(p.name == "wking"){
                                                    try {
                                                        if(spot[row+1][collum].getComponentCount() == 0){  
                                                            spot[row+1][collum].setBorder(yellowBorder);
                                                        }   
                                                        else if(spot[row+1][collum].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row+1 && pt.piecey == collum && pt.black == true){
                                                                        spot[row+1][collum].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                    } 
                                                    catch (Exception e){}
                                                    try {
                                                        if(spot[row+1][collum+1].getComponentCount() == 0){  
                                                            spot[row+1][collum+1].setBorder(yellowBorder);
                                                        }   
                                                        else if(spot[row+1][collum+1].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row+1 && pt.piecey == collum+1 && pt.black == true){
                                                                        spot[row+1][collum+1].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                    } 
                                                    catch (Exception e){}
                                                    try {
                                                        if(spot[row+1][collum-1].getComponentCount() == 0){  
                                                            spot[row+1][collum-1].setBorder(yellowBorder);
                                                        }   
                                                        else if(spot[row+1][collum-1].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row+1 && pt.piecey == collum-1 && pt.black == true){
                                                                        spot[row+1][collum-1].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                    } 
                                                    catch (Exception e){}
                                                    try {
                                                        if(spot[row-1][collum].getComponentCount() == 0){  
                                                            spot[row-1][collum].setBorder(yellowBorder);
                                                        }   
                                                        else if(spot[row-1][collum].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row-1 && pt.piecey == collum && pt.black == true){
                                                                        spot[row-1][collum].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                    } 
                                                    catch (Exception e){}
                                                    try {
                                                        if(spot[row-1][collum+1].getComponentCount() == 0){  
                                                            spot[row-1][collum+1].setBorder(yellowBorder);
                                                        }   
                                                        else if(spot[row-1][collum+1].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row-1 && pt.piecey == collum+1 && pt.black == true){
                                                                        spot[row-1][collum+1].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                    } 
                                                    catch (Exception e){}
                                                    try {
                                                        if(spot[row-1][collum-1].getComponentCount() == 0){  
                                                            spot[row-1][collum-1].setBorder(yellowBorder);
                                                        }   
                                                        else if(spot[row-1][collum-1].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row-1 && pt.piecey == collum-1 && pt.black == true){
                                                                        spot[row-1][collum-1].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                    } 
                                                    catch (Exception e){}
                                                    try {
                                                        if(spot[row][collum+1].getComponentCount() == 0){  
                                                            spot[row][collum+1].setBorder(yellowBorder);
                                                        }   
                                                        else if(spot[row][collum+1].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row && pt.piecey == collum+1 && pt.black == true){
                                                                        spot[row][collum+1].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                    } 
                                                    catch (Exception e){}
                                                    try {
                                                        if(spot[row][collum-1].getComponentCount() == 0){  
                                                            spot[row][collum-1].setBorder(yellowBorder);
                                                        }   
                                                        else if(spot[row][collum-1].getComponentCount()> 0){
                                                                    for(Piece pt: piecelist){
                                                                        if(pt.piecex == row && pt.piecey == collum-1 && pt.black == true){
                                                                        spot[row][collum-1].setBorder(redBorder);
                                                                        
                                                                        }
                                                                    }
                                                                }
                                                    } 
                                                    catch (Exception e){}
                                                     
                                                }
                                             
                                             
                                             
                                             
                                             
                                             
                                             }
                                         }
                                     }
                                 }
                                 
                            
                             
                             }
                          
                          }
                         
                         public void mouseReleased(MouseEvent evt){
                             
                             JPanel parent = (JPanel)evt.getSource();
                             
                             
                             
                             
                           
                         
                            Point mousePos = board.getMousePosition();
                            if(mousePos == null){
                           return;
                           }
                          
                           int pcollum = parent.getX()/66;
                           int prow = parent.getY()/66;
                           Component[] pcomponents = spot[prow][pcollum].getComponents();
                          
                           
                           int mcollum = mousePos.x / 75;
                           int mrow = mousePos.y / 75;
                           
                           
                           
                            
                            
                           
                           
                           for (Component pcomp: pcomponents){
                            if(pcomp.getClass().equals(JLabel.class)){
                                for(Piece p: piecelist){
                                    
                                    if (p.picked == true){
                                        if(mcollum == pcollum && mrow == prow){
                                            System.out.println("Can't move there");
                                          
                                        }
                                        else{
                                               if(spot[mrow][mcollum].getBorder() == yellowBorder){
                                                p.moves++;
                                                spot[mrow][mcollum].removeAll();
                                                spot[mrow][mcollum].add(pcomp);
                                                p.piecex = mrow;
                                                p.piecey = mcollum;
                                                spot[prow][pcollum].remove(pcomp);
                                                spot[prow][pcollum].repaint();
                                                spot[prow][pcollum].revalidate();
                                                spot[mrow][mcollum].repaint();
                                                spot[mrow][mcollum].revalidate();
                                                if(Playerstats.stats.getturn() == 1){
                                                    Playerstats.stats.setturn(2);
                                                }
                                                else{
                                                    Playerstats.stats.setturn(1);
                                                }
                                               }
                                               else if(spot[mrow][mcollum].getBorder() == redBorder){
                                                p.moves++;
                                                spot[mrow][mcollum].removeAll();
                                                for(Piece pt: piecelist){
                                                    if(pt.piecex == mrow && pt.piecey == mcollum ){
                                                        pt.piecex = 9;
                                                        pt.piecey = 9;
                                                    }
                                                
                                                }
                                                spot[mrow][mcollum].add(pcomp);
                                                p.piecex = mrow;
                                                p.piecey = mcollum;
                                                spot[prow][pcollum].remove(pcomp);
                                                spot[prow][pcollum].repaint();
                                                spot[prow][pcollum].revalidate();
                                                spot[mrow][mcollum].repaint();
                                                spot[mrow][mcollum].revalidate();
                                                
                                                if(Playerstats.stats.getturn() == 1){
                                                    Playerstats.stats.setturn(2);
                                                    Player.pr.setkill(2);
                                                }
                                                else{
                                                    Playerstats.stats.setturn(1);
                                                    Player.pr.setkill(1);
                                                }
                                               }
                                               else{
                                                   System.out.println("Can't move there");
                                               }
                                            
                                        }
                                    }
                           
                                }
                         
                            }
                           }
                           for(Piece p: piecelist){
                            p.picked = false;
                           }
                            for (int row = 0; row < 8; row++){
                                for (int collum = 0; collum <8; collum++){
                                    spot[row][collum].setBorder(null);
                                }
                            }
                            if (Playerstats.stats.getturn() == 1){
                                Player.pr.setturn(1);
                            }
                            else if(Playerstats.stats.getturn() == 2){
                                Player.pr.setturn(2);
                            }
                            
                         } 
                         
                           
                         
                         
                          });
            
           
           
           
           board.add(spot[row][collum]);
           
            }
        }
     int size = 60;
     for(Piece p: piecelist){
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
                
                
       
    board.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    board.setResizable(false);
    board.setLocationRelativeTo(null);
    
    
    
    
    }
     
 
    
}


