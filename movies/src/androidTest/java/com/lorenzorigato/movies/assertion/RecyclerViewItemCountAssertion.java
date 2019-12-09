package com.lorenzorigato.movies.assertion;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

public class RecyclerViewItemCountAssertion implements ViewAssertion {


    // Private class attributes ********************************************************************
    private int expectedCount;


    // Constructor *********************************************************************************
    public RecyclerViewItemCountAssertion(int expectedCount) {
        this.expectedCount = expectedCount;
    }


    // ViewAssertion methods ***********************************************************************
    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        RecyclerView recyclerView = (RecyclerView) view;
        RecyclerView.Adapter adapter = recyclerView.getAdapter();

        assertNotNull(adapter);
        assertThat(adapter.getItemCount(), is(expectedCount));
    }
}
