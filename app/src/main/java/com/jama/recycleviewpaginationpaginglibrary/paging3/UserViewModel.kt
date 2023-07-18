package com.jama.recycleviewpaginationpaginglibrary.paging3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn

class UserViewModel:ViewModel() {
    val flow = Pager(
        PagingConfig(20)
    ){
        UserDataPagingDataSource()
    }.flow.cachedIn(viewModelScope)
}