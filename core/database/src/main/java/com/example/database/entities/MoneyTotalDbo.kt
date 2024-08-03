package com.example.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.database.entities.MoneyTotalDbo.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class MoneyTotalDbo(
    @PrimaryKey
    val id: Long,
    val value: Double
) {
    companion object {

        fun create(value: Double): MoneyTotalDbo = MoneyTotalDbo(
            id = 1,
            value = value
        )
        const val TABLE_NAME = "money_total_dbo"
    }
}
