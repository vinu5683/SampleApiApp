package com.avenger.nobrokerassignment.localdatabase

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = arrayOf(SampleEntity::class), version = 1)
abstract class SampleDatabase() : RoomDatabase() {

    abstract fun getSampleDAO(): SampleDAO

    companion object {
        val TAG = "SampleDatabase"
        private var INSTANCE: SampleDatabase? = null

        //SingleTon Class Method.
        fun getInstance(context: Context): SampleDatabase {
            if (INSTANCE == null) {
                val builder = Room.databaseBuilder(
                    context.applicationContext,
                    SampleDatabase::class.java,
                    "sampledb"
                )
                builder.fallbackToDestructiveMigration()
                return builder.build()
            }
            return INSTANCE!!
        }
    }

}