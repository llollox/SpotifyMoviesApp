package com.lorenzorigato.movies.matcher;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class DrawableMatcher extends TypeSafeMatcher<View> {


    // Static **************************************************************************************
    public static Matcher<View> withDrawable(int resId) {
        return new DrawableMatcher(resId);
    }


    // Private class attributes ********************************************************************
    private int expectedId;


    // Constructor *********************************************************************************
    public DrawableMatcher(int expectedId) {
        this.expectedId = expectedId;
    }


    // TypeSafeMatcher methods *********************************************************************
    @Override
    protected boolean matchesSafely(View target) {
        if (!(target instanceof ImageView)){
            return false;
        }
        ImageView imageView = (ImageView) target;
        if (expectedId < 0){
            return imageView.getDrawable() == null;
        }
        Resources resources = target.getContext().getResources();
        Drawable expectedDrawable = resources.getDrawable(expectedId);
        if (expectedDrawable == null) {
            return false;
        }
        Bitmap bitmap = getBitmap (imageView.getDrawable());
        Bitmap otherBitmap = getBitmap(expectedDrawable);
        return bitmap.sameAs(otherBitmap);
    }

    @Override
    public void describeTo(Description description) {
        if (description != null) {
            description.appendText("No ImageView found");
        }
    }


    // Private class methods ***********************************************************************
    private Bitmap getBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
