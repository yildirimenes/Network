package group.beymen.network.data.util

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
    return try {
        Resource.Success(apiCall.invoke())
    } catch (e: Exception) {
        Resource.Error(e.localizedMessage ?: "Unknown error occurred")
    }
}
