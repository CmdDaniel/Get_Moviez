package com.example.getmovies.data.network

enum class Status {
    LOADING,
    SUCCESS,
    ERROR
}

enum class Error{
    NO_DATA,
    NETWORK_ERROR,
    SERVER_ERROR,
    UNKNOWN_ERROR
}


class NetworkCallStatus(val status:Status,val error: Error?,val msg:String?) {
    companion object{
        fun success(error: Error? = null,msg: String? = null):NetworkCallStatus{
            return NetworkCallStatus(
                        Status.SUCCESS,
                        error,
                        msg
            )
        }
        fun loading(msg: String? = null):NetworkCallStatus{
            return NetworkCallStatus(
                        Status.LOADING,
                        null,
                        msg
            )
        }
        fun error(error: Error?,msg: String? = null):NetworkCallStatus{
            return NetworkCallStatus(
                        Status.ERROR,
                        error,
                        msg
            )
        }
    }












}