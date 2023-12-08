package AOC2022;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList; 

public class day10 {
    public static void main(String[] args) {
        ArrayList<String> instructions = new ArrayList<>();
        readInputFile(instructions);

        CRT crt = new CRT();
        sumOfSignalStrengths(crt, instructions);
        System.out.println("The sum of The signal strengths is: " + crt.result1);
        System.out.println("The screen displays: ");
        for (int i = 0; i < crt.screen.length; i++) {
            System.out.println(crt.screen[i]);
        }

    }



    static void readInputFile(ArrayList<String> strings) {
        try {
            // read input file
            File file = new File("input/day10_input.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                // add each line to a list of pairs
                String row = scanner.nextLine();
                strings.add(row);
            }
            scanner.close();
        // if file couldnt be read print out error mgs
        } catch (FileNotFoundException e) {
            System.out.println("File could not be read.");
            e.printStackTrace();
        }
    }



    static void sumOfSignalStrengths(CRT crt, ArrayList<String> instructions) {
        for (int i = 0; i < instructions.size(); i++) {
            String[] instruction = instructions.get(i).split(" ");

            if (instruction[0].equals("noop")) {
                doCycle(crt);
            }
            else {
                doCycle(crt);
                doCycle(crt);
                crt.x += Integer.parseInt(instruction[1]);
            }
        }
    }



    static void doCycle(CRT crt) {
        //---------------------------- PART 2 ----------------------------//
        if (Math.abs((crt.cycle - crt.posY * 40) - crt.x) < 2) {
            crt.screen[crt.posY] = crt.screen[crt.posY] + "#";
        }
        else {
            crt.screen[crt.posY] = crt.screen[crt.posY] + ".";
        }

        //---------------------------- PART 1 ----------------------------//
        crt.cycle++;
        if ((crt.cycle + 20) % 40 == 0) {
            crt.result1 += crt.cycle * crt.x;
        }

        //---------------------------- PART 2 ----------------------------//
        if (crt.cycle % 40 == 0) {
            crt.posY++;
        }
    }
}



class CRT {
    int cycle = 0;
    int x = 1;
    int posX = 0;
    int posY = 0;
    int result1 = 0;
    String[] screen = {"", "", "", "", "", ""};
}