package com.saurabharora.android_blank.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saurabharora.android_blank.R
import com.saurabharora.android_blank.paging.CityListEvent
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

private const val TAG = "LoadingAdapter"

class LoadingAdapter(private val type: Type) : RecyclerView.Adapter<LoadingViewHolder>() {

    enum class Type {
        Header, Footer
    }

    var state: CityListEvent by Delegates.observable(
        CityListEvent.Success.SuccessInitial,
        { _: KProperty<*>, old: CityListEvent, new: CityListEvent ->
            when (type) {
                Type.Header -> {
                    if (old is CityListEvent.Loading.LoadingPreviousPage && new is CityListEvent.Success.SuccessPreviousPage)
                        notifyItemRemoved(0)
                    else if (old is CityListEvent.Success.SuccessInitial && new is CityListEvent.Loading.LoadingPreviousPage)
                        notifyItemInserted(0)
                }

                Type.Footer -> {
                    Log.d(TAG, "observable() called with: old = $old, new = $new")
                    if (old is CityListEvent.Loading.LoadingNextPage && new is CityListEvent.Success.SuccessNextPage)
                        notifyItemRemoved(0)
                    else if (old is CityListEvent.Success.SuccessPreviousPage && new is CityListEvent.Loading.LoadingNextPage)
                        notifyItemInserted(0)
                }
            }
        })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadingViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
        return LoadingViewHolder(v)
    }

    override fun getItemCount(): Int {
        return when (type) {
            Type.Footer -> {
                if (state is CityListEvent.Loading.LoadingNextPage) 1 else 0
            }
            Type.Header -> {
                if (state is CityListEvent.Loading.LoadingPreviousPage) 1 else 0
            }
        }
    }

    override fun onBindViewHolder(holder: LoadingViewHolder, position: Int) {

    }
}

class LoadingViewHolder(root: View) : RecyclerView.ViewHolder(root) {
}