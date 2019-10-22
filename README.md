
[![](https://jitpack.io/v/tim4dev/colorbar.svg)](https://jitpack.io/#tim4dev/colorbar)

# Color Bar Selector

A simple color choice for your e-commerce store client. Written in Kotlin.

 - `minSdkVersion 16`
 - AndroidX

![Color Bar in Action (picture).](https://res.cloudinary.com/ddhl2pupw/image/upload/v1571678573/library-colorbar/colorbar_1.png)


[![Color Bar in Action (youtube).](https://img.youtube.com/vi/6gx_0Bk6UOU/0.jpg)](https://www.youtube.com/watch?v=6gx_0Bk6UOU)

# Gradle

    allprojects {
        repositories {
    	    maven { url 'https://jitpack.io' }
    	}
    }

    dependencies {
        implementation "com.github.tim4dev:colorbar:VERSION"
    }    
    

# Usage

## XML Layout

    <dev.tim4.colorbar.ColorBar
        android:id="@+id/colorBar"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        
        custom:colorBarColumns="@integer/colorbar_columns"
        custom:iconCircleMarginsDp="@dimen/colorbar_circle_margins"
        custom:iconCircleSizeDp="@dimen/colorbar_circle_size" 
    />

## dimensions

    <dimen name="colorbar_circle_size">32dp</dimen>
    <dimen name="colorbar_circle_margins">2dp</dimen>
    <integer name="colorbar_columns">5</integer>

## Kotlin example

    val colors = listOf(
                ColorCircleData(Color.parseColor("red")),
                ColorCircleData(Color.parseColor("yellow")),
                ColorCircleData(Color.parseColor("green"))
            )
    colorBar.drawColorBar(colors) { Log.d(TAG, "onClickEvent. data = $colors") }
    

## Java example

    ColorBar colorBar = findViewById(R.id.colorBar);
    ArrayList<ColorCircleData> colors = new ArrayList<>();
    colors.add(new ColorCircleData(Color.parseColor("red"), true));
    colors.add(new ColorCircleData(Color.parseColor("yellow")));
    colorBar.drawColorBar(colors, colorData -> Log.d(TAG, "onClickEvent. data = " + colorData.toString()));
    
# See also

[A simple text choice for your e-commerce store client. Written in Kotlin.](https://github.com/tim4dev/textbar)
    

# License

The MIT License

Copyright (c) 2019 tim4dev https://www.tim4.dev

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
