package com.lorenzorigato.base.datastructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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


    public List<String> startsWith(String prefix) {
        TrieNode childNode = this.getNode(this.root, prefix.toCharArray(), 0);
        if (childNode == null) {
            return new ArrayList<>();
        }

        Set<String> strings = new HashSet<>();
        this.getLeafStringsDfs(childNode, new StringBuilder(prefix), strings);
        List<String> list = new ArrayList<>(strings);
        Collections.sort(list);
        return list;
    }

    /**
     * Given a string as input, this method checks if that string is contained in the Trie or not.
     * The time and space complexity for this operation is O(K), where K is the length of the string as input.
     *
     * @param string The string that must be inserted
     * @return if the string is contained in the Trie or not
     */
    public boolean search(String string) {
        TrieNode childNode = this.getNode(this.root, string.toCharArray(), 0);
        return childNode != null && childNode.isEndOfWord();
    }


    // Private class methods ***********************************************************************
    private TrieNode getNode(TrieNode node, char[] charArray, int index) {
        if (index == charArray.length) {
            return node;
        }

        char childChar = charArray[index];
        TrieNode childNode = node.getChildren().get(childChar);

        return childNode == null ? null : getNode(childNode, charArray, index + 1);
    }

    private void getLeafStringsDfs(TrieNode node, StringBuilder sb, Set<String> words) {
        if (node.isEndOfWord()) {
            words.add(sb.toString());
        }

        for (HashMap.Entry<Character, TrieNode> entry : node.getChildren().entrySet()) {
            char c = entry.getKey();
            sb.append(c);
            TrieNode childNode = entry.getValue();
            getLeafStringsDfs(childNode, sb, words);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}
