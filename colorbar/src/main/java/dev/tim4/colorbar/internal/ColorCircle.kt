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


package dev.tim4.colorbar.internal

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import dev.tim4.colorbar.R

/**
 * Creates circle of a specified color. Show check image if checked.
 */
class ColorCircle : FrameLayout {

    private val mainView: View = View.inflate(context,
        R.layout.custom_color_circle, this)
    private lateinit var circleImage: ImageView
    private lateinit var checkImage: ImageView

    private lateinit var colorData: ColorCircleData
    private var circleSizePx: Int = 0
    private var marginsPx: Int = 0



    /**
     * Create an instance programmatically
     */
    constructor(_context: Context,
                _colorData: ColorCircleData,
                _circleSizePx: Int,
                _marginsPx: Int,
                _onClickListener: (ColorCircleData) -> Unit = {} // external callback
    ) : super(_context) {

        this.colorData = _colorData
        this.circleSizePx = _circleSizePx
        this.marginsPx = _marginsPx

        initView()

        // onclick listener
        circleImage.setOnClickListener {
            colorData.isChecked = !colorData.isChecked
            showChecked()
            // callback
            _onClickListener.invoke(colorData)
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
        // UI
        circleImage = mainView.findViewById(R.id.color_circle)
        checkImage = mainView.findViewById(R.id.color_circle_check)

        // set circle color
        val circleDrawable = getDrawable(context, R.drawable.colorbar_circle) as GradientDrawable
        circleDrawable.setColor(colorData.color)
        circleImage.setImageDrawable(circleDrawable)

        // set check
        val checkDrawable = if (isColorDark(colorData.color)) {
            // circle - dark color, check - white
            getDrawable(context, R.drawable.ic_check_white)
        } else {
            // circle - light color, check - dark
            getDrawable(context, R.drawable.ic_check_dark)
        }
        checkImage.setImageDrawable(checkDrawable)

        showChecked()
    }

    private fun showChecked() {
        checkImage.visibility = if (colorData.isChecked) View.VISIBLE else View.GONE
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // set margins
        if (layoutParams is MarginLayoutParams) {
            val layoutParams = layoutParams as MarginLayoutParams
            layoutParams.width = circleSizePx
            layoutParams.height = circleSizePx
            layoutParams.setMargins(marginsPx, marginsPx, marginsPx, marginsPx)
            mainView.layoutParams = layoutParams
        }
    }

    fun setChecked(checked: Boolean) {
        colorData.isChecked = checked
        showChecked()
    }

}