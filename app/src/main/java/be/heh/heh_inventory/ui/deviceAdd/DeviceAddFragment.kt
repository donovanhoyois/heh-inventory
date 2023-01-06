package be.heh.heh_inventory.ui.deviceAdd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import be.heh.heh_inventory.database.DatabaseHelper
import be.heh.heh_inventory.HomeActivity
import be.heh.heh_inventory.R
import be.heh.heh_inventory.data.DeviceFamily
import be.heh.heh_inventory.data.ErrorCode
import be.heh.heh_inventory.database.device.Device
import be.heh.heh_inventory.databinding.FragmentDeviceAddBinding

class DeviceAddFragment : Fragment() {
    private var _binding: FragmentDeviceAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Binding
        _binding = FragmentDeviceAddBinding.inflate(inflater, container, false)

        // Activity
        val activity = activity as HomeActivity

        // Spinner values list
        val dropdown = binding.spinnerFamily
        val families = resources.getStringArray(R.array.device_families)
        dropdown.adapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, families) }

        // Insert scanned ref if exists
        if (activity.lastCheckedRef != null){
            binding.inputReference.setText(activity.lastCheckedRef)
            activity.lastCheckedRef = null
        }

        // Click listeners
        binding.addDeviceButton.setOnClickListener{
            when(checkInputs()){
                ErrorCode.REFERENCE_FORMAT_INVALID -> Toast.makeText(context, R.string.error_reference_format, Toast.LENGTH_SHORT).show()
                ErrorCode.REFERENCE_ALREADY_EXISTS -> Toast.makeText(context, R.string.error_reference_already_exists, Toast.LENGTH_SHORT).show()
                ErrorCode.BRAND_FORMAT_INVALID -> Toast.makeText(context, R.string.error_brand_format, Toast.LENGTH_SHORT).show()
                ErrorCode.NAME_FORMAT_INVALID -> Toast.makeText(context, R.string.error_name_format, Toast.LENGTH_SHORT).show()
                ErrorCode.WEBSITE_FORMAT_INVALID -> Toast.makeText(context, R.string.error_website_format, Toast.LENGTH_SHORT).show()
                ErrorCode.OK -> {
                    addToDatabase()
                    Toast.makeText(activity.applicationContext, resources.getString(R.string.toast_confirm_added_device, binding.inputReference.text), Toast.LENGTH_SHORT).show()
                    binding.inputReference.setText("")
                }
            }
        }

        return binding.root
    }

    private fun checkInputs() : Enum<ErrorCode>{
        // Retrieving text
        val reference = binding.inputReference.text
        val brand = binding.inputBrand.text
        val model = binding.inputName.text
        val website = binding.inputWebsite.text

        // Check errors
        if (reference == null) return ErrorCode.REFERENCE_FORMAT_INVALID
        else if (DatabaseHelper.db.deviceDao().getByRef(reference.toString()) != null) return ErrorCode.REFERENCE_ALREADY_EXISTS
        if (brand == null || brand.isEmpty()) return ErrorCode.BRAND_FORMAT_INVALID
        if (model == null || model.isEmpty()) return ErrorCode.NAME_FORMAT_INVALID
        if (website == null || brand.isEmpty() || !website.contains(".")) return ErrorCode.WEBSITE_FORMAT_INVALID
        return ErrorCode.OK
    }

    private fun addToDatabase() {
        // Register the new device created
        val family = if(binding.spinnerFamily.selectedItem.toString() == resources.getStringArray(R.array.device_families)[0]) DeviceFamily.PHONE else DeviceFamily.TABLET
        val newDevice = Device(
            binding.inputReference.text.toString(),
            family,
            binding.inputBrand.text.toString(),
            binding.inputName.text.toString(),
            binding.inputWebsite.text.toString()
        )
        DatabaseHelper.db.deviceDao().insert(newDevice)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}