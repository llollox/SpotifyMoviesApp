package com.lorenzorigato.base.datastructure;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TrieStartsWithDistanceUnitTest {

    @Test
    public void withFirstValidChar__whenAllStartWithSameLetter__shouldReturnAllValuesSorted() {
        String[] strings = {"Action", "Action Power", "Adventure", "Another genre"};
        Trie trie = new Trie(strings);
        List<String> output = trie.startsWith("A", 0);
        Assert.assertEquals(4, output.size());
        Assert.assertEquals("Action", output.get(0));
        Assert.assertEquals("Action Power", output.get(1));
        Assert.assertEquals("Adventure", output.get(2));
        Assert.assertEquals("Another genre", output.get(3));
    }

    @Test
    public void withFirstValidChar__shouldReturnAllStringsThatStartWithSameChar() {
        String[] strings = {"Action", "Action Power", "Adventure", "Another genre", "Drama"};
        Trie trie = new Trie(strings);
        List<String> output = trie.startsWith("A", 0);
        Assert.assertEquals(4, output.size());
        Assert.assertEquals("Action", output.get(0));
        Assert.assertEquals("Action Power", output.get(1));
        Assert.assertEquals("Adventure", output.get(2));
        Assert.assertEquals("Another genre", output.get(3));
    }

    @Test
    public void withCharInInput__whenTrieEmpty__shouldReturnEmptyList() {
        String[] strings = {};
        Trie trie = new Trie(strings);
        List<String> output = trie.startsWith("A", 0);
        Assert.assertEquals(0, output.size());
    }

    @Test
    public void withBlankCharInIput__shouldReturnAllValues() {
        String[] strings = {"Action", "Action Power", "Adventure", "Another genre", "Drama"};
        Trie trie = new Trie(strings);
        List<String> output = trie.startsWith("", 0);
        Assert.assertEquals(5, output.size());
        Assert.assertEquals("Action", output.get(0));
        Assert.assertEquals("Action Power", output.get(1));
        Assert.assertEquals("Adventure", output.get(2));
        Assert.assertEquals("Another genre", output.get(3));
        Assert.assertEquals("Drama", output.get(4));
    }

    @Test
    public void withValidCharInInput__whenSomeStringsMatch__shouldReturnOnlyStringsWithSamePrefix() {
        String[] strings = {"Action", "Action Power", "Adventure", "Another genre", "Drama"};
        Trie trie = new Trie(strings);
        List<String> output = trie.startsWith("Ac", 0);
        Assert.assertEquals(2, output.size());
        Assert.assertEquals("Action", output.get(0));
        Assert.assertEquals("Action Power", output.get(1));
    }

    @Test
    public void with1CharMissingAtBeginning__whenSomeStringsMatch__shouldReturnActionAndActionPower() {
        String[] strings = {"Action", "Action Power", "Adventure", "Another genre", "Drama"};
        Trie trie = new Trie(strings);
        List<String> output = trie.startsWith("ction", 1);
        Assert.assertEquals(2, output.size());
        Assert.assertEquals("Action", output.get(0));
        Assert.assertEquals("Action Power", output.get(1));
    }

    @Test
    public void with1CharMissingInMiddle__whenSomeStringsMatch__shouldReturnActionAndActionPower() {
        String[] strings = {"Action", "Action Power", "Adventure", "Another genre", "Drama"};
        Trie trie = new Trie(strings);
        List<String> output = trie.startsWith("Acion", 1);
        Assert.assertEquals(2, output.size());
        Assert.assertEquals("Action", output.get(0));
        Assert.assertEquals("Action Power", output.get(1));
    }

    @Test
    public void with1CharMissingAtTheEnd__whenSomeStringsMatch__shouldReturnActionAndActionPower() {
        String[] strings = {"Action", "Action Power", "Adventure", "Another genre", "Drama"};
        Trie trie = new Trie(strings);
        List<String> output = trie.startsWith("Actio", 1);
        Assert.assertEquals(2, output.size());
        Assert.assertEquals("Action", output.get(0));
        Assert.assertEquals("Action Power", output.get(1));
    }

    @Test
    public void with1CharInsertedAtBeginning__whenSomeStringsMatch__shouldReturnActionAndActionPower() {
        String[] strings = {"Action", "Action Power", "Adventure", "Another genre", "Drama"};
        Trie trie = new Trie(strings);
        List<String> output = trie.startsWith("zAction", 1);
        Assert.assertEquals(2, output.size());
        Assert.assertEquals("Action", output.get(0));
        Assert.assertEquals("Action Power", output.get(1));
    }

    @Test
    public void with1CharInsertedInTheMiddle__whenSomeStringsMatch__shouldReturnActionAndActionPower() {
        String[] strings = {"Action", "Action Power", "Adventure", "Another genre", "Drama"};
        Trie trie = new Trie(strings);
        List<String> output = trie.startsWith("Acztion", 1);
        Assert.assertEquals(2, output.size());
        Assert.assertEquals("Action", output.get(0));
        Assert.assertEquals("Action Power", output.get(1));
    }

    @Test
    public void with1CharInsertedAtTheEnd__whenSomeStringsMatch__shouldReturnActionAndActionPower() {
        String[] strings = {"Action", "Action Power", "Adventure", "Another genre", "Drama"};
        Trie trie = new Trie(strings);
        List<String> output = trie.startsWith("Acztion", 1);
        Assert.assertEquals(2, output.size());
        Assert.assertEquals("Action", output.get(0));
        Assert.assertEquals("Action Power", output.get(1));
    }

    @Test
    public void with1CharReplacedAtBeginning__whenSomeStringsMatch__shouldReturnActionAndActionPower() {
        String[] strings = {"Action", "Action Power", "Adventure", "Another genre", "Drama"};
        Trie trie = new Trie(strings);
        List<String> output = trie.startsWith("dction", 1);
        Assert.assertEquals(2, output.size());
        Assert.assertEquals("Action", output.get(0));
        Assert.assertEquals("Action Power", output.get(1));
    }

    @Test
    public void with1CharReplacedInTheMiddle__whenSomeStringsMatch__shouldReturnActionAndActionPower() {
        String[] strings = {"Action", "Action Power", "Adventure", "Another genre", "Drama"};
        Trie trie = new Trie(strings);
        List<String> output = trie.startsWith("Acrion", 1);
        Assert.assertEquals(2, output.size());
        Assert.assertEquals("Action", output.get(0));
        Assert.assertEquals("Action Power", output.get(1));
    }

    @Test
    public void with1CharReplacedAtTheEnd__whenSomeStringsMatch__shouldReturnActionAndActionPower() {
        String[] strings = {"Action", "Action Power", "Adventure", "Another genre", "Drama"};
        Trie trie = new Trie(strings);
        List<String> output = trie.startsWith("Actio", 1);
        Assert.assertEquals(2, output.size());
        Assert.assertEquals("Action", output.get(0));
        Assert.assertEquals("Action Power", output.get(1));
    }

    @Test
    public void with2MissingChars__whenSomeStringsMatch__shouldReturnActionAndActionPower() {
        String[] strings = {"Action", "Action Power", "Adventure", "Another genre", "Drama"};
        Trie trie = new Trie(strings);
        List<String> output = trie.startsWith("ctio", 2);
        Assert.assertEquals(2, output.size());
        Assert.assertEquals("Action", output.get(0));
        Assert.assertEquals("Action Power", output.get(1));
    }

    @Test
    public void with2InsertedChars__whenSomeStringsMatch__shouldReturnActionAndActionPower() {
        String[] strings = {"Action", "Action Power", "Adventure", "Another genre", "Drama"};
        Trie trie = new Trie(strings);
        List<String> output = trie.startsWith("ZActizon", 2);
        Assert.assertEquals(2, output.size());
        Assert.assertEquals("Action", output.get(0));
        Assert.assertEquals("Action Power", output.get(1));
    }

    @Test
    public void with2ReplacedChars__whenSomeStringsMatch__shouldReturnActionAndActionPower() {
        String[] strings = {"Action", "Action Power", "Adventure", "Another genre", "Drama"};
        Trie trie = new Trie(strings);
        List<String> output = trie.startsWith("fctuon", 2);
        Assert.assertEquals(2, output.size());
        Assert.assertEquals("Action", output.get(0));
        Assert.assertEquals("Action Power", output.get(1));
    }

    @Test
    public void with1Inserted1ReplacedChars__whenSomeStringsMatch__shouldReturnActionAndActionPower() {
        String[] strings = {"Action", "Action Power", "Adventure", "Another genre", "Drama"};
        Trie trie = new Trie(strings);
        List<String> output = trie.startsWith("Azctuon", 2);
        Assert.assertEquals(2, output.size());
        Assert.assertEquals("Action", output.get(0));
        Assert.assertEquals("Action Power", output.get(1));
    }

    @Test
    public void withTwoChars__shouldReturnActionAndActionPower() {
        String[] strings = {"Action", "Adventure", "Animation",
                "Comedy", "Crime", "Documentary", "Drama", "Family", "Fantasy", "History",
                "Horror", "Music", "Mystery", "Romance", "Science Fiction", "TV Movie",
                "Thriller", "War", "Western"};

        Trie trie = new Trie(strings);
        List<String> output = trie.startsWith("Act", 2);
        Assert.assertEquals(4, output.size());
        Assert.assertEquals("Action", output.get(0));
        Assert.assertEquals("Adventure", output.get(1));
        Assert.assertEquals("Animation", output.get(2));
        Assert.assertEquals("Science Fiction", output.get(3));
    }
}
