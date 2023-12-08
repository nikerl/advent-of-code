package AOC2022;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class day02 {
    public static void main(String[] args) throws Exception {
        ArrayList<String> rounds = new ArrayList<String>();
        readInputFile(rounds);

        int score1 = scoreOfRounds1(rounds);
        int score2 = scoreOfRounds2(rounds);

        System.out.println("Part 1 score: " + score1);
        System.out.println("Part 2 score: " + score2);
    }



    static void readInputFile(ArrayList<String> rounds) {
        try {
            // read input file
            File file = new File("input/day02_input.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                // add each line to a list of rounds
                String items = scanner.nextLine();
                rounds.add(items);
            }
            scanner.close();
        // if file couldnt be read print out error mgs
        } catch (FileNotFoundException e) {
            System.out.println("File could not be read.");
            e.printStackTrace();
        }
    }



    //---------------------------- PART 1 ----------------------------//

    static int scoreOfRounds1(ArrayList<String> rounds) {
        int score = 0;

        for (int i = 0; i < rounds.size(); i++) {
            char opponent = rounds.get(i).charAt(0);
            char you = rounds.get(i).charAt(2);

            score += (int)you - 87; // add the points for your choice

            if ((int)you - (int)opponent == 23) {
                score += 3; // draw
            }
            else if ((you == 'X' && opponent == 'C') || // rock takes scissors
                     (you == 'Y' && opponent == 'A') || // paper takes rock
                     (you == 'Z' && opponent == 'B')) { // scissors takes paper
                score += 6;
            }
            else {
                score += 0; // you lost
            }
        }
        return score;
    }



    //---------------------------- PART 2 ----------------------------//

    static int scoreOfRounds2(ArrayList<String> rounds) {
        int score = 0;

        for (int i = 0; i < rounds.size(); i++) {
            char opponent = rounds.get(i).charAt(0);
            char you = rounds.get(i).charAt(2);


            if (you == 'X') { // you need to lose
                switch (opponent) {
                    case ('A'): score += 3; break; // rock takes scissors
                    case ('B'): score += 1; break; // paper takes rock
                    case ('C'): score += 2; break; // scissors takes paper
                }
                score += 0;
            }
            else if (you == 'Y') { // you need to draw
                score += (int)opponent - 64; // add the points of your opponents choice
                score += 3;
            }
            else if (you == 'Z') { // you need to win
                switch (opponent) {
                    case ('A'): score += 2; break; // paper takes rock
                    case ('B'): score += 3; break; // scissors takes paper
                    case ('C'): score += 1; break; // rock takes scissors
                }
                score += 6;
            }
        }
        return score;
    }
}
