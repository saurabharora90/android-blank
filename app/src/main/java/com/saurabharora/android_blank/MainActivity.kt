package com.saurabharora.android_blank

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import com.saurabharora.android_blank.adapter.CityPagingAdapter
import com.saurabharora.android_blank.adapter.LoadingAdapter
import com.saurabharora.android_blank.paging.CitiesListDataSourceFactory
import com.saurabharora.android_blank.paging.City
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val cityAdapter: CityPagingAdapter by lazy {
        CityPagingAdapter()
    }

    private val headerLoadingAdapter = LoadingAdapter(LoadingAdapter.Type.Header)
    private val footerLoadingAdapter = LoadingAdapter(LoadingAdapter.Type.Footer)

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvCities.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val mergeAdapter = MergeAdapter(headerLoadingAdapter, cityAdapter, footerLoadingAdapter)
            adapter = mergeAdapter
        }

        val dataSourceFactory = CitiesListDataSourceFactory()
        dataSourceFactory.dataSource.switchMap { it.event }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d(TAG, "Event emission: $it")
                headerLoadingAdapter.state = it
                footerLoadingAdapter.state = it
            }


        val itemsPerPage = 20

        val config: PagedList.Config = PagedList.Config.Builder()
            .setPageSize(itemsPerPage)
            .setPrefetchDistance(itemsPerPage / 2)
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(itemsPerPage)
            .setMaxSize(PagedList.Config.MAX_SIZE_UNBOUNDED)
            .build()

        val pagedList = LivePagedListBuilder<Int, City>(dataSourceFactory, config).build()

        pagedList.observe(this, Observer {
            cityAdapter.submitList(it)
        })
    }
}
