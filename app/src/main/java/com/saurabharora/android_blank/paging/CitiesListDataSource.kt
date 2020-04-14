package com.saurabharora.android_blank.paging

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class CitiesListDataSource(private val initialPage: Int) : PageKeyedDataSource<Int, City>() {

    private val disposable = CompositeDisposable()

    private val _event = BehaviorSubject.create<CityListEvent>()
    val event: Observable<CityListEvent> = _event

    private var retryHandler: (() -> Unit)? = null

    init {
        addInvalidatedCallback { disposable.dispose() }
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, City>
    ) {
        Single.just(
            listOf(
                City("Damascus"),
                City("Dhaka"),
                City("Doha"),
                City("Dublin"),
                City("Edinburgh"),
                City("Georgetown"),
                City("Hanoi"),
                City("Harare"),
                City("Helsinki"),
                City("Islamabad"),
                City("Jakarta"),
                City("Kabul"),
                City("Kathmandu"),
                City("Lisbon"),
                City("London"),
                City("Luxembourg"),
                City("Madrid"),
                City("Male"),
                City("Manila"),
                City("Monaco")
            )
        )
            .delay(5, TimeUnit.SECONDS)
            .doOnSubscribe { _event.onNext(CityListEvent.Loading.LoadingInitial) }
            .subscribe(
                { thread ->
                    if (thread.isEmpty()) {
                        CityListEvent.Empty
                    } else {
                        CityListEvent.Success.SuccessInitial
                    }.let(_event::onNext)

                    callback.onResult(
                        thread, initialPage - 1, //assuming initial page is 2 for demo sake
                        initialPage + 1 //assuming initial page is 2 and we have a total of 3 pages for demo sake
                    )
                },
                { throwable ->
                    _event.onNext(CityListEvent.Error.ErrorInitial(throwable))
                    retryHandler = { loadInitial(params, callback) }
                }
            )
            .also { disposable.add(it) }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, City>) {
        Single.just(
            listOf(
                City("Abu Dhabi"),
                City("Amsterdam"),
                City("Ankara"),
                City("Athens"),
                City("Baghdad"),
                City("Baku"),
                City("Bangkok"),
                City("Beijing"),
                City("Belfast"),
                City("Berlin"),
                City("Bern"),
                City("Brussels"),
                City("Bucharest"),
                City("Buenos Aires"),
                City("Cairo"),
                City("Cardiff"),
                City("Castries"),
                City("Colombo"),
                City("Conakry"),
                City("Copenhagen")
            )
        )
            .delay(5, TimeUnit.SECONDS)
            .doOnSubscribe { _event.onNext(CityListEvent.Loading.LoadingPreviousPage) }
            .subscribe(
                { thread ->
                    _event.onNext(CityListEvent.Success.SuccessPreviousPage)
                    callback.onResult(
                        thread,
                        if (params.key == 1) null else params.key - 1
                    )
                },
                { throwable ->
                    _event.onNext(CityListEvent.Error.ErrorPreviousPage(throwable))
                }
            )
            .also { disposable.add(it) }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, City>) {
        Single.just(
            listOf(
                City("Nairobi"),
                City("New Delhi"),
                City("Oslo"),
                City("Paris"),
                City("Phnom Penh"),
                City("Prague"),
                City("Pyongyang"),
                City("Quito"),
                City("Reykjavik"),
                City("Riyadh"),
                City("Rome"),
                City("Santiago"),
                City("Seoul"),
                City("Singapore"),
                City("Stockholm"),
                City("Taipei"),
                City("Tokyo"),
                City("Vienna"),
                City("Warsaw"),
                City("Washington D.C.\n")
            )
        )
            .delay(5, TimeUnit.SECONDS)
            .doOnSubscribe { _event.onNext(CityListEvent.Loading.LoadingNextPage) }
            .subscribe(
                { thread ->
                    _event.onNext(CityListEvent.Success.SuccessNextPage)
                    callback.onResult(
                        thread,
                        null //hardcode assumption that we'll only be loading one "after" page
                    )
                },
                { throwable ->
                    _event.onNext(CityListEvent.Error.ErrorNextPage(throwable))
                }
            )
            .also { disposable.add(it) }
    }
}

class CitiesListDataSourceFactory(private val initialPage: Int = 2) :
    DataSource.Factory<Int, City>() {

    private val _dataSource = BehaviorSubject.create<CitiesListDataSource>()
    val dataSource: Observable<CitiesListDataSource> = _dataSource

    override fun create(): DataSource<Int, City> = CitiesListDataSource(
        initialPage
    ).also(_dataSource::onNext)
}
