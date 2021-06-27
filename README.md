
[![](https://jitpack.io/v/tim4dev/colorbar.svg)](https://jitpack.io/#tim4dev/colorbar)

# Color Bar Selector

A simple color choice for your e-commerce store client. Written in Kotlin + coroutines.

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
        android:id="@+id/colorBar1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"

        app:colorBarColumns="5"
        app:iconCircleMarginsDp="2dp"
        app:iconCircleSizeDp="32dp" />

## Kotlin example

    val colors1 = listOf(
        ColorBarItemData(color = Color.parseColor("red"), tag = 1),
        ColorBarItemData(color = Color.parseColor("yellow"), tag = 2),
        ColorBarItemData(color = Color.parseColor("green"), tag = 3, isChecked = true),
        ColorBarItemData(color = Color.parseColor("blue"), tag = 4),
        ColorBarItemData(color = Color.parseColor("#3399FF"), tag = 5)
    )
    colorBar1.setupColorBar(colors1)

    // NOTE. GlobalScope is used here only as an example app.
    GlobalScope.launch(Dispatchers.Main) {
        colorBar1.valueFlow.collect { data ->
            Log.d(TAG, "collect1 = $data")
            log1.text = "${data.isChecked}, ${data.tag}"
        }
    }

# See also

[A simple text choice for your e-commerce store client. Written in Kotlin.](https://github.com/tim4dev/textbar)
