import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class day12 {
    public static void main(String[] args) {
        ArrayList<String> graph = new ArrayList<String>();
        readInputFile(graph);

        Node start = findNode(graph, 'S');
        Node end = findNode(graph, 'E');

        int pathLength = bfs(graph, start, end);
        System.out.println("The shortest path is " + pathLength + " steps long");

        int bestPathLength = bestStartLength(graph, end);
        System.out.println("The shortest path from any 'a' square is " + bestPathLength + " steps long");

    }


    
    static void readInputFile(ArrayList<String> strings) {
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
    }



    //---------------------------- PART 1 ----------------------------//

    static int bfs(ArrayList<String> graph, Node start, Node end) {
        Queue<Node> queue = new LinkedList<Node>();
        ArrayList<Node> visited = new ArrayList<Node>();

        queue.add(start);
        visited.add(start);

        while (queue.size() > 0) {
            Node current = queue.poll();
            if (current.equals(end)) {
                return pathLength(start, current);
            }
            addNeibours(current, graph, queue, visited);
        }
        return -1;
    }



    static void addNeibours(Node current, ArrayList<String> graph, Queue<Node> queue, ArrayList<Node> visited) {
        int x = current.x;
        int y = current.y;

        if (x > 0) {
            char nodeName = graph.get(y).charAt(x - 1);
            Node left;
            if (nodeName == 'E') {
                left = new Node(current, nodeName, 25, x - 1, y);
            }
            else if (nodeName == 'S') {
                left = new Node(current, nodeName, 0, x - 1, y);
            }
            else {
                left = new Node(current, nodeName, (int)nodeName - 97, x - 1, y);
            }
            if (!visited.contains(left) && ((int)left.height - (int)current.height) <= 1) {
                queue.add(left);
                visited.add(left);
            }
        }
        if (x < graph.get(y).length() - 1) {
            char nodeName = graph.get(y).charAt(x + 1);
            Node right;
            if (nodeName == 'E') {
                right = new Node(current, nodeName, 25, x + 1, y);
            }
            else if (nodeName == 'S') {
                right = new Node(current, nodeName, 0, x + 1, y);
            }
            else {
                right = new Node(current, nodeName, (int)nodeName - 97, x + 1, y);
            }
            if (!visited.contains(right) && ((int)right.height - (int)current.height) <= 1) {
                queue.add(right);
                visited.add(right);
            }
        }
        if (y > 0) {
            char nodeName = graph.get(y - 1).charAt(x);
            Node up;
            if (nodeName == 'E') {
                up = new Node(current, nodeName, 25, x, y - 1);
            }
            else if (nodeName == 'S') {
                up = new Node(current, nodeName, 0, x, y - 1);
            }
            else {
                up = new Node(current, nodeName, (int)nodeName - 97, x, y - 1);
            }
            if (!visited.contains(up) && ((int)up.height - (int)current.height) <= 1) {
                queue.add(up);
                visited.add(up);
            }
        }
        if (y < graph.size() - 1) {
            char nodeName = graph.get(y + 1).charAt(x);
            Node down;
            if (nodeName == 'E') {
                down = new Node(current, nodeName, 25, x, y + 1);
            }
            else if (nodeName == 'S') {
                down = new Node(current, nodeName, 0, x, y + 1);
            }
            else {
                down = new Node(current, nodeName, (int)nodeName - 97, x, y + 1);
            }
            if (!visited.contains(down) && ((int)down.height - (int)current.height) <= 1) {
                queue.add(down);
                visited.add(down);
            }
        }
    }



    static Node findNode(ArrayList<String> graph, char name) {
        for (int i = 0; i < graph.size(); i++) {
            for (int j = 0; j < graph.get(i).length(); j++) {
                if (graph.get(i).charAt(j) == name) {
                    if (name == 'S') {
                        return new Node(null, name, 0, j, i);
                    }
                    else if (name == 'E') {
                        return new Node(null, name, 25, j, i);
                    }
                }
            }
        }
        return null;
    }



    static int pathLength(Node start, Node current) {
        if (current.equals(start)) {
            return 0;
        }
        return 1 + pathLength(start, current.parent);
    }



    //---------------------------- PART 2 ----------------------------//

    static int bestStartLength(ArrayList<String> graph, Node end) {
        int max = Integer.MAX_VALUE;
        for (int i = 0; i < graph.size(); i++) {
            Node start = new Node(null, graph.get(i).charAt(0), 0, 0, i);
            int path = bfs(graph, start, end);
            
            if (path < max) {
                max = path;
            }
        }
        return max;
    }
}



class Node {
    char name;
    int height;
    int x;
    int y;
    Node parent;

    public Node(Node parent, char name, int height, int x, int y) {
        this.parent = parent;
        this.name = name;
        this.height = height;
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
        Node node = (Node)p;
        return node.x == x && node.y == y;
    }  
}
