package com.ono.streamerlibrary

 class ApiResponse<T> {
     var status = 0
     var code = 0
     var message: String? = null
     var errorMessage: String? = null
     var data: T? = null
}