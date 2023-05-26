import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Tic_Tac_Toe {
    static char board[] = { '-', '-', '-', '-', '-', '-', '-', '-', '-' };
    static char ai = 'X';
    static char human = 'O';
    static char currentplayer;

    public static void main(String[] args) {         // Main Entry point
        Scanner sc = new Scanner(System.in);
        System.out.println("You wanna play first ? y/n");
        String choice = sc.next();
        if (choice.equals("y")) {
            draw();
            currentplayer = human;
        } else if (choice.equals("n")) {
            currentplayer = ai;
        }
        boolean gameplaying = true;
        while (gameplaying) {
            pressed();
            draw();
            char c = checkwin();
            if (c == 'X') {
                System.out.println("Winner is : X");
                gameplaying = false;
            }
            if (c == 'O') {
                System.out.println("Winner is : O");
                gameplaying = false;
            }
            if (c == '.') {
                System.out.println("Its a tie :)");
                gameplaying = false;
            }

        }
    }

    // Drawing Board 
    public static void draw() {
        System.out.println(board[0] + " | " + board[1] + " | " + board[2]);
        System.out.println(board[3] + " | " + board[4] + " | " + board[5]);
        System.out.println(board[6] + " | " + board[7] + " | " + board[8]);
    }
    
    // helper function to check if the rows are equal or not 
    public static boolean equals(char a, char b, char c) {
        if (a == b && b == c && a != '-')
            return true;
        return false;
    }

    //checking win
    public static char checkwin() {
        char winner = '-';
        if (equals(board[0], board[1], board[2]))
            winner = board[0];
        if (equals(board[3], board[4], board[5]))
            winner = board[3];
        if (equals(board[6], board[7], board[8]))
            winner = board[6];

        if (equals(board[0], board[3], board[6]))
            winner = board[0];
        if (equals(board[1], board[4], board[7]))
            winner = board[1];
        if (equals(board[2], board[5], board[8]))
            winner = board[2];

        if (equals(board[0], board[4], board[8]))
            winner = board[0];
        if (equals(board[2], board[4], board[6]))
            winner = board[2];
        int openspots = 0;
        for (int i = 0; i < 9; i++)
            if (board[i] == '-')
                openspots++;
        if (winner == '-' && openspots == 0) {
            return '.';
        }
        return winner;
    }

    // pressed function to get the user input and then change the turn 
    public static void pressed() {
        checkwin();
        if (currentplayer == human) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter Your Move: ");
            int n = sc.nextInt() - 1;
            if (board[n] == '-') {
                board[n] = human;
                currentplayer = ai;
                bestmove();
            }
        } else {
            bestmove();
        }
    }
    // best move function to get the best move and then write it on the board ( ai side )
    public static void bestmove() {
        int bestscore = Integer.MIN_VALUE;
        int move = -1;
        for (int i = 0; i < 9; i++) {
            if (board[i] == '-') {
                board[i] = ai;
                int score = minimax(board, 0, false);
                board[i] = '-';
                if (score > bestscore) {
                    bestscore = score;
                    move = i;
                }
            }
        }
        if (move != -1) {
            board[move] = ai;
            currentplayer = human;
        }
    }
    
    // hashmap to write the outcome for every move (ai side)
    static HashMap<Character, Integer> scores = new HashMap<Character, Integer>() {
        {
            put('X', 10);
            put('O', -10);
            put('.', 0);
        }
    };
    
    // main massala minimax algorithm 
    public static int minimax(char[] board2, int depth, boolean isMaximizing) {
        char result = checkwin();
        if (result != '-')
            return scores.get(result);
        if (isMaximizing) {
            int bestscore = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                if (board2[i] == '-') {
                    board2[i] = ai;
                    int score = minimax(board2, depth + 1, false);
                    board2[i] = '-';
                    bestscore = Math.max(score, bestscore);
                }
            }
            return bestscore;
        }
        int bestscore = Integer.MAX_VALUE;
        for (int i = 0; i < 9; i++) {
            if (board2[i] == '-') {
                board2[i] = human;
                int score = minimax(board2, depth + 1, true);
                board2[i] = '-';
                bestscore = Math.min(score, bestscore);
            }
        }
        return bestscore;

    }
}
