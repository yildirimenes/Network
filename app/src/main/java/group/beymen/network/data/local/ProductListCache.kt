package group.beymen.network.data.local

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import group.beymen.network.data.model.productlist.Classification
import group.beymen.network.data.model.productlist.Media
import group.beymen.network.data.model.productlist.OtherProductImages
import group.beymen.network.data.model.productlist.Product
import group.beymen.network.data.model.productlist.ProductEntity
import group.beymen.network.data.model.productlist.ProductPromotion
import group.beymen.network.data.model.productlist.Stock
import group.beymen.network.data.source.local.ProductListDao
import javax.inject.Inject

class ProductListCache @Inject constructor(
    private val productListDao: ProductListDao
) {

    suspend fun saveProducts(categoryId: Int, page: Int, products: List<Product>) {
        val entities = products.mapIndexed { index, product ->
            ProductEntity(
                id = product.ID,
                categoryId = categoryId,
                page = page,
                orderIndex = ((page - 1) * PAGE_SIZE) + index,
                actualPrice = product.ActualPrice,
                actualPriceToShowOnScreen = product.ActualPriceToShowOnScreen,
                brandName = product.BrandName,
                classifications = Gson().toJson(product.Classifications),
                discount = product.Discount,
                discountLabelType = product.DiscountLabelType,
                isDiscountBadgeEnabled = product.IsDiscountBadgeEnabled,
                displayName = product.DisplayName,
                estimatedSupplyDate = product.EstimatedSupplyDate?.toString(),
                externalSystemCode = product.ExternalSystemCode,
                firstProductImageURL = product.FirstProductImageURL,
                isHero = product.IsHero,
                isLabelPriceActive = product.IsLabelPriceActive,
                isSeasonPriceActive = product.IsSeasonPriceActive,
                isStrikeThroughPriceExists = product.IsStrikeThroughPriceExists,
                labelPrice = product.LabelPrice,
                mediaList = Gson().toJson(product.MediaList),
                productType = product.ProductType,
                seasonPrice = product.SeasonPrice,
                strikeThroughPriceToShowOnScreen = product.StrikeThroughPriceToShowOnScreen,
                totalDiscount = product.TotalDiscount?.toString(),
                variantWithStockList = Gson().toJson(product.VariantWithStockList),
                otherProductImages = Gson().toJson(product.OtherProductImages),
                productPromotion = product.ProductPromotion?.let { Gson().toJson(it) }
            )
        }
        productListDao.insertProducts(entities)
    }

    suspend fun getAllProducts(categoryId: Int): List<Product> {
        return productListDao.getAllProducts(categoryId).sortedBy { it.orderIndex }.map { entity ->
            Product(
                ID = entity.id,
                ActualPrice = entity.actualPrice,
                ActualPriceToShowOnScreen = entity.actualPriceToShowOnScreen,
                BrandName = entity.brandName,
                Classifications = Gson().fromJson(entity.classifications, object : TypeToken<List<Classification>>() {}.type),
                Discount = entity.discount,
                DiscountLabelType = entity.discountLabelType,
                IsDiscountBadgeEnabled = entity.isDiscountBadgeEnabled,
                DisplayName = entity.displayName,
                EstimatedSupplyDate = entity.estimatedSupplyDate,
                ExternalSystemCode = entity.externalSystemCode,
                FirstProductImageURL = entity.firstProductImageURL,
                IsHero = entity.isHero,
                IsLabelPriceActive = entity.isLabelPriceActive,
                IsSeasonPriceActive = entity.isSeasonPriceActive,
                IsStrikeThroughPriceExists = entity.isStrikeThroughPriceExists,
                LabelPrice = entity.labelPrice,
                MediaList = Gson().fromJson(entity.mediaList, object : TypeToken<List<Media>>() {}.type),
                ProductType = entity.productType,
                SeasonPrice = entity.seasonPrice,
                StrikeThroughPriceToShowOnScreen = entity.strikeThroughPriceToShowOnScreen,
                TotalDiscount = entity.totalDiscount,
                VariantWithStockList = Gson().fromJson(entity.variantWithStockList, object : TypeToken<List<Stock>>() {}.type),
                OtherProductImages = Gson().fromJson(entity.otherProductImages, object : TypeToken<List<OtherProductImages>>() {}.type),
                ProductPromotion = entity.productPromotion?.let { Gson().fromJson(it, ProductPromotion::class.java) }
            )
        }
    }

    suspend fun clearProductsByCategory(categoryId: Int) {
        productListDao.clearProductsByCategory(categoryId)
    }
}
