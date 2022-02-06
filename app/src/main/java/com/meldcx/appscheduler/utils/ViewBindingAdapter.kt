package com.meldcx.appscheduler.ui.main

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.meldcx.appscheduler.data.Alarm
import com.meldcx.appscheduler.data.AppItem
import com.meldcx.appscheduler.ui.selectapp.AppListAdapter

@BindingAdapter("setAdapter")
fun setAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?) {
    adapter?.let {
        recyclerView.adapter = it
    }
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("submitList")
fun submitList(recyclerView: RecyclerView, alarms: List<Alarm>?) {
    val adapter = recyclerView.adapter as AlarmRecyclerViewAdapter?
    adapter?.setAlarms(alarms ?: listOf())
}

@BindingAdapter("submitAppList")
fun submitAppList(recyclerView: RecyclerView, appItems: List<AppItem>?) {
    val adapter = recyclerView.adapter as AppListAdapter?
    adapter?.setApps(appItems ?: listOf())
}
