package com.example.wera.domain.models

data class Messages(
    val success : Boolean,
    val messages : List<message>,
    val user: MessageUser

)

data class  message(
    val id:Int?,
    val sender_id:String?,
    val receiver_id : String?,
    val chat_id: String?,
    val message : String?,
    val created_at : String?,
    val updated_at : String?,

)

data class MessageUser(
    val name: String
)
