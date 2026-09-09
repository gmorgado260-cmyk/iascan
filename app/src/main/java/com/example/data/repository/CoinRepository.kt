package com.example.data.repository

import com.example.data.database.CoinDao
import com.example.model.CoinAnalysisResult
import com.example.model.CoinEntity
import kotlinx.coroutines.flow.Flow

class CoinRepository(private val coinDao: CoinDao) {

    val allHistory: Flow<List<CoinEntity>> = coinDao.getAllHistory()
    val collection: Flow<List<CoinEntity>> = coinDao.getCollection()
    val favorites: Flow<List<CoinEntity>> = coinDao.getFavorites()

    fun getRecentScans(limit: Int = 5): Flow<List<CoinEntity>> = coinDao.getRecentScans(limit)

    fun getCoinById(id: Long): Flow<CoinEntity?> = coinDao.getCoinById(id)

    suspend fun saveScanResult(result: CoinAnalysisResult, inCollection: Boolean = false): Long {
        val entity = CoinEntity.fromAnalysisResult(result, inCollection = inCollection)
        return coinDao.insertCoin(entity)
    }

    suspend fun updateCoin(coin: CoinEntity) {
        coinDao.updateCoin(coin)
    }

    suspend fun toggleFavorite(id: Long, currentFavorite: Boolean) {
        coinDao.updateFavorite(id, !currentFavorite)
    }

    suspend fun setInCollection(id: Long, inCollection: Boolean, notes: String? = null) {
        coinDao.updateCollectionStatus(id, inCollection, notes)
    }

    suspend fun deleteCoin(id: Long) {
        coinDao.deleteCoinById(id)
    }

    suspend fun clearHistoryOnly() {
        coinDao.clearHistoryOnly()
    }

    fun searchCollection(query: String): Flow<List<CoinEntity>> {
        return if (query.isBlank()) {
            coinDao.getCollection()
        } else {
            coinDao.searchCollection(query.trim())
        }
    }
}
