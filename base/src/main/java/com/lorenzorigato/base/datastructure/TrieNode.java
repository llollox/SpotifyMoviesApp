package com.lorenzorigato.base.datastructure;

import java.util.HashMap;

/**
 * This class defines how a Trie node is structured
 *
 * @property isEndOfWord it defines if the node corresponds with the end of a string.
 * @property children Each node can have multiple children.
 * Each children corresponds to a different character.
 * It is implemented with an HashMap in order to execute the lookup operation in O(1)
 */
public class TrieNode {


    // Class attributes ****************************************************************************
    private HashMap<Character, TrieNode> children = new HashMap<>();
    private boolean isEndOfWord = false;


    // Constructor *********************************************************************************
    public TrieNode(HashMap<Character, TrieNode> children, boolean isEndOfWord) {
        this.isEndOfWord = isEndOfWord;
        this.children = children;
    }

    public TrieNode(HashMap<Character, TrieNode> children) {
        this.children = children;
    }

    public TrieNode() { }


    // Class methods *******************************************************************************
    public HashMap<Character, TrieNode> getChildren() {
        return children;
    }

    public boolean isEndOfWord() {
        return isEndOfWord;
    }

    public void setEndOfWord(boolean endOfWord) {
        isEndOfWord = endOfWord;
    }
}
