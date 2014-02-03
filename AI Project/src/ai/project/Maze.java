package ai.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author Miguel Silva and Adam Silva
 */
public class Maze {

    private Space maze[][];
    private boolean isPossible = true;
    private Stack<Space> stack;
    private int begin[] = new int[2];
    private int end[] = new int[2];
    private long dfsTime, bfsTime;

    //Method which will read a text file, and from it create the respective Maze
    public void readFile(String filepath) throws FileNotFoundException {
        Scanner in = new Scanner(new File(filepath));

        //Reads the file, and saves each line as a String in an ArrayList
        ArrayList<String> text = new ArrayList<>();
        while (in.hasNextLine()) {
            text.add(in.nextLine());
        }

        //Gets the coordinates for the Beginning and End of the Maze from the last two lines of the text file
        String txtBegin[] = text.get(text.size() - 2).split(",");
        getBegin()[0] = Integer.parseInt(txtBegin[0]);
        getBegin()[1] = Integer.parseInt(txtBegin[1]);
        String txtEnd[] = text.get(text.size() - 1).split(",");
        getEnd()[0] = Integer.parseInt(txtEnd[0]);
        getEnd()[1] = Integer.parseInt(txtEnd[1]);

        //Gets the number of lines and columns the maze will have, and then calls the method create it
        int nrColumns = (text.get(0).length() - 1) / 2;
        int nrLines = (text.size() - 3) / 2;
        maze = new Space[nrLines][nrColumns];

        //Creates every Space for the Maze checking around each one for it's viable directions, checking for barriers and walls, seeing where it's possible to go from there. If direction is true, it's possible
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

                maze[i][j] = new Space(up, left, down, right, i, j);
                column = column + 2;
            }
            line = line + 2;
        }
    }

    //Right->Down->Left->Up
    public void methodDFS() {
        long startTime = System.nanoTime();

        Stack<Space> stackDFS = new Stack();
        int count = 0;
        boolean nextMove = true;
        Space currentSpace = maze[getBegin()[0]][getBegin()[1]];
        Space nextOne;

        while (nextMove) {
            nextMove = false;
            if (currentSpace.hasSpaceRight() == true && maze[currentSpace.getLine()][currentSpace.getColumn() + 1].getSpaceNumber() == 0) {
                nextOne = maze[currentSpace.getLine()][currentSpace.getColumn() + 1];
                stackDFS.addElement(nextOne);
                nextMove = true;
            }
            if (currentSpace.hasSpaceDown() == true && maze[currentSpace.getLine() + 1][currentSpace.getColumn()].getSpaceNumber() == 0) {
                nextOne = maze[currentSpace.getLine() + 1][currentSpace.getColumn()];
                stackDFS.addElement(nextOne);
                nextMove = true;
            }
            if (currentSpace.hasSpaceLeft() == true && maze[currentSpace.getLine()][currentSpace.getColumn() - 1].getSpaceNumber() == 0) {
                nextOne = maze[currentSpace.getLine()][currentSpace.getColumn() - 1];
                stackDFS.addElement(nextOne);
                nextMove = true;
            }
            if (currentSpace.hasSpaceUp() == true && maze[currentSpace.getLine() - 1][currentSpace.getColumn()].getSpaceNumber() == 0) {
                nextOne = maze[currentSpace.getLine() - 1][currentSpace.getColumn()];
                stackDFS.addElement(nextOne);
                nextMove = true;
            }

            count++;
            maze[currentSpace.getLine()][currentSpace.getColumn()].setSpaceNumber(count);

            if (!stackDFS.isEmpty()) {
                do {
                    if (stackDFS.peek().getSpaceNumber() != 0) {
                        stackDFS.pop();
                    }
                } while (!stackDFS.isEmpty() && stackDFS.peek().getSpaceNumber() != 0);
                if (!stackDFS.isEmpty()) {
                    currentSpace = stackDFS.pop();
                    nextMove = true;
                }
            }
        }

        if (maze[end[0]][end[1]].getSpaceNumber() == 0) {
            isPossible = false;
        }

        dfsTime = System.nanoTime() - startTime;
    }

    //Up->Left->Down->Right
    public void methodBFS() {
        long startTime = System.nanoTime();
        Queue<Space> queueBFS = new LinkedList();
        int count = 0;
        boolean nextMove = true;
        Space currentSpace = maze[getBegin()[0]][getBegin()[1]];
        Space nextOne;

        while (nextMove) {
            nextMove = false;
            if (currentSpace.hasSpaceUp() == true && maze[currentSpace.getLine() - 1][currentSpace.getColumn()].getSpaceNumber() == 0) {
                nextOne = maze[currentSpace.getLine() - 1][currentSpace.getColumn()];
                queueBFS.add(nextOne);
                nextMove = true;
            }
            if (currentSpace.hasSpaceLeft() == true && maze[currentSpace.getLine()][currentSpace.getColumn() - 1].getSpaceNumber() == 0) {
                nextOne = maze[currentSpace.getLine()][currentSpace.getColumn() - 1];
                queueBFS.add(nextOne);
                nextMove = true;
            }
            if (currentSpace.hasSpaceDown() == true && maze[currentSpace.getLine() + 1][currentSpace.getColumn()].getSpaceNumber() == 0) {
                nextOne = maze[currentSpace.getLine() + 1][currentSpace.getColumn()];
                queueBFS.add(nextOne);
                nextMove = true;
            }
            if (currentSpace.hasSpaceRight() == true && maze[currentSpace.getLine()][currentSpace.getColumn() + 1].getSpaceNumber() == 0) {
                nextOne = maze[currentSpace.getLine()][currentSpace.getColumn() + 1];
                queueBFS.add(nextOne);
                nextMove = true;
            }
            count++;
            maze[currentSpace.getLine()][currentSpace.getColumn()].setSpaceNumber(count);
            //printQueue(queueBFS);

//            currentSpace = queueBFS.poll();
            if (!queueBFS.isEmpty()) {
                do {
                    if (queueBFS.peek().getSpaceNumber() != 0) {
                        queueBFS.poll();
                    }
                } while (!queueBFS.isEmpty() && queueBFS.peek().getSpaceNumber() != 0);
                if (!queueBFS.isEmpty()) {
                    currentSpace = queueBFS.poll();
                    nextMove = true;
                }
            }

        }

        if (maze[end[0]][end[1]].getSpaceNumber() == 0) {
            isPossible = false;
        }

        bfsTime = System.nanoTime() - startTime;
    }

    public Stack<Space> solve() {

        if (isPossible = true) {
            stack = new Stack<Space>();
            Space currentSpace = maze[end[0]][end[1]];
            Space next;

            do {
                stack.push(currentSpace);
                next = currentSpace;

                if (currentSpace.getLine() != 0
                        && maze[currentSpace.getLine() - 1][currentSpace.getColumn()].getSpaceNumber() < next.getSpaceNumber()
                        && maze[currentSpace.getLine()][currentSpace.getColumn()].hasSpaceUp() == true) {
                    next = maze[currentSpace.getLine() - 1][currentSpace.getColumn()];
                }
                if (currentSpace.getColumn() != 0
                        && maze[currentSpace.getLine()][currentSpace.getColumn() - 1].getSpaceNumber() < next.getSpaceNumber()
                        && maze[currentSpace.getLine()][currentSpace.getColumn()].hasSpaceLeft() == true) {
                    next = maze[currentSpace.getLine()][currentSpace.getColumn() - 1];
                }
                if (currentSpace.getLine() != maze.length - 1
                        && maze[currentSpace.getLine() + 1][currentSpace.getColumn()].getSpaceNumber() < next.getSpaceNumber()
                        && maze[currentSpace.getLine()][currentSpace.getColumn()].hasSpaceDown() == true) {
                    next = maze[currentSpace.getLine() + 1][currentSpace.getColumn()];
                }
                if (currentSpace.getColumn() != maze[0].length - 1
                        && maze[currentSpace.getLine()][currentSpace.getColumn() + 1].getSpaceNumber() < next.getSpaceNumber()
                        && maze[currentSpace.getLine()][currentSpace.getColumn()].hasSpaceRight() == true) {
                    next = maze[currentSpace.getLine()][currentSpace.getColumn() + 1];
                }
                currentSpace = next;

            } while (currentSpace.getSpaceNumber() != 1);

        }
        return stack;
    }

    public boolean[] CheckBlockBorders(Space space) {
        boolean walls[] = new boolean[4];
        walls[0] = walls[1] = walls[2] = walls[3] = true;

        if (space.getLine() == 0) {
            walls[0] = false;
        }
        if (space.getColumn() == 0) {
            walls[1] = false;
        }
        if (space.getLine() == maze.length - 1) {
            walls[2] = false;
        }
        if (space.getColumn() == maze[0].length - 1) {
            walls[3] = false;
        }

        return walls;
    }

    public void printQueue(Queue<Space> queue) {
        for (Space space : queue) {
            System.out.print(space.getSpaceNumber() + ",");
        }
        System.out.println();
    }

    public int[] getBegin() {
        return begin;
    }

    public void setBegin(int[] begin) {
        this.begin = begin;
    }

    public int[] getEnd() {
        return end;
    }

    public void setEnd(int[] end) {
        this.end = end;
    }

    public Space getSpace(int line, int column) {
        return maze[line][column];
    }

    public int getNumLines() {
        return maze.length;
    }

    public int getNumColumns() {
        return maze[0].length;
    }

    public boolean getPossible() {
        return isPossible;
    }

    public void clearMaze() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                maze[i][j].setSpaceNumber(0);
            }
        }
        stack = null;
    }

    public long getDfsTime() {
        return dfsTime;
    }

    public long getBfsTime() {
        return bfsTime;
    }

}