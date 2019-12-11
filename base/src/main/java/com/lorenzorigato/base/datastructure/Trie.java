package com.lorenzorigato.base.datastructure;

import com.lorenzorigato.base.util.LevenstheinDistance;

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
    private LevenstheinDistance levenstheinDistance = new LevenstheinDistance();


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

        HashMap<TrieNode, String> selected = this.getNodesOfLevel(
                this.root, fixPrefix.length(), fixMaxDistance);

        HashMap<TrieNode, String> levenstheinDistanceFiltered = this.filterWithLevenstheinDistance(
                fixPrefix, selected, fixMaxDistance);

        HashMap<TrieNode, String> removedChildren = this.removeChildNodes(levenstheinDistanceFiltered);

        Set<String> strings = new HashSet<>();
        for (Map.Entry<TrieNode, String> entry : removedChildren.entrySet()) {
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

    private HashMap<TrieNode, String> removeChildNodes(HashMap<TrieNode, String> map) {
        HashMap<TrieNode, String> filtered = new HashMap<>(map);
        for (HashMap.Entry<TrieNode, String> entry : map.entrySet()) {
            TrieNode root = entry.getKey();
            removeChildrenDfs(root, root, filtered);
        }
        return filtered;
    }

    private void removeChildrenDfs(TrieNode root, TrieNode node, HashMap<TrieNode, String> map) {
        if (node != root) {
            map.remove(node);
        }

        for (HashMap.Entry<Character, TrieNode> entry : node.getChildren().entrySet()) {
            TrieNode child = entry.getValue();
            this.removeChildrenDfs(root, child, map);
        }
    }

    private HashMap<TrieNode, String> filterWithLevenstheinDistance(String input, HashMap<TrieNode, String> mapNodes, int maxDistance) {
        HashMap<TrieNode, String> filtered = new HashMap<>();
        for (HashMap.Entry<TrieNode, String> entry : mapNodes.entrySet()) {
            TrieNode node = entry.getKey();
            String prefix = entry.getValue();

            int levenstheinDistance = this.levenstheinDistance.levenstheinDistance(input, prefix);
            if (levenstheinDistance <= maxDistance) {
                filtered.put(node, prefix);
            }
        }
        return filtered;
    }


    private HashMap<TrieNode, String> getNodesOfLevel(TrieNode root, int level, int maxDistance) {
        HashMap<TrieNode, String> mapNodes = new HashMap<>();
        StringBuilder prefix = new StringBuilder();
        this.calculatesNodesOfLevelDfs(root, level, level + maxDistance, mapNodes, prefix);
        return mapNodes;
    }

    private void calculatesNodesOfLevelDfs(
            TrieNode node, int lowerBoundLevel, int upperBoundLevel,
            HashMap<TrieNode, String> mapNodes, StringBuilder prefix) {

        int currentLevel = prefix.length();

        if (currentLevel > upperBoundLevel) {
            return;
        }

        boolean isWithinLevelBounds = (currentLevel >= lowerBoundLevel && currentLevel <= upperBoundLevel);

        if (isWithinLevelBounds || node.getChildren().isEmpty() || node.isEndOfWord()) {
            mapNodes.put(node, prefix.toString());
        }

        for (HashMap.Entry<Character, TrieNode> entry : node.getChildren().entrySet()) {
            TrieNode child = entry.getValue();
            Character character = entry.getKey();
            prefix.append(character);
            this.calculatesNodesOfLevelDfs(child, lowerBoundLevel, upperBoundLevel, mapNodes, prefix);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

}
