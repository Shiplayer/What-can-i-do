package com.developer.ship.whatcanido.model

import android.arch.lifecycle.ViewModel
import com.developer.ship.whatcanido.entity.ExampleDescription
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.SingleSubject
import io.reactivex.subjects.Subject
import java.util.concurrent.TimeUnit

/**
 * Created by Shiplayer on 29.11.18.
 */

class MainModel : ViewModel() {
    private var mRecycleData: Subject<List<ExampleDescription>> = PublishSubject.create()
    private var mSingle = SingleSubject.create<Any>()//Single<Any>.timeout(3000, TimeUnit.MILLISECONDS)
    private var disposable = CompositeDisposable()
    private val urlRecyclerView = "https://downloader.disk.yandex.ru/preview/d96c8783b6bf32a31f5f9fb004a328a09b7799256259572f9fa91a53bc34203d/5c010e28/bDG6VZjHVrRY1PNHpmrdYYw99txnBKAZExK-B3PvRSymrp9WyHuZVwlI1Dqcs2C8Vs3buxIWx4OyrtxlfnBIRw%3D%3D?uid=0&filename=SmartSelect_20181130-003110_What%20can%20i%20do.jpg&disposition=inline&hash=&limit=0&content_type=image%2Fjpeg&tknv=v2&size=2048x2048"
    private var Sending = false

    init {
        mSingle.onSuccess("")
    }

    override fun onCleared() {
        super.onCleared()
        mRecycleData.onComplete()
        disposable.dispose()
    }

    public fun setRecycleData(data: List<ExampleDescription>){
        mRecycleData.onNext(data)

    }

    public fun getRecycleData() : Observable<List<ExampleDescription>> = mRecycleData

    fun waitAndSend() : Single<Any> {
        return mSingle.delay(3000, TimeUnit.MILLISECONDS)

    }
}