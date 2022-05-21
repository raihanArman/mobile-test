package com.raydev.mobile_test.feature.user_list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.raydev.mobile_test.R
import com.raydev.mobile_test.base.BaseActivity
import com.raydev.mobile_test.data.model.User
import com.raydev.mobile_test.databinding.ActivityUserListBinding
import com.raydev.mobile_test.feature.user_add.UserAddActivity
import com.raydev.mobile_test.util.ResponseState
import com.raydev.mobile_test.util.ext.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListActivity : BaseActivity<ActivityUserListBinding>(),SearchView.OnQueryTextListener  {

    private val viewModel: UserViewModel by viewModels()
    private val userAdapter: UserAdapter by lazy {
        UserAdapter()
    }

    private val userList: ArrayList<User> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupAdapter()
        setupObserve()

        binding.fbAdd.setOnClickListener {
            startActivity(Intent(this, UserAddActivity::class.java))
        }

    }

    private fun getUsers() {
        viewModel.getUsers()
        viewModel.getCities()
    }

    private fun setupObserve() {
        viewModel.observableGetUsers.observe(this){response->
            when(response){
                is ResponseState.Loading->{
                    loadingData()
                }
                is ResponseState.Success->{
                    setData(response.data)
                }
                is ResponseState.Error->{
                    dataIsEmpty()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_filter ->{
                showFilterBottomSheet()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showFilterBottomSheet() {
        val dialog: BottomSheetDialogFragment = FilterBottomSheetFragment()
        val fm = supportFragmentManager
        dialog.show(
            fm,
            dialog.tag
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_menu, menu)

        val search = menu?.findItem(R.id.menu_search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setData(data: List<User>?) {
        data.let {
            if(it != null && it.isNotEmpty()) {
                dataIsExist()
                userList.clear()
                userList.addAll(it)
                userAdapter.setUserList(it)
            }else{
                dataIsEmpty()
            }
        }
    }

    private fun setupAdapter() {
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = userAdapter
    }

    private fun searchThroughDatabase(query: String) {
        if(query != ""){
            val userSearch = userList.filter {
                it.name.lowercase().contains(query.lowercase())
            }
            userAdapter.setUserList(userSearch)

            if(userSearch.isEmpty()) dataIsEmpty()
            else dataIsExist()

        }else{
            dataIsExist()
            userAdapter.setUserList(userList)
        }
    }

    override fun onResume() {
        super.onResume()
        getUsers()
    }

    override fun getViewBinding(): ActivityUserListBinding = ActivityUserListBinding.inflate(layoutInflater)

    override fun onQueryTextSubmit(query: String?): Boolean {
        searchThroughDatabase(query ?: "")
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        searchThroughDatabase(query ?: "")
        return true
    }



    override fun loadingData() {
        binding.progressBar.visibility = View.VISIBLE
        binding.tvEmpty.visibility = View.GONE
    }

    override fun dataIsEmpty() {
        binding.progressBar.visibility = View.GONE
        binding.tvEmpty.visibility = View.VISIBLE
    }

    override fun dataIsExist() {
        binding.progressBar.visibility = View.GONE
        binding.tvEmpty.visibility = View.GONE
    }
}