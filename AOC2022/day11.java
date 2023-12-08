package AOC2022;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList; 
import java.util.*;

public class day11 {
    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<>();
        readInputFile(strings);
        ArrayList<Monkey> monkeys = new ArrayList<>();

        //---------------------------- PART 2 ----------------------------//
        startingPos(monkeys);
        for (int i = 0; i < 20; i++) {
            doRound(monkeys, false);
        }
        System.out.println("Part 1: The level of monkey business is: " + monkeyBusiness(monkeys));

        //---------------------------- PART 2 ----------------------------//
        startingPos(monkeys);
        for (int i = 0; i < 10000; i++) {
            doRound(monkeys, true);
        }
        System.out.println("Part 2: The level of monkey business is: " + monkeyBusiness(monkeys));
    }



    static void readInputFile(ArrayList<String> strings) {
        try {
            // read input file
            File file = new File("input/day11_input.txt");
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
    


    static void doRound(ArrayList<Monkey> monkeys, boolean worried) {
        long m = 1;
        for (int i = 0; i < monkeys.size(); i++) {
            m *= monkeys.get(i).test;
        }
        for (int i = 0; i < monkeys.size(); i++) {
            while (monkeys.get(i).startingItems.size() != 0) {
                Long worryLevel;

                if (monkeys.get(i).opMul == -1) {
                    worryLevel = monkeys.get(i).startingItems.get(0) * monkeys.get(i).startingItems.get(0);
                }
                else {
                    worryLevel = (monkeys.get(i).startingItems.get(0) * monkeys.get(i).opMul) + monkeys.get(i).opAdd;
                }
                
                monkeys.get(i).itemsExamined++;
                if (worried == false) {
                    worryLevel = worryLevel / 3;
                }
                else {
                    worryLevel = worryLevel % m;
                }

                if ((worryLevel % monkeys.get(i).test) == 0) {
                    int toIndex = monkeys.get(i).ifTrue;
                    monkeys.get(toIndex).startingItems.add(worryLevel);
                }
                else {
                    int toIndex = monkeys.get(i).ifFalse;
                    monkeys.get(toIndex).startingItems.add(worryLevel);
                }
                monkeys.get(i).startingItems.remove(0);
            }
        }
    }



    static long monkeyBusiness(ArrayList<Monkey> monkeys) {
        long mostActive = 0;
        long secondMostActive = 0;

        for (int i = 0; i < monkeys.size(); i++) {
            if (monkeys.get(i).itemsExamined > mostActive) {
                secondMostActive = mostActive;
                mostActive = monkeys.get(i).itemsExamined;
            }
            else if (monkeys.get(i).itemsExamined > secondMostActive) {
                secondMostActive = monkeys.get(i).itemsExamined;
            }
        }
        return mostActive * secondMostActive;
    }



    static void startingPos(ArrayList<Monkey> monkeys) {
        Monkey monkey0 = new Monkey(2, 3, 0, 2, 6);
        monkey0.startingItems = new ArrayList<>(Arrays.asList(54L, 53L));
        Monkey monkey1 = new Monkey(7, 11, 0, 3, 4);
        monkey1.startingItems = new ArrayList<>(Arrays.asList(95L, 88L, 75L, 81L, 91L, 67L, 65L, 84L));
        Monkey monkey2 = new Monkey(3, 1, 6, 5, 1);
        monkey2.startingItems = new ArrayList<>(Arrays.asList(76L, 81L, 50L, 93L, 96L, 81L, 83L));
        Monkey monkey3 = new Monkey(11, 1, 4, 7, 4);
        monkey3.startingItems = new ArrayList<>(Arrays.asList(83L, 85L, 85L, 63L));
        Monkey monkey4 = new Monkey(17, 1, 8, 0, 7);
        monkey4.startingItems = new ArrayList<>(Arrays.asList(85L, 52L, 64L));
        Monkey monkey5 = new Monkey(5, 1, 2, 1, 3);
        monkey5.startingItems = new ArrayList<>(Arrays.asList(57L));
        Monkey monkey6 = new Monkey(13, -1, 0, 2, 5);
        monkey6.startingItems = new ArrayList<>(Arrays.asList(60L, 95L, 76L, 66L, 91L));
        Monkey monkey7 = new Monkey(19, 1, 5, 6, 0);
        monkey7.startingItems = new ArrayList<>(Arrays.asList(65L, 84L, 76L, 72L, 79L, 65L));

        monkeys.clear();
        monkeys.addAll(Arrays.asList(monkey0, monkey1, monkey2, monkey3, monkey4, monkey5, monkey6, monkey7));
    }
    static void testCaseStartingPos(ArrayList<Monkey> monkeys) {
        Monkey monkey0 = new Monkey(23, 19, 0, 2, 3);
        monkey0.startingItems = new ArrayList<>(Arrays.asList(79L, 98L));
        Monkey monkey1 = new Monkey(19, 1, 6, 2, 0);
        monkey1.startingItems = new ArrayList<>(Arrays.asList(54L, 65L, 75L, 74L));
        Monkey monkey2 = new Monkey(13, -1, 0, 1, 3);
        monkey2.startingItems = new ArrayList<>(Arrays.asList(79L, 60L, 97L));
        Monkey monkey3 = new Monkey(17, 1, 3, 0, 1);
        monkey3.startingItems = new ArrayList<>(Arrays.asList(74L));

        monkeys.clear();
        monkeys.addAll(Arrays.asList(monkey0, monkey1, monkey2, monkey3));
    }
}



class Monkey {
    ArrayList<Long> startingItems = new ArrayList<>();
    int test;
    int opMul;
    int opAdd;
    int ifTrue;
    int ifFalse;
    long itemsExamined = 0;

    Monkey(int test, int opMul, int opAdd, int ifTrue, int ifFalse) {
        this.test = test;
        this.opMul = opMul;
        this.opAdd = opAdd;
        this.ifTrue = ifTrue;
        this.ifFalse = ifFalse;
    }
}
