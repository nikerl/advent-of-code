package AOC2022;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class day08 {
    public static void main(String[] args) throws Exception {
        ArrayList<String> forest = new ArrayList<>();
        readInputFile(forest);

        int count = visibleTrees(forest);
        System.out.println(count);

        int score = scenicScore(forest);
        System.out.println(score);
    }


    
    static void readInputFile(ArrayList<String> logs) {
        try {
            // read input file
            File file = new File("input/day08_input.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                // add each line to a list of pairs
                String items = scanner.nextLine();
                logs.add(items);
            }
            scanner.close();
        // if file couldnt be read print out error mgs
        } catch (FileNotFoundException e) {
            System.out.println("File could not be read.");
            e.printStackTrace();
        }
    }



    //---------------------------- PART 1 ----------------------------//

    static int visibleTrees(ArrayList<String> forest) {
        int count = 2*(forest.size()-2) + 2*forest.get(0).length();

        for (int i = 1; i < forest.size()-1; i++) {
            String row = forest.get(i);
            for (int j = 1; j < row.length()-1; j++) {
                int tree = row.charAt(j)-48;
                
                int above;
                int index = i - 1;
                while (index >= 0) {
                    above = forest.get(index).charAt(j)-48;
                    if (tree <= above) {
                        break;
                    }
                    index--;
                }
                if (index == -1) {
                    count++;
                    continue;
                }

                int below;
                index = i + 1;
                while (index <= forest.size()-1) {
                    below = forest.get(index).charAt(j)-48;
                    if (tree <= below) {
                        break;
                    }
                    index++;
                }
                if (index == forest.size()) {
                    count++;
                    continue;
                }

                int left;
                index = j - 1;
                while (index >= 0) {
                    left = row.charAt(index)-48;
                    if (tree <= left) {
                        break;
                    }
                    index--;
                }
                if (index == -1) {
                    count++;
                    continue;
                }

                int right;
                index = j + 1;
                while (index <= row.length()-1) {
                    right = row.charAt(index)-48;
                    if (tree <= right) {
                        break;
                    }
                    index++;
                }
                if (index == forest.size()) {
                    count++;
                    continue;
                }
            }
        }

        return count;
    }    



    //---------------------------- PART 2 ----------------------------//

    static int scenicScore(ArrayList<String> forest) {
        int score = 1;
        int newScore = 1;
        int count = 0;

        for (int i = 1; i < forest.size()-1; i++) {
            String row = forest.get(i);
            for (int j = 1; j < row.length()-1; j++) {
                newScore = 1;
                int tree = row.charAt(j)-48;
                
                int above;
                int index = i - 1;
                while (index >= 0) {
                    above = forest.get(index).charAt(j)-48;
                    if (tree <= above) {
                        count++;
                        break;
                    }
                    index--;
                    count++;
                }
                if (count != 0) {
                    newScore *= count;
                }
                count = 0;

                int below;
                index = i + 1;
                while (index <= forest.size()-1) {
                    below = forest.get(index).charAt(j)-48;
                    if (tree <= below) {
                        count++;
                        break;
                    }
                    index++;
                    count++;
                }
                if (count != 0) {
                    newScore *= count;
                }
                count = 0;

                int left;
                index = j - 1;
                while (index >= 0) {
                    left = row.charAt(index)-48;
                    if (tree <= left) {
                        count++;
                        break;
                    }
                    index--;
                    count++;
                }
                if (count != 0) {
                    newScore *= count;
                }
                count = 0;

                int right;
                index = j + 1;
                while (index <= row.length()-1) {
                    right = row.charAt(index)-48;
                    if (tree <= right) {
                        count++;
                        break;
                    }
                    index++;
                    count++;
                }
                if (count != 0) {
                    newScore *= count;
                }
                count = 0;


                if (newScore > score) {
                    score = newScore;
                }
            }
        }
        return score;
    }    
}
