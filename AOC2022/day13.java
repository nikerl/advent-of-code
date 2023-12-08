package AOC2022;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class day13 {
    public static void main(String[] args) {
        ArrayList<String> packets = readInputFile();
        System.out.println(packets);


    }


    static ArrayList<String> readInputFile() {
        ArrayList<String> strings = new ArrayList<String>();

        try {
            // read input file
            File file = new File("input/day12_input.txt");
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

        return strings;
    }


    static Pair parsePair(String[] inputs) {
        Pair pair = new Pair();

        for (int i = 0; i < inputs.length; i++) {
            String input = inputs[i];
            ArrayList<ArrayList<Integer>> packet = pair.packets.get(i);
            System.out.println(packet);
            int arrayIndex = 0;

            for (int j = 0; j < input.length(); j++) {
                char c = input.charAt(j);
                if (c == '[') {
                    pair.packets.get(i).add(new ArrayList<Integer>());
                    continue;
                }
                if (c == ']') {
                    arrayIndex++;
                    continue;
                }
                while (c != ']' && c != ',') {
                    int num = Character.getNumericValue(c);
                    pair.packets.get(i).get(arrayIndex).add(num);
                }
            }
        } 
        return pair;
    }
    
    
    static void comparePackets(ArrayList<String> packets, ArrayList<Integer> indices) {
        int i = 0;
        int pairIndex = 1;

        while (i < packets.size()) {
            String left = packets.get(i);
            String right = packets.get(i + 1);
            Pair pair = parsePair(new String[] {left, right});
            for (int j = 0; j < packets.get(i).length(); j++) {
                System.out.println(pair);
            }
        }
        System.out.println(pairIndex);
    }










    static void parser(ArrayList<String> inputs) {
        ArrayList<PacketPair> packets = new ArrayList<>();
        System.out.println(packets);
        for (int pairIndex = 0; pairIndex < 2; pairIndex++) {
            for (int i = 0; i < inputs.size(); i++) {
                String input = inputs.get(i);
                PacketPair packet = new PacketPair();
                int arrayIndex = 0;
                
                for (int j = 0; j < input.length(); j++) {
                    char c = input.charAt(j);
                    if (c == '[') {
                        packet.pair.get(pairIndex).get(arrayIndex);
                    }
                }
            }
            
        }

    }


}

class Pair {
    int leftIndex = 0;
    int rightIndex = 0;
    ArrayList<ArrayList<ArrayList<Integer>>> packets = new ArrayList<ArrayList<ArrayList<Integer>>>();
    
    public Pair() {
        packets.add(new ArrayList<ArrayList<Integer>>());
        packets.add(new ArrayList<ArrayList<Integer>>());
    }

}


class PacketPair {
    ArrayList<ArrayList<Object>> pair = new ArrayList<>();

    public PacketPair() {
        pair.add(new ArrayList<>());
        pair.add(new ArrayList<>());
    }
    
}