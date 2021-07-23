package app.moviebase.androidx.widget.recyclerview.adapter.list

import android.graphics.drawable.Drawable
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.moviebase.androidx.widget.recyclerview.adapter.GlideItemAdapter
import app.moviebase.androidx.widget.recyclerview.adapter.RecyclerViewAdapterBase
import app.moviebase.androidx.widget.recyclerview.viewholder.ImageViewHolder
import com.bumptech.glide.RequestBuilder

interface GlideRecyclerAdapter<T : Any> : RecyclerViewAdapterBase<T>, GlideItemAdapter<T> {

    override fun onCreate(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        val holder = super.onCreate(parent, viewType)
        if (holder is ImageViewHolder) preloadProvider.setView(holder.imageView)
        return holder
    }

    override fun onBind(value: T?, holder: RecyclerView.ViewHolder) {
        super.onBind(value, holder)

        if (holder is ImageViewHolder) {
            val imageView = holder.imageView

            val tag = imageView.tag
            val newTag = glideConfig.loader?.getTag(value)
            // don't load it again if it has the same tag
            if (tag != null && newTag == tag) return

            val requestBuilder = glideConfig.loader?.load(value, holder)
            requestBuilder?.into(imageView)?.waitForLayout()
            imageView.tag = newTag
        }
    }

    override fun onClear(holder: RecyclerView.ViewHolder) {
        super.onClear(holder)
        if (holder is ImageViewHolder) {
            val imageView = holder.imageView
            glideConfig.loader?.clearGlide(imageView)
            imageView.tag = null
        }
    }

    override fun getPreloadRequestBuilder(item: T): RequestBuilder<Drawable>? {
        return glideConfig.loader?.preload(item, null)
    }

    override fun getPreloadItems(position: Int): List<T> {
        val currentData = data
        return if (position < 0 || currentData == null || position >= currentData.size)
            emptyList()
        else
            currentData.subList(position, position + 1)
    }
}
