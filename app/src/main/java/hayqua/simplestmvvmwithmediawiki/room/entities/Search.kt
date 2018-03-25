package hayqua.simplestmvvmwithmediawiki.room.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Son Au on 24/03/2018.
 */
@Entity(tableName = "search_keywords")
data class Search(

        @ColumnInfo( name = "keywords")
        val keywords: String
) {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}