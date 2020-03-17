package br.com.jeramovies.presentation.util.extensions

import android.content.Context
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import br.com.jeramovies.R

fun Context.makeStatusBarTransparent(window: Window) {
    window.decorView.systemUiVisibility =
        (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false, window)
    window.statusBarColor = ResourcesCompat.getColor(resources, R.color.colorBlackTransparent, theme)
}

fun setWindowFlag(bits: Int, on: Boolean, window: Window) {
    val win: Window = window
    val winParams: WindowManager.LayoutParams = win.attributes
    if (on) {
        winParams.flags = winParams.flags or bits
    } else {
        winParams.flags = winParams.flags and bits.inv()
    }
    win.attributes = winParams
}

fun AppCompatActivity.setupToolbar(
    toolbar: Toolbar?,
    showHome: Boolean = true,
    title: String? = null
) {
    if (title != null) {
        setupToolbarWithTitle(toolbar, title, showHome)
    } else {
        setupToolbar(toolbar, showHome)
    }
}

private fun AppCompatActivity.setupToolbar(toolbar: Toolbar?, showHome: Boolean) {
    toolbar?.let { setSupportActionBar(it) }
    supportActionBar?.run {
        setDisplayHomeAsUpEnabled(showHome)
        setDisplayShowHomeEnabled(showHome)
        setDisplayShowTitleEnabled(false)
    }
}

private fun AppCompatActivity.setupToolbarWithTitle(
    toolbar: Toolbar?,
    title: String,
    showHome: Boolean
) {
    setSupportActionBar(toolbar)
    supportActionBar?.run {
        toolbar?.title = title
        setDisplayHomeAsUpEnabled(showHome)
        setDisplayShowHomeEnabled(showHome)
    }
}
