package com.example.kotlincoroutinewithretrofit.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlincoroutinewithretrofit.R
import com.example.kotlincoroutinewithretrofit.models.responsemodels.UserModel
import kotlinx.android.synthetic.main.user_item.view.*

class AnimalAdapter(val items: ArrayList<UserModel.Data>, val context: Context) :
    RecyclerView.Adapter<ViewHolder>() {

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.user_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(context)
            .load(items.get(position).avatar)
            .into(holder.ivUser)

        holder.tvUserName?.text =
            items.get(position).first_name + " " + items.get(position).last_name
        holder.tvUserEmail?.text = items.get(position).email
    }

    fun addAll(itemList: ArrayList<UserModel.Data>) {
        items.addAll(itemList)
        notifyDataSetChanged()
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val tvUserName = view.tvUserName
    val tvUserEmail = view.tvUserEmail
    val ivUser = view.ivUser
}