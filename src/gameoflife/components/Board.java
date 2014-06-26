/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameoflife.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

/**
 *
 * @author toshiba
 */
class Board extends JPanel {
   private List<Rectangle> squares = new ArrayList<Rectangle>();
   private int panelSideValue;
   
   public Board(int panelSideValue){
       this.panelSideValue = panelSideValue;
   }

   public void addCell(int x, int y) {
      Rectangle rect = new Rectangle(x, y, Cell.sideValue, Cell.sideValue);
      squares.add(rect);
   }
   
   public void addCell(Cell cell) {
      Rectangle rect = new Rectangle(cell.getX(), cell.getY(), Cell.sideValue, Cell.sideValue);
      squares.add(rect);
   }

   @Override
   public Dimension getPreferredSize() {
      return new Dimension(panelSideValue, panelSideValue);
   }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;
      for (Rectangle rect : squares) {
         g2.draw(rect);
      }
   }

}