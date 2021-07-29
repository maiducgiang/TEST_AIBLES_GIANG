
package com.example.inventory

import android.app.Application
import com.example.inventory.data.UserRoomDatabase


class InventoryApplication : Application() {
    val database: UserRoomDatabase by lazy { UserRoomDatabase.getDatabase(this) }
}
