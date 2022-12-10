import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList; 


public class day09 {
    public static void main(String[] args) {
        ArrayList<String> moves = new ArrayList<>();
        readInputFile(moves);

        ArrayList<Pair> positions1 = new ArrayList<>();
        findTailPositions(moves, positions1);
        System.out.println("The tail visited " + positions1.size() + " positions at least once.");

        ArrayList<Pair> positions2 = new ArrayList<>();
        findLongTailPositions(moves, positions2, 10);
        System.out.println("The long tail visited " + positions2.size() + " positions at least once.");
    }



    static void readInputFile(ArrayList<String> moves) {
        try {
            // read input file
            File file = new File("input/day09_input.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                // add each line to a list of pairs
                String row = scanner.nextLine();
                moves.add(row);
            }
            scanner.close();
        // if file couldnt be read print out error mgs
        } catch (FileNotFoundException e) {
            System.out.println("File could not be read.");
            e.printStackTrace();
        }
    }



    static boolean updateTailPos(Pair head, Pair tail) {
        int deltaX = head.x - tail.x;
        int deltaY = head.y - tail.y;

        if (Math.abs(deltaX) <= 1 && Math.abs(deltaY) <= 1) {
            return true;
        }

        if ((deltaX == 0) && (deltaY == 2)) { 
            tail.y++;
        }
        else if ((deltaX == 1) && (deltaY == 2) || (deltaX == 2) && (deltaY == 1) || (deltaX == 2) && (deltaY == 2)) {
            tail.x++;
            tail.y++;
        }
        else if ((deltaX == 2) && (deltaY == 0)) {
            tail.x++;
        }
        else if ((deltaX == 1) && (deltaY == -2) || (deltaX == 2) && (deltaY == -1) || (deltaX == 2) && (deltaY == -2)) {
            tail.x++;
            tail.y--;
        }
        else if ((deltaX == 0) && (deltaY == -2)) {
            tail.y--;
        }
        else if ((deltaX == -1) && (deltaY == -2) || (deltaX == -2) && (deltaY == -1) || (deltaX == -2) && (deltaY == -2)) {
            tail.x--;
            tail.y--;
        }
        else if ((deltaX == -2) && (deltaY == 0)) {
            tail.x--;
        }
        else if ((deltaX == -1) && (deltaY == 2) || (deltaX == -2) && (deltaY == 1) || (deltaX == -2) && (deltaY == 2)) {
            tail.x--;
            tail.y++;
        }

        return false;
    }


    
    //---------------------------- PART 1 ----------------------------//

    static void findTailPositions(ArrayList<String> moves, ArrayList<Pair> positions) {
        Pair head = new Pair(0, 0);
        Pair tail = new Pair(0, 0);

        for (int i = 0; i < moves.size(); i++) {
            String[] move = moves.get(i).split(" ");

            int count = Integer.parseInt(move[1]);
            char direction = move[0].charAt(0);
            for (int j = 0; j < count; j++) {
                switch (direction) {
                    case 'U': head.y++; break;
                    case 'D': head.y--; break;
                    case 'R': head.x++; break;
                    case 'L': head.x--; break;
                }
                updateTailPos(head, tail);
                
                Pair currentPos = new Pair(tail.x, tail.y);
                if (!positions.contains(currentPos)) {
                    positions.add(currentPos);
                }
            }
        }
    }



    //---------------------------- PART 2 ----------------------------//

    static void findLongTailPositions(ArrayList<String> moves, ArrayList<Pair> positions, int noKnots) {
        ArrayList<Pair> knots = new ArrayList<>();
        for (int i = 0; i < noKnots; i++) {
            knots.add(new Pair(0, 0));
        }
        
        for (int i = 0; i < moves.size(); i++) {
            String[] move = moves.get(i).split(" ");

            int count = Integer.parseInt(move[1]);
            char direction = move[0].charAt(0);
            for (int j = 0; j < count; j++) {
                switch (direction) {
                    case 'U': knots.get(0).y++; break;
                    case 'D': knots.get(0).y--; break;
                    case 'R': knots.get(0).x++; break;
                    case 'L': knots.get(0).x--; break;
                }

                for (int k = 1; k < noKnots; k++) {
                    boolean allUpdated = updateTailPos(knots.get(k-1), knots.get(k));
                    if (allUpdated == true) {
                        break;
                    }
                }
                Pair currentPos = new Pair(knots.get(knots.size()-1).x, knots.get(knots.size()-1).y);
                if (!positions.contains(currentPos)) {
                    positions.add(currentPos);
                }
            }
        }
    }
}



class Pair {
    int x;
    int y;

    Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object p) {
        if (this == p) {
            return true;
        }
        if (p == null) {
            return false;
        }
        if (getClass() != p.getClass()) {
            return false;
        }
        Pair pair = (Pair)p;
        return pair.x == x && pair.y == y;
    }   
}
