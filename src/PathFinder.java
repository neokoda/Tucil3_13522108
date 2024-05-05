import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

class PathFinder {
    private HashSet<String> visited;
    private PriorityQueue<WordNode> wordQueue;
    private HashMap<String, String> predecessors;

    public PathFinder() {
        visited = new HashSet<>();
        wordQueue = new PriorityQueue<>();
        predecessors = new HashMap<>();
    }

    public void resetPathFinder() {
        visited.clear();
        wordQueue.clear();
        predecessors.clear();
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

    public ArrayList<WordNode> findChildNodes(WordNode parent, String endWord, DictSet dictset, String method) {
        ArrayList<WordNode> childNodes = new ArrayList<WordNode>();

        for (String childWord : dictset.getDict()) {
            if (isChildWord(parent.getWord(), childWord)) {
                WordNode childNode;
                switch (method) {
                    case "UCS":
                        childNode = new WordNode(childWord, parent.getCost() + 1);
                        childNodes.add(childNode);
                        break;
                    case "GBFS":
                        childNode = new WordNode(childWord, countCharacterDiff(childWord, endWord));
                        childNodes.add(childNode);
                        break;
                    case "A*":
                        childNode = new WordNode(childWord, countCharacterDiff(childWord, endWord) + parent.getCost() + 1);
                        childNodes.add(childNode);
                        break;
                }

                if (!predecessors.containsKey(childWord)) {
                    predecessors.put(childWord, parent.getWord());
                }
            }
        }

        return childNodes;
    }

    public ArrayList<String> getPath(String endWord) {
        ArrayList<String> path = new ArrayList<>();

        String word = endWord;
        while (word != "") {
            path.add(word);
            word = predecessors.get(word);
        }
        Collections.reverse(path);
        
        return path;
    }

    public ArrayList<String> findPath(String startWord, String endWord, DictSet dictset, String method) {
        ArrayList<String> path = new ArrayList<>();

        WordNode startNode = new WordNode(startWord, 0);
        predecessors.put(startWord, "");
        wordQueue.add(startNode);
        visited.add(startNode.getWord());

        while (!wordQueue.isEmpty()) {
            WordNode wordNode = wordQueue.poll();

            ArrayList<WordNode> childNodes = findChildNodes(wordNode, endWord, dictset, method);
            for (WordNode childNode : childNodes) {
                if (childNode.getWord().equals(endWord)) {
                    path = getPath(endWord);
                    resetPathFinder();
                    return path;
                }
                if (!visited.contains(childNode.getWord())) {
                    wordQueue.add(childNode);   
                    visited.add(childNode.getWord());
                }
            }
            childNodes.clear();
        }

        resetPathFinder();
        return path;
    }
}
