package com.example.ui

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ai.AIService
import com.example.ai.GeminiCoinAiService
import com.example.ai.ImageUtils
import com.example.data.database.AppDatabase
import com.example.data.repository.CoinRepository
import com.example.data.repository.ReferenceCatalogRepository
import com.example.model.CoinAnalysisResult
import com.example.model.CoinEntity
import com.example.model.ReferenceCoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class CoinSide {
    OBVERSE, // Frente / Anverso
    REVERSE  // Verso / Reverso
}

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val repository = CoinRepository(database.coinDao())
    private val catalogRepository = ReferenceCatalogRepository()
    private val aiService: AIService = GeminiCoinAiService(application, catalogRepository)

    // Data streams from Room
    val historyList: StateFlow<List<CoinEntity>> = repository.allHistory
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val collectionList: StateFlow<List<CoinEntity>> = repository.collection
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favoritesList: StateFlow<List<CoinEntity>> = repository.favorites
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val recentScans: StateFlow<List<CoinEntity>> = repository.getRecentScans(4)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Active scan state
    private val _obverseBitmap = MutableStateFlow<Bitmap?>(null)
    val obverseBitmap: StateFlow<Bitmap?> = _obverseBitmap.asStateFlow()

    private val _reverseBitmap = MutableStateFlow<Bitmap?>(null)
    val reverseBitmap: StateFlow<Bitmap?> = _reverseBitmap.asStateFlow()

    private val _activeSide = MutableStateFlow(CoinSide.OBVERSE)
    val activeSide: StateFlow<CoinSide> = _activeSide.asStateFlow()

    // Analysis state
    private val _isAnalyzing = MutableStateFlow(false)
    val isAnalyzing: StateFlow<Boolean> = _isAnalyzing.asStateFlow()

    private val _analysisStep = MutableStateFlow("")
    val analysisStep: StateFlow<String> = _analysisStep.asStateFlow()

    private val _analysisProgress = MutableStateFlow(0f)
    val analysisProgress: StateFlow<Float> = _analysisProgress.asStateFlow()

    private val _currentAnalysis = MutableStateFlow<CoinAnalysisResult?>(null)
    val currentAnalysis: StateFlow<CoinAnalysisResult?> = _currentAnalysis.asStateFlow()

    private val _selectedHistoryCoin = MutableStateFlow<CoinEntity?>(null)
    val selectedHistoryCoin: StateFlow<CoinEntity?> = _selectedHistoryCoin.asStateFlow()

    // Catalog state
    private val _catalogQuery = MutableStateFlow("")
    val catalogQuery: StateFlow<String> = _catalogQuery.asStateFlow()

    private val _selectedCatalogCategory = MutableStateFlow("Todas")
    val selectedCatalogCategory: StateFlow<String> = _selectedCatalogCategory.asStateFlow()

    private val _filteredCatalog = MutableStateFlow(catalogRepository.getAllReferenceCoins())
    val filteredCatalog: StateFlow<List<ReferenceCoin>> = _filteredCatalog.asStateFlow()

    // Collection filter & search
    private val _collectionQuery = MutableStateFlow("")
    val collectionQuery: StateFlow<String> = _collectionQuery.asStateFlow()

    private val _collectionRarityFilter = MutableStateFlow("Todas")
    val collectionRarityFilter: StateFlow<String> = _collectionRarityFilter.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    val catalogCategories: List<String> = catalogRepository.getCategories()

    fun setActiveSide(side: CoinSide) {
        _activeSide.value = side
    }

    fun setObverseImage(bitmap: Bitmap) {
        _obverseBitmap.value = bitmap
        // Automatically prompt to capture reverse if not yet captured
        if (_reverseBitmap.value == null) {
            _activeSide.value = CoinSide.REVERSE
        }
    }

    fun setReverseImage(bitmap: Bitmap) {
        _reverseBitmap.value = bitmap
    }

    fun clearCapturedImages() {
        _obverseBitmap.value = null
        _reverseBitmap.value = null
        _activeSide.value = CoinSide.OBVERSE
        _currentAnalysis.value = null
        _analysisStep.value = ""
        _analysisProgress.value = 0f
    }

    fun startAnalysis(onSuccess: () -> Unit) {
        val obverse = _obverseBitmap.value
        if (obverse == null) {
            _errorMessage.value = "Por favor, fotografe pelo menos a frente da moeda."
            return
        }

        viewModelScope.launch {
            _isAnalyzing.value = true
            _analysisProgress.value = 0.15f
            _analysisStep.value = "Identificando contorno e orla da moeda..."
            delay(500)

            _analysisProgress.value = 0.35f
            _analysisStep.value = "Segmentando relevo, efígie e legendas..."
            delay(600)

            _analysisProgress.value = 0.60f
            _analysisStep.value = "Comparando com catálogo numismático e variantes..."
            delay(500)

            _analysisProgress.value = 0.85f
            _analysisStep.value = "Processando com Inteligência Artificial..."

            try {
                // Save images to persistent storage so they can be reviewed later
                val obversePath = ImageUtils.saveBitmapToInternalStorage(getApplication(), obverse, "obverse")
                val reversePath = _reverseBitmap.value?.let {
                    ImageUtils.saveBitmapToInternalStorage(getApplication(), it, "reverse")
                }

                val analysis = aiService.analyzeCoin(
                    obverseBitmap = obverse,
                    reverseBitmap = _reverseBitmap.value
                ).copy(
                    obverseImagePath = obversePath,
                    reverseImagePath = reversePath
                )

                _analysisProgress.value = 1.0f
                _analysisStep.value = "Identificação concluída!"
                delay(300)

                _currentAnalysis.value = analysis

                // Auto-save to scan history (without saving to collection yet until user confirms)
                repository.saveScanResult(analysis, inCollection = false)

                _isAnalyzing.value = false
                onSuccess()
            } catch (e: Exception) {
                _isAnalyzing.value = false
                _errorMessage.value = "Erro na análise: ${e.localizedMessage ?: "Não foi possível conectar ao serviço de análise."}"
            }
        }
    }

    fun saveCurrentToCollection(userNotes: String? = null) {
        val current = _currentAnalysis.value ?: return
        viewModelScope.launch {
            repository.saveScanResult(current, inCollection = true)
        }
    }

    fun toggleFavorite(id: Long, currentFavorite: Boolean) {
        viewModelScope.launch {
            repository.toggleFavorite(id, currentFavorite)
        }
    }

    fun removeCoin(id: Long) {
        viewModelScope.launch {
            repository.deleteCoin(id)
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            repository.clearHistoryOnly()
        }
    }

    fun selectHistoryCoin(coin: CoinEntity) {
        _selectedHistoryCoin.value = coin
        _currentAnalysis.value = coin.toAnalysisResult()
    }

    fun setCatalogQuery(query: String) {
        _catalogQuery.value = query
        filterCatalog()
    }

    fun setCatalogCategory(category: String) {
        _selectedCatalogCategory.value = category
        filterCatalog()
    }

    private fun filterCatalog() {
        val q = _catalogQuery.value
        val cat = _selectedCatalogCategory.value
        var list = if (cat == "Todas") {
            catalogRepository.getAllReferenceCoins()
        } else {
            catalogRepository.findByCategory(cat)
        }
        if (q.isNotBlank()) {
            val queryLower = q.trim().lowercase()
            list = list.filter {
                it.name.lowercase().contains(queryLower) ||
                it.country.lowercase().contains(queryLower) ||
                it.denomination.lowercase().contains(queryLower) ||
                it.year.contains(queryLower) ||
                it.description.lowercase().contains(queryLower)
            }
        }
        _filteredCatalog.value = list
    }

    fun setCollectionQuery(query: String) {
        _collectionQuery.value = query
    }

    fun setCollectionRarityFilter(rarity: String) {
        _collectionRarityFilter.value = rarity
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
