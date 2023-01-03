package be.heh.heh_inventory.ui.devicesList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import be.heh.heh_inventory.DatabaseHelper
import be.heh.heh_inventory.DevicesAdapter
import be.heh.heh_inventory.databinding.FragmentDevicesListBinding

class DevicesListFragment : Fragment() {

    private var _binding: FragmentDevicesListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val devicesListViewModel =
            ViewModelProvider(this).get(DevicesListViewModel::class.java)

        _binding = FragmentDevicesListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val devicesList = binding.devicesRecyclerView
        devicesListViewModel.devices.observe(viewLifecycleOwner) {
            devicesList.layoutManager = LinearLayoutManager(this.context)
            devicesList.adapter = DevicesAdapter(DatabaseHelper.db.storedItemDao().getAll())
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}