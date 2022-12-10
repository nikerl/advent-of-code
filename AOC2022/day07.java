import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class day07 {
    final static int TOTAL_SPACE_AVAILABE = 70_000_000;
    final static int REQUIRED_FREE_SPACE = 30_000_000;

    public static void main(String[] args) {
        ArrayList<String> logs = new ArrayList<>();
        readInputFile(logs);

        Dir root = new Dir("/");
        buildFileStructure(root, logs);

        int totalUsedSpace = sizeOfDirs(root);
        int sumOfUnder100k = dirsUnder100k(root);

        System.out.println("The sum of the sizes of all directories under 100k is " + sumOfUnder100k);

        int dirToDelete = choseDirToDelete(root, totalUsedSpace, Integer.MAX_VALUE);
        System.out.println("The size of the deleted directory was: " + dirToDelete);

    }



    static void readInputFile(ArrayList<String> logs) {
        try {
            // read input file
            File file = new File("input/day07_input.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                // add each line to a list of pairs
                String items = scanner.nextLine();
                logs.add(items);
            }
            scanner.close();
        // if file couldnt be read print out error mgs
        } catch (FileNotFoundException e) {
            System.out.println("File could not be read.");
            e.printStackTrace();
        }
    }

    

    static void buildFileStructure(Dir root, ArrayList<String> logs) {
        //System.out.println(logs.get(0));
        logs.remove(0);
        while (logs.size() != 0) {
            //System.out.println(logs.get(0));
            String[] subStrings = logs.get(0).split(" ");
            if (subStrings[0].equals("$")) {
                if (subStrings[1].equals("cd")) {
                    if (subStrings[2].equals("..")) {
                        logs.remove(0);
                        return;
                    }
                    else {
                        for (int j = 0; j < root.folders.size(); j++) {
                            if (subStrings[2].equals(root.folders.get(j).name)) {
                               buildFileStructure(root.folders.get(j), logs);
                            }
                        }
                    }
                }
                if (subStrings[1].equals("ls")) {
                    logs.remove(0);
                    continue;
                }
            }
            else if (subStrings[0].equals("dir")) {
                Dir subDir = new Dir(subStrings[1]);
                root.folders.add(subDir);
                logs.remove(0);
            }
            else {
                root.sizeOfFiles += Integer.parseInt(subStrings[0]);
                logs.remove(0);
            }
        }
        
    }



    static int sizeOfDirs(Dir root) {
        root.totalSize += root.sizeOfFiles;
        if (root.folders.size() != 0) {
            for (int i = 0; i < root.folders.size(); i++) {
                root.totalSize += sizeOfDirs(root.folders.get(i));
            }
        }
        return root.totalSize;
    }



    //---------------------------- PART 1 ----------------------------//

    static int dirsUnder100k(Dir root) {
        int sum = 0;
        if (root.totalSize <= 100_000) {
            sum += root.totalSize;
        }
        if (root.folders.size() != 0) {
            for (int i = 0; i < root.folders.size(); i++) {
                sum += dirsUnder100k(root.folders.get(i));
            }
        }
            
        return sum;
    }



    //---------------------------- PART 2 ----------------------------//

    static int choseDirToDelete(Dir root, int totalUsedSpace, int dirToDelete) {
        int subDir = 0;
        if ((TOTAL_SPACE_AVAILABE - totalUsedSpace + root.totalSize > REQUIRED_FREE_SPACE)) {
            dirToDelete = root.totalSize;
        }
        if (root.folders.size() != 0) {
            for (int i = 0; i < root.folders.size(); i++) {
                subDir = choseDirToDelete(root.folders.get(i), totalUsedSpace, dirToDelete);
                if (subDir < dirToDelete) {
                    dirToDelete = subDir;
                }
            }
        }
        return dirToDelete;
    }
}



class Dir {
    String name;
    int totalSize = 0;
    int sizeOfFiles = 0;
    ArrayList<Dir> folders = new ArrayList<>();
    
    public Dir(String name) {
        this.name = name;
    }
}