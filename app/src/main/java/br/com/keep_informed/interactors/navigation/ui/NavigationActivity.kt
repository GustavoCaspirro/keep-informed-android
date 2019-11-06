package br.com.keep_informed.interactors.navigation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import br.com.keep_informed.R
import br.com.keep_informed.databinding.ActivityNavigationBinding
import br.com.keep_informed.interactors.navigation.events.SearchEvent
import com.miguelcatalan.materialsearchview.MaterialSearchView
import org.greenrobot.eventbus.EventBus

class NavigationActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    lateinit var binding : ActivityNavigationBinding

    lateinit var navController: NavController

    lateinit var bottomAppBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_navigation)
        binding.lifecycleOwner = this

        navController = Navigation.findNavController(this,R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener(this)

        setupActionBar()
        setupSearchView()
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        bottomAppBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_map, R.id.navigation_bookmark
            )
        )
        setupActionBarWithNavController(navController, bottomAppBarConfiguration)
        binding.navView.setupWithNavController(navController)
    }

    private fun setupActionBar() {
        binding.mainToolbar.title = ""
        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
    }

    private fun setupSearchView(){
        binding.searchView.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener{
            override fun onSearchViewClosed() {
                if (navController.currentDestination?.id == R.id.navigation_search){
                    navController.navigateUp()
                }
            }
            override fun onSearchViewShown() {
                navController.navigate(R.id.navigation_search)
            }

        })

        binding.searchView.setOnQueryTextListener(object: MaterialSearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    EventBus.getDefault().post(SearchEvent(it))
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                binding.mainToolbar.title = ""
            }
            else ->{}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {

        when(destination.id){
            R.id.navigation_search -> {}
            else -> {
                if (binding.searchView.isSearchOpen){
                    binding.searchView.closeSearch()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val item = menu.findItem(R.id.action_search)
        binding.searchView.setMenuItem(item)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
