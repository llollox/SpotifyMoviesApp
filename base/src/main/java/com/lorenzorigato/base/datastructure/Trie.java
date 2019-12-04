package com.lorenzorigato.base.datastructure;

import java.util.List;

/**
 * A Trie (also called Prefix Tree) is a data structure that
 * allow to easily find all the strings that start a certain prefix given as input.
 * (Very useful to implement an autocomplete).
 *
 * @property root The root of the Trie. It doesn't corresponds to any character.
 * @constructor Initialize the Trie with the strings passed as argument. By default the Trie is initialized with no strings.
 */
public class Trie {


    // Private class attributes ********************************************************************
    private TrieNode root = new TrieNode();


    // Constructor *********************************************************************************
    public Trie(List<String> strings) {
        for (String string : strings) {
            insert(string);
        }
    }

    public Trie(String[] strings) {
        for (String string : strings) {
            insert(string);
        }
    }


    // Class methods *******************************************************************************
    /**
     * This method insert a string into the Trie
     *
     * @param string The string that must be inserted
     */
    public void insert(String string) {

        TrieNode node = this.root;

        for(char c : string.toCharArray()) {
            TrieNode childNode = node.getChildren().get(c);
            if (childNode == null) {
                childNode = new TrieNode();
                node.getChildren().put(c, childNode);
            }

            node = childNode;
        }

        node.setEndOfWord(true);
    }

    public boolean isEmpty() {
        return this.root.getChildren().isEmpty();
    }

    /**
     * Given a string as input, this method checks if that string is contained in the Trie or not.
     * The time and space complexity for this operation is O(K), where K is the length of the string as input.
     *
     * @param string The string that must be inserted
     * @return if the string is contained in the Trie or not
     */
    public boolean search(String string) {
        return search(this.root, string.toCharArray(), 0);
    }


    // Private class methods ***********************************************************************
    private boolean search(TrieNode node, char[] charArray, int index) {
        if (index == charArray.length) {
            return node.isEndOfWord();
        }

        char childChar = charArray[index];
        TrieNode childNode = node.getChildren().get(childChar);

        return childNode != null && search(childNode, charArray, index + 1);
    }
}
