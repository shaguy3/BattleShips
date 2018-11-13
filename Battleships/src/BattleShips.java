import java.util.Random;
import java.util.Scanner;

public class BattleShips {
        //First method: its purpose is to print the current state of the map.
        private static void printTheMap(char[][] mapToPrint) {
            int row, col;
            System.out.println("   0 1 2 3 4 5 6 7 8 9   ");
            for (row = 0; row < mapToPrint.length; row++) {
                System.out.print(row + " |");
                for (col = 0; col < mapToPrint[0].length; col++) {
                    if (mapToPrint[row][col] == '\u0000') {
                        if (col == 9) {
                            System.out.print(" ");
                        } else {
                            System.out.print("  "); } }
                    else if (col == 9){
                        System.out.print(mapToPrint[row][col]); }
                    else {
                        System.out.print(mapToPrint[row][col] + " "); } }
                System.out.println("| " + row); }
            System.out.println("   0 1 2 3 4 5 6 7 8 9   ");
            System.out.println(); }
        //End of printTheMap method.

        //Second method: its purpose is to set up the player's ships on the map.
        private static int[][] playerShipSetUp(char[][] theMap) {
            Scanner input = new Scanner(System.in);
            System.out.println("Lets set up your ships!");
            int [][] playerPos = new int[5][2];
            int row, col;
            int shipsPlaced = 0;
            while (shipsPlaced < 5) {
                System.out.print("Choose your next ship's row: ");
                while (!input.hasNextInt()) {
                    System.out.print("Choose your next ship's row(in numbers): ");
                    input.next(); }
                row = input.nextInt();
                while (row > 9 || row < 0) {
                    System.out.print("Please choose a number between 0 and 9: ");
                    while (!input.hasNextInt()) {
                        input.next();
                        System.out.print("Please choose a NUMBER between 0 and 9: "); }
                    row = input.nextInt(); }
                System.out.print("Choose your next ship's column: ");
                while (!input.hasNextInt()) {
                    System.out.print("Choose your next ships's column(in numbers): ");
                    input.next(); }
                col = input.nextInt();
                while (col > 9 || col < 0) {
                    System.out.print("Please choose a number between 0 and 9: ");
                    while (!input.hasNextInt()) {
                        input.next();
                        System.out.print("Please choose a NUMBER between 0 and 9: "); }
                    col = input.nextInt(); }
                if (theMap[row][col] == '@') {
                    System.out.println("This place is occupied. Please choose a different space."); }
                else {
                    theMap[row][col] = '@';
                    playerPos[shipsPlaced][0] = row;
                    playerPos[shipsPlaced][1] = col;
                    shipsPlaced++;
                    System.out.println("Ship no'" + shipsPlaced + " is placed."); } }
            printTheMap(theMap);
            System.out.println();
            return playerPos; }
        // End of the playerShipsSetUp method.

        //Forth method: its purpose is to set up the computer's map.
        private static int[][] computerShipSetUp(int[][] playerPos) {
            Random randnum = new Random();
            int[][] computerPos = new int[5][2];
            int row,col;
            int shipsPlaced = 0;
            while (shipsPlaced < 5) {
                row = randnum.nextInt(9);
                col = randnum.nextInt(9);
                if (!(playerPos[shipsPlaced][0] == row) || !(playerPos[shipsPlaced][1] == col)) {
                    computerPos[shipsPlaced][0] = row;
                    computerPos[shipsPlaced][1] = col;
                    shipsPlaced++; } }
            return computerPos; }
        //End of the computerShipSetUp method.

