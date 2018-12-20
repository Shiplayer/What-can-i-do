package com.developer.ship.whatcanido.utils

import android.graphics.drawable.Drawable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.net.URL

/**
 * Created by Shiplayer on 30.11.18.
 */

class Utilities{
    companion object {
        public fun getImageByURL(url:String): Single<Drawable> = Single.just(url).subscribeOn(Schedulers.io()).map {
            val imageURL = URL(it)
            Drawable.createFromStream(imageURL.openStream(), "recyclerView")
        }
    }
}