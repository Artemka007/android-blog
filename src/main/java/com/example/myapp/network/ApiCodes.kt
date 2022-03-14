package com.example.myapp.network

class ApiCodes {
    companion object {
        /**
         * In response returned the error message
         */
        const val ERROR = 0
        /**
         * In response returned the success message
         */
        const val SUCCESS = 1
        /**
         * In response returned the warning message
         */
        const val WARNING = 2
        /**
         * In response returned the information message
         */
        const val INFO = 3
        /**
         * In response returned the waiting message
         */
        const val WAIT = 4
        /**
         * User did not authorized
         */
        const val UNAUTHORIZED = 5
        /**
         * In response returned the message about bad request
         */
        const val BAD_REQUEST = 6
        /**
         * In response returned the message about closing server connection
         */
        const val CLOSE = 7
    }
}