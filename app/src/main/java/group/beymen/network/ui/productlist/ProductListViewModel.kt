package group.beymen.network.ui.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import group.beymen.network.data.mapper.toFavoriteProductEntity
import group.beymen.network.data.model.favorite.FavoriteProductEntity
import group.beymen.network.data.model.productlist.Product
import group.beymen.network.data.repository.ProductListRepository
import group.beymen.network.data.repository.FavoriteRepository
import group.beymen.network.data.util.NetworkResult
import group.beymen.network.data.model.productlist.ProductCache
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: ProductListRepository,
    private val favoriteRepository: FavoriteRepository,
    private val productCache: ProductCache
) : ViewModel() {

    private val _state = MutableStateFlow(ProductListState())
    val state: StateFlow<ProductListState> = _state

    private val _favorites = MutableStateFlow<List<FavoriteProductEntity>>(emptyList())
    val favorites: StateFlow<List<FavoriteProductEntity>> = _favorites


    private var currentPage = 1
    private var isLastPage = false
    private var isLoadingMore = false

    init {
        loadFavorites()
        productCache.clear()
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
        if (isLoadingMore || isLastPage) return

        isLoadingMore = true

        _state.update {
            it.copy(isLoading = page == 1)
        }

        viewModelScope.launch {
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
                    is NetworkResult.OnLoading -> _state.update {
                        it.copy(isLoading = true)
                    }
                    is NetworkResult.OnSuccess -> {
                        val newProducts = result.data?.ProductList ?: emptyList()
                        val response = result.data
                        isLastPage = newProducts.isEmpty()
                        currentPage = page

                        if (page > 2) productCache.evict(page - 2)

                        _state.update {
                            it.copy(
                                isLoading = false,
                                productModel = response,
                                products = it.products + newProducts
                            )
                        }
                    }
                    is NetworkResult.OnError -> {
                        val cachedProducts = productCache.get(page)
                        if (cachedProducts != null && cachedProducts.isNotEmpty()) {
                            _state.value = state.value.copy(
                                isLoading = false,
                                error = result.message,
                                products = cachedProducts
                            )
                        } else {
                            _state.value = state.value.copy(
                                isLoading = false,
                                error = result.message,
                                products = emptyList()
                            )
                        }
                    }
                }
                isLoadingMore = false
            }
        }
    }

    fun loadNextPage(categoryId: Int) {
        val nextPage = (_state.value.products.size / 20) + 1
        loadProducts(categoryId, nextPage)
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


// Not Cache Version
/*
@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: ProductListRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProductListState())
    val state: StateFlow<ProductListState> = _state

    private val _favorites = MutableStateFlow<List<FavoriteProductEntity>>(emptyList())
    val favorites: StateFlow<List<FavoriteProductEntity>> = _favorites

    private var currentPage = 1
    private var isLastPage = false
    private var isLoadingMore = false

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
        if (isLoadingMore || isLastPage) return

        isLoadingMore = true

        viewModelScope.launch {
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
                    is NetworkResult.OnLoading -> _state.update {
                        it.copy(isLoading = page == 1)
                    }
                    is NetworkResult.OnSuccess -> {
                        val newProducts = result.data?.ProductList ?: emptyList()
                        val response = result.data
                        isLastPage = newProducts.isEmpty()
                        currentPage = page
                        _state.update {
                            it.copy(
                                isLoading = false,
                                productModel = response,
                                products = it.products + newProducts
                            )
                        }
                    }
                    is NetworkResult.OnError -> _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.message ?: "An error occurred"
                        )
                    }
                }
                isLoadingMore = false
            }
        }
    }

    fun loadNextPage(categoryId: Int) {
        loadProducts(categoryId = categoryId, page = currentPage + 1)
    }

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
*/