package io.plaidapp.dribbble.ui.shot

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.N
import androidx.annotation.ColorInt
import androidx.appcompat.content.res.AppCompatResources
import io.plaidapp.core.util.ColorUtils
import io.plaidapp.dribbble.R
import javax.inject.Inject
import io.plaidapp.core.R as coreR

class ShotDecorator @Inject constructor(context: Context) {

    @Suppress("DEPRECATION")
    val locale = if (SDK_INT >= N) {
        context.resources.configuration.locales[0]
    } else {
        context.resources.configuration.locale
    }

    val linkColors = AppCompatResources.getColorStateList(context, R.color.dribbble_links)!!

    @ColorInt
    val highlightColor = ColorUtils.getThemeColor(
        context,
        coreR.attr.colorPrimary,
        coreR.color.primary
    )
}
