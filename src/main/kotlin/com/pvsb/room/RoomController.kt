package com.pvsb.room

import com.pvsb.data.MessageDataSource
import com.pvsb.data.model.Message
import io.ktor.http.cio.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap

class RoomController(
    private val messageDataSource: MessageDataSource
) {


    private val members = ConcurrentHashMap<String, Member>()

    fun onJoin(
        userName: String,
        sessionId: String,
        socket: WebSocketSession
    ) {

        if (members.contains(userName)) throw MemberAlreadyExistsException()

        members[userName] = Member(
            userName, sessionId, socket
        )
    }

    suspend fun sendMessage(senderUserName: String, message: String) {
        members.values.forEach { member ->
            val messageEntity = Message(message, senderUserName, System.currentTimeMillis())

            messageDataSource.insertMessage(messageEntity)

            val parsedMessage = Json.encodeToString(messageEntity)
            member.webSocket.send(Frame.Text(parsedMessage))
        }
    }

    suspend fun getAllMessages(): List<Message> {
        return messageDataSource.getAllMessages()
    }

    suspend fun tryToDisconnect(userName: String) {

        members[userName]?.let { member ->
            member.webSocket.close()
            members.remove(member.userName)
        }
    }
}