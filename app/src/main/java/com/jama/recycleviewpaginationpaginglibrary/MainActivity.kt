package com.jama.recycleviewpaginationpaginglibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.jama.recycleviewpaginationpaginglibrary.adapter.UserPaginationAdapter
import com.jama.recycleviewpaginationpaginglibrary.databinding.ActivityMainBinding
import com.jama.recycleviewpaginationpaginglibrary.models.UserData
import com.jama.recycleviewpaginationpaginglibrary.networking.ApiClient
import com.jama.recycleviewpaginationpaginglibrary.networking.ApiService
import com.jama.recycleviewpaginationpaginglibrary.pagination.PaginationScrollListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var apiService:ApiService
    lateinit var userPaginationAdapter: UserPaginationAdapter
    private var currentPage = 1
    private var TOTAL_PAGES = 0
    private var isLastPage = false
    private var isLoading = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiService = ApiClient.getRetrofit().create(ApiService::class.java)
        // Pagination with recycleview

        // 1. custom
        // 2. Paging3 library
        val linearLayoutManager  = LinearLayoutManager(this)
        binding.rv.layoutManager = linearLayoutManager
        userPaginationAdapter = UserPaginationAdapter()
        binding.rv.adapter = userPaginationAdapter
        loadFirstPage()
        binding.rv.addOnScrollListener(object :PaginationScrollListener(linearLayoutManager){
            override fun loadMoreItems() {
                currentPage++
                loadNextPage()
                isLoading = true
            }

            override fun isLastPage(): Boolean = isLastPage

            override fun isLoading(): Boolean = isLoading

        })
    }

    private fun loadNextPage() {
        apiService.getUsers(currentPage)
            .enqueue(object :Callback<UserData>{
                override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                    if (response.isSuccessful){
                        userPaginationAdapter.removeLoadingFooter()
                        isLoading = false
                        userPaginationAdapter.addAll(response.body()?.data?: emptyList())
                        if (currentPage!=TOTAL_PAGES){
                            userPaginationAdapter.addLoadingFooter()
                        }else isLastPage = true
                    }
                }

                override fun onFailure(call: Call<UserData>, t: Throwable) {

                }

            })
    }

    private fun loadFirstPage(){
        apiService.getUsers()
            .enqueue(object :Callback<UserData>{
                override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                    if (response.isSuccessful){
                        userPaginationAdapter.addAll(response.body()?.data?: emptyList())
                        binding.progress.visibility = View.GONE
                        TOTAL_PAGES = response.body()?.total_pages?:0
                        if (currentPage<=TOTAL_PAGES){
                            userPaginationAdapter.addLoadingFooter()
                        }else{
                            isLastPage = true
                        }
                    }
                }

                override fun onFailure(call: Call<UserData>, t: Throwable) {

                }

            })
    }
}