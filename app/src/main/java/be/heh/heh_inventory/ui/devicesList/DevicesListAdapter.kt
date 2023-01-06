package be.heh.heh_inventory.ui.devicesList

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.heh.heh_inventory.HomeActivity
import be.heh.heh_inventory.R
import be.heh.heh_inventory.data.DeviceAction
import be.heh.heh_inventory.data.DeviceFamily
import be.heh.heh_inventory.database.device.Device

class DevicesListAdapter constructor(dataset : List<Device>, activity: Activity) : RecyclerView.Adapter<DevicesListAdapter.ViewHolder>(){
    val devicesList = dataset
    private val activity = activity as HomeActivity
    var onItemClick : ((Device) -> Unit)? = null

    // ViewHolder
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deviceStatusColor: View = itemView.findViewById(R.id.device_status_color)
        val deviceFamilyImage: ImageView = itemView.findViewById(R.id.device_family_image)
        val deviceFullName: TextView = itemView.findViewById(R.id.device_full_name)
        val deviceReference: TextView = itemView.findViewById(R.id.device_reference)

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

        // Insert device values
            // Family image
        if (devicesList[position].family == DeviceFamily.PHONE){ holder.deviceFamilyImage.setImageResource(
            R.drawable.icon_smartphone
        ) }
        else{ holder.deviceFamilyImage.setImageResource(R.drawable.icon_tablet) }
            // Status
        if (devicesList[position].nextAction == DeviceAction.GIVE){ holder.deviceStatusColor.setBackgroundResource(
            R.color.device_status_available
        ) }
        else { holder.deviceStatusColor.setBackgroundResource(R.color.device_status_unavailable) }
        val deviceFullName = device.brand+" "+device.name
        holder.deviceFullName.text = deviceFullName
        holder.deviceReference.text = device.ref

        // Click listener
        holder.itemView.setOnClickListener {
            activity.lastCheckedRef = devicesList[position].ref
            activity.navController.navigate(R.id.nav_device_show)
        }
    }

    override fun getItemCount(): Int {
        return devicesList.size
    }
}