package by.dro.pets.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import by.dro.pets.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

fun ImageView.load(url: String, loadOnlyFromCache: Boolean = false, onLoadingFinished: () -> Unit = {}) {


    val listener = object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            onLoadingFinished()
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            onLoadingFinished()
            return false
        }
    }

    val requestOptions = RequestOptions.placeholderOf(R.drawable.dog_placeholder)
        .dontTransform().onlyRetrieveFromCache(loadOnlyFromCache)

    Glide.with(this)
        .load(url)
        .apply(requestOptions)
        .listener(listener)
        .into(this)
}

fun RecyclerView.ViewHolder.getContext() : Context = itemView.context