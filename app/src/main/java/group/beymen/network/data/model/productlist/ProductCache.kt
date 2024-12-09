package group.beymen.network.data.model.productlist

class ProductCache {
    private val cache = mutableMapOf<Int, List<Product>>()

    fun put(page: Int, products: List<Product>) {
        cache[page] = products.map { product ->
            product.copy(
                MediaList = product.MediaList?.map { it.copy() },
                OtherProductImages = product.OtherProductImages?.map { it.copy() }
            )
        }
    }

    fun get(page: Int): List<Product>? {
        return cache[page]
    }

    fun clear() {
        cache.clear()
    }

    fun evict(page: Int) {
        cache.remove(page)
    }
}

