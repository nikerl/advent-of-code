import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class day04 {
    public static void main(String[] args) {
        ArrayList<String> pairs = new ArrayList<String>();
        readInputFile(pairs);

        int count1 = AssignmentContains(pairs);
        int count2 = AssignmentsOverlaps(pairs);
        System.out.println("The number of duplicate assignments was: " + count1);
        System.out.println("The number of assignments that overlap was: " + count2);
    }



    static void readInputFile(ArrayList<String> pairs) {
        try {
            // read input file
            File file = new File("input/day04_input.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                // add each line to a list of pairs
                String items = scanner.nextLine();
                pairs.add(items);
            }
            scanner.close();
        // if file couldnt be read print out error mgs
        } catch (FileNotFoundException e) {
            System.out.println("File could not be read.");
            e.printStackTrace();
        }
    }



    //---------------------------- PART 1 ----------------------------//

    static int AssignmentContains(ArrayList<String> pairs) {
        int count = 0;
        
        for (int i = 0; i < pairs.size(); i++) {
            String[] elves = pairs.get(i).split(",");
            String[] elf1 = elves[0].split("-");
            String[] elf2 = elves[1].split("-");
            int elf1_lo = Integer.parseInt(elf1[0]);
            int elf1_hi = Integer.parseInt(elf1[1]);
            int elf2_lo = Integer.parseInt(elf2[0]);
            int elf2_hi= Integer.parseInt(elf2[1]);

            if ((elf1_lo <= elf2_lo && elf1_hi >= elf2_hi) ||
                (elf2_lo <= elf1_lo && elf2_hi >= elf1_hi)) {
                count++;
            }
        }

        return count;
    }



    //---------------------------- PART 2 ----------------------------//

    static int AssignmentsOverlaps(ArrayList<String> pairs) {
        int count = 0; 

        for (int i = 0; i < pairs.size(); i++) {
            String[] elves = pairs.get(i).split(",");
            String[] elf1 = elves[0].split("-");
            String[] elf2 = elves[1].split("-");
            int elf1_lo = Integer.parseInt(elf1[0]);
            int elf1_hi = Integer.parseInt(elf1[1]);
            int elf2_lo = Integer.parseInt(elf2[0]);
            int elf2_hi= Integer.parseInt(elf2[1]);

            if ((elf1_lo >= elf2_lo && elf1_lo <= elf2_hi) ||
                (elf2_lo >= elf1_lo && elf2_lo <= elf1_hi)) {
                count++;
            }
        }
        return count;
    }
}
