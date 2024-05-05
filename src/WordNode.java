public class WordNode implements Comparable<WordNode> {
    private String word;
    private int cost;

    public WordNode(String word, int cost) {
        this.word = word;
        this.cost = cost;
    }

    public String getWord() {
        return word;
    }

    public int getCost() {
        return cost;
    }

    public int compareTo(WordNode otherWord) {
        return Integer.compare(this.cost, otherWord.cost);
    }
}
