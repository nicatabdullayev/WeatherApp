package com.delaroystudios.weatherapp.model

import com.delaroystudios.weatherapp.model.Status.ERROR
import com.delaroystudios.weatherapp.model.Status.LOADING
import com.delaroystudios.weatherapp.model.Status.SUCCESS

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
class Resource<T>(status: Status, data: T?, message: String?) {
    val status: Status
    val message: String?
    val data: T?

    init {
        this.status = status
        this.data = data
        this.message = message
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }
        val resource = o as Resource<*>
        if (status !== resource.status) {
            return false
        }
        if (if (message != null) message != resource.message else resource.message != null) {
            return false
        }
        return if (data != null) data == resource.data else resource.data == null
    }

    override fun hashCode(): Int {
        var result: Int = status.hashCode()
        result = 31 * result + (message?.hashCode() ?: 0)
        result = 31 * result + (data?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Resource{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}'
    }

    companion object {
        fun <T> success(data: T?): Resource<T?> {
            return Resource<T?>(SUCCESS, data, null)
        }

        fun <T> error(msg: String?, data: T?): Resource<T?> {
            return Resource<T?>(ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<ResultType>? {
            return Resource<T?>(LOADING, data, null)
        }
    }
}
