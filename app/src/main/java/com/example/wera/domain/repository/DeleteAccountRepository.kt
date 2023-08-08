package com.example.wera.domain.repository

import com.example.wera.data.network.DeleteAccount
import com.example.wera.domain.models.LogoutResponse
import retrofit2.Response
import javax.inject.Inject


class DeleteAccountRepository @Inject constructor(private val deleteAccount: DeleteAccount) {
    suspend fun deleteAccount() : Response<LogoutResponse>{
        return deleteAccount.deleteAccount()
    }
}



