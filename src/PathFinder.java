import java.util.HashSet;
import java.util.ArrayList;
import java.util.PriorityQueue;

class PathFinder {
    private HashSet<String> visited;
    private PriorityQueue<WordNode> wordQueue;

    public PathFinder() {
        visited = new HashSet<>();
        wordQueue = new PriorityQueue<>();
    }

    public int countCharacterDiff(String word1, String word2) {
        if (word1.length() != word2.length()) {
            return -1;
        }
        
        int count = 0;
        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                count++;
            }
        }

        return count;
    }

    public boolean isChildWord(String parent, String child) {
        if (parent.length() != child.length()) {
            return false;
        }

        int count = 0;
        for (int i = 0; i < parent.length(); i++) {
            if (parent.charAt(i) != child.charAt(i)) {
                count++;
            }
            if (count > 1) {
                return false;
            }
        }

        return count == 1;
    }

    public ArrayList<WordNode> findChildNodes(WordNode parent, DictSet dictset) {
        ArrayList<WordNode> childNodes = new ArrayList<WordNode>();

        for (String childWord : dictset.getDict()) {
            if (isChildWord(parent.getWord(), childWord)) {
                WordNode childNode = new WordNode(childWord, parent.getCost() + 1);
                childNodes.add(childNode);
            }
        }

        return childNodes;
    }

    public ArrayList<String> UCS(String startWord, String endWord, DictSet dictset) {
        ArrayList<String> path = new ArrayList<String>();

        WordNode startNode = new WordNode(startWord, 0);
        wordQueue.add(startNode);
        visited.add(startNode.getWord());

        while (!wordQueue.isEmpty()) {
            WordNode wordNode = wordQueue.poll();

            ArrayList<WordNode> childNodes = findChildNodes(wordNode, dictset);
            for (WordNode childNode : childNodes) {
                if (childNode.getWord().equals(endWord)) {
                    System.out.println(childNode.getWord() + " " + childNode.getCost());
                    return path;
                }
                if (!visited.contains(childNode.getWord())) {
                    wordQueue.add(childNode);   
                    visited.add(childNode.getWord());
                }
            }
            childNodes.clear();
        }

        return path;
    }
}
