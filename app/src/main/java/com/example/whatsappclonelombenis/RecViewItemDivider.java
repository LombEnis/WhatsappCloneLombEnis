package com.example.whatsappclonelombenis;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class RecViewItemDivider extends RecyclerView.ItemDecoration{
    private static final int[]ATTRS=new int[]{android.R.attr.listDivider};

    private Drawable divider;
    private int firstPosition;

    public RecViewItemDivider(Context context, int firstPosition){
        final TypedArray styledAttributes=context.obtainStyledAttributes(ATTRS);
        divider=styledAttributes.getDrawable(0);
        styledAttributes.recycle();

        this.firstPosition = firstPosition;
    }

    @Override
    public void onDraw(Canvas c,RecyclerView parent,RecyclerView.State state){
        int right=parent.getWidth();

        int childCount=parent.getChildCount();
        for(int i = firstPosition; i < (childCount-1); i++){
        View child=parent.getChildAt(i);

        RecyclerView.LayoutParams params=(RecyclerView.LayoutParams)child.getLayoutParams();

        int top=child.getBottom();
        int bottom=top+divider.getIntrinsicHeight();

        divider.setBounds(230,top,right,bottom);
        divider.draw(c);
        }
    }
}