package by.dro.pets.util

import android.content.Context
import android.widget.Toast
import by.dro.pets.data.entities.DogModel
import by.dro.pets.data.entities.SectionItemModel
import by.dro.pets.data.entities.SectionModel
import com.google.firebase.database.FirebaseDatabase

fun Context.addNewRecord() {
    val database = FirebaseDatabase.getInstance().reference
        .child("pets").child("dogs").child("v2")
    val key = database.push().key!!
    val pet = DogModel(
        uid = key,
        sections = listOf(
            SectionModel(
                title = "Общая информация",
                items = listOf(
                    SectionItemModel("Международное наименование", "TEXT"),
                    SectionItemModel("Использование", "TEXT"),
                    SectionItemModel("Страна происхождения", "TEXT"),
                    SectionItemModel("Окрас", "TEXT"),
                    SectionItemModel("Шерсть", "TEXT"),
                    SectionItemModel("Вес", "TEXT"),
                    SectionItemModel("Размер", "TEXT"),
                    SectionItemModel("Продолжительность жизни", "TEXT"),
                )
            ),
            SectionModel(
                title = "Характер",
                items = listOf(
                    SectionItemModel("Общее", "TEXT"),
                    SectionItemModel("Отношение к детям", "TEXT"),
                    SectionItemModel("Отношение к посторонним", "TEXT"),
                    SectionItemModel("Дрессировка", "TEXT"),
                )
            ),
            SectionModel(
                title = "Содержание и уход",
                items = listOf(
                    SectionItemModel("Общее", "TEXT"),
                    SectionItemModel("Выгул", "TEXT"),
                    SectionItemModel("Игры", "TEXT"),
                    SectionItemModel("Купание и шерсть", "TEXT"),
                    SectionItemModel("Когти и зубы", "TEXT"),
                    SectionItemModel("Глаза и уши", "TEXT"),
                )
            ),
            SectionModel(
                title = "Рацион",
                items = listOf(
                    SectionItemModel("Чем кормить", "TEXT"),
                    SectionItemModel("Что нельзя", "TEXT"),
                )
            ),
            SectionModel(
                title = "Здоровье",
                items = listOf(
                    SectionItemModel("Частые болезни", "TEXT"),
                    SectionItemModel("Лекарства", "TEXT"),
                    SectionItemModel("Вакцинация", "TEXT"),
                    SectionItemModel("Чувствительность к погоде", "TEXT"),
                )
            ),
        )
    )
    database.child(key).setValue(pet)
    Toast.makeText(this, "add $key", Toast.LENGTH_LONG).show()
}
