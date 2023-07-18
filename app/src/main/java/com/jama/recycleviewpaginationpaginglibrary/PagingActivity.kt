package com.jama.recycleviewpaginationpaginglibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.jama.recycleviewpaginationpaginglibrary.databinding.ActivityPagingBinding
import com.jama.recycleviewpaginationpaginglibrary.paging3.UserPaging3Adapter
import com.jama.recycleviewpaginationpaginglibrary.paging3.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PagingActivity : AppCompatActivity(), CoroutineScope {
    lateinit var userViewModel: UserViewModel
    lateinit var userPaging3Adapter: UserPaging3Adapter
    lateinit var binding: ActivityPagingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPagingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        userPaging3Adapter = UserPaging3Adapter()
        binding.rv.adapter = userPaging3Adapter
        userViewModel.flow
        launch {
            userViewModel.flow.collect{
                userPaging3Adapter.submitData(it)
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
}