package by.dro.pets.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import by.dro.pets.domain.entities.Dog

class PetsViewModel {
    companion object{
       val data: LiveData<Map<String, Dog>> = MutableLiveData()
    }
}