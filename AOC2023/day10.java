package AOC2023;

import AOC2023.libraries.Functions;
import java.util.ArrayList;


/* ------- NOT DONE ------- */

public class day10 {
    public static void main(String[] args) {
        ArrayList<String> input = Functions.readInputFile("AOC2023/input/day10_example.txt");

        System.out.println("Part 1: " + part1(input));
        System.out.println("Part 2: " + part2(input));
    }

    static int part1(ArrayList<String> input) {
        char[][] charMatrix = createCharMatrix(input);
        Node start = findStart(charMatrix);

        Node path = findPath(charMatrix, start);
        
        int pathLength = findPathLength(path);
        int farthestDistance = pathLength / 2;
        return farthestDistance;
    }

    static int part2(ArrayList<String> input) {
        return 0;
    }

    
    static char[][] createCharMatrix(ArrayList<String> input) {
        char[][] charMatrix = new char[input.size()][input.get(0).length()];

        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            for (int j = 0; j < line.length(); j++) {
                charMatrix[i][j] = line.charAt(j);
            }
        }

        return charMatrix;
    }

    static Node findStart(char[][] charMatrix) {
        Node start = new Node(-1, -1, null);

        for (int col = 0; col < charMatrix.length; col++) {
            for (int row = 0; row < charMatrix[col].length; row++) {
                if (charMatrix[row][col] == 'S') {
                    start.x = col;
                    start.y = row;
                    return start;
                }
            }
        }

        return start;
    }

    static int findPathLength(Node start) {
        int pathLength = 0;
        Node currentNode = new Node(start);
        do {
            currentNode = currentNode.child;
            pathLength++;
        } while (!start.equals(currentNode));

        return pathLength;
    }


    static Node findPath(char[][] charMatrix, Node start) {
        Node startNode = new Node(start);
        Node previousNode = null;
        Node currentNode = startNode;

        while (true) {
            loop:
            for (int row = currentNode.y-1; row <= currentNode.y+1; row++) {
                for (int col = currentNode.x-1; col <= currentNode.x+1; col++) {
                    if (col < 0 || col >= charMatrix.length || row < 0 || row >= charMatrix[col].length) {
                        continue;
                    }
                    if (col == currentNode.x && row == currentNode.y) {
                        continue;
                    }
                    if (previousNode != null) {
                        if (col == previousNode.x && row == previousNode.y) {
                            continue;
                        }
                    }
                    
                    char currentChar = charMatrix[row][col];
                    char previousChar = charMatrix[currentNode.y][currentNode.x];
                    switch (currentChar) {
                        case '|':
                            if (col == currentNode.x) {
                                switch (previousChar) {
                                    case '7':
                                    case 'F':
                                        if (row > currentNode.y) {
                                            Node newNode = new Node(col, row, currentNode);
                                            currentNode.child = newNode;
                                            previousNode = currentNode;
                                            currentNode = newNode;
                                            break loop;
                                        }
                                        break;
                                    case 'L':
                                    case 'J':
                                        if (row < currentNode.y) {
                                            Node newNode = new Node(col, row, currentNode);
                                            currentNode.child = newNode;
                                            previousNode = currentNode;
                                            currentNode = newNode;
                                            break loop;
                                        }
                                        break;
                                    case 'S':
                                        Node newNode = new Node(col, row, currentNode);
                                        currentNode.child = newNode;
                                        previousNode = currentNode;
                                        currentNode = newNode;
                                        break loop;
                                    default:
                                        break;
                                }  
                            }
                            break;
                        case '-':
                            if (row == currentNode.y) {
                                switch (previousChar) {
                                    case '7':
                                    case 'J':
                                        if (col < currentNode.x) {
                                            Node newNode = new Node(col, row, currentNode);
                                            currentNode.child = newNode;
                                            previousNode = currentNode;
                                            currentNode = newNode;
                                            break loop;
                                        }
                                        break;
                                    case 'F':
                                    case 'L':
                                        if (col > currentNode.x) {
                                            Node newNode = new Node(col, row, currentNode);
                                            currentNode.child = newNode;
                                            previousNode = currentNode;
                                            currentNode = newNode;
                                            break loop;
                                        }
                                        break;
                                    case 'S':
                                        Node newNode = new Node(col, row, currentNode);
                                        currentNode.child = newNode;
                                        previousNode = currentNode;
                                        currentNode = newNode;
                                        break loop;
                                    default:
                                        break;
                                }  
                            }
                            break;
                        case 'L':
                            if (col == currentNode.x && row > currentNode.y) {
                                Node newNode = new Node(col, row, currentNode);
                                currentNode.child = newNode;
                                previousNode = currentNode;
                                currentNode = newNode;
                                break loop;
                            }
                            else if (col < currentNode.x && row == currentNode.y) {
                                Node newNode = new Node(col, row, currentNode);
                                currentNode.child = newNode;
                                previousNode = currentNode;
                                currentNode = newNode;
                                break loop;
                            }
                            break;
                        case 'J':
                            if (col == currentNode.x && row > currentNode.y) {
                                Node newNode = new Node(col, row, currentNode);
                                currentNode.child = newNode;
                                previousNode = currentNode;
                                currentNode = newNode;
                                break loop;
                            }
                            else if (col > currentNode.x && row == currentNode.y) {
                                Node newNode = new Node(col, row, currentNode);
                                currentNode.child = newNode;
                                previousNode = currentNode;
                                currentNode = newNode;
                                break loop;
                            }
                            break;
                        case '7':
                            if (col == currentNode.x && row < currentNode.y) {
                                Node newNode = new Node(col, row, currentNode);
                                currentNode.child = newNode;
                                previousNode = currentNode;
                                currentNode = newNode;
                                break loop;
                            }
                            else if (col > currentNode.x && row == currentNode.y) {
                                Node newNode = new Node(col, row, currentNode);
                                currentNode.child = newNode;
                                previousNode = currentNode;
                                currentNode = newNode;
                                break loop;
                            }
                            break;
                        case 'F':
                            if (col == currentNode.x && row < currentNode.y) {
                                Node newNode = new Node(col, row, currentNode);
                                currentNode.child = newNode;
                                previousNode = currentNode;
                                currentNode = newNode;
                                break loop;
                            }
                            else if (col < currentNode.x && row == currentNode.y) {
                                Node newNode = new Node(col, row, currentNode);
                                currentNode.child = newNode;
                                previousNode = currentNode;
                                currentNode = newNode;
                                break loop;
                            }
                            break;
                        case '.':
                            break;
                        case 'S':
                            if (!startNode.child.equals(currentNode)) {
                                currentNode.child = startNode;
                                return startNode;
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }


}


class Node {
    int x;
    int y;
    Node child;

    Node(int x, int y, Node parent) {
        this.x = x;
        this.y = y;
    }

    Node(Node node) {
        this.x = node.x;
        this.y = node.y;
        this.child = node.child;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Node) {
            Node node = (Node) obj;
            if (this.x == node.x && this.y == node.y) {
                return true;
            }
        }
        return false;
    }
}
