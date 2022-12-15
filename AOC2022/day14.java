import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class day14 {
    static int SAND_COUNT = 0;

    public static void main(String[] args) {
        ArrayList<String> input = readInputFile();

        Point bottom1 = new Point(500, 500);
        boolean[][] cave1 = buildCave(input, bottom1);
        int count1 = simulateSand(cave1);
        System.out.println(count1 + " units of sand came to rest before sand started flowing into the abyss.");

        Point bottom2 = new Point(500, 500);
        boolean[][] cave2 = buildCave(input, bottom2);
        addFloor(cave2, bottom2);
        int count2 = simulateSand(cave2);
        System.out.println(count2 + " units of sand came to rest.");
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



    //---------------------------- PART 1 ----------------------------//

    static boolean[][] buildCave(ArrayList<String> input, Point bottom) {
        boolean[][] cave = new boolean[1000][501];
        Point current = new Point(0, 0);
        Point next = new Point(0, 0);

        for (int i = 0; i < input.size(); i++) {
            String[] structure = input.get(i).split(" -> ");

            for (int j = 1; j < structure.length; j++) {
                String[] p1 = structure[j - 1].split(",");
                String[] p2 = structure[j].split(",");

                current.x = Integer.parseInt(p1[0]);
                current.y = cave[0].length - Integer.parseInt(p1[1]) - 1;
                next.x = Integer.parseInt(p2[0]);
                next.y = cave[0].length - Integer.parseInt(p2[1]) - 1;

                if (next.y < bottom.y) {
                    bottom.y = next.y;
                }

                if (next.x - current.x < 0) {
                    for (int k = current.x; k >= next.x; k--) {
                        cave[k][next.y] = true;
                    }
                }
                else if (next.x - current.x > 0) {
                    for (int k = current.x; k <= next.x; k++) {
                        cave[k][next.y] = true;
                    }
                }
                else if (next.y - current.y < 0) {
                    for (int k = current.y; k >= next.y; k--) {
                        cave[next.x][k] = true;
                    }
                }
                else if (next.y - current.y > 0) {
                    for (int k = current.y; k <= next.y; k++) {
                        cave[next.x][k] = true;
                    }
                }
            }
        }
        return cave;
    }



    static boolean dropSand(boolean[][] cave) {
        Point sand = new Point(500, cave[0].length-1);

        while (true) {
            if (sand.y == 0) {
                return true;
            }
            else if (cave[sand.x][sand.y - 1] == false) {
                sand.y--;
            }
            else if (cave[sand.x - 1][sand.y - 1] == false) {
                sand.x--;
                sand.y--;
            }
            else if (cave[sand.x + 1][sand.y - 1] == false) {
                sand.x++;
                sand.y--;
            }
            else {
                cave[sand.x][sand.y] = true;
                SAND_COUNT++;

                return false;
            }
        }
    }



    static int simulateSand(boolean[][] cave) {
        SAND_COUNT = 0;
        while (true) {
            boolean abyss = dropSand(cave);
            if (abyss || cave[500][cave[0].length-1]) {
                return SAND_COUNT;
            }
        }
    }



    //---------------------------- PART 2 ----------------------------//

    static void addFloor(boolean[][] cave, Point bottom) {
        int bottomIndex = bottom.y - 2;
        for (int i = 0; i < cave.length; i++) {
            cave[i][bottomIndex] = true;
        }
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
