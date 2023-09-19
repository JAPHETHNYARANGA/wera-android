package com.werrah.wera.domain.repository

import com.werrah.wera.data.network.DeleteAccount
import com.werrah.wera.domain.models.LogoutResponse
import retrofit2.Response
import javax.inject.Inject


class DeleteAccountRepository @Inject constructor(private val deleteAccount: DeleteAccount) {
    suspend fun deleteAccount() : Response<LogoutResponse>{
        return deleteAccount.deleteAccount()
    }
}



