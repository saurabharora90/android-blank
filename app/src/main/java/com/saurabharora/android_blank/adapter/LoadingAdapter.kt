package com.saurabharora.android_blank.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saurabharora.android_blank.R
import com.saurabharora.android_blank.paging.CityListEvent
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class LoadingAdapter : RecyclerView.Adapter<LoadingViewHolder>() {

    var state: CityListEvent by Delegates.observable(
        CityListEvent.Loading.LoadingInitial,
        { _: KProperty<*>, old: CityListEvent, new: CityListEvent ->
            if (old is CityListEvent.Loading && new is CityListEvent.Success)
                notifyItemRemoved(0)
            else if (old is CityListEvent.Success && new is CityListEvent.Loading)
                notifyItemInserted(0)
            else notifyItemChanged(0)
        })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadingViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
        return LoadingViewHolder(v)
    }

    override fun getItemCount(): Int {
        return if (state is CityListEvent.Loading) 1 else 0
    }

    override fun onBindViewHolder(holder: LoadingViewHolder, position: Int) {

    }
}

class LoadingViewHolder(root: View) : RecyclerView.ViewHolder(root) {
}