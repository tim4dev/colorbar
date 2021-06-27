/*
 * The MIT License
 *
 * Copyright (c) 2019, 2021 https://www.tim4.dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package dev.tim4.colorbardemo

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import dev.tim4.colorbar.ColorBar
import dev.tim4.colorbar.ColorBarItemData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@SuppressLint("SetTextI18n")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val colorBar1 = findViewById<ColorBar>(R.id.colorBar1)
        val colorBar2 = findViewById<ColorBar>(R.id.colorBar2)

        val log1 = findViewById<TextView>(R.id.textViewLog1)
        val log2 = findViewById<TextView>(R.id.textViewLog2)

        val colors1 = listOf(
            ColorBarItemData(color = Color.parseColor("red"), tag = 1),
            ColorBarItemData(color = Color.parseColor("yellow"), tag = 2),
            ColorBarItemData(color = Color.parseColor("green"), tag = 3, isChecked = true),
            ColorBarItemData(color = Color.parseColor("blue"), tag = 4),
            ColorBarItemData(color = Color.parseColor("magenta"), tag = 5)
        )
        colorBar1.setupColorBar(colors1)

        // NOTE. GlobalScope is used here only as an example app.
        GlobalScope.launch(Dispatchers.Main) {
            colorBar1.valueFlow.collect { data ->
                Log.d(TAG, "collect1 = $data")
                log1.text = "${data.isChecked}, ${data.tag}"
            }
        }

        val colors2 = listOf(
            ColorBarItemData(color = Color.parseColor("#CC0000"), tag = 1),
            ColorBarItemData(color = Color.parseColor("yellow"), tag = 2),
            ColorBarItemData(color = Color.parseColor("green"), tag = 3),
            ColorBarItemData(color = Color.parseColor("blue"), tag = 4),
            ColorBarItemData(color = Color.parseColor("magenta"), tag = 5),
            ColorBarItemData(color = Color.parseColor("#3399FF"), tag = 6),
            ColorBarItemData(color = Color.parseColor("#FF9933"), tag = 7)
        )
        colorBar2.setupColorBar(colors2)

        // NOTE. GlobalScope is used here only as an example app.
        GlobalScope.launch(Dispatchers.Main) {
            colorBar2.valueFlow.collect { data ->
                Log.d(TAG, "collect2 = $data")
                log2.text = "${data.isChecked}, ${data.tag}"
            }
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }
}
