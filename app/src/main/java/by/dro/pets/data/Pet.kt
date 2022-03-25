package by.dro.pets.data

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class Pet(
    var uid: String? = "",
    var name: String? = "",
    var nameInternational: String? = "",
    var titleImg: String? = "",
    var description: String? = "",
    var popularityRating: Int? = 0,
    var trainingRating: Int? = 0,
    var sizeRating: Int? = 0,
    var mindRating: Int? = 0,
    var protectionRating: Int? = 0,
    var childrenRating: Int? = 0,
    var dexterityRating: Int? = 0,
    var moltRating: Int? = 0,
    var standartNumber: String? = "",
    var country: String? = "",
    var using: String? = "",
    var size: String? = "",
    var weight: String? = "",
    var wool: String? = "",
    var color: String? = "",
    var character: String? = "",
    var care: String? = "",
    var lifespan: String? = "",
    var problems: String? = ""
):Parcelable


@IgnoreExtraProperties
@Parcelize
data class ListPets(
    var name: String? = "",
    var map: MutableMap<String, Pet> = HashMap()
):Parcelable
