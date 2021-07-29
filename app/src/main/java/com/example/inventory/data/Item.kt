
package com.example.inventory.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "data")
data class User(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val id: Int = 0,

    @ColumnInfo(name = "login")
    @SerializedName("login")
    val userLogin: String,

    @SerializedName("type")
    @ColumnInfo(name = "type")
    val userType: String,

    @ColumnInfo(name = "url")
    @SerializedName("url")
    val userUrl: String,
)
/**
 * Returns the passed in price in currency format.
 */