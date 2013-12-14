/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ai.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author Miguel
 */
public class AIProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Maze maze = new Maze();
//        JFileChooser fc = new JFileChooser();
//        int returnVal = fc.showOpenDialog(null);
//
//        if (returnVal == JFileChooser.APPROVE_OPTION) {
//            File file = fc.getSelectedFile();
//            String path = file.getAbsolutePath();
//            if (path.endsWith(".txt")) {
//                try {
//                    maze.readFile(path);
//                } catch (FileNotFoundException ex) {
//                    Logger.getLogger(AIProject.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
        try {
            maze.readFile("C:\\Users\\Adam\\Documents\\AI-Project\\testing normal maze.txt");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AIProject.class.getName()).log(Level.SEVERE, null, ex);
        }
        maze.methodBFS();
        maze.resetMaze();
        System.out.println("_______________________________________");
        
        maze.methodDFS();
       
    }
    
}
