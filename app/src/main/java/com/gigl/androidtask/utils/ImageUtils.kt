import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.gigl.androidtask.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

object ImageUtils {

    fun loadImage(
        context: Context?, imageView: ImageView?, url: String
    ) {
        if (url.isNotEmpty() && context != null) Timber.d("checkinglide5 %s", url)

        context?.let {
            Glide.with(it)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(
                    RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache both original and resized image

                        // Placeholder while loading // Fallback image if URL is null
                        .dontAnimate() // Disable default animations
                )
                .thumbnail(0.50f) // Show thumbnail while loading
                .listener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable?>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        GlobalScope.launch(Dispatchers.Main) {
                            imageView?.setImageDrawable(
                                ContextCompat.getDrawable(
                                    context, R.drawable.long_video_image
                                )
                            )
                        }
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable?>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                })
                .into(imageView!!)
        }
    }
}
