package com.raydev.mobile_test.feature.user_add

import android.app.ProgressDialog
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.raydev.mobile_test.R
import com.raydev.mobile_test.base.BaseActivity
import com.raydev.mobile_test.data.model.City
import com.raydev.mobile_test.data.request.UserRequest
import com.raydev.mobile_test.databinding.ActivityUserAddBinding
import com.raydev.mobile_test.util.CheckConnection
import com.raydev.mobile_test.util.ResponseState
import com.raydev.mobile_test.util.ext.toast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserAddActivity : BaseActivity<ActivityUserAddBinding>() {

    private val viewModel: UserAddViewModel by viewModels()
    lateinit var progressDialog: ProgressDialog

    @Inject
    lateinit var checkConnection: CheckConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = getString(R.string.title_add_user)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage(getString(R.string.loading_data))

        setupObserve()
        getCities()

        binding.btnAdd.setOnClickListener {
            if(checkConnection.isOnline()) addUser()
            else toast(getString(R.string.network_offline))

        }

    }

    private fun addUser() {
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val address = binding.etAddress.text.toString()
        val city = binding.spCity.selectedItem.toString()
        val user = UserRequest(
            name,
            email,
            address,
            city,
        )
        viewModel.addUser(user)
    }

    private fun getCities() {
        viewModel.getCities()
    }

    private fun setupObserve() {
        viewModel.observableGetCities.observe(this){response->
            when(response){
                is ResponseState.Loading->{
                }
                is ResponseState.Success->{
                    setDataSpinner(response.data)
                }
                is ResponseState.Error->{
                    progressDialog.dismiss()
                }
            }
        }
        viewModel.observableAddUser.observe(this){response->
            when(response){
                is ResponseState.Loading->{
                    loadingData()
                }
                is ResponseState.Success->{
                    dataIsExist()
                    toast(response.data)
                    finish()
                }
                is ResponseState.Error->{
                    dataIsEmpty()
                    toast(response.errorMessage)
                }
            }
        }
    }

    private fun setDataSpinner(data: List<City>?) {
        val citiesNameList = mutableListOf<String>()
        if (data != null) {
            for (i in data.indices) {
                citiesNameList.add(data[i].name)
            }
        }
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, R.layout.item_spinner, R.id.textView, citiesNameList)
        binding.spCity.adapter = arrayAdapter
    }

    override fun getViewBinding(): ActivityUserAddBinding = ActivityUserAddBinding.inflate(layoutInflater)

    override fun loadingData() {
        progressDialog.show()
    }

    override fun dataIsEmpty() {
        progressDialog.dismiss()
    }

    override fun dataIsExist() {
        progressDialog.dismiss()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}