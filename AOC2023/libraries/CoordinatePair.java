package AOC2023.libraries;

public class CoordinatePair {
    public int x;
    public int y;

    public CoordinatePair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public CoordinatePair(CoordinatePair coordinatePair) {
        this.x = coordinatePair.x;
        this.y = coordinatePair.y;
    }

    @Override
    public boolean equals(Object p) {
        if (this == p) {
            return true;
        }
        if (p == null || getClass() != p.getClass()) {
            return false;
        }
        CoordinatePair pair = (CoordinatePair) p;
        return x == pair.x && y == pair.y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}