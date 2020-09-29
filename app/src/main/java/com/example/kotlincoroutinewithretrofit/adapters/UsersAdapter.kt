package com.example.kotlincoroutinewithretrofit.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlincoroutinewithretrofit.R
import com.example.kotlincoroutinewithretrofit.models.responsemodels.DummyUserModel
import kotlinx.android.synthetic.main.item_progress.view.*
import kotlinx.android.synthetic.main.user_item.view.*

class UsersAdapter(
    private val items: ArrayList<DummyUserModel.Data?>,
    private val context: Context
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isLoading = false
    private val viewTypeItem = 1
    private val viewTypeProgress = 2

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position] == null) viewTypeProgress else viewTypeItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == viewTypeItem) {
            ItemViewHolder(
                LayoutInflater.from(context).inflate(R.layout.user_item, parent, false)
            )
        } else {
            ProgressHolder(
                LayoutInflater.from(context).inflate(R.layout.item_progress, parent, false)
            )
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is ItemViewHolder) {
            viewHolder.tvUserName.text =
                items[position]!!.name
            viewHolder.tvUserEmail.text = items[position]!!.email
            viewHolder.tvIndex.text = (position + 1).toString()
        } else if (viewHolder is ProgressHolder) {
            viewHolder.pbItem.visibility = View.VISIBLE
        }
    }

    fun addAll(itemList: ArrayList<DummyUserModel.Data>) {
        items.addAll(itemList)
        notifyDataSetChanged()
    }

    fun addLoadingFooter() {
        isLoading = true
        items.add(null)
        notifyItemInserted(items.size - 1)
    }

    fun removeLoadingFooter() {
        isLoading = false
        if (items.size > 0) {
            val position = items.size - 1
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}

class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvUserName: AppCompatTextView = view.tvUserName
    val tvUserEmail: AppCompatTextView = view.tvUserEmail
    val tvIndex: AppCompatTextView = view.tvIndx
}

class ProgressHolder(view: View) : RecyclerView.ViewHolder(view) {
    val pbItem: ProgressBar = view.pbItem
}