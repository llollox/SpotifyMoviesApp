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
 * @constructor Initialize the Trie with the strings passed as argument.
 */
public class Trie {


    // Private class attributes ********************************************************************
    private TrieNode root = new TrieNode();


    // Constructors ********************************************************************************
    public Trie(List<String> strings) {
        for (String string : strings) {
            insert(string);
        }
    }

    public Trie(String[] strings) {
        this(Arrays.asList(strings));
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

    /**
     * This method verifies if the trie contains at least one string
     *
     * @return if the trie contains at least one string
     */
    public boolean isEmpty() {
        return this.root.getChildren().isEmpty();
    }

    /**
     * This is an overload of the method startsWith(String prefix, int maxDistance)
     * passing 0 as maxDistance.
     *
     * @param prefix the prefix to search.
     * @return the list of strings that matches the prefix
     */
    public List<String> startsWith(String prefix) {
        return this.startsWith(prefix, 0);
    }

    /**
     * This method finds all the strings that matches the prefix.
     * It tries also to change the prefix in order to support any user's input errors.
     * A change is an edit into the string: deletion, insertion, replacement. (Levensthein distance)
     * A prefix is considered valid if the number of changes is less then or equal maxDistance.
     *
     * @param prefix the prefix to search.
     * @param maxDistance the max number of errors between the prefix passed as argument
     *                    and the prefix found traversing the trie.
     * @return all the strings that matches the prefix.
     */
    public List<String> startsWith(String prefix, int maxDistance) {
        String fixPrefix = prefix == null ? "" : prefix;
        int fixMaxDistance = maxDistance < 0 ? 0 : maxDistance;

        Map<TrieNode, String> selected = new HashMap<>();
        this.levenstheinDistanceDfs(this.root, new StringBuilder(fixPrefix), 0, selected, 0, fixMaxDistance);

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


    // Private class methods ***********************************************************************
    private Set<String> getLeafStrings(TrieNode node, String prefix) {
        Set<String> strings = new HashSet<>();
        StringBuilder sb = new StringBuilder(prefix);
        this.leafStringsDfs(node, sb, strings);
        return strings;
    }

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
