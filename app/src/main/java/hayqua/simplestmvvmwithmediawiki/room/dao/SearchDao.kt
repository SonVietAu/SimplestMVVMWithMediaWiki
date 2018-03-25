package hayqua.simplestmvvmwithmediawiki.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import hayqua.simplestmvvmwithmediawiki.room.entities.Search

/**
 * Created by Son Au on 24/03/2018.
 */
@Dao
interface SearchDao {

    @Query("SELECT * FROM search_keywords")
    fun getAll(): List<Search>

    @Insert
    fun insert(search: Search): Long
}