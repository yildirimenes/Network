package group.beymen.network.data.source.remote

import group.beymen.network.util.Resource

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
    return try {
        Resource.Success(apiCall.invoke())
    } catch (e: Exception) {
        Resource.Error(e.localizedMessage ?: "Unknown error occurred")
    }
}
