package be.heh.heh_inventory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.heh.heh_inventory.database.entity.Device

class DevicesListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_objects_list)

        // Getting list of devices
        val dataset: MutableList<Device> = ArrayList()
        dataset.add(Device(0, "Phone", "Samsung", "Galaxy S8"))
        dataset.add(Device(1, "Phone", "Samsung", "Galaxy A5"))

        val adapter = DevicesAdapter(dataset)

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.devices_recycler_view)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
    }
}