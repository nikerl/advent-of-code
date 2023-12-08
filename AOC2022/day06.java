package AOC2022;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class day06 {
    public static void main(String[] args) {
        String buffer = readInputFile();

        int count1 = startOfPacket(buffer);
        System.out.println("The first packet marker appears after char " + count1);
        int count2 = startOfMessage(buffer);
        System.out.println("The first message  marker appears after char " + count2);
        
    }


    static String readInputFile() {
        try {
            // read input file
            File file = new File("input/day06_input.txt");
            Scanner scanner = new Scanner(file);
            String buffer = scanner.nextLine();
            scanner.close();
            return buffer;
        // if file couldnt be read print out error mgs
        } catch (FileNotFoundException e) {
            System.out.println("File could not be read.");
            e.printStackTrace();
            return "";
        }

    }



    static Boolean isMarker(String substr) {
        for (int j = 0; j < substr.length(); j++) {
            for (int k = 0; k < substr.length(); k++) {
                if (j != k && substr.charAt(j) == substr.charAt(k)) {
                    return false;
                }
            }
        }
        return true;
    }



    //---------------------------- PART 1 ----------------------------//

    static int startOfPacket(String buffer) {

        for (int i = 0; i < buffer.length() - 4; i++) {
            String substr = buffer.substring(i, i + 4);

            if (isMarker(substr)) {
                return i + 4;
            }


            //System.out.println(substr);

        }

        return -1;
    }



    //---------------------------- PART 2 ----------------------------//

    static int startOfMessage(String buffer) {

        for (int i = 0; i < buffer.length() - 14; i++) {
            String substr = buffer.substring(i, i + 14);

            if (isMarker(substr)) {
                return i + 14;
            }


            //System.out.println(substr);

        }
        return -1;
    }
}
