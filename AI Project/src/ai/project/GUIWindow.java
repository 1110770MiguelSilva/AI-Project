package ai.project;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author Miguel
 */
public class GUIWindow extends JFrame {

    private Maze maze = new Maze();
    JMenuBar menu;
    JMenu file, solve;
    JMenuItem exit, dfs, bfs, importTxt;
    ImageIcon background;
    JScrollPane scrollPane;

    public GUIWindow() {
        super("Maze Solver");
        setIconImage(new ImageIcon("Images\\Icon.png").getImage());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        setLayout(new BorderLayout());

        setSize(1000, 850);
        setBounds(0, 0, 1000, 850);
        setMinimumSize(new Dimension(600, 400));

        menu = new JMenuBar();
        add(menu);

        background = new ImageIcon("Images\\background.jpg");
        JPanel panel = new JPanel() {

            @Override
            public void paintComponent(Graphics g) {

                Dimension d = getSize();
                g.drawImage(background.getImage(), 0, 0, d.width, d.height, null);
            }
        };
        panel.setLayout(new BorderLayout());
        panel.add(menu, BorderLayout.NORTH);
        scrollPane = new JScrollPane(panel);
        setContentPane(scrollPane);

        file = new JMenu("File");
        file.setMnemonic('F');
        solve = new JMenu("Solve");
        solve.setMnemonic('S');
        exit = new JMenuItem("Exit", 'E');
        exit.setAccelerator(KeyStroke.getKeyStroke("ctrl E"));
        dfs = new JMenuItem("DFS", 'D');
        dfs.setAccelerator(KeyStroke.getKeyStroke("ctrl D"));
        bfs = new JMenuItem("BFS", 'B');
        bfs.setAccelerator(KeyStroke.getKeyStroke("ctrl B"));
        importTxt = new JMenuItem("Import Maze");
        importTxt.setAccelerator(KeyStroke.getKeyStroke("ctrl I"));

        menu.add(file);
        menu.add(solve);
        file.add(importTxt);
        file.add(exit);
        solve.add(dfs);
        solve.add(bfs);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });

        exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });

        importTxt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fd = new FileDialog(GUIWindow.this, "Import Maze", FileDialog.LOAD);
                fd.setFile(".txt");
                fd.setLocation(GUIWindow.this.getX() + 100, GUIWindow.this.getY() + 100);
                fd.show();
                if (!fd.getFile().endsWith(".txt")) {
                    JOptionPane.showMessageDialog(GUIWindow.this, "Wrong file extension", "Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        maze.readFile(fd.getDirectory() + "\\" + fd.getFile());
                    } catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(rootPane, "Error openning the file.");
                    }
                }

            }
        });

        setVisible(true);
    }

    private void exit() {
        Object[] opYesNo = {"Yes", "No"};
        if (JOptionPane.showOptionDialog(this, "Do you really want to quit?", "Quit?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opYesNo, opYesNo[1]) == 0) {
            dispose();
        }
    }

    private void readFile(String file) {

    }
}
