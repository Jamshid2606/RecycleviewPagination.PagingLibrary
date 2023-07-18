package com.jama.recycleviewpaginationpaginglibrary.paging3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jama.recycleviewpaginationpaginglibrary.databinding.UserItemBinding
import com.jama.recycleviewpaginationpaginglibrary.models.Data
import com.squareup.picasso.Picasso

class UserPaging3Adapter :PagingDataAdapter <Data, UserPaging3Adapter.DataVh>(MyDiffUtil()){

    class MyDiffUtil:DiffUtil.ItemCallback<Data>(){
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean = oldItem == newItem
    }

    inner class DataVh(val userItemBinding: UserItemBinding):RecyclerView.ViewHolder(userItemBinding.root){
        fun onBind(data: Data){
            userItemBinding.apply {
                Picasso.get().load(data.avatar).into(avatar)
                fullNameTv.text = data.first_name + " " + data.last_name
                emailTv.text = data.email
            }
        }
    }

    override fun onBindViewHolder(holder: DataVh, position: Int) {
        val item = getItem(position)
        if (item!=null){
            holder.onBind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataVh = DataVh(
        UserItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
}