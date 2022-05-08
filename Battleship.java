package battleship;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Battleship {
    /*******************************************************
     * this class holds the global varibles that will 
     * represent the game pieces and the game board.
     *******************************************************/
    public class Board {
        public static int[][] grid = {
            {5, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {5, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {5, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {5, 0, 0, 0, 0, 0, 0, 2, 0, 0},
            {5, 0, 3, 3, 3, 0, 0, 2, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 4, 4, 4, 4, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        public static int[][] hits = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        public static int shots = 20;
        public static int twoShip = 1;
        public static int threeShip = 1;
        public static int fourShip = 1;
        public static int fiveShip = 1;
    }
    
    /**************************************************
     * Displays game menue.
     **************************************************/
    public static int displayMenu() {
        Scanner menuInput = new Scanner(System.in);
        System.out.println("Menu:");
        System.out.println("1: Begin a new game.");
        System.out.println("2: Continue current game.");
        System.out.println("3: Save game.");
        System.out.println("4: Load saved game.");
        System.out.println("5: Quit game.");
        System.out.println("You can type 0 at any time to return to this menu.");
        int choice = menuInput.nextInt();
        //menuInput.close();
        return choice;
    }
    /***************************************************
     * uses the hits array to print th current
     * game grid showing hits and misses. 
     ***************************************************/
    public static void printGrid() {
        for (int row = 0; row < 10; row++) {
            if (row == 0) {
                System.out.println("    1   2   3   4   5   6   7   8   9   10");
                System.out.println("  -----------------------------------------");
            }
            for (int col = 0; col < 10; col++) {
                //wite the number and the first grid line
                if (col == 0) {
                    //adjusts for the 10th row to align right
                    if (row == 9) {
                        if (Board.hits[row][col] > 0) { //hit
                            System.out.print((row + 1) + "| X ");
                        }
                        else if (Board.hits[row][col] < 0) { //miss
                            System.out.print((row + 1) + "| ~ ");    
                        }
                        else { //no iteraction
                            System.out.print((row + 1) + "|   ");
                        }
                    }
                    //all the other rows have an extara space
                    else {
                        if (Board.hits[row][col] > 0) { //hit
                            System.out.print((row + 1) + " | X ");
                        }
                        else if (Board.hits[row][col] < 0) { //miss
                            System.out.print((row + 1) + " | ~ ");    
                        }
                        else { //no interaction
                            System.out.print((row + 1) + " |   ");
                        }
                    }
                }
                //the end of the row also prints different
                else if (col == 9) {
                    if (Board.hits[row][col] > 0) { //hit
                        System.out.println("| X |");
                    }
                    else if (Board.hits[row][col] < 0) { //miss
                        System.out.println("| ~ |");    
                    }
                    else { //no interaction
                        System.out.println("|   |");
                    }
                }
                //how all the middle squares print
                else {
                    if (Board.hits[row][col] > 0) { //hit
                        System.out.print("| X ");
                    }
                    else if (Board.hits[row][col] < 0) { //miss
                        System.out.print("| ~ ");
                    }
                    else { //no interaction
                        System.out.print("|   ");
                    }
                }
            }
            System.out.println("  -----------------------------------------");
        }
    }

    /**************************************************************
     * sets all of the board variables to their default state,
     * creating a new game.
     **************************************************************/
    public static void newGame() {
        //this sets the hits grid array to all 0s.
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Board.hits[row][col] = 0;
            }
        }
        //this sets the shots vairiable to 20
        Board.shots = 20;

        //set all ships to 1 to say they aren't sunk
        Board.twoShip = 1;
        Board.threeShip = 1;
        Board.fourShip = 1;
        Board.fiveShip = 1;

        //show the empty grid to the player.
        printGrid();
    }

    /******************************************************
     * checks what ship got hit, counts how many times it
     * got hit and checks if it sunk.
     ******************************************************/
    public static void checkSink(int ship) {
        int count = 0;
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if (Board.hits[y][x] == ship) {
                    count += 1;
                }
            }
        }
        if (count == ship) {
            System.out.println("The ship sunk!");
            switch(ship) {
                case 2:
                Board.twoShip = 0;
                break;
                case 3:
                Board.threeShip = 0;
                break;
                case 4:
                Board.fourShip = 0;
                break;
                case 5:
                Board.fiveShip = 0;
                break;
            }
        }
    }

    /*********************************************************
     * Takes the coordinants the user put in and checks if 
     * they hit or miss.
     *********************************************************/
    public static void checkHit(int row, int col) {
        if (Board.grid[row][col] > 0) {
            System.out.println("HIT!");
            Board.hits[row][col] = Board.grid[row][col];
            checkSink(Board.hits[row][col]);
        }
        else {
            System.out.println("Miss...");
            Board.hits[row][col] = -1;
        }
    }

    /************************************************************************
     * If all the ships have been sunk return true.
     ************************************************************************/
    public static int checkWin() {
        if ((Board.twoShip == 0) && (Board.threeShip == 0) && (Board.fourShip == 0) && (Board.fiveShip == 0)) {
            return 1;
        }
        else {
            return 0;
        }
    }

    /******************************************************
     * Play game lets the user interact with the current
     * grid.
     ******************************************************/
    public static void playGame() {
        Scanner inputScanner = new Scanner(System.in);
        int x;
        int y;

        while (Board.shots > 0) {
            System.out.print("Input a column number: ");
            x = inputScanner.nextInt();
            while (x > 10 || x < 0) {
                System.out.println("ERROR: Number not valid");
                System.out.print("Input a column number: ");
                x = inputScanner.nextInt();
            }
            if (x == 0) {
                return;
            }
            System.out.print("Input a row number: ");
            y = inputScanner.nextInt();
            while (y > 10 || y < 0) {
                System.out.println("ERROR: Number not valid");
                System.out.print("Input a row number: ");
                y = inputScanner.nextInt();
            }
            if (y == 0) {
                return;
            }
            checkHit(y-1, x-1);
            printGrid();
            Board.shots -= 1;
            if (checkWin() > 0) {
                break;
            }
            System.out.println("You have " + Board.shots + " shots left.");
        }
        if (checkWin() > 0) {
            System.out.println("You win!");
        }
        else {
            System.out.println("You're out of shots. You lose.");
        }
    }

    /******************************************************
     * This will save the games current progress.
     ******************************************************/
    public static void saveGame() {
        try {
            FileWriter writeSave = new FileWriter("save.txt");
            for (int row = 0; row < 10; row++) {
                for (int col = 0; col < 10; col++) {
                    if (col == 9) {
                        writeSave.write(Board.hits[row][col] + "\n");
                    }
                    else {
                        writeSave.write(Board.hits[row][col] + " ");
                    }
                }
            }
            //close the file
            writeSave.close();
            System.out.println("Your game was saved successfully!");
        } catch (IOException e) {
            System.out.println("ERROR");
            e.printStackTrace();
        }
    }

    /***********************************************************
     * copies the contents in the save file to hits and uses it
     * to check how many shots are left.
     ***********************************************************/
    public static void loadGame() {
        char[] contents = new char[200];
        Board.shots = 20;
        try {
            FileReader loadSave = new FileReader("save.txt");
            loadSave.read(contents);

            int x = 0;
            for (int row = 0; row < 10; row++) {
                for (int col = 0; col < 10; col++) {
                    if (contents[x] != ' ' && contents[x] != '\n' && contents[x] != '-') {
                        if (contents[x] == '1') {
                            Board.hits[row][col] = -1;
                        }
                        else {
                            Board.hits[row][col] = Character.getNumericValue(contents[x]);
                        }
                        if (Board.hits[row][col] != 0) {
                            Board.shots -= 1;
                        }
                    }
                    else {
                        col--;
                    }
                    x++;
                }
            }
            //close the file*/
            loadSave.close();
        } catch (IOException e) {
            System.out.println("ERROR");
            e.printStackTrace();
        }
    }

    /******************************************************
     * the main function. Starting point of the code.
     ******************************************************/
    public static void main(String[] args) {
        int menuAction = 0;
        int startup = 1;

        do {
            if (startup == 1) { //this will only happen on startup
                System.out.println("Welcome to Battleship!");
                startup += 1;
            }
            menuAction = displayMenu();
            switch (menuAction) {
                case 1:
                System.out.println("Starting Game..");
                newGame();
                playGame();
                break;
                case 2:
                printGrid();
                playGame();
                break;
                case 3:
                saveGame();
                break;
                case 4:
                loadGame();
                printGrid();
                playGame();
                break;
                case 5:
                break;
                default:
                System.out.println("Invalid Input");
            }
        } while (menuAction != 5);
    }
}
