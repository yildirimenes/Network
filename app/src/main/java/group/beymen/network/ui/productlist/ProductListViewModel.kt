package group.beymen.network.ui.productlist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import group.beymen.network.data.local.ProductListCache
import group.beymen.network.data.mapper.toFavoriteProductEntity
import group.beymen.network.data.model.favorite.FavoriteProductEntity
import group.beymen.network.data.model.productlist.Product
import group.beymen.network.data.repository.ProductListRepository
import group.beymen.network.data.repository.FavoriteRepository
import group.beymen.network.data.util.NetworkResult
import group.beymen.network.data.util.NetworkUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: ProductListRepository,
    private val favoriteRepository: FavoriteRepository,
    private val productCache: ProductListCache,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(ProductListState())
    val state: StateFlow<ProductListState> = _state

    private val _favorites = MutableStateFlow<List<FavoriteProductEntity>>(emptyList())
    val favorites: StateFlow<List<FavoriteProductEntity>> = _favorites

    private var currentPage = 1
    private var isLastPage = false
    private var isLoadingMore = false
    private var maxCachedPage = 1
    private val visitedPages = mutableSetOf<Int>()

    init {
        loadFavorites()
    }

    fun loadProducts(
        categoryId: Int,
        page: Int = 1,
        pageSize: Int = 20,
        orderOption: String = "default",
        minPrice: Double? = null,
        maxPrice: Double? = null,
        filters: String = "",
        priceRange: String? = null
    ) {
        if (isLoadingMore || (page > 1 && isLastPage)) return

        val isOnline = NetworkUtil.isOnline(context)
        isLoadingMore = true

        viewModelScope.launch {
            if (isOnline) {
                repository.getProductList(
                    categoryId = categoryId,
                    page = page,
                    dropListingPageSize = pageSize,
                    orderOption = orderOption,
                    minPrice = minPrice,
                    maxPrice = maxPrice,
                    filters = filters,
                    priceRange = priceRange
                ).collect { result ->
                    when (result) {
                        is NetworkResult.OnLoading -> {
                            if (page == 1) {
                                _state.update { it.copy(isLoading = true) }
                            }
                        }
                        is NetworkResult.OnSuccess -> {
                            val newProducts = result.data?.ProductList ?: emptyList()
                            if (newProducts.isNotEmpty()) {
                                visitedPages.add(page)
                                productCache.saveProducts(categoryId, page, newProducts)
                            }

                            currentPage = page
                            maxCachedPage = visitedPages.maxOrNull() ?: 1
                            isLastPage = newProducts.isEmpty()

                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    productModel = result.data,
                                    products = if (page == 1) newProducts else it.products + newProducts
                                )
                            }
                        }
                        is NetworkResult.OnError -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    error = result.message ?: "Error fetching data"
                                )
                            }
                        }
                    }
                    isLoadingMore = false
                }
            } else {
                loadFromCache(categoryId)
            }
        }
    }

    private fun loadFromCache(categoryId: Int) {
        viewModelScope.launch {
            val cachedProducts = productCache.getAllProducts(categoryId)
            if (cachedProducts.isNotEmpty()) {
                isLastPage = true
                _state.update {
                    it.copy(
                        isLoading = false,
                        products = cachedProducts
                    )
                }
            } else {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "No cached data available"
                    )
                }
            }
            isLoadingMore = false
        }
    }

    fun loadNextPage(categoryId: Int) {
        val isOnline = NetworkUtil.isOnline(context)
        if (!isOnline) return

        loadProducts(categoryId = categoryId, page = currentPage + 1)
    }

    // Favorite Locale Operations
    private fun loadFavorites() {
        viewModelScope.launch {
            favoriteRepository.getFavorites().collect {
                _favorites.value = it
            }
        }
    }

    fun toggleFavorite(product: Product) {
        val favoriteProduct = product.toFavoriteProductEntity()
        viewModelScope.launch {
            if (_favorites.value.any { it.id == favoriteProduct.id }) {
                favoriteRepository.removeFavorite(favoriteProduct)
            } else {
                favoriteRepository.addFavorite(favoriteProduct)
            }
        }
    }

    fun isFavorite(productId: Int): Boolean {
        return _favorites.value.any { it.id == productId }
    }
}