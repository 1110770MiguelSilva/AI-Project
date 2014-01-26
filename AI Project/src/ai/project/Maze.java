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
    
    public void resetMaze() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                maze[i][j].setSpaceNumber(0);
            }
        }
    }
    
    //Right->Down->Left->Up
    public void methodDFS(){
        long startTime = System.nanoTime();    

        Stack <Space> stackDFS = new Stack();
        int count=0;
        boolean nextMove = true;
        Space currentSpace = maze[getBegin()[0]][getBegin()[1]];
        Space nextOne;
        
        while(nextMove){
            nextMove=false;
            if(currentSpace.hasSpaceRight()==true && maze[currentSpace.getLine()][currentSpace.getColumn()+1].getSpaceNumber()==0){
                //System.out.println("RIGHT = "+currentSpace.getLine()+","+currentSpace.getColumn());
                nextOne = maze[currentSpace.getLine()][currentSpace.getColumn()+1];
                stackDFS.addElement(nextOne);
                nextMove=true;
            }
            if(currentSpace.hasSpaceDown()==true && maze[currentSpace.getLine()+1][currentSpace.getColumn()].getSpaceNumber()==0){
                //System.out.println("DOWN = "+currentSpace.getLine()+","+currentSpace.getColumn());
                nextOne = maze[currentSpace.getLine()+1][currentSpace.getColumn()];
                stackDFS.addElement(nextOne);
                nextMove=true;
            }
            if(currentSpace.hasSpaceLeft()==true && maze[currentSpace.getLine()][currentSpace.getColumn()-1].getSpaceNumber()==0){
                //System.out.println("LEFT = "+currentSpace.getLine()+","+currentSpace.getColumn());
                nextOne = maze[currentSpace.getLine()][currentSpace.getColumn()-1];
                stackDFS.addElement(nextOne);
                nextMove=true;
            }
            if(currentSpace.hasSpaceUp()==true && maze[currentSpace.getLine()-1][currentSpace.getColumn()].getSpaceNumber()==0){
                //System.out.println("UP = "+currentSpace.getLine()+","+currentSpace.getColumn());
                nextOne = maze[currentSpace.getLine()-1][currentSpace.getColumn()];
                stackDFS.addElement(nextOne);
                nextMove=true;
            }
            
            count++;
            maze[currentSpace.getLine()][currentSpace.getColumn()].setSpaceNumber(count);
            //System.out.println("CurrentSpace: "+maze[currentSpace.getLine()][currentSpace.getColumn()].getSpaceNumber());
            
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
        long elapsedTime = System.nanoTime() - startTime;
        System.out.println("DFS-Elapsed Time:"+elapsedTime);
    }
    
    //Up->Left->Down->Right
    public void methodBFS(){
        long startTime = System.nanoTime();
        Queue <Space> queueBFS = new LinkedList();
        int count=0;
        boolean nextMove = true;
        Space currentSpace = maze[getBegin()[0]][getBegin()[1]];
        Space nextOne;
        
        while(nextMove){
            nextMove=false;
            if(currentSpace.hasSpaceUp()==true && maze[currentSpace.getLine() - 1][currentSpace.getColumn()].getSpaceNumber()==0){
                
                nextOne = maze[currentSpace.getLine()-1][currentSpace.getColumn()];
                queueBFS.add(nextOne);
                nextMove=true;
            }
            if(currentSpace.hasSpaceLeft()==true && maze[currentSpace.getLine()][currentSpace.getColumn()-1].getSpaceNumber()==0){
                
                nextOne = maze[currentSpace.getLine()][currentSpace.getColumn()-1];
                queueBFS.add(nextOne);
                nextMove=true;
            }
            if(currentSpace.hasSpaceDown()==true && maze[currentSpace.getLine()+1][currentSpace.getColumn()].getSpaceNumber()==0){
                
                nextOne = maze[currentSpace.getLine()+1][currentSpace.getColumn()];
                queueBFS.add(nextOne);
                nextMove=true;
            }
            if(currentSpace.hasSpaceRight()==true && maze[currentSpace.getLine()][currentSpace.getColumn()+1].getSpaceNumber()==0){
                
                nextOne = maze[currentSpace.getLine()][currentSpace.getColumn()+1];
                queueBFS.add(nextOne);
                nextMove=true;
            }
            count++;
            maze[currentSpace.getLine()][currentSpace.getColumn()].setSpaceNumber(count);
            //System.out.println("CurrentSpace: "+maze[currentSpace.getLine()][currentSpace.getColumn()].getSpaceNumber());
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
        long elapsedTime = System.nanoTime() - startTime;
        System.out.println("BFS-Elapsed Time:"+elapsedTime);
    }
    
    public void createMaze(int num1, int num2){
        Space maze[][] = new Space[num1][num2];
        
        for (int i = 0; i < num1; i++) {
            
            for (int j = 0; j < num2; j++) {
                
            }
        }
        
    }
    
    public void printQueue(Queue<Space> queue){
        for(Space space:queue){
            System.out.print(space.getSpaceNumber()+",");
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
    
    public Space getSpace(int line, int column){
        return maze[line][column];        
    }
    
    public int getNumLines(){
        return maze.length;
    }
    
    public int getNumColumns(){
        return maze[0].length;
    }
    
    
}
