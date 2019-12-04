package com.lorenzorigato.base.datastructure;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TrieStartsWithUnitTest {

    @Test
    public void withFirstValidChar__whenAllStartWithSameLetter__shouldReturnAllValuesSorted() {
        String[] strings = {"Action", "Action Power", "Adventure", "Another genre"};
        Trie trie = new Trie(strings);
        List<String> output = trie.startsWith("A");
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
        List<String> output = trie.startsWith("A");
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
        List<String> output = trie.startsWith("A");
        Assert.assertEquals(0, output.size());
    }

    @Test
    public void withBlankCharInIput__shouldReturnAllValues() {
        String[] strings = {"Action", "Action Power", "Adventure", "Another genre", "Drama"};
        Trie trie = new Trie(strings);
        List<String> output = trie.startsWith("");
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
        List<String> output = trie.startsWith("Ac");
        Assert.assertEquals(2, output.size());
        Assert.assertEquals("Action", output.get(0));
        Assert.assertEquals("Action Power", output.get(1));
    }
}
