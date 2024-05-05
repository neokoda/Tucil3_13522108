import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        DictSet dictset = new DictSet();
        dictset.loadDictFromFile("src/dictionary.txt");

        PathFinder pf = new PathFinder();
        ArrayList<String> path;

        String startWord = "rotation";
        String endWord = "forklift";
        
        path = pf.findPath(startWord, endWord, dictset, "UCS");
        System.out.println(path);

        path = pf.findPath(startWord, endWord, dictset, "GBFS");
        System.out.println(path);

        path = pf.findPath(startWord, endWord, dictset, "A*");
        System.out.println(path);
    }
}