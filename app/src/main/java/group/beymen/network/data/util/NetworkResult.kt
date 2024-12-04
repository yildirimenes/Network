package group.beymen.network.data.util

import com.google.gson.annotations.SerializedName

sealed class NetworkResult<out T> {
    data class OnSuccess<out T>(val data: T?, val action: ActionModel? = null) : NetworkResult<T>()
    data class OnError(val message: String?) : NetworkResult<Nothing>()
    object OnLoading : NetworkResult<Nothing>()
}

data class ActionModel(
    @SerializedName("ActionType") val actionType: Int,
    @SerializedName("DeepLink") val deeplink: String,
    @SerializedName("RefreshToken") val refreshToken: String
)
