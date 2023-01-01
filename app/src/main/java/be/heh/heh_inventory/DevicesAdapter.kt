package be.heh.heh_inventory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.heh.heh_inventory.database.entity.Device

class DevicesAdapter constructor(dataset : List<Device>) : RecyclerView.Adapter<DevicesAdapter.ViewHolder>(){
    val devicesList = dataset
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val deviceFamilyImage = itemView.findViewById<ImageView>(R.id.device_family_image)
        val deviceBrand = itemView.findViewById<TextView>(R.id.device_brand)
        val deviceName = itemView.findViewById<TextView>(R.id.device_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_device, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val device = devicesList[position]
        holder.deviceFamilyImage.setImageResource(R.drawable.logo_heh)
        holder.deviceBrand.text = device.brand
        holder.deviceName.text = device.name
    }

    override fun getItemCount(): Int {
        if (devicesList != null) return devicesList.size
        return 0
    }
}