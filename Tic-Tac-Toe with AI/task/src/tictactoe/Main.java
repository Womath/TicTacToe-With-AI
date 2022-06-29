package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        while (true) {
            System.out.println("Input command: ");
            Scanner scanner = new Scanner(System.in);
            String[] inputCommand = scanner.nextLine().split(" ");
            if (inputCommand[0].equalsIgnoreCase("exit")) {
                System.exit(0);
            } else if (inputCommand.length != 3) {
                System.out.println("Bad parameters!");
                continue;
            } else if (inputCommand[0].equalsIgnoreCase("start") &&
                    (inputCommand[1].equalsIgnoreCase("easy") || inputCommand[1].equalsIgnoreCase("user") ||
                            inputCommand[1].equalsIgnoreCase("medium") || inputCommand[1].equalsIgnoreCase("hard")) &&
                    (inputCommand[2].equalsIgnoreCase("easy") || inputCommand[2].equalsIgnoreCase("user") ||
                            inputCommand[2].equalsIgnoreCase("medium") || inputCommand[2].equalsIgnoreCase("hard"))
            ){
                Board board = new Board();
                board.setBoard();
                while ((!board.isOWin) && (!board.isXWin) && (!board.isDraw)) {
                    switch (inputCommand[1].toLowerCase()) {
                        case "easy":
                            board.moveEasyAI();
                            break;
                        case "medium":
                            board.moveMediumAI();
                            break;
                        case "user":
                            board.placeNext();
                            break;
                        case "hard":
                            board.bestMove();
                            break;
                    }

                    if ((board.isOWin) || (board.isXWin) || (board.isDraw)) {
                        break;
                    }

                    switch (inputCommand[2].toLowerCase()) {
                        case "easy":
                            board.moveEasyAI();
                            break;
                        case "medium":
                            board.moveMediumAI();
                            break;
                        case "user":
                            board.placeNext();
                            break;
                        case "hard":
                            board.bestMove();
                    }
                }
            } else {
                System.out.println("Bad parameters!");
                continue;
            }
        }
    }
}
