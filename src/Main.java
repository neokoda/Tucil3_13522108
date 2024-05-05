public class Main {
    public static void main(String[] args) {
        DictSet dictset = new DictSet();
        dictset.loadDictFromFile("dictionary.txt");

        PathFinder pf = new PathFinder();

        pf.UCS("BRAIN", "CRANE", dictset);
    }
}
