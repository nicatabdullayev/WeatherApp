package com.example.weatherappfinalproject

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.media.audiofx.DynamicsProcessing
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import dagger.android.DispatchingAndroidInjector
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject

class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {
    private var mAppBarConfiguration: AppBarConfiguration? = null
    var drawer: DrawerLayout? = null

    @Inject
    var viewModelFactory: Channel.Factory? = null

    @Inject
    var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>? = null

    @Inject
    var navigationController: NavController? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getSupportActionBar()?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    R.color.transparent
                )
            )
        )
        drawer = findViewById<DrawerLayout>(R.id.drawerLayout)
        val navigationView: NavigationView = findViewById<NavigationView>(R.id.navView)
        navigationView.setNavigationItemSelectedListener(this)
        mAppBarConfiguration = AppBarConfiguration.Builder(
            R.id.navigation_today,
            R.id.navigation_weekly,
            R.id.navigation_share,
            R.id.settings,
            R.id.about
        )
            .setOpenableLayout(drawer)
            .build()
        val navController: NavController = findNavController(this, R.id.nav_host_fragment)
        setupActionBarWithNavController(this, navController, mAppBarConfiguration!!)
        setupWithNavController(navView, navController)
        navController.addOnDestinationChangedListener(NavController.OnDestinationChangedListener { controller: NavController?, destination: NavDestination, arguments: Bundle? ->
            if (destination.id == R.id.navigation_share) {
                val temp: String = SharedPreferences.getInstance(this).getTemp()
                val desc: String = SharedPreferences.getInstance(this).getDesc()
                val sendIntent = Intent()
                sendIntent.setAction(Intent.ACTION_SEND)
                sendIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Today's weather is $desc with temperature: $temp"
                )
                sendIntent.setType("text/plain")
                if (sendIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(sendIntent)
                }
            } else if (destination.id == R.id.navigation_weekly) {
            } else if (destination.id == R.id.navigation_today) {
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController: NavController = findNavController(this, R.id.nav_host_fragment)
        return (mAppBarConfiguration?.let { navigateUp(navController, it) } == true
                || super.onSupportNavigateUp())
    }

    fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment>? {
        return dispatchingAndroidInjector
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.settings -> {
                navigationController.navigateToSettings()
            }
        }
        //close navigation drawer
        drawer?.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = getMenuInflater()
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search ->                 // User chose the "Search" item, show the app settings UI...
                true

            else ->                 // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                super.onOptionsItemSelected(item)
        }
    }
}