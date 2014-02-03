/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextLayout;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUISpace extends Panel {

    boolean up, left, down, right, begin, end, passed, correct;
    int number;
    JLabel field;

    public GUISpace(boolean up, boolean left, boolean down, boolean right, boolean begin, boolean end) {
        this.up = up;
        this.left = left;
        this.down = down;
        this.right = right;
        this.begin = begin;
        this.end = end;
        this.passed = false;
        this.correct = false;
        this.number = 0;
    }

    @Override
    public void paint(Graphics g) {
        Dimension d = this.getSize();
        int width = d.width;
        int height = d.height;

        if (begin) {
            this.setBackground(Color.GREEN);
        } else if (end) {
            this.setBackground(Color.RED);
        } else {
            this.setBackground(Color.LIGHT_GRAY);
        }

        if (!up) {
            g.drawLine(0, 0, width, 0);
        }
        if (!left) {
            g.drawLine(0, 0, 0, height);
        }
        if (!down) {
            g.drawLine(0, height, width, height);
        }
        if (!right) {
            g.drawLine(width, 0, width, height);
        }
        if (passed) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(GUISpace.class.getName()).log(Level.SEVERE, null, ex);
            }
            g.drawString("" + number, (width / 3) + (width / 7), height / 3 + height / 7);
        }
        if (correct) {
            String draw = "";
            int l = String.valueOf(number).length();
            for (int i = 0; i < l + 2; i++) {
                draw += "*";
            }
            draw += "\n*" + number + "*\n";
            for (int i = 0; i < l + 2; i++) {
                draw += "*";
            }
            g.drawString(draw, (width / 3) + (width / 7), height / 3 + height / 7);
        }
    }

    private void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n")) {
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
        }
    }

    public void setPassed(int Number) {
        passed = true;
        this.number = Number;
    }

    public void setUnPassed() {
        passed = false;
        correct = false;
    }

    public void setCorrect() {
        correct = true;
    }
}
