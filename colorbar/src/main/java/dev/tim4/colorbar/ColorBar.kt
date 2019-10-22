/*
 * The MIT License
 *
 * Copyright (c) 2019 https://www.tim4.dev
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
import dev.tim4.colorbar.internal.ColorCircle
import dev.tim4.colorbar.internal.ColorCircleData
import dev.tim4.colorbar.internal.dpToPx

/**
 * Create grid of color circles.
 */
class ColorBar : TableLayout {

    private var circleSizePx: Int = 0
    private var marginsPx: Int = 0
    private var columns: Int = 0

    //original data
    var dataList: List<ColorCircleData> = ArrayList()
    //result checked data
    private var viewList: MutableList<ColorCircle> = ArrayList()



    constructor(context: Context) : super(context) {
        initView(null)
    }

    constructor(context: Context, _attrs: AttributeSet) : super(context, _attrs) {
        initView(_attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        View.inflate(context, R.layout.custom_color_bar, this)

        val typedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.ColorBar,
                0, 0)

        circleSizePx = typedArray.getDimensionPixelSize(R.styleable.ColorBar_iconCircleSizeDp, CIRCLE_SIZE_DEFAULT_DP.dpToPx)
        marginsPx = typedArray.getDimensionPixelSize(R.styleable.ColorBar_iconCircleMarginsDp, MARGINS_DEFAULT_DP.dpToPx)
        columns = typedArray.getInteger(
            R.styleable.ColorBar_colorBarColumns,
            COLUMNS_COUNT_DEFAULT
        )

        if (dataList.isEmpty()) stub()

        typedArray.recycle()
    }

    /**
     * Draw Color Bar
     * @param colorDataList data
     */
    fun drawColorBar(colorDataList: List<ColorCircleData>,
                     _onClickListener: (ColorCircleData) -> Unit = {} // external callback
                     ) {

        this.removeAllViews()

        this.dataList = colorDataList
        this.viewList = ArrayList(colorDataList.size)

        var countCols = 0

        // Fills the table with circles based on the array of colors.
        var row = TableRow(context)
        for (colorCircleData in colorDataList) {

            val colorView =
                ColorCircle(
                    context,
                    colorCircleData,
                    circleSizePx,
                    marginsPx
                ) { data ->
                    // radio button behavior
                    val idx = dataList.indexOf(data)
                    for (i in dataList.indices) {
                        if (i != idx) {
                            dataList[i].isChecked = false
                            viewList[i].setChecked(false)
                        }
                    }

                    // call external callback
                    _onClickListener.invoke(data)
                }

            viewList.add(colorView)

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
    }

    private fun stub() {
        val stubList = ArrayList<ColorCircleData>()
        stubList.add(ColorCircleData(Color.parseColor("red")))
        stubList.add(ColorCircleData(Color.parseColor("yellow")))
        stubList.add(ColorCircleData(Color.parseColor("green")))

        val row = TableRow(context)
        for (stubData in stubList) {
            val colorCircle = ColorCircle(
                context,
                stubData,
                circleSizePx,
                marginsPx
            ) { /* nothing */ }
            row.addView(colorCircle)
        }
        addView(row)
    }

    /**
     * Validate.
     * @return true if selected
     */
    override fun isSelected(): Boolean {
        var result = false
        for (data in dataList) {
            if (data.isChecked) {
                result = true
                break
            }
        }
        return result
    }

    companion object {
        private const val COLUMNS_COUNT_DEFAULT = 5
        private const val CIRCLE_SIZE_DEFAULT_DP = 32
        private const val MARGINS_DEFAULT_DP = 2
    }
}