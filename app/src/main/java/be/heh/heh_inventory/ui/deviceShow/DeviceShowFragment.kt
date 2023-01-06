package be.heh.heh_inventory.ui.deviceShow

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import be.heh.heh_inventory.DatabaseHelper
import be.heh.heh_inventory.HomeActivity
import be.heh.heh_inventory.R
import be.heh.heh_inventory.data.DeviceAction
import be.heh.heh_inventory.data.DeviceFamily
import be.heh.heh_inventory.database.entity.Device
import be.heh.heh_inventory.databinding.FragmentDeviceShowBinding

class DeviceShowFragment : Fragment() {
    private var _binding: FragmentDeviceShowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DeviceShowViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // ViewModel and binding
        viewModel = ViewModelProvider(this).get(DeviceShowViewModel::class.java)
        _binding = FragmentDeviceShowBinding.inflate(inflater, container, false)

        // Activity
        val activity = activity as HomeActivity

        // Retrieve device
        val device : Device? =
            activity.lastCheckedRef?.let {
                DatabaseHelper.db.deviceDao().getByRef(it)
            }
        activity.lastCheckedRef = null

        // Redirect if device is null
        if (device == null) (activity as HomeActivity).navController.navigate(R.id.nav_home)

        // Bind to UI
        else{
            binding.family.setImageResource(getFamilyImage(device.family))
            binding.reference.text = device.ref
            binding.status.text = getStatus(device.nextAction)
            binding.brand.text = device.brand
            binding.name.text = device.name
            binding.website.text = device.website
        }

        // Click listeners
        binding.deleteDeviceButton.setOnClickListener(){
            val alertDialogBuilder = AlertDialog.Builder(this.requireContext())
            with(alertDialogBuilder) {
                setTitle(R.string.popup_device_delete_title)
                setMessage(getString(R.string.popup_device_delete_message, device?.ref))
                setPositiveButton(R.string.popup_confirm){dialogInterface, which ->
                    Toast.makeText(context, getString(R.string.toast_confirm_delete_device, device?.ref), Toast.LENGTH_LONG).show()
                    DatabaseHelper.db.deviceDao().delete(device!!)
                    activity.navController.navigate(R.id.nav_devices_list)
                }
                setNegativeButton(R.string.popup_cancel){dialogInterface, which ->
                    activity.lastCheckedRef = device?.ref
                    activity.navController.navigate(R.id.nav_device_show)
                }
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

        return binding.root
    }

    private fun getFamilyImage(family: DeviceFamily): Int {
        // Return the correct drawable depending of the device family
        if (family == DeviceFamily.PHONE) return R.drawable.icon_smartphone
        return R.drawable.icon_tablet
    }

    private fun getStatus(nextAction: DeviceAction): CharSequence? {
        // Return the status depending of nextAction value
        if (nextAction == DeviceAction.GIVE) return resources.getString(R.string.device_action_take_back)
        return resources.getString(R.string.device_action_give)
    }

}