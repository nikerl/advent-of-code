import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Stack;

public class day05 {
    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<String>();
        ArrayList<Stack<Character>> stacks1 = new ArrayList<Stack<Character>>();
        ArrayList<Stack<Character>> stacks2 = new ArrayList<Stack<Character>>();
        readInputFile(strings);
        
        startingStacks(strings, stacks1);
        rearrange(strings, stacks1);
        String result1 = getTopCrates(stacks1);
        System.out.println("Part 1: The top crates are: " + result1);
        
        startingStacks(strings, stacks2);
        rearrange2(strings, stacks2);
        String result2 = getTopCrates(stacks2);
        System.out.println("Part 2: The top crates are: " + result2);

    }



    static void readInputFile(ArrayList<String> pairs) {
        try {
            // read input file
            File file = new File("input/day05_input.txt");
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


    
    static void startingStacks(ArrayList<String> strings, ArrayList<Stack<Character>> stacks) {
        int row = 0;
        int col = 1;

        while (strings.get(row) != "") {
            row++;
        }
        row--;
        
        while (col <= strings.get(row).length()) {
            Stack<Character> stack = new Stack<>();
            for (int i = row; i >= 0; i--) {
                if (strings.get(i).charAt(col) == ' ') {
                    break;
                }
                stack.push(strings.get(i).charAt(col));
            }
            stacks.add(stack);
            col += 4;
        }
    }
    


    static String getTopCrates(ArrayList<Stack<Character>> stacks) {
        String topCrates = "";
        for (int i = 0; i < stacks.size(); i++) {
            topCrates = topCrates + stacks.get(i).peek();
        }

        return topCrates;
    }
    


    //---------------------------- PART 1 ----------------------------//

    static void rearrange(ArrayList<String> strings, ArrayList<Stack<Character>> stacks) {
        int row = 0;
        while (strings.get(row) != "") {
            row++;
        }
        row++;
        
        for (int i = row; i < strings.size(); i++) {
            String[] split = strings.get(i).split(" ");

            int cratesToMove = Integer.parseInt(split[1]);
            int fromStack = Integer.parseInt(split[3]) - 1;
            int toStack = Integer.parseInt(split[5]) - 1;

            for (int j = 0; j < cratesToMove; j++) {
                stacks.get(toStack).push(stacks.get(fromStack).pop());
            }
        }
    }



    //---------------------------- PART 2 ----------------------------//

    static void rearrange2(ArrayList<String> strings, ArrayList<Stack<Character>> stacks) {
        int row = 0;
        while (strings.get(row) != "") {
            row++;
        }
        row++;

        for (int i = row; i < strings.size(); i++) {
            String[] split = strings.get(i).split(" ");

            int cratesToMove = Integer.parseInt(split[1]);
            int fromStack = Integer.parseInt(split[3]) - 1;
            int toStack = Integer.parseInt(split[5]) - 1;

            for (int j = cratesToMove; j > 0; j--) {
                stacks.get(toStack).push(stacks.get(fromStack).get(stacks.get(fromStack).size()-j));
            }
            for (int j = 0; j < cratesToMove; j++) {
                stacks.get(fromStack).pop();
            }
        }
    }
}
