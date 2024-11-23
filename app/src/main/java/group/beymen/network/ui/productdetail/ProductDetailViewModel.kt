package group.beymen.network.ui.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import group.beymen.network.data.repository.ProductDetailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val repository: ProductDetailRepository
) : ViewModel() {

    private val _productDetailState = MutableStateFlow<ProductDetailState>(ProductDetailState.Loading)
    val productDetailState: StateFlow<ProductDetailState> = _productDetailState

    fun getProductDetail(productId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.fetchProductDetail(productId)
                if (response.Success) {
                    _productDetailState.value = ProductDetailState.Success(response.Result)
                } else {
                    _productDetailState.value = ProductDetailState.Error(response.Message)
                }
            } catch (e: Exception) {
                _productDetailState.value = ProductDetailState.Error(e.message ?: "Unknown Error")
            }
        }
    }
}
