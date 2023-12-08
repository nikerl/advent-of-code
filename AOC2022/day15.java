package AOC2022;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class day15 {
    public static void main(String[] args) {
        ArrayList<String> input = readInputFile();

        buildGraph(input);
    }

    static ArrayList<String> readInputFile() {
        ArrayList<String> strings = new ArrayList<String>();
        try {
            // read input file
            File file = new File("input/day14_input.txt");
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

    static int[][] buildGraph(ArrayList<String> input) {
        int[][] graph = new int[4_000_000][4_000_000];

        for (int i = 0; i < input.size(); i++) {
            String[] points = input.get(i).split(" ");
            
            int sensorX = Integer.parseInt(points[2].substring(2, points[2].length()-1));
            int sensorY = Integer.parseInt(points[3].substring(2, points[3].length()-1));
            Point sensor = new Point(sensorX, sensorY);
            System.out.println(sensor);
            
        }

        return graph;
    }
}


class Point {
    int x;
    int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}