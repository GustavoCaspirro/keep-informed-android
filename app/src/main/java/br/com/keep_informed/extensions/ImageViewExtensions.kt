package br.com.keep_informed.extensions

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.squareup.picasso.Picasso


fun ImageView.load(url: String?,
                   @DrawableRes placeholder: Int?,
                   @DrawableRes errorId: Int?,
                   fade: Boolean = true,
                   maxWidth: Int? = null,
                   maxHeight: Int? = null){
    if (url?.isNotEmpty() == true){
        Picasso.get()
            .load(url)
            .also {
                if(maxWidth != null && maxHeight != null){
                    it.resize(maxWidth,maxHeight)
                    it.onlyScaleDown()
                }
            }
            .also { if (!fade) it.noFade() }
            .also{ errorId?.apply { it.error(this) }}
            .also{ placeholder?.apply { it.placeholder(this) }}
            .into(this)
    }else{
        placeholder?.let {
            Picasso.get()
                .load(it)
                .also { requestCreator ->  if (!fade) requestCreator.noFade() }
                .into(this)
        }
    }
}