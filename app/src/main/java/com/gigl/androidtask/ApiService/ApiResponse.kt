sealed class ApiResponse<T>(val data: T? = null, val message: String? = null) {

    class Success<T>(details: T) : ApiResponse<T>(details)
    class Error<T>(message: String?, details: T? = null) : ApiResponse<T>(details, message)
    class Loading<T> : ApiResponse<T>()

}
