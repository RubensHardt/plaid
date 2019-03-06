package io.plaidapp.dribbble.ui.shot

import android.graphics.drawable.AnimatedVectorDrawable
import android.text.format.DateUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.PluralsRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import io.plaidapp.core.util.HtmlUtils
import io.plaidapp.core.util.glide.GlideApp
import java.util.Date

@BindingAdapter("relativeTime")
fun bindRelativeTime(
    textView: TextView,
    date: Date?
) {
    date?.let {
        textView.text = DateUtils.getRelativeTimeSpanString(
            it.time,
            System.currentTimeMillis(),
            DateUtils.SECOND_IN_MILLIS
        ).toString().toLowerCase()
    }
}

@BindingAdapter("imageUrl", "placeholder", "circleCrop", "crossFade", requireAll = false)
fun bindImage(
    imageView: ImageView,
    imageUrl: String?,
    placeholder: Int? = null,
    circleCrop: Boolean? = false,
    crossFade: Boolean? = false
) {
    var request = GlideApp.with(imageView.context).load(imageUrl)
    if (placeholder != null) {
        request = request.placeholder(placeholder)
    }
    if (circleCrop == true) {
        request = request.circleCrop()
    }
    if (crossFade == true) {
        request = request.transition(DrawableTransitionOptions.withCrossFade())
    }
    request.into(imageView)
}

@BindingAdapter("htmlText")
fun bindHtmlText(
    textView: TextView,
    htmlText: CharSequence?
) {
    if (htmlText == null) return
    HtmlUtils.setTextWithNiceLinks(textView, htmlText)
}

@BindingAdapter("pluralResource", "pluralQuantity", "pluralValue", requireAll = true)
fun bindNumberFormattedPlural(
    textView: TextView,
    @PluralsRes plural: Int,
    pluralQuantity: Int,
    pluralValue: String?
) {
    textView.text = textView.resources.getQuantityString(
        plural,
        pluralQuantity,
        pluralValue ?: pluralQuantity.toString()
    )
}

@BindingAdapter("animatedOnClick")
fun bindAnimatedOnClick(
    textView: TextView,
    onClickAction: () -> Unit
) = setAnimatedOnClick(textView, onClickAction)

@BindingAdapter("animatedOnClick")
fun bindAnimatedOnClick(
    textView: TextView,
    enabled: Boolean
) = setAnimatedOnClick(textView, enabled = enabled)

private fun setAnimatedOnClick(
    textView: TextView,
    onClickAction: (() -> Unit)? = null,
    enabled: Boolean = true
) {
    textView.setOnClickListener {
        // If the top compound drawable is an AVD then start it when clicked
        (textView.compoundDrawables[1] as? AnimatedVectorDrawable)?.start()
        if (enabled) {
            onClickAction?.invoke()
        }
    }
}
