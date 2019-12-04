package com.lorenzorigato.base.datastructure;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TrieStartsWithUnitTest {

    @Test
    public void withValidInput__shouldReturnTrue() {
        String[] strings = {"Action", "Action Power", "Adventure", "Another genre"};
        Trie trie = new Trie(strings);
        List<String> output = trie.startsWith("A");
        Assert.assertEquals(4, output.size());
        Assert.assertEquals("Action", output.get(0));
        Assert.assertEquals("Action Power", output.get(1));
        Assert.assertEquals("Adventure", output.get(2));
        Assert.assertEquals("Another genre", output.get(3));
    }
}
