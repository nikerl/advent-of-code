package AOC2023.libraries;

public class Pair<T1, T2> {
    public T1 x;
    public T2 y;

    public Pair(T1 x, T2 y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object p) {
        if (this == p) {
            return true;
        }
        if (p == null || getClass() != p.getClass()) {
            return false;
        }
        Pair<?, ?> pair = (Pair<?, ?>) p;
        return x.equals(pair.x) && y.equals(pair.y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
