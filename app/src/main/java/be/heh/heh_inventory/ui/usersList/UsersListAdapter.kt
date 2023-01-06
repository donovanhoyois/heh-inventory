package be.heh.heh_inventory.ui.usersList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import be.heh.heh_inventory.HomeActivity
import be.heh.heh_inventory.R
import be.heh.heh_inventory.data.DatabasePermission
import be.heh.heh_inventory.database.user.User

class UsersListAdapter(dataset: List<User>, activity: FragmentActivity?) : RecyclerView.Adapter<UsersListAdapter.ViewHolder>() {
    val usersList = dataset
    private val activity = activity as HomeActivity
    var onItemClick : ((User) -> Unit)? = null

    // ViewHolder
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userImage: ImageView = itemView.findViewById(R.id.user_image)
        val userMail: TextView = itemView.findViewById(R.id.user_mail)
        val userPermission: TextView = itemView.findViewById(R.id.user_permission)

        init{
            itemView.setOnClickListener{
                onItemClick?.invoke(usersList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = usersList[position]

        // Insert user values
        holder.userImage.setImageResource(R.drawable.icon_user)
        holder.userMail.text = user.email
            // Permission
        holder.userPermission.text =
            if(user.permission == DatabasePermission.READ) activity.resources.getString(R.string.database_permission_read)
            else activity.resources.getString(R.string.database_permission_read_write)

        // Click listener
        holder.itemView.setOnClickListener{
            print("ok")
        }
    }

    override fun getItemCount(): Int {
        return usersList.size
    }
}
