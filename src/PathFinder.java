import java.util.HashSet;
import java.util.ArrayList;
import java.util.PriorityQueue;

class PathFinder {
    private HashSet<String> visited;
    private PriorityQueue<WordNode> wordQueue;
    private ArrayList<String> resultPath;

    public PathFinder() {
        visited = new HashSet<>();
        wordQueue = new PriorityQueue<>();
        resultPath = new ArrayList<>();
    }

    public void resetPathFinder() {
        visited.clear();
        wordQueue.clear();
        resultPath.clear();
    }

    public ArrayList<String> getResultPath() {
        return resultPath;
    }

    public int getNumVisited() {
        return visited.size();
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
                ArrayList<String> newPath = new ArrayList<>(parent.getPath());
                newPath.add(parent.getWord());

                switch (method) {
                    case "UCS":
                        childNode = new WordNode(childWord, newPath, newPath.size());
                        childNodes.add(childNode);
                        break;
                    case "GBFS":
                        childNode = new WordNode(childWord, newPath, countCharacterDiff(childWord, endWord));
                        childNodes.add(childNode);
                        break;
                    case "A*":
                        childNode = new WordNode(childWord, newPath, newPath.size() + countCharacterDiff(childWord, endWord));
                        childNodes.add(childNode);
                        break;
                }
            }
        }

        return childNodes;
    }

    public void findPath(String startWord, String endWord, DictSet dictset, String method) {
        resetPathFinder();

        ArrayList<String> startPath = new ArrayList<>();
        WordNode startNode = new WordNode(startWord, startPath, 0); 

        wordQueue.add(startNode);
        visited.add(startNode.getWord());

        while (!wordQueue.isEmpty()) {
            WordNode wordNode = wordQueue.poll();

            ArrayList<WordNode> childNodes = findChildNodes(wordNode, endWord, dictset, method);
            for (WordNode childNode : childNodes) {
                if (childNode.getWord().equals(endWord)) {
                    resultPath.addAll(childNode.getPath());
                    resultPath.add(childNode.getWord());
                    return;
                }
                if (!visited.contains(childNode.getWord())) {
                    wordQueue.add(childNode);   
                    visited.add(childNode.getWord());
                }
            }
            childNodes.clear();
        }
    }
}
