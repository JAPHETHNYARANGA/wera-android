package com.werrah.wera.domain.models

data class Messages(
    val success : Boolean,
    val messages : List<message>,
)

data class  message(
    val id:Int?,
    val sender_id:String?,
    val receiver_id : String?,
    val chat_id: String?,
    val message : String?,
    val created_at : String?,
    val updated_at : String?,
    val user : MessageUser?

)

data class MessageUser(
    val name: String,
    val userId :String,
    val profile : String
)