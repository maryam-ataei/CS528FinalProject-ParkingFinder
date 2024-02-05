package com.example.firebasep

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasep.DataModels.UserModel

class UserAdapter(private val userList: List<UserModel>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvUserName: TextView = view.findViewById(R.id.tvUserName)
        val tvUserEmail: TextView = view.findViewById(R.id.tvUserEmail)
        val tvUserPassword: TextView = view.findViewById(R.id.tvUserPassword)
        val tvUserCarModel: TextView = view.findViewById(R.id.tvUserCarModel)
        val tvUserCarColor: TextView = view.findViewById(R.id.tvUserCarColor)
        val tvUserCarPlate: TextView = view.findViewById(R.id.tvUserCarPlate)
        val tvUserCarYear: TextView = view.findViewById(R.id.tvUserCarYear)
        val tvUserNickname: TextView = view.findViewById(R.id.tvUserNickname)
        val tvUserPhone: TextView = view.findViewById(R.id.tvUserPhone)
        val tvUserHandicapped: TextView = view.findViewById(R.id.tvUserHandicapped)
        val tvUserEV: TextView = view.findViewById(R.id.tvUserEV)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
       // holder.tvUserId.text = currentUser.uId
        holder.tvUserName.text = currentUser.uName
        holder.tvUserEmail.text = currentUser.uEmail
        holder.tvUserPassword.text = currentUser.uPass
        holder.tvUserCarModel.text = currentUser.carColor
        holder.tvUserCarYear.text = currentUser.carYear
        holder.tvUserCarPlate.text = currentUser.carPlate
        holder.tvUserNickname.text = currentUser.nname
        holder.tvUserPhone.text = currentUser.Phone
        holder.tvUserHandicapped.text = if (currentUser.handicapped) "Yes" else "No"
        holder.tvUserEV.text = if (currentUser.ev) "Enabled" else "Disabled"


        Log.e("reach to adapter ", "Addapter ")
        Log.e("UserAdapter", "User Nickname: ${currentUser.nname}, UserName: ${currentUser.uName}")
    }

    override fun getItemCount() = userList.size
}
