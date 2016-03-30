package string;

public class Trie {
    static final int ALPHA = 26;

    static public class TrieNode {
        public TrieNode[] sons;
        public int count;

        public TrieNode() {
            sons = new TrieNode[ALPHA];
            count = 0;
        }
    }

    TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String s) {
        TrieNode p = root;
        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i) - 'a';
            if (p.sons[c] == null) {
                TrieNode q = new TrieNode();
                p.sons[c] = q;
            }
            p = p.sons[c];
        }
        p.count++;
    }

    public boolean query(String s) {
        TrieNode p = root;
        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i) - 'a';
            if (p.sons[c] == null) return false;
            p = p.sons[c];
        }
        if (p == null) return false;
        if (p.count == 0) return false;
        return true;
    }

    public boolean startsWith(String prefix) {
        TrieNode p = root;
        for (int i = 0; i < prefix.length(); i++) {
            int c = prefix.charAt(i) - 'a';
            if (p.sons[c] == null) return false;
            p = p.sons[c];
        }
        if (p == null) return false;
        return true;
    }
}

