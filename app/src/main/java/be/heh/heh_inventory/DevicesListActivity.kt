package be.heh.heh_inventory

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.heh.heh_inventory.database.entity.Device

class DevicesListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_objects_list)

        // Getting list of devices and map it to the recyclerView
        val dataset: MutableList<Device> = DatabaseHelper.db.storedItemDao().getAll()
        val adapter = DevicesAdapter(dataset)
        val recyclerView = findViewById<RecyclerView>(R.id.devices_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Clicks listeners
        adapter.onItemClick = { device ->
            Toast.makeText(this, device.uid.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}