package group.beymen.network.data.model

import com.google.gson.annotations.SerializedName
import group.beymen.network.data.util.ActionModel

open class BaseResponseModel<T> {
    @SerializedName("Success")
    var isSuccess: Boolean = false

    @SerializedName("Message")
    var message: String = ""

    @SerializedName("Result")
    val result: T? = null

    @SerializedName("Exception")
    val exception: Exception? = null

    @SerializedName("Action")
    val action: ActionModel? = null
}