package be.heh.heh_inventory.ui.home

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import be.heh.heh_inventory.database.DatabaseHelper
import be.heh.heh_inventory.HomeActivity
import be.heh.heh_inventory.R
import be.heh.heh_inventory.data.DatabasePermission
import be.heh.heh_inventory.data.DeviceAction
import be.heh.heh_inventory.databinding.FragmentHomeBinding
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView


class HomeFragment : Fragment(), ZXingScannerView.ResultHandler {

    private var _binding: FragmentHomeBinding? = null

    // QR Code
    private var scannerView : ZXingScannerView? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // QR Code
        setPermission()
        this.scannerView = binding.qrScanCameraPreview
        scannerView!!.startCamera()
        return root
    }

    override fun handleResult(result: Result?) {
        // Stop preview and camera
        scannerView!!.stopCameraPreview()
        scannerView!!.stopCamera()

        // Find device in database (return null if don't exists)
        val device = DatabaseHelper.db.deviceDao().getByRef(result.toString())

        // Retrieve the parent navController
        val activity = (activity as HomeActivity)

        // Prepare alert dialog box
        val alertDialogBuilder = AlertDialog.Builder(this.requireContext())

        if ((this.activity as HomeActivity).permission as DatabasePermission == DatabasePermission.READ_WRITE){
            if (device == null){
                // ACTION : Add unregistered device
                with(alertDialogBuilder) {
                    setTitle(R.string.popup_device_add_title)
                    setMessage(R.string.popup_device_add_message)
                    setPositiveButton(R.string.popup_confirm){dialogInterface, which ->
                        activity.lastCheckedRef = result.toString()
                        activity.navController.navigate(R.id.nav_device_add)
                    }
                    setNegativeButton(R.string.popup_cancel){dialogInterface, which ->
                        restartCamera()
                    }
                }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }
            else{
                // ACTION : Edit the device status
                // Change message content according the the action (give or take back)
                val message =
                    if(device.nextAction == DeviceAction.GIVE) getString(R.string.popup_device_change_status_message, device.ref, getString(R.string.device_action_give).lowercase())
                    else getString(R.string.popup_device_change_status_message, device.ref, getString(R.string.device_action_take_back).lowercase())
                with(alertDialogBuilder) {
                    setTitle(R.string.popup_device_change_status_title)
                    setMessage(message)
                    setPositiveButton(R.string.popup_confirm){dialogInterface, which ->
                        // Update status in Database
                        device.nextAction = if(device.nextAction == DeviceAction.GIVE) DeviceAction.TAKE_BACK else DeviceAction.GIVE
                        DatabaseHelper.db.deviceDao().update(device)
                        Toast.makeText(context, R.string.toast_confirm_status_device, Toast.LENGTH_LONG).show()
                        restartCamera()
                    }
                    setNegativeButton(R.string.popup_cancel){dialogInterface, which ->
                        restartCamera()
                    }
                }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }
        }
        else{
            // See the device
            val title = getString(R.string.popup_device_show_title, device.ref)
            val message =
                if (device.nextAction == DeviceAction.GIVE) getString(R.string.popup_device_show_message, getString(R.string.device_action_take_back).lowercase())
                else getString(R.string.popup_device_show_message, getString(R.string.device_action_give).lowercase())
            with(alertDialogBuilder) {
                setTitle(title)
                setMessage(message)
                setPositiveButton("Voir les détails"){dialogInterface, which ->
                    activity.lastCheckedRef = result.toString()
                    activity.navController.navigate(R.id.nav_device_show)
                }
                setNeutralButton(R.string.popup_neutral){dialogInterface, which ->
                    restartCamera()
                }
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }

    fun restartCamera(){
        scannerView?.setResultHandler(this)
        scannerView?.startCamera(0)
    }

    override fun onResume() {
        super.onResume()
        restartCamera()
    }

    override fun onStop() {
        super.onStop()
        scannerView?.stopCamera()
    }

    private fun setPermission(){
        val permission = this.context?.let { ContextCompat.checkSelfPermission(it, android.Manifest.permission.CAMERA) }
        if(permission != PackageManager.PERMISSION_GRANTED){
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this.activity as Activity, arrayOf(android.Manifest.permission.CAMERA), 1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}