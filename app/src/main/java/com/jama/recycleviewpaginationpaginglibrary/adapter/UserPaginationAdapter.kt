package com.jama.recycleviewpaginationpaginglibrary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jama.recycleviewpaginationpaginglibrary.databinding.LoadItemBinding
import com.jama.recycleviewpaginationpaginglibrary.databinding.UserItemBinding
import com.jama.recycleviewpaginationpaginglibrary.models.Data
import com.squareup.picasso.Picasso

class UserPaginationAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val LOADING = 0
    private val ITEM = 1
    private var isLoading = false
    private var userList = ArrayList<Data>()

    fun addAll(list: List<Data>){
        list.forEach {
            add(it)
        }
    }
    fun addLoadingFooter(){
        isLoading = true
        add(Data())
    }
    fun removeLoadingFooter(){
        isLoading = false
        val position = userList.size - 1
        userList.removeAt(position)
        notifyItemRemoved(position)

    }

    fun add(data: Data) {
        userList.add(data)
        notifyItemInserted(userList.size-1)
    }


    inner class DataVh(var userItemBinding:UserItemBinding):RecyclerView.ViewHolder(userItemBinding.root){
        fun onBind(data: Data){
            userItemBinding.apply {
                Picasso.get().load(data.avatar).into(avatar)
                fullNameTv.text = data.first_name + " " + data.last_name
                emailTv.text = data.email
            }
        }
    }
    inner class LoadVh(var loadItemBinding: LoadItemBinding):RecyclerView.ViewHolder(loadItemBinding.root){
        fun onBind(){
            loadItemBinding.progress.visibility = View.VISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ITEM){
            return DataVh(UserItemBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false)
            )
        }else{
            return LoadVh(LoadItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)
            )
        }
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position)==ITEM){
            val dataVh = holder as DataVh
            dataVh.onBind(userList[position])
        }else{
            val loadVh = holder as LoadVh
            loadVh.onBind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == userList.size-1 && isLoading) return LOADING
        return ITEM
    }
}