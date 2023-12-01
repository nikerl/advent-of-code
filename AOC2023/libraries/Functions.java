//package AOC2023.libraries;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Functions {

    public static ArrayList<String> readInputFile(String path) {
        ArrayList<String> textArr = new ArrayList<String>();
        try {
            // read input file
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                // add each line to a list of pairs
                String line = scanner.nextLine();
                textArr.add(line);
            }
            scanner.close();
        // if file couldnt be read print out error mgs
        } catch (FileNotFoundException e) {
            System.out.println("File could not be read.");
            e.printStackTrace();
        }
        return textArr;
    }
    

}
