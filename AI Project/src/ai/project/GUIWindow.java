package ai.project;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.GridLayout;
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
    GUISpace spaces[][];
    JPanel cardLayers;
    CardLayout cardLayout;
    String filePath;

    public GUIWindow() {
        super("Maze Solver");
        setIconImage(new ImageIcon("Images\\Icon.png").getImage());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        setLayout(new BorderLayout());

        setSize(700, 550);
        setBounds(0, 0, 700, 550);
        setMinimumSize(new Dimension(600, 400));

        cardLayout = new CardLayout();
        cardLayers = new JPanel(cardLayout);
        cardLayers.setOpaque(false);

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
        panel.add(cardLayers, BorderLayout.CENTER);
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
                        filePath = fd.getDirectory() + "\\" + fd.getFile();
                        maze.readFile(filePath);
                        boolean top = false, left = false, down = false, right = false, begin = false, end = false;
                        int[] entranceSpace = maze.getBegin();
                        int[] exitSpace = maze.getEnd();

                        spaces = new GUISpace[maze.getNumLines()][maze.getNumColumns()];

                        for (int i = 0; i < maze.getNumLines(); i++) {
                            for (int j = 0; j < maze.getNumColumns(); j++) {
                                if (maze.getSpace(i, j).hasSpaceUp()) {
                                    top = true;
                                }
                                if (maze.getSpace(i, j).hasSpaceLeft()) {
                                    left = true;
                                }
                                if (maze.getSpace(i, j).hasSpaceDown()) {
                                    down = true;
                                }
                                if (maze.getSpace(i, j).hasSpaceRight()) {
                                    right = true;
                                }
                                if (entranceSpace[0] == i && entranceSpace[1] == j) {
                                    begin = true;
                                }
                                if (exitSpace[0] == i && exitSpace[1] == j) {
                                    end = true;
                                }
                                spaces[i][j] = new GUISpace(top, left, down, right, begin, end);
                                top = left = down = right = begin = end = false;
                            }
                        }

                        JPanel mazePanel = new JPanel(new GridLayout(maze.getNumLines(), maze.getNumColumns()));

                        for (int i = 0; i < spaces.length; i++) {
                            for (int j = 0; j < spaces[0].length; j++) {
                                mazePanel.add(spaces[i][j]);
                            }
                        }

                        cardLayers.add(mazePanel, "Load Maze");
                        

                    } catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(rootPane, "Error openning the file.");
                    }
                }
                printMaze();
                cardLayout.show(cardLayers, "Maze Loaded");
            }
        });

        setVisible(true);
    }

    private void exit() {
        Object[] opYesNo = {"Yes", "No"};
        if (JOptionPane.showOptionDialog(this, "Do you really want to exit?", "Exit?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opYesNo, opYesNo[1]) == 0) {
            dispose();
        }
    }

    private void printMaze() {
        for (int i = 0; i < maze.getNumLines(); i++) {
            for (int j = 0; j < maze.getNumColumns(); j++) {
                spaces[i][j].setUnPassed();
                spaces[i][j].repaint();
            }
        }
    }
}
