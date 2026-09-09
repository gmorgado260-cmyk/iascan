package com.example.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.model.CoinEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {
    @Query("SELECT * FROM coins ORDER BY timestamp DESC")
    fun getAllHistory(): Flow<List<CoinEntity>>

    @Query("SELECT * FROM coins WHERE isInCollection = 1 ORDER BY timestamp DESC")
    fun getCollection(): Flow<List<CoinEntity>>

    @Query("SELECT * FROM coins WHERE isFavorite = 1 ORDER BY timestamp DESC")
    fun getFavorites(): Flow<List<CoinEntity>>

    @Query("SELECT * FROM coins ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentScans(limit: Int): Flow<List<CoinEntity>>

    @Query("SELECT * FROM coins WHERE id = :id LIMIT 1")
    fun getCoinById(id: Long): Flow<CoinEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoin(coin: CoinEntity): Long

    @Update
    suspend fun updateCoin(coin: CoinEntity)

    @Delete
    suspend fun deleteCoin(coin: CoinEntity)

    @Query("DELETE FROM coins WHERE id = :id")
    suspend fun deleteCoinById(id: Long)

    @Query("DELETE FROM coins WHERE isInCollection = 0")
    suspend fun clearHistoryOnly()

    @Query("UPDATE coins SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Long, isFavorite: Boolean)

    @Query("UPDATE coins SET isInCollection = :inCollection, userNotes = :notes WHERE id = :id")
    suspend fun updateCollectionStatus(id: Long, inCollection: Boolean, notes: String?)

    @Query("""
        SELECT * FROM coins 
        WHERE isInCollection = 1 
        AND (title LIKE '%' || :query || '%' 
             OR country LIKE '%' || :query || '%' 
             OR denomination LIKE '%' || :query || '%' 
             OR year LIKE '%' || :query || '%'
             OR rarity LIKE '%' || :query || '%')
        ORDER BY timestamp DESC
    """)
    fun searchCollection(query: String): Flow<List<CoinEntity>>
}
