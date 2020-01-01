package by.dro.pets.data

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@IgnoreExtraProperties
@Parcelize
data class Pet(
    var uid: String? = "",
    var name: String? = ""
):Parcelable


@IgnoreExtraProperties
@Parcelize
data class ListPets(
    var name: String? = "",
    var pets: MutableMap<String, Pet> = HashMap()
):Parcelable
