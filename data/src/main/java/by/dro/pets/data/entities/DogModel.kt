package by.dro.pets.data.entities

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
@Keep
data class DogModel(
    var uid: String? = "",
    var name: String? = "",
    var title_img: String? = "",
    var description: String? = "",
    var popularity_rating: Double? = 0.0,
    var fci_number: Int? = 0,
    var comments: String? = "",
    var sections: List<SectionModel>? = emptyList(),
) : Parcelable

@IgnoreExtraProperties
@Parcelize
@Keep
data class SectionModel(
    var title: String? = "",
    var items: List<SectionItemModel>? = emptyList(),
) : Parcelable

@IgnoreExtraProperties
@Parcelize
@Keep
data class SectionItemModel(
    var title: String? = "",
    var body: String? = "",
) : Parcelable
