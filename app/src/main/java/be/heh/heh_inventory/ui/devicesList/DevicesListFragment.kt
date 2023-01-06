package be.heh.heh_inventory.ui.devicesList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import be.heh.heh_inventory.database.DatabaseHelper
import be.heh.heh_inventory.HomeActivity
import be.heh.heh_inventory.databinding.FragmentDevicesListBinding

class DevicesListFragment : Fragment() {
    private var _binding: FragmentDevicesListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DevicesListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // ViewModel and Binding
        viewModel = ViewModelProvider(this).get(DevicesListViewModel::class.java)
        _binding = FragmentDevicesListBinding.inflate(inflater, container, false)

        // Bind to UI
        viewModel.devices.observe(viewLifecycleOwner) {
            binding.devicesRecyclerView.layoutManager = LinearLayoutManager(this.context)
            binding.devicesRecyclerView.adapter = DevicesListAdapter(DatabaseHelper.db.deviceDao().getAll(), (activity as HomeActivity))
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}