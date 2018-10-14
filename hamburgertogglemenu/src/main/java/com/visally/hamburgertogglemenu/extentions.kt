package com.visally.hamburgertogglemenu

import android.content.Context
import android.util.DisplayMetrics

/**
 * Created by Mahdi_ZareTahghighDoost(ZTD)
 *  on 8/5/2018.
 */

fun Context.dpToPx(dp: Int): Float {
    val displayMetrics = this.resources.displayMetrics
    return (dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
}