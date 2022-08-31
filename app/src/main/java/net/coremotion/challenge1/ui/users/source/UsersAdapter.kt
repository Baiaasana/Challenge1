package net.coremotion.challenge1.ui.users.source

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import net.coremotion.challenge1.databinding.ItemUserBinding
import net.coremotion.challenge1.domain.model.Users
import net.coremotion.challenge1.extensions.setImage

typealias UserItemOnClick = (id: Int) -> Unit

class UsersAdapter() : PagingDataAdapter<Users.Data, UsersAdapter.UserViewHolder>(
    DiffCallback()
) {

    var userItemOnClick: UserItemOnClick? = null
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.onBind()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder(
        ItemUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind() {
            val user = getItem(bindingAdapterPosition)
            binding.profileImage.setImage(user?.avatar)
            binding.tvName.text = user?.firstName.toString().plus(" ").plus(user?.lastName.toString())
            binding.tvEmail.text = user?.email.toString()
            binding.root.setOnClickListener {
                user?.id?.let { it -> userItemOnClick?.invoke(it) }
            }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Users.Data>() {
    override fun areItemsTheSame(oldItem: Users.Data, newItem: Users.Data) =
        oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: Users.Data, newItem: Users.Data): Boolean {
        return oldItem == newItem
    }


}