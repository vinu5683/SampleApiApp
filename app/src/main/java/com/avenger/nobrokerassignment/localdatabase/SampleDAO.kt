package com.avenger.nobrokerassignment.localdatabase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SampleDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addEntry(entity: SampleEntity)

    @Query("select * from sample_table")
    fun getMySampleList(): LiveData<List<SampleEntity>>

    @Query("select * from sample_table Limit :limit Offset :offset")
    fun getSampleListByIndex(limit: Int, offset: Int): LiveData<List<SampleEntity>>

    @Delete
    fun removeEntry(entity: SampleEntity)

    @Update
    fun updateEntity(entity: SampleEntity)
}