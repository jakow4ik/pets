package by.dro.pets.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PetsViewModel {
    companion object{
       val data: LiveData<MutableMap<String, Pet>> = MutableLiveData()
    }
}