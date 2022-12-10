import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class day01 {
    public static void main(String[] args) {
        int max = findMaxCals();
        int maxThree = findThreeMaxCals();

        System.out.println("The elf with the most calories had: " + max + " calories");
        System.out.println("The three elves with the most calories had: " + maxThree + " calories");
    }

    

   //---------------------------- PART 1 ----------------------------//

    static int findMaxCals() {
        int max = 0;
        try {
            // read input file
            File file = new File("input/day01_input.txt");
            Scanner scanner = new Scanner(file);
            int sum;

            while (scanner.hasNextLine()) {
                String calories = scanner.nextLine();
                sum = 0;

                while (calories != "") {
                    sum += Integer.parseInt(calories);

                    if (scanner.hasNextLine()) {
                        calories = scanner.nextLine();
                    }
                    else {
                        break;
                    }
                }
                if (sum > max) {
                    max = sum;
                }
            }
            scanner.close();

        // if file couldnt be read print out error mgs
        } catch (FileNotFoundException e) {
            System.out.println("File could not be read.");
            e.printStackTrace();
        }
        return max;
    }



   //---------------------------- PART 2 ----------------------------//

    static int findThreeMaxCals() {
        int[] max = {0, 0, 0};
        try {
            // read input file
            File file = new File("inputfiles/aoc1_input.txt");
            Scanner scanner = new Scanner(file);
            int sum;

            while (scanner.hasNextLine()) {
                String calories = scanner.nextLine();
                sum = 0;

                while (calories != "") {
                    sum += Integer.parseInt(calories);

                    if (scanner.hasNextLine()) {
                        calories = scanner.nextLine();
                    }
                    else {
                        break;
                    }
                }
                if (sum > max[0]) {
                    max[2] = max[1];
                    max[1] = max[0];
                    max[0] = sum;
                }
                else if (sum > max[1]) {
                    max[2] = max[1];
                    max[1] = sum;
                }
                else if (sum > max[2]) {
                    max[2] = sum;
                }
            }
            scanner.close();

        // if file couldnt be read print out error mgs
        } catch (FileNotFoundException e) {
            System.out.println("File could not be read.");
            e.printStackTrace();
        }

        int total = max[0] + max[1] + max[2];
        return total;
    }
}
