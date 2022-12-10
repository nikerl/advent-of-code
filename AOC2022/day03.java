import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class day03 {
    public static void main(String[] args) throws Exception {
        ArrayList<String> backpacks = new ArrayList<String>();
        readInputFile(backpacks);
        
        int sumMisplaced = sumOfMisplacedItems(backpacks);
        int sumBadges = sumOfBadges(backpacks);
        
        System.out.println("The sum of the misplaced items is: " + sumMisplaced);
        System.out.println("The sum of all badges was: " + sumBadges);
    }



    static void readInputFile(ArrayList<String> backpacks) {
        try {
            // read input file
            File file = new File("input/day03_input.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                // add each line to a list of backpacks
                String items = scanner.nextLine();
                backpacks.add(items);
            }
            scanner.close();
        // if file couldnt be read print out error mgs
        } catch (FileNotFoundException e) {
            System.out.println("File could not be read.");
            e.printStackTrace();
        }
    }



    static int findItemPriority(char item) {
        int itemPriority;
        // if upper case subtract 38 from ascii value
        if (Character.isUpperCase(item)) {
            itemPriority = (int)item - 38;
        } 
        // if lower case subtract 96 from ascii value 
        else {
            itemPriority = (int)item - 96;
        }

        return itemPriority;
    }



    //---------------------------- PART 1 ----------------------------//

    static int sumOfMisplacedItems(ArrayList<String> backpacks) {
        int sum = 0;
        for (int i = 0; i < backpacks.size(); i++) {
            String items = backpacks.get(i);

            // split the strings of items into two compartments
            String compartment1 = items.substring(0, items.length() / 2);
            String compartment2 = items.substring(items.length() / 2, items.length());

            // compare each char in the compartments 
            nextBackpack:
            for (int j = 0; j < compartment1.length(); j++) {
                // if they're the same find the priority and add it to the sum
                for (int k = 0; k < compartment2.length(); k++) {
                    char item1 = compartment1.charAt(j);
                    char item2 = compartment2.charAt(k);

                    if (item1 == item2) {
                        sum += findItemPriority(item1);
                        
                        break nextBackpack;
                    }
                }
            }
        }
        return sum;
    }


    
    //---------------------------- PART 2 ----------------------------//
    
    static int sumOfBadges(ArrayList<String> backpacks) {
        int sum = 0;

        int i = 0;
        while (i < backpacks.size()) {
            String backpack1 = backpacks.get(i++);
            String backpack2 = backpacks.get(i++);
            String backpack3 = backpacks.get(i++);

            nextBackpack:
            for (int j = 0; j < backpack1.length(); j++) {
                for (int k = 0; k < backpack2.length(); k++) {
                    for (int l = 0; l < backpack3.length(); l++) {
                        char item1 = backpack1.charAt(j);
                        char item2 = backpack2.charAt(k);
                        char item3 = backpack3.charAt(l);

                        if (item1 == item2 && item2 == item3) {
                            sum += findItemPriority(item1);

                            break nextBackpack;
                        }
                    }
                }
            }
        }
        return sum;
    }
}
