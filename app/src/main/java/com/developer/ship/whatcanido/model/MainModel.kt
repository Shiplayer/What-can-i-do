package com.developer.ship.whatcanido.model

import android.arch.lifecycle.ViewModel
import com.developer.ship.whatcanido.entity.ExampleDescription
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/**
 * Created by Shiplayer on 29.11.18.
 */

class MainModel : ViewModel() {
    private var mRecycleData: Subject<List<ExampleDescription>> = PublishSubject.create()

    override fun onCleared() {
        super.onCleared()
        mRecycleData.onComplete()
    }

    public fun setRecycleData(data: List<ExampleDescription>){
        mRecycleData.onNext(data)

    }

    public fun getRecycleData() : Observable<List<ExampleDescription>> = mRecycleData
}