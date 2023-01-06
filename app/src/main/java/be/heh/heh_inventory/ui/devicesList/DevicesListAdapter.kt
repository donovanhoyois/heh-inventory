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
import be.heh.heh_inventory.database.Device.Device

class DevicesListAdapter constructor(dataset : List<Device>, activity: Activity) : RecyclerView.Adapter<DevicesListAdapter.ViewHolder>(){
    val devicesList = dataset
    val activity = activity as HomeActivity
    var onItemClick : ((Device) -> Unit)? = null

    // ViewHolder
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deviceStatusColor = itemView.findViewById<View>(R.id.device_status_color)
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
        holder.deviceFullName.text = device.brand+" "+device.name
        holder.deviceReference.text = device.ref

        // Click listener
        holder.itemView.setOnClickListener {
            activity.lastCheckedRef = devicesList[position].ref
            activity.navController.navigate(R.id.nav_device_show)
        }
    }

    override fun getItemCount(): Int {
        if (devicesList != null) return devicesList.size
        return 0
    }
}