package uz.ilhomjon.newsapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AllCategory(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var category_name: String,
    var isSelected: Boolean = false
)
