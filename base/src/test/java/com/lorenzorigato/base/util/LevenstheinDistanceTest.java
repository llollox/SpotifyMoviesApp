package com.lorenzorigato.base.util;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class LevenstheinDistanceTest {

    LevenstheinDistance ld = new LevenstheinDistance();

    @Test
    public void whenBothNull__return0() {
        int output = this.ld.levenstheinDistance(null, null);
        assertEquals(0, output);
    }

    @Test
    public void whenNullAndEmpty__return0() {
        int output = this.ld.levenstheinDistance(null, "");
        assertEquals(0, output);
    }

    @Test
    public void whenEmptyAndNull__return0() {
        int output = this.ld.levenstheinDistance("", null);
        assertEquals(0, output);
    }

    @Test
    public void whenNullAndValorized__returnLengthOfValorized() {
        String b = "ciao";
        int output = this.ld.levenstheinDistance(null, b);
        assertEquals(b.length(), output);
    }

    @Test
    public void whenBothEmpty__return0() {
        int output = this.ld.levenstheinDistance("", "");
        assertEquals(0, output);
    }

    @Test
    public void whenDifferentSingleChar__return1() {
        int output = this.ld.levenstheinDistance("A", "B");
        assertEquals(1, output);
    }

    @Test
    public void whenValorizedDifferFor2__return2() {
        int output = this.ld.levenstheinDistance("Act", "Sci");
        assertEquals(2, output);
    }

    @Test
    public void whenValorizedDifferFor5__return5() {
        int output = this.ld.levenstheinDistance("Action", "Science");
        assertEquals(5, output);
    }


    @Test
    public void whenValorizedDifferFor10__return10() {
        int output = this.ld.levenstheinDistance("Action", "Science Fiction");
        assertEquals(10, output);
    }

    @Test
    public void whenValorizedDifferFor8__return8() {
        int output = this.ld.levenstheinDistance("Action", "SciencFiction");
        assertEquals(8, output);
    }
}
