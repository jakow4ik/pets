package by.dro.pets.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import by.dro.pets.R
import by.dro.pets.data.PetsViewModel
import by.dro.pets.domain.entities.Dog
import by.dro.pets.presentation.pets_list.PetsListFragment
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModels()

    private val liveData: MutableLiveData<Map<String, Dog>> =
        PetsViewModel.data as MutableLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getDogsUseCase.execute(Unit).collect {
                    Log.d("kkk", "dddd")
                    liveData.value = it
                }
            }
        }
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
                    findNavController(R.id.fragment).navigate(
                        R.id.action_petsListFragment_to_detailFragment,
                        arguments
                    )

            }
    }
}
