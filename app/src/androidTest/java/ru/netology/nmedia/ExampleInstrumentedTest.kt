package ru.netology.nmedia

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import ru.netology.nmedia.dto.Post

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {


    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("ru.netology.nmedia", appContext.packageName)
    }

    //Tests for fun likeText(likeClickCount)  for like imageButton
    @Test
    fun tenLikes() {
        val post =
            Post(likes = 10) //The value of the counter that the function is being tested with
        val expected = "10"
        val actual = likeText(post.likes)
        assertEquals(expected, actual)
    }

    @Test
    fun thousandLikes() {
        val post = Post(likes = 1000)
        val expected = "1K"
        val actual = likeText(post.likes)
        assertEquals(expected, actual)
    }

    @Test
    fun thousandAndHundredLikes() {
        val post = Post(likes = 1_100)
        val expected = "1.1K"
        val actual = likeText(post.likes)
        assertEquals(expected, actual)
    }

    @Test
    fun tenThousandLikes() {
        val post = Post(likes = 10_000)
        val expected = "10K"
        val actual = likeText(post.likes)
        assertEquals(expected, actual)
    }

    @Test
    fun millionLikes() {
        val post = Post(likes = 1_000_000)
        val expected = "1M"
        val actual = likeText(post.likes)
        assertEquals(expected, actual)
    }

    @Test
    fun millionAndThousandLikes() {
        val post = Post(likes = 1_100_000)
        val expected = "1.1M"
        val actual = likeText(post.likes)
        assertEquals(expected, actual)
    }


//---------------------------------------------------------------------------------------------
// Tests for fun likeText(shareClickCount)  for share imageButton

    @Test
    fun tenShares() {
        val post = Post(shares = 10)
        val expected = "10"
        val actual = likeText(post.shares)
        assertEquals(expected, actual)
    }
    @Test
    fun thousandShares() {
        val post = Post(shares = 1000)
        val expected = "1K"
        val actual = likeText(post.shares)
        assertEquals(expected, actual)
    }

    @Test
    fun thousandAndHundredShares() {
        val post = Post(shares = 1100)
        val expected = "1.1K"
        val actual = likeText(post.shares)
        assertEquals(expected, actual)
    }

    @Test
    fun tenThousandShares() {
        val post = Post(shares = 10_000)
        val expected = "10K"
        val actual = likeText(post.shares)
        assertEquals(expected, actual)
    }

    @Test
    fun millionShares() {
        val post = Post(shares = 1_000_000)
        val expected = "1M"
        val actual = likeText(post.shares)
        assertEquals(expected, actual)
    }

    @Test
    fun millionAndThousandShares() {
        val post = Post(shares = 1_100_000)
        val expected = "1.1M"
        val actual = likeText(post.shares)
        assertEquals(expected, actual)
    }


//---------------------------------------------------------------------------------------------
// Tests for fun likeText(lookClickCount)  for look imageButton


    @Test
    fun lookTen() {
        val post = Post(looks = 10)
        val expected = "10"
        val actual = likeText(post.looks)
        assertEquals(expected, actual)
    }
    @Test
    fun lookThousand() {
        val post = Post(looks = 1_000)
        val expected = "1K"
        val actual = likeText(post.looks)
        assertEquals(expected, actual)
    }

    @Test
    fun lookThousandAndHundred() {
        val post = Post(looks = 1_100)
        val expected = "1.1K"
        val actual = likeText(post.looks)
        assertEquals(expected, actual)
    }

    @Test
    fun lookTenThousand() {
        val post = Post(looks = 10_000)
        val expected = "10K"
        val actual = likeText(post.looks)
        assertEquals(expected, actual)
    }

    @Test
    fun lookMillion() {
        val post = Post(looks = 1_000_000)
        val expected = "1M"
        val actual = likeText(post.looks)
        assertEquals(expected, actual)
    }

    @Test
    fun lookMillionAndThousand() {
        val post = Post(looks = 1_100_000)
        val expected = "1.1M"
        val actual = likeText(post.looks)
        assertEquals(expected, actual)
    }
}