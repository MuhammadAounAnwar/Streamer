package com.ono.streamer.ui.mainscreen

interface IRepository {
    companion object {
        fun get(source: ISource) = IRepositoryImpl(source)
    }

    fun getAllData(): ArrayList<String>
}

interface ISource {
    companion object {
        fun get() = ISourceImpl()
    }

    fun getAllData(): ArrayList<String>
}

class IRepositoryImpl(private val source: ISource) : IRepository {
    override fun getAllData(): ArrayList<String> = source.getAllData()

}

class ISourceImpl : ISource {
    override fun getAllData(): ArrayList<String> {
        TODO("Not yet implemented")
    }

}