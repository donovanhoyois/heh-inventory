package be.heh.heh_inventory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import be.heh.heh_inventory.database.entity.Device

class DevicesAdapter constructor(dataset : List<Device>, navController: NavController) : RecyclerView.Adapter<DevicesAdapter.ViewHolder>(){
    val devicesList = dataset
    val navController = navController
    var onItemClick : ((Device) -> Unit)? = null
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val deviceFamilyImage = itemView.findViewById<ImageView>(R.id.device_family_image)
        val deviceFullName = itemView.findViewById<TextView>(R.id.device_full_name)
        val deviceReference = itemView.findViewById<TextView>(R.id.device_reference)

        init{
            itemView.setOnClickListener{
                onItemClick?.invoke(devicesList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_device, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val device = devicesList[position]
        holder.deviceFamilyImage.setImageResource(R.drawable.logo_heh)
        holder.deviceFullName.text = device.brand+" "+device.name
        holder.deviceReference.text = device.ref
        holder.itemView.setOnClickListener {
            // TODO : Onclick item
            Toast.makeText(holder.itemView.rootView.context, devicesList[position].ref, Toast.LENGTH_SHORT).show()
            navController.navigate(R.id.nav_home)
        }
    }

    override fun getItemCount(): Int {
        if (devicesList != null) return devicesList.size
        return 0
    }
}