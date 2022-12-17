package com.pvsb.di

import com.pvsb.data.MessageDataSource
import com.pvsb.data.MessageDataSourceImpl
import com.pvsb.room.RoomController
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val mainModule = module {

    single { KMongo.createClient().coroutine.getDatabase("message_db") }

    single<MessageDataSource> { MessageDataSourceImpl(get()) }

    single { RoomController(get()) }

}