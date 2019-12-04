package com.lorenzorigato.base.datastructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
    private boolean isCaseSensitive = true;


    // Constructor *********************************************************************************
    public Trie(List<String> strings) {
        for (String string : strings) {
            insert(string);
        }
    }

    public Trie(String[] strings) {
        this(Arrays.asList(strings));
    }

    public Trie(List<String> strings, boolean isCaseSensitive) {
        this.isCaseSensitive = isCaseSensitive;
        for (String string : strings) {
            insert(string);
        }
    }

    public Trie(String[] strings, boolean isCaseSensitive) {
        this(Arrays.asList(strings), isCaseSensitive);
    }

    // Class methods *******************************************************************************
    /**
     * This method insert a string into the Trie
     *
     * @param string The string that must be inserted
     */
    public void insert(String string) {

        String inserted = this.isCaseSensitive ? string : string.toLowerCase();

        TrieNode node = this.root;

        for(char c : inserted.toCharArray()) {
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

        Set<String> strings = this.getLeafStrings(childNode, prefix);
        List<String> list = new ArrayList<>(strings);
        Collections.sort(list);

        return list;
    }


    public List<String> startsWith(String prefix, int maxDistance) {
        Map<TrieNode, String> selected = new HashMap<>();
        this.levenstheinDistanceDfs(this.root, new StringBuilder(prefix), 0, selected, 0, maxDistance);

        Set<String> strings = new HashSet<>();
        for (Map.Entry<TrieNode, String> entry : selected.entrySet()) {
            TrieNode node = entry.getKey();
            String nodePrefix = entry.getValue();
            strings.addAll(this.getLeafStrings(node, nodePrefix));
        }

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

    public Set<String> getLeafStrings(TrieNode node, String prefix) {
        Set<String> strings = new HashSet<>();
        StringBuilder sb = new StringBuilder(prefix);
        this.leafStringsDfs(node, sb, strings);
        return strings;
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

    private void leafStringsDfs(TrieNode node, StringBuilder sb, Set<String> words) {
        if (node.isEndOfWord()) {
            words.add(sb.toString());
        }

        for (HashMap.Entry<Character, TrieNode> entry : node.getChildren().entrySet()) {
            char c = entry.getKey();
            sb.append(c);
            TrieNode childNode = entry.getValue();
            this.leafStringsDfs(childNode, sb, words);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    private void levenstheinDistanceDfs(
            TrieNode node, StringBuilder prefix,
            int index, Map<TrieNode, String> selected,
            int distance, int maxDistance) {

        if (distance > maxDistance || selected.containsKey(node)) {
            return;
        }

        if (index == prefix.length()) {
            selected.put(node, prefix.toString());
            return;
        }

        char currentChar = prefix.charAt(index);
        TrieNode nextNode = node.getChildren().get(currentChar);

        if (nextNode != null) {
            // Character found.
            // Continue without incrementing distance
            levenstheinDistanceDfs(nextNode, prefix, index + 1, selected, distance, maxDistance);
            return;
        }

        // Try edit distance cases for each child:
        for (HashMap.Entry<Character, TrieNode> entry : node.getChildren().entrySet()) {

            char childChar = entry.getKey();
            TrieNode childNode = entry.getValue();

            // Insertion
            prefix.insert(index, childChar);
            levenstheinDistanceDfs(childNode, prefix, index + 1, selected, distance + 1, maxDistance);
            prefix.deleteCharAt(index);

            // Deletion
            prefix.deleteCharAt(index);
            levenstheinDistanceDfs(node, prefix, index, selected, distance + 1, maxDistance);
            prefix.insert(index, currentChar);

            // Replacement
            prefix.setCharAt(index, childChar);
            levenstheinDistanceDfs(childNode, prefix, index + 1, selected, distance + 1, maxDistance);
            prefix.setCharAt(index, currentChar);
        }
    }
}
