package group.beymen.network.ui.productdetail

import group.beymen.network.data.model.productdetail.ProductDetailResult

sealed class ProductDetailState {
    object Loading : ProductDetailState()
    data class Success(val product: ProductDetailResult?) : ProductDetailState()
    data class Error(val message: String) : ProductDetailState()
}
