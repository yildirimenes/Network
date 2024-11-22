package group.beymen.network.ui.productlist

import group.beymen.network.data.model.productlist.Product
import group.beymen.network.data.model.productlist.ProductListResponseModel

data class ProductListState(
    val isLoading: Boolean = false,
    val productModel: List<ProductListResponseModel> = emptyList(),
    val products: List<Product> = emptyList(),
    val errorMessage: String? = null
)
