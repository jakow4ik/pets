package by.dro.pets.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.dro.pets.R
import by.dro.pets.databinding.ActivityMainBinding
import by.dro.pets.presentation.pets_list.PetsListFragment
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupSystemUi()
        savedInstanceState ?: initBottomNavigation()
    }

    private fun initBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment
        binding.bottomNavigation.setupWithNavController(navHostFragment.navController)
    }

    private fun setupSystemUi() {
        window.apply {
            WindowCompat.setDecorFitsSystemWindows(this, false)
            val insetsControllerCompat = WindowInsetsControllerCompat(this, decorView)
            insetsControllerCompat.isAppearanceLightNavigationBars = true
            insetsControllerCompat.isAppearanceLightStatusBars = true
            navigationBarColor = getColor(R.color.primarily_background)
            statusBarColor = getColor(R.color.primarily_background)
        }
    }

    private fun initDynamicLinks() {
        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { link ->
                Log.d("MainActivityKKK", "${link?.link}")
//                findNavController(R.id.fragment).handleDeepLink(Intent().apply {
//                    data = link?.link
//
//                })
                val arguments = Bundle()
                arguments.putString(PetsListFragment.ARG_UID, link?.link?.getQueryParameter("uid"))
                if (link?.link != null)
                    findNavController(R.id.main_fragment_container).navigate(
                        R.id.action_petsListFragment_to_detailFragment,
                        arguments
                    )
            }
    }
}
