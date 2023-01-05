package be.heh.heh_inventory


import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import be.heh.heh_inventory.data.DatabasePermission
import be.heh.heh_inventory.databinding.ActivityHomeBinding
import be.heh.heh_inventory.ui.home.HomeFragment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar


class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding
    lateinit var navController : NavController

    // Intent extras
    lateinit var userMail : String
    lateinit var permission : DatabasePermission



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHome.toolbar)

        // Floating button
        binding.appBarHome.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        navController = findNavController(R.id.nav_host_fragment_content_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_devices_list, R.id.nav_slideshow
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
                else if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    val fragment = supportFragmentManager.findFragmentById(R.id.fragment_home)
                    print(fragment)
                    //fragment.confirmAuthorization()
                }
            }
        }
        print(requestCode)
    }
}