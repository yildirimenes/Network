package group.beymen.network.ui.productlist

import group.beymen.network.data.model.productlist.Product
import group.beymen.network.data.model.productlist.ProductListResponseModel

data class ProductListState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val productModel: ProductListResponseModel? = null,
    val products: List<Product> = emptyList(),
    val error: String? = null,
    val favoriteProducts: List<Int> = emptyList(),
    val favoriteItems: List<Int> = emptyList()
)