        //Fifth method: its purpose is to get a valid bombing choice from the player.
        private static int[] playerTurn () {
            Scanner input = new Scanner(System.in);
            int[] playerChoice = new int[2];
            int row, col;
            System.out.print("Choose a row to bomb: ");
            while (!input.hasNextInt()) {
                System.out.print("Choose a row to bomb(in numbers): ");
                input.next(); }
            row = input.nextInt();
            while (row > 9 || row < 0) {
                System.out.print("Please choose a number between 0 and 9: ");
                while (!input.hasNextInt()) {
                    input.next();
                    System.out.print("Please choose a NUMBER between 0 and 9: "); }
                row = input.nextInt(); }
            System.out.print("Choose a column to bomb: ");
            while (!input.hasNextInt()) {
                System.out.print("Choose a column to bomb(in numbers): ");
                input.next(); }
            col = input.nextInt();
            while (col > 9 || col < 0) {
                System.out.print("Please choose a number between 0 and 9: ");
                while (!input.hasNextInt()) {
                    input.next();
                    System.out.print("Please choose a NUMBER between 0 and 9: "); }
                col = input.nextInt(); }
            playerChoice[0] = row;
            playerChoice[1] = col;
            return playerChoice; }
        //End of playerTurn.

        //Sixth method: its purpose is to get a valid bombing choice from the computer.
        private static int[] compTurn () {
            int[] compChoice = new int[2];
            Random randNum = new Random();
            int row = randNum.nextInt(9);
            int col = randNum.nextInt(9);
            compChoice[0] = row;
            compChoice[1] = col;
            return compChoice; }
        //End of compTurn.

        //Seventh method: its purpose is to check if a valid choice is a miss, a hit, or a self hit.
        private static int hitScan(int[] choice, int[]ownPos, int[] enemyPos) {
            if (choice[0] == enemyPos[0] && choice[1] == enemyPos[1])
                return 1;
            else if (choice[0] == ownPos[0] && choice[1] == ownPos[1])
                return -1;
            else
                return 0; }
        //End of hitScan.

        /*Eighth method: its purpose is to generate player and computer choices and checking if they hit or miss.
          This process will continue until one of the sides is out of ships. */
        private static void battle (int[][] playerPos, int[][] computerPos, char[][] theMap) {
            System.out.println("Lets start the battle!!");
            int playerRemShips = 5;
            int computerRemShips = 5;
            int i, j, isHit =0;
            while ((playerRemShips > 0) && (computerRemShips > 0)) {
                System.out.println("Your turn.");
                int[] choice = playerTurn();
                for (i = 0; i < 5; i++) {
                    isHit = hitScan(choice, playerPos[i], computerPos[i]);
                    if (isHit == 1) {
                        System.out.println("Hit!");
                        System.out.println();
                        theMap[choice[0]][choice[1]] = 'h';
                        computerRemShips--;
                        break; }
                    else if (isHit == -1) {
                        System.out.println("Self hit!");
                        System.out.println();
                        theMap[choice[0]][choice[1]] = 's';
                        playerRemShips--;
                        break; } }
                if (isHit == 0) {
                    System.out.println("Miss!");
                    System.out.println();
                    theMap[choice[0]][choice[1]] = 'x'; }
                System.out.println("The computer's turn.");
                choice = compTurn();
                for (j = 0; j < 5; j++) {
                    isHit = hitScan(choice, computerPos[j], playerPos[j]);
                    if (isHit == 1) {
                        System.out.println("Hit!");
                        System.out.println();
                        theMap[choice[0]][choice[1]] = 'h';
                        playerRemShips--;
                        printTheMap(theMap);
                        break; }
                    else if (isHit == -1) {
                        System.out.println("Self hit!");
                        System.out.println();
                        theMap[choice[0]][choice[1]] = 's';
                        computerRemShips--;
                        printTheMap(theMap);
                        break; } }
                if (isHit == 0) {
                    System.out.println("Miss!");
                    System.out.println();
                    theMap[choice[0]][choice[1]] = 'x';
                    printTheMap(theMap); } }
            if (playerRemShips == 0)
                System.out.print("Game over. You lost.");
            if (computerRemShips == 0)
                System.out.print("Game over. You won!!"); }
        //End of battle.

        public static void main(String[] args) {
            System.out.println("Welcome to the game of Battleships!");
            System.out.println();
            char[][] theMap = new char[10][10];
            printTheMap(theMap);
            int[][] playerPos = playerShipSetUp(theMap);
            int[][] computerPos = computerShipSetUp(playerPos);
            battle(playerPos, computerPos, theMap);
            //Testing Commits to Github
        }
    }
