package group.beymen.network.data.util

import group.beymen.network.data.model.BaseResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException

fun <T> sendRequest(serviceCall: suspend () -> BaseResponseModel<T>) =
    flow {
        emit(NetworkResult.OnLoading)
        emit(safeApiCall { serviceCall() })
    }.flowOn(Dispatchers.IO)

private suspend fun <T> safeApiCall(
    apiCall: suspend () -> BaseResponseModel<T>
): NetworkResult<T> {
    return try {
        val result = apiCall()
        when (result.isSuccess) {
            true -> NetworkResult.OnSuccess(result.result,result.action)
            false -> NetworkResult.OnError(result.message)
        }
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> NetworkResult.OnError(throwable.message)
            is HttpException -> NetworkResult.OnError(throwable.message)
            else -> NetworkResult.OnError(throwable.message)
        }
    }
}