package be.heh.heh_inventory


import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import be.heh.heh_inventory.data.DatabasePermission
import be.heh.heh_inventory.databinding.ActivityHomeBinding
import com.google.android.material.navigation.NavigationView


class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding
    lateinit var navController : NavController

    // Intent extras
    lateinit var userMail : String
    lateinit var permission : DatabasePermission

    // Data shared between fragments
    var lastCheckedRef : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Binding
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Action bar
        setSupportActionBar(binding.appBarHome.toolbar)

        // Floating button
        binding.appBarHome.fab.setOnClickListener {
            navController.navigate(R.id.nav_home)
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        navController = findNavController(R.id.nav_host_fragment_content_home)

        // Menu main destinations
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_devices_list, R.id.nav_device_add, R.id.nav_users_list
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Retrieve permissions and mail
        this.permission  = intent.getSerializableExtra("user_permission") as DatabasePermission
        this.userMail = intent.getStringExtra("user_email").toString()

        // Update menu
        hideUnauthorizedMenus(navView, this.permission)
    }

    private fun updateUserMail(userMail : String?) {
        // Change mail in navigation drawer
        val userEmailText = findViewById<TextView>(R.id.nav_user_email)
        userEmailText.text = userMail
    }

    private fun hideUnauthorizedMenus(navView : NavigationView, permission : DatabasePermission) {
        if (permission == DatabasePermission.READ){
            val s = navView.menu.findItem(R.id.nav_device_add)
            s.isVisible = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        updateUserMail(userMail)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1 -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        applicationContext,
                        "You need camera permission",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}