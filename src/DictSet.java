import java.util.HashSet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class DictSet {
    private HashSet<String> dict;

    public DictSet() {
        dict = new HashSet<>();
    }

    public HashSet<String> getDict() {
        return dict;
    }

    public void loadDictFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                dict.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}