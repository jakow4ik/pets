package by.dro.pets.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import by.dro.pets.R
import by.dro.pets.presentation.pets_list.PetsListFragment
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
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
