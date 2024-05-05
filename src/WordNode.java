    import java.util.ArrayList;

    public class WordNode implements Comparable<WordNode> {
        private String word;
        private ArrayList<String> path;
        private int cost;

        public WordNode(String word, ArrayList<String> path, int cost) {
            this.word = word;
            this.path = path;
            this.cost = cost;
        }

        public String getWord() {
            return word;
        }

        public ArrayList<String> getPath() {
            return path;
        }

        public int getCost() {
            return cost;
        }

        public int compareTo(WordNode otherWord) {
            return Integer.compare(this.cost, otherWord.cost);
        }
    }
