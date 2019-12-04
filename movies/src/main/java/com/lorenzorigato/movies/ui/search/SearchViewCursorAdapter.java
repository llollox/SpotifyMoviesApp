package com.lorenzorigato.movies.ui.search;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.BaseColumns;

import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import java.util.List;

public class SearchViewCursorAdapter extends SimpleCursorAdapter {


    // Constructor *********************************************************************************
    public SearchViewCursorAdapter(Context context) {
        super(context,
                android.R.layout.simple_list_item_1,
                null,
                new String[] {"name"},
                new int[] {android.R.id.text1},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }


    // Class methods *******************************************************************************
    public void setSuggestions(List<String> suggestions) {
        Cursor cursor = createCursorFromResult(suggestions);
        this.swapCursor(cursor);
    }


    // Private class methods ***********************************************************************
    private Cursor createCursorFromResult(List<String> strings)  {
        final MatrixCursor cursor = new MatrixCursor(new String[]{ BaseColumns._ID, "name" });
        for (int i=0; i<strings.size(); i++) {
            String s = strings.get(i);
            cursor.addRow(new Object[] {i, s});
        }

        return cursor;
    }
}
