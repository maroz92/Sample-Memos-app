package pl.mrozok.myapplication.presentation.tools

import android.graphics.Rect
import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.recyclerview.widget.RecyclerView

class VerticalSpaceItemDecoration(
    verticalSpaceHeightInDp: Int,
    displayMetrics: DisplayMetrics
) : RecyclerView.ItemDecoration() {

    private val spaceInPixels: Int =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, verticalSpaceHeightInDp.toFloat(), displayMetrics)
            .toInt()

    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        outRect.bottom = spaceInPixels
    }
}