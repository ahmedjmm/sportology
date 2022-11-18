package com.mobile.sportology.viewsUtils.recyclerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewItemsDivider(
    context: Context,
    resId: Int
) : RecyclerView.ItemDecoration() {
    private var mDivider: Drawable = ContextCompat.getDrawable(context, resId)!!

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        // left margin for the divider
        val marginLeft: Int = 32

        // right margin for the divider with
        // reference to the parent width
        val marginRight = parent.width - 32

        // this loop creates the top and bottom
        // divider for each items in the RV
        // as each items are different
        for (i in 0 until parent.childCount) {

            // this condition is because the last
            // and the first items in the RV have
            // no dividers in the list
            if (i != parent.childCount - 1) {
                val child: View = parent.getChildAt(i)

                val params = child.layoutParams as RecyclerView.LayoutParams

                // calculating the distance of the
                // divider to be drawn from the top
                val marginTop: Int = child.bottom + params.bottomMargin
                val marginBottom: Int = marginTop + mDivider.intrinsicHeight

                mDivider.setBounds(marginLeft, marginTop, marginRight, marginBottom)
                mDivider.draw(c)
            }
        }
    }
}