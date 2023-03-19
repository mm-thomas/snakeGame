package mthomas;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Snake {

    ArrayList<Integer> xSnakeLoc = new ArrayList<Integer>(); // X coordinate of Snake Location
    ArrayList<Integer> ySnakeLoc = new ArrayList<Integer>(); // Y coordinate of Snake Location

    private int WIDTH = 25;
    private int HEIGHT = 30;
    private char[][] board = new char[WIDTH][HEIGHT];

    private int snakeLength = 2;
    private boolean isAlive = true;
    private char dir = 'R'; // initial direction of Snake

    private int xFruitLoc; // location of Fruit in game board
    private int yFruitLoc;

    Snake(){
        setFruitLoc();
        
        //set Snake Starting Location (5,5) , (5,6)
        xSnakeLoc.add( 5);
        ySnakeLoc.add( 5);
        xSnakeLoc.add( 5);
        ySnakeLoc.add( 6);

        draw();
    }

    public void setFruitLoc(){
        xFruitLoc = generateFruit();
        yFruitLoc = generateFruit();
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void draw(){
        for (int i = 0; i <WIDTH; i++){
            for (int j = 0; j <HEIGHT; j++){
                if (i == 0 || i == WIDTH -1 || j == 0 || j == HEIGHT-1){
                    board[i][j] = '#'; // wall symbol
                    System.out.print('#');
                }
                else if (xSnakeLoc.get(0) == i && ySnakeLoc.get(0) == j){
                    System.out.print('+'); // snakeHead symbol
                }
                else if (isSnakeLocation(i,j) == true){
                    System.out.print('$'); // snakeBody symbol
                }
                else if (i == xFruitLoc && j == yFruitLoc){
                    board[i][j] = '@'; // fruit symbol
                    System.out.print('@');
                }
                else{
                    System.out.print(" ");
                }
            }
           System.out.println("\t");
        }
    }

    boolean isSnakeLocation(int row, int col){// find if Snake body exists at row, col
        for (int i = 1; i < snakeLength; i++){
            if (xSnakeLoc.get(i) == row && ySnakeLoc.get(i) == col){
                return true;
            }
        }    
        return false;  
    }

    // filter commands with switch statement, if previous dir is contrary to current dir (eg. prev= left and new input= right)
    // move the snake left
    // because eg. the snake head facing left cannot move to the right, it can only go up, down or straight left.
    public void move(char input){

        if(input == 'S'){
            input = dir;
        }
  
        switch(input){
            case 'R': // MOVE RIGHT
            if (dir != 'L') {
                if (snakeLength == 1){
                    System.out.println("Entered right");
                    ySnakeLoc.set(0, ySnakeLoc.get(0) + 1);
                }
                else{
                    xSnakeLoc.add(0, xSnakeLoc.get(0));   
                    ySnakeLoc.add(0, ySnakeLoc.get(0) + 1); // new head location at same row, y+1 col
                    dir = 'R';

                    xSnakeLoc.remove(xSnakeLoc.size() - 1);
                    ySnakeLoc.remove(ySnakeLoc.size() - 1);
                }
                break;
            }else{
                move(dir);
            }

            case 'L': // MOVE LEFT
                if (dir != 'R'){
                    if (snakeLength == 1){
                        ySnakeLoc.set(0, ySnakeLoc.get(0)-1);
                    }else{
                        xSnakeLoc.add(0, xSnakeLoc.get(0));   // new head location at same row, y-1 column
                        ySnakeLoc.add(0, ySnakeLoc.get(0) - 1);
                        dir = 'L';

                        xSnakeLoc.remove(xSnakeLoc.size() - 1);
                        ySnakeLoc.remove(ySnakeLoc.size() - 1);
                    }
                    break;
                }else{
                    move(dir);
                }

            case 'U': // MOVE UP
                if (dir != 'D'){
                    if (snakeLength == 1){
                        xSnakeLoc.set(0, xSnakeLoc.get(0)-1);
                    }else{
                        xSnakeLoc.add(0, xSnakeLoc.get(0)-1);   // new head location at x-1 row, same column
                        ySnakeLoc.add(0, ySnakeLoc.get(0));
                        dir = 'U';

                        xSnakeLoc.remove(xSnakeLoc.size() - 1);
                        ySnakeLoc.remove(ySnakeLoc.size() - 1);
                    }
                    break;
                }
                else{
                    move(dir);
                }
            
            case 'D': // MOVE DOWN
            if (dir != 'U'){
                if (snakeLength == 1){
                    xSnakeLoc.set(0, xSnakeLoc.get(0)+1);
                } else{
                    xSnakeLoc.add(0, xSnakeLoc.get(0)+1);  // new head location at x+1 row, same column
                    ySnakeLoc.add(0, ySnakeLoc.get(0));
                    dir = 'D';

                    xSnakeLoc.remove(xSnakeLoc.size() - 1);
                    ySnakeLoc.remove(ySnakeLoc.size() - 1);
                }
                break;
            }else{
                move(dir);
            }
        }   
    }

    public int generateFruit(){
        Random rand = new Random();
        int random_int = rand.nextInt(WIDTH) + 1;
        while (random_int > WIDTH -1){
            random_int = rand.nextInt(WIDTH) + 1 ;
        }
        return random_int;
    }

    // .size() returns non-index order. eg (2,4) (1,3) == 2 size
    public void ifEatFruit(){
        if (xSnakeLoc.get(0) == xFruitLoc && ySnakeLoc.get(0) == yFruitLoc){
            snakeLength += 1;
            xSnakeLoc.add(xSnakeLoc.get(xSnakeLoc.size()-1)+1);
            // +1 of X at the end of snake body was put here randomly... writing +1 of Y at end of snake body instead, 
            // would only change the location of the grown snake's tail. -- same effect, slightly different locations
            ySnakeLoc.add(ySnakeLoc.get(ySnakeLoc.size()-1));
            board[xFruitLoc][yFruitLoc] = ' ';
            setFruitLoc(); // set new Fruit location
        } 
    }

    public boolean ifHitBody(){
        for (int i = 1; i < snakeLength; i++){
            if (xSnakeLoc.get(0) == xSnakeLoc.get(i) && 
            ySnakeLoc.get(0) == ySnakeLoc.get(i)){
                System.out.println("Hit snake body. Snake is dead.");
                isAlive = false; // false - is not alive
                return true; // true - hit body
            }
        }
        return false; // false - did not hit body
    }

    public boolean ifHitWall(){
        int xSnake = xSnakeLoc.get(0); // snake head X location
        int ySnake = ySnakeLoc.get(0); // snake head Y location
        if(xSnake == 0 || xSnake == WIDTH-1 ||
            ySnake == 0 || ySnake == HEIGHT-1){
            System.out.println("Hit wall. Snake is dead");
            isAlive = false; // false - is not alive
            return true;  // true hit wall
        }
        else{
            return false; // false - did not hit wall
        }
    }
   
  
    public static void main (String[] args){
        Snake s = new Snake();
        try (Scanner sc = new Scanner(System.in)) {
            while(s.isAlive()){
            System.out.println("Type R/L/U/D to change snake direction or S to keep it moving: ");
            char input = sc.next().charAt(0);  // Read user input
            
            s.move(input);
            s.ifEatFruit() ;

            if (s.ifHitBody() || s.ifHitWall()){
                break;
            }
            s.draw();
            }
        }
        System.out.println("Game Over.");
    }   
}


