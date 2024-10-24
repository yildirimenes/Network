package group.beymen.network.data.model

import com.google.gson.annotations.SerializedName

class BaseModel<T> {
    @SerializedName("data")
    var data: T? = null

    @SerializedName("resultStatus")
    var resultStatus: Int? = null

    @SerializedName("message")
    var message: String? = null
}
