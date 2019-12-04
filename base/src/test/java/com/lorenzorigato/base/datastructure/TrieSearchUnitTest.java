package com.lorenzorigato.base.datastructure;

import org.junit.Assert;
import org.junit.Test;

public class TrieSearchUnitTest {

    @Test
    public void withValidInput__shouldReturnTrue() {
        String[] strings = {"Action", "Adventure", "Science Fiction"};
        Trie trie = new Trie(strings);
        Assert.assertTrue(trie.search("Action"));
    }

    @Test
    public void withInvalidInput__shouldReturnFalse() {
        String[] strings = {"Action", "Adventure", "Science Fiction"};
        Trie trie = new Trie(strings);
        Assert.assertFalse(trie.search("Drama"));
    }

    @Test
    public void withValidInputLowerCased__shouldReturnFalse() {
        String[] strings = {"Action", "Adventure", "Science Fiction"};
        Trie trie = new Trie(strings);
        Assert.assertFalse(trie.search("action"));
    }

    @Test
    public void withPieceOfValidInput__shouldReturnFalse() {
        String[] strings = {"Action", "Adventure", "Science Fiction"};
        Trie trie = new Trie(strings);
        Assert.assertFalse(trie.search("Science"));
    }

    @Test
    public void withEmptyInput__shouldReturnFalse() {
        String[] strings = {"Action", "Adventure", "Science Fiction"};
        Trie trie = new Trie(strings);
        Assert.assertFalse(trie.search(""));
    }

    @Test
    public void withEmptyInputAndNoWords__shouldReturnFalse() {
        String[] strings = {};
        Trie trie = new Trie(strings);
        Assert.assertFalse(trie.search(""));
    }
}
