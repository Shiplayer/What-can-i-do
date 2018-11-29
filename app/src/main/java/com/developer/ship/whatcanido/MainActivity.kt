package com.developer.ship.whatcanido

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.developer.ship.whatcanido.entity.ExampleDescription
import com.developer.ship.whatcanido.model.MainModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var model: MainModel
    private lateinit var mAdapter: ExampleItemAdapter

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        model = ViewModelProviders.of(this).get(MainModel::class.java)
        mAdapter = ExampleItemAdapter()
        rv_example_item.adapter = mAdapter
        rv_example_item.layoutManager = LinearLayoutManager(baseContext)
        model.getRecycleData().subscribeOn(AndroidSchedulers.mainThread()).doOnSubscribe{show()}.subscribe {
            mAdapter.setData(it)
            hidden()
        }
        Observable.timer(3000, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe{
            val list = mutableListOf<ExampleDescription>()
            list.add(ExampleDescription("", "Recycler view", "This is recycler view with card view!", Date()))
            model.setRecycleData(list)
        }
    }

    private fun hidden(){
        rv_example_item.visibility = View.VISIBLE
        frame_progress_bar.visibility = View.GONE
    }

    private fun show(){
        rv_example_item.visibility = View.GONE
        frame_progress_bar.visibility = View.VISIBLE
    }
}
