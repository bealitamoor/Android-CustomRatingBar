Android-CustomRatingBar
============

CustomRatingBar is a library to help you show custom rating bar in your android app.

![screen shot](https://i.imgur.com/vtq24gV.png)

## Install

You can download from jitpack.

Latest version is [![](https://www.jitpack.io/v/bealitamoor/Android-CustomRatingBar.svg)](https://www.jitpack.io/#bealitamoor/Android-CustomRatingBar)

Step 1. Add the JitPack repository to your build file 
 
```groovy
  allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
```


Step 2. Add the dependency

```groovy
dependencies {
	        implementation 'com.github.bealitamoor:Android-CustomRatingBar:Tag'
	}
```

## Usage

### Configuration

Android-Rate provides methods to configure its behavior.

```java
class MainActivity : AppCompatActivity(), CustomRatingBar.OnStarChangeListener {

    protected var mRb: CustomRatingBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRb = findViewById(R.id.rb)
        mRb!!.onStarChangeListener = this
    }

    override fun onStarChange(ratingBar: CustomRatingBar?, star: Float) {
        //Toast.makeText(this, "Rating: $star", Toast.LENGTH_SHORT).show()
    }
}
```

### XML attributes

```xml
app:currentStar="3.5"
app:maxStar="5"
app:minStar="0.5"
app:padding="10dp"
app:starHeight="15dp"
app:starWidth="15dp"
```

## Support

Android-Rate supports API level 24 and up.
