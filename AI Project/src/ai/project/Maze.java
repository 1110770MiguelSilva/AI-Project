package ai.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author Miguel Silva and Adam Silva
 */
public class Maze {

    private Space maze[][];
    private int begin[] = new int[2];
    private int end[] = new int[2];

    //Method which will read a text file, and from it create the respective Maze
    public void readFile(String filepath) throws FileNotFoundException {
        Scanner in = new Scanner(new File(filepath));

        //Reads the file, and saves each line as a String in an ArrayList
        ArrayList<String> text = new ArrayList<String>();
        while (in.hasNextLine()) {
            text.add(in.nextLine());
        }

        //Gets the coordinates for the Beginning and End of the Maze from the last two lines of the text file
        String txtBegin[] = text.get(text.size() - 2).split(",");
        begin[0] = Integer.parseInt(txtBegin[0]);
        begin[1] = Integer.parseInt(txtBegin[1]);
        String txtEnd[] = text.get(text.size() - 1).split(",");
        end[0] = Integer.parseInt(txtEnd[0]);
        end[1] = Integer.parseInt(txtEnd[1]);

        //Gets the number of lines and columns the maze will have, and then calls the method create it
        int nrColumns = (text.get(0).length() - 1) / 2;
        int nrLines = (text.size() - 3) / 2;
        maze = new Space[nrLines][nrColumns];

        //Creates every Space for the Maze checking around each one for it's viable directions, checking for barriers and walls, seeing where it's possible to go from there. If direction is true, it's possible.
        boolean up, right, left, down;
        int line = 1, column;
        for (int i = 0; i < nrLines; i++) {
            column = 1;
            for (int j = 0; j < nrColumns; j++) {
                up = right = left = down = true;

                if ((text.get(line - 1).charAt(column)) != ' ') {
                    up = false;
                }
                if ((text.get(line).charAt(column + 1)) != ' ') {
                    right = false;
                }
                if ((text.get(line + 1).charAt(column)) != ' ') {
                    down = false;
                }
                if ((text.get(line).charAt(column - 1)) != ' ') {
                    left = false;
                }

                maze[i][i] = new Space(up, left, down, right, i, j);
                column = column + 2;
            }
            line = line + 2;
        }
    }
}
