package example.com.acl.travelmantics.viewbindings

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import example.com.acl.travelmantics.R
import example.com.acl.travelmantics.glide.GlideApp

object ViewBinding {
    @JvmStatic
    @BindingAdapter("app:url")
    fun setImageFromUrl(imageView: ImageView, url: String?) {
        val locUrl = url ?: R.mipmap.ic_launcher
        GlideApp.with(imageView.context)
                .load(locUrl)
                .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("app:goneToVisible")
    fun setVisibility(view: View, isVisible: Boolean) {
        view.visibility = if (isVisible) VISIBLE else GONE
    }
}