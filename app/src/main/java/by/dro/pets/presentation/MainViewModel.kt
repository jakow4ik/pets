package by.dro.pets.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import by.dro.pets.domain.usecases.GetDogsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val getDogsUseCase: GetDogsUseCase,
) : ViewModel() {

    init {
        Log.d("kkk", "init view model")
    }
}