package com.ono.streamerlibrary


class Resource<T> {
    var status: Status? = null

    var message: String? = null

    var data: T? = null

    private var serverItemCount: Int? = null

    constructor(status: Status, data: T?, message: String?) {
        this.status = status
        this.data = data
        this.message = message
        this.serverItemCount = null
    }

    constructor(status: Status, data: T?, message: String?, serverItemCount: Int) {
        this.status = status
        this.data = data
        this.message = message
        this.serverItemCount = serverItemCount
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }
        val resource: Resource<*> =
            other as Resource<*>
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
}