package com.avenger.nobrokerassignment.localdatabase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SampleDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addEntry(entity: SampleEntity)

    @Query("select * from sample_table")
    fun getMySampleList(): LiveData<List<SampleEntity>>

//    Thought to do but time constraint problem
//    @Query("select * from sample_table Limit :limit Offset :offset")
//    fun getSampleListByIndex(limit: Int, offset: Int): LiveData<ArrayList<SampleEntity>>

    @Delete
    fun removeEntry(entity: SampleEntity)

    @Update
    fun updateEntity(entity: SampleEntity)

    @Query("DELETE FROM sample_table")
    fun removeAll()

    @Query("SELECT * FROM SAMPLE_TABLE st WHERE st.title LIKE(:trim) OR st.subTitle LIKE(:trim)")
    fun searchResponse(trim: String): LiveData<List<SampleEntity>>?
}