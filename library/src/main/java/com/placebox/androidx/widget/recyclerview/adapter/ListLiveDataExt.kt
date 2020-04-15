package com.placebox.androidx.widget.recyclerview.adapter

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T : Any> LiveData<out List<T>>.bindAdapterUpdate(owner: LifecycleOwner, adapter: AbstractListRecyclerAdapter<T>) {
    observe(owner, Observer {
        if (adapter.data.isEmpty())
            adapter.setData(it)
        else
            adapter.updateData(it)
    })
}

fun <T : Any> LiveData<out List<T>>.bindAdapter(owner: LifecycleOwner, adapter: AbstractListRecyclerAdapter<T>) {
    observe(owner, Observer { adapter.setData(it) })
}

