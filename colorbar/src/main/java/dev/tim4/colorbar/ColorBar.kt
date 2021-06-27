/*
 * The MIT License
 *
 * Copyright (c) 2019, 2021 https://www.tim4.dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package dev.tim4.colorbar

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.content.withStyledAttributes
import dev.tim4.colorbar.internal.ColorCircle
import dev.tim4.colorbar.internal.dpToPx
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Create grid of color circles.
 */
class ColorBar : TableLayout, IColorBar {

    private val layoutId: Int get() = R.layout.colorbar_layout

    private lateinit var _valueFlow: MutableStateFlow<ColorBarItemData>
    override lateinit var valueFlow: StateFlow<ColorBarItemData>

    private var circleSizePx: Int = 0
    private var marginsPx: Int = 0
    private var columns: Int = 0

    private var childs: MutableList<ColorCircle> = ArrayList()

    init {
        View.inflate(context, layoutId, this)
    }

    constructor(context: Context) : super(context) {
        initLayout(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initLayout(context, attrs)
    }

    private fun initLayout(context: Context, attrs: AttributeSet?) {
        context.withStyledAttributes(attrs, R.styleable.ColorBar) {
            circleSizePx = getDimensionPixelSize(
                R.styleable.ColorBar_iconCircleSizeDp,
                CIRCLE_SIZE_DEFAULT_DP.dpToPx
            )
            marginsPx = getDimensionPixelSize(
                R.styleable.ColorBar_iconCircleMarginsDp,
                MARGINS_DEFAULT_DP.dpToPx
            )
            columns = getInteger(
                R.styleable.ColorBar_colorBarColumns,
                1
            )
        }

        if (isInEditMode) stub()
    }

    override fun setupColorBar(dataList: List<ColorBarItemData>) {
        this.removeAllViews()

        childs = ArrayList(dataList.size)

        var countCols = 0

        // Fills the table with circles based on the array of colors.
        var row = TableRow(context)
        dataList.forEach { data ->
            val colorView = ColorCircle(
                context = context,
                data = data,
                circleSizePx = circleSizePx,
                marginsPx = marginsPx
            ) { view, data1 ->
                // onClick
                emitValue(view, data1)
            }
            childs.add(colorView)

            row.addView(colorView)

            countCols++

            if (countCols >= columns) {
                // new row
                addView(row)
                row = TableRow(context)
                countCols = 0
            }
        }
        addView(row)

        initStateFlow()
    }

    private fun emitValue(view: ColorCircle, data: ColorBarItemData) {
        // radio button behavior
        childs.forEach { child ->
            if (child != view) {
                child.toggleChecked(false)
            } else {
                child.toggleChecked(data.isChecked)
            }
        }
        // emit
        _valueFlow.value = data
    }

    private fun initStateFlow() {
        val first = childs.find {
            it.getData().isChecked
        } ?: childs[0]
        _valueFlow = MutableStateFlow(first.getData())
        valueFlow = _valueFlow
    }

    private fun stub() {
        val row = TableRow(context)
        repeat((0 until columns).count()) {
            val colorCircle = ColorCircle(
                context = context,
                data = ColorBarItemData(Color.parseColor("white")),
                circleSizePx = circleSizePx,
                marginsPx = marginsPx
            )
            row.addView(colorCircle)
        }
        addView(row)
    }

    companion object {
        private const val CIRCLE_SIZE_DEFAULT_DP = 32
        private const val MARGINS_DEFAULT_DP = 2
    }
}