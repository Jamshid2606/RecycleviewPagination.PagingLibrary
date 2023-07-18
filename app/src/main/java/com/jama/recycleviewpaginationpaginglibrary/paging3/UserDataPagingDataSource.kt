package com.jama.recycleviewpaginationpaginglibrary.paging3

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jama.recycleviewpaginationpaginglibrary.models.Data
import com.jama.recycleviewpaginationpaginglibrary.networking.ApiClient
import com.jama.recycleviewpaginationpaginglibrary.networking.ApiService
import java.lang.Exception

class UserDataPagingDataSource:PagingSource<Int, Data>() {
    private val apiService = ApiClient.getRetrofit().create(ApiService::class.java)

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        try {
            var nextPageNumber = params.key ?: 1
            val response = apiService.getPaging3Users(nextPageNumber)
            if (response.isSuccessful){
                return LoadResult.Page(response.body()?.data?: emptyList(), null,++nextPageNumber)
            }else{
                return LoadResult.Error(Throwable("Error"))
            }
        }catch (e:Exception){
            return LoadResult.Error(e)
        }
    }
}