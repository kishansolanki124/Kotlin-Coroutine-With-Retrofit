package com.example.kotlincoroutinewithretrofit.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlincoroutinewithretrofit.R
import com.example.kotlincoroutinewithretrofit.models.responsemodels.UserModel
import kotlinx.android.synthetic.main.user_item.view.*

class AnimalAdapter(private val items: ArrayList<UserModel.Data>, private val context: Context) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.user_item, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(context)
            .load(items[position].avatar)
            .into(holder.ivUser)

        holder.tvUserName.text =
            items[position].first_name + " " + items[position].last_name
        holder.tvUserEmail.text = items[position].email
    }

    fun addAll(itemList: ArrayList<UserModel.Data>) {
        items.addAll(itemList)
        notifyDataSetChanged()
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvUserName: AppCompatTextView = view.tvUserName
    val tvUserEmail: AppCompatTextView = view.tvUserEmail
    val ivUser: AppCompatImageView = view.ivUser
}