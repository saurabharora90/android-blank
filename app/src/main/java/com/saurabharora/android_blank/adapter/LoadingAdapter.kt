package com.saurabharora.android_blank.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saurabharora.android_blank.R
import com.saurabharora.android_blank.paging.CityListEvent
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class LoadingAdapter(private val type: Type) : RecyclerView.Adapter<LoadingViewHolder>() {

    enum class Type {
        Header, Footer
    }

    var state: CityListEvent by Delegates.observable(
        CityListEvent.Success.SuccessInitial,
        { _: KProperty<*>, _: CityListEvent, new: CityListEvent ->
            when (type) {
                Type.Header -> {
                    if (new is CityListEvent.Success.SuccessPreviousPage)
                        notifyItemRemoved(0)
                    else if (new is CityListEvent.Loading.LoadingPreviousPage)
                        notifyItemInserted(0)
                }

                Type.Footer -> {
                    if (new is CityListEvent.Success.SuccessNextPage)
                        notifyItemRemoved(0)
                    else if (new is CityListEvent.Loading.LoadingNextPage)
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