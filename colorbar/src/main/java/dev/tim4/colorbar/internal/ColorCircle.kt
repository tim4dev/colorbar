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


package dev.tim4.colorbar.internal

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.view.updateMargins
import dev.tim4.colorbar.ColorBarItemData
import dev.tim4.colorbar.R

/**
 * Creates circle of a specified color. Show check image if checked.
 */
class ColorCircle : FrameLayout {

    private val layoutId: Int get() = R.layout.colorbar_item_view

    @Suppress("JoinDeclarationAndAssignment")
    private val layout: View

    private val circleImage: ImageView
    private val checkImage: ImageView

    private lateinit var data: ColorBarItemData
    private var circleSizePx: Int = 0
    private var marginsPx: Int = 0

    init {
        layout = View.inflate(context, layoutId, this)
        circleImage = findViewById(R.id.colorbarCircle)
        checkImage = findViewById(R.id.colorbarCircleCheck)
    }

    /**
     * Create an instance programmatically
     */
    @Suppress("LongParameterList", "MagicNumber")
    constructor(
        context: Context,
        data: ColorBarItemData,
        circleSizePx: Int,
        marginsPx: Int,
        onClickListener: (ColorCircle, ColorBarItemData) -> Unit = { _, _ -> }
    ) : super(context) {

        this.data = data.copy()
        this.circleSizePx = circleSizePx
        this.marginsPx = marginsPx

        initView()

        // onclick listener
        layout.setOnClickListener {
            val checked = !this.data.isChecked
            this.data = this.data.copy(isChecked = checked)
            showChecked(checked)
            onClickListener.invoke(this, this.data)
        }
    }

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
    }

    //https://developer.android.com/guide/topics/graphics/drawables
    private fun initView() {
        // set circle color
        val circleDrawable = getDrawable(context, R.drawable.colorbar_circle) as GradientDrawable
        circleDrawable.setColor(data.color)
        circleImage.setImageDrawable(circleDrawable)

        // set check
        val checkDrawable = if (isColorDark(data.color)) {
            // circle - dark color, check - white
            getDrawable(context, R.drawable.colorbar_ic_check_white)
        } else {
            // circle - light color, check - dark
            getDrawable(context, R.drawable.colorbar_ic_check_dark)
        }
        checkImage.setImageDrawable(checkDrawable)

        showChecked(data.isChecked)
    }

    fun getData() = data

    private fun showChecked(isChecked: Boolean) {
        checkImage.visibility = if (isChecked) View.VISIBLE else View.GONE
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        circleImage.layoutParams.height = circleSizePx
        circleImage.layoutParams.width = circleSizePx

        checkImage.layoutParams.height = circleSizePx
        checkImage.layoutParams.width = circleSizePx

        // set margins
        if (layoutParams is MarginLayoutParams) {
            (layoutParams as MarginLayoutParams).updateMargins(
                left = marginsPx,
                top = marginsPx,
                right = marginsPx,
                bottom = marginsPx
            )
        }
    }

    fun toggleChecked(isChecked: Boolean) {
        data = data.copy(isChecked = isChecked)
        showChecked(isChecked)
    }
}
