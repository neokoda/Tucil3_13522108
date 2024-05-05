import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        DictSet dictset = new DictSet();
        dictset.loadDictFromFile("src/dictionary.txt");

        PathFinder pf = new PathFinder();
        ArrayList<String> path;

        String startWord = "sound";
        String endWord = "plays";
        
        path = pf.findPath(startWord, endWord, dictset, "UCS");
        System.out.println(path + " " + path.size());

        path = pf.findPath(startWord, endWord, dictset, "GBFS");
        System.out.println(path + " " + path.size());

        path = pf.findPath(startWord, endWord, dictset, "A*");
        System.out.println(path + " " + path.size());
    }
}