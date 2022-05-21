package com.raydev.mobile_test.feature.user_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raydev.mobile_test.data.model.User
import com.raydev.mobile_test.databinding.ItemUserBinding

class UserAdapter: RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private val userList: ArrayList<User> = ArrayList()

    fun setUserList(userList: List<User>){
        this.userList.clear()
        this.userList.addAll(userList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = userList.size

    inner class ViewHolder(
        private val itemBinding: ItemUserBinding
    ): RecyclerView.ViewHolder(itemBinding.root){
        fun bind(user: User){
            itemBinding.tvName.text = user.name
            itemBinding.tvCity.text = user.city
        }
    }

}