package com.developer.ship.whatcanido

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.developer.ship.whatcanido.entity.ExampleDescription
import com.developer.ship.whatcanido.model.MainModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), IErrorHandler, ILaunchActivity {
    override fun launch(clazz: Class<*>) {
        startActivity(Intent(baseContext, clazz))
    }

    override fun showError(throwable: Throwable) {
        Log.w("MainActivity", throwable.message!!)
        Snackbar.make(rv_example_item, throwable.message!!, Snackbar.LENGTH_LONG)
    }

    /**
     *
     */
    private lateinit var mModel: MainModel
    private lateinit var mAdapter: ExampleItemAdapter
    private var mDispose = CompositeDisposable()
    private val urlRecyclerView = "https://downloader.disk.yandex.ru/preview/d96c8783b6bf32a31f5f9fb004a328a09b7799256259572f9fa91a53bc34203d/5c010e28/bDG6VZjHVrRY1PNHpmrdYYw99txnBKAZExK-B3PvRSymrp9WyHuZVwlI1Dqcs2C8Vs3buxIWx4OyrtxlfnBIRw%3D%3D?uid=0&filename=SmartSelect_20181130-003110_What%20can%20i%20do.jpg&disposition=inline&hash=&limit=0&content_type=image%2Fjpeg&tknv=v2&size=2048x2048"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mModel = ViewModelProviders.of(this).get(MainModel::class.java)
        mAdapter = ExampleItemAdapter(this)
        rv_example_item.adapter = mAdapter
        rv_example_item.layoutManager = LinearLayoutManager(baseContext)
        mDispose.add(mModel.getRecycleData().subscribeOn(AndroidSchedulers.mainThread()).doOnNext{hidden()}.subscribe {
            Log.i("MainActivity", it.size.toString())
            mAdapter.setData(it)
        })
        mDispose.add(mModel.waitAndSend().observeOn(AndroidSchedulers.mainThread()).doOnSubscribe { show() }.doOnDispose { Log.i("MainActivity", "single disposed") }.doOnError { hidden() }.subscribe ({
            val list = mutableListOf<ExampleDescription>()
            list.add(
                ExampleDescription(
                    urlRecyclerView,
                    "Title recycle view",
                    "Description recycle view",
                    Date()
                )
            )
            list.add(
                ExampleDescription(
                    urlRecyclerView,
                    "Title recycle view",
                    "Description recycle view",
                    Date()
                )
            )
            Log.i("MainActivity", "wait")
            mModel.setRecycleData(list)
        }, {
            it.stackTrace
            showError(it)
        }))

    }

    private fun hidden(){
        rv_example_item.visibility = View.VISIBLE
        frame_progress_bar.visibility = View.GONE
    }

    private fun show(){
        rv_example_item.visibility = View.GONE
        frame_progress_bar.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        mDispose.dispose()
    }
}
