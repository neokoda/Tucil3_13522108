import java.util.Scanner;

public class CLI {
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);

        DictSet dictset = new DictSet();
        dictset.loadDictFromFile("../src/dictionary.txt");

        System.out.println("Word Ladder Solver");

        System.out.print("Start word: ");
        String startWord = scanner.nextLine().toLowerCase();

        System.out.print("End word: ");
        String endWord = scanner.nextLine().toLowerCase();

        System.out.print("Mode (UCS, GBFS, A*): ");
        String mode = scanner.nextLine().toUpperCase();

        boolean error = false;

        if (!dictset.getDict().contains(startWord) || !dictset.getDict().contains(endWord)) {
            System.out.println("Invalid input. Words are not in dictionary.");
            error = true;
        }

        if (startWord.length() != endWord.length()) {
            System.out.println("Invalid input. Words are of different length.");
            error = true;
        }

        if (!(mode.equals("UCS") || mode.equals("GBFS") || mode.equals("A*"))) {
            System.out.println("Invalid mode. Please enter UCS, GBFS, or A*.");
            error = true;
        }

        if (!error) {
            PathFinder pf = new PathFinder();

            long startTime = System.currentTimeMillis();
            pf.findPath(startWord, endWord, dictset, mode);
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;

            if (pf.getResultPath().size() == 0) {
                System.out.println("No path was found between start word and end word.");
            } else {
                System.out.println("Path: " + pf.getResultPath());
            }

            System.out.println("Nodes visited: " + pf.getNumVisited());
            System.out.println("Execution time: " + executionTime + " ms");

            Runtime runtime = Runtime.getRuntime();
            long memoryUsed = runtime.totalMemory() - runtime.freeMemory();
            System.out.println("Memory used: " + memoryUsed / 1024.0 + " kilobytes");
        }

        scanner.close();
    }
}
