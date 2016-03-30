package string;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TrieTest {
    @Test
    public void testingTrieRegularInsertion() {
        Trie trie = new Trie();
        trie.insert("abc");
        trie.insert("abcdef");
        trie.insert("defghi");
        assertEquals("def should not be in the trie: ", false, trie.query("def"));
        assertEquals("abcdef should be in the trie: ", true, trie.query("abcdef"));
        assertEquals("startWith def should be true: ", true, trie.startsWith("defg"));
    }
}