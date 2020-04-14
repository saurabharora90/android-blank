package com.saurabharora.android_blank.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.saurabharora.android_blank.R
import com.saurabharora.android_blank.paging.City
import kotlinx.android.synthetic.main.item_cities.view.*

class CityPagingAdapter : PagedListAdapter<City, CityViewHolder>(Diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_cities, parent, false)
        return CityViewHolder(v)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = getItem(position)

        if (city == null) {
            return
        } else {
            holder.bind(city)
        }
    }

    object Diff : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City) = oldItem.name == newItem.name
        override fun areContentsTheSame(oldItem: City, newItem: City) = true
    }
}

class CityViewHolder(root: View) : RecyclerView.ViewHolder(root) {

    fun bind(city: City) {
        itemView.tvCityName.text = city.name
    }
}