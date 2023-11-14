package com.werrah.wera.data.network

import com.werrah.wera.domain.models.Categories
import com.werrah.wera.domain.models.ChatId
import com.werrah.wera.domain.models.DeleteListingResponse
import com.werrah.wera.domain.models.Favorites
import com.werrah.wera.domain.models.FavoritesResponse
import com.werrah.wera.domain.models.ForgotPassword
import com.werrah.wera.domain.models.ForgotPasswordResponse
import com.werrah.wera.domain.models.GetUserData
import com.werrah.wera.domain.models.IndividualListing
import com.werrah.wera.domain.models.ListingsData
import com.werrah.wera.domain.models.LoginResponse
import com.werrah.wera.domain.models.LogoutResponse
import com.werrah.wera.domain.models.Messages
import com.werrah.wera.domain.models.OtherProfile
import com.werrah.wera.domain.models.PostItemData
import com.werrah.wera.domain.models.PostItemResponse
import com.werrah.wera.domain.models.PostMessage
import com.werrah.wera.domain.models.PostMessageResponse
import com.werrah.wera.domain.models.ReceiverIdResponse
import com.werrah.wera.domain.models.RegisterRequest
import com.werrah.wera.domain.models.RegisterResponse
import com.werrah.wera.domain.models.UpdateProfileData
import com.werrah.wera.domain.models.UpdateProfileResponse
import com.werrah.wera.domain.models.UserLogin
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface RegisterUser{
    @POST("register")
    suspend fun register(@Body Users: RegisterRequest): Response<RegisterResponse>
}

interface LoginUser{
    @POST("login")
    suspend fun login(@Body userLogin : UserLogin): Response<LoginResponse>
}

interface Logout {
    @POST("logout")
    suspend fun logout(): Response<LogoutResponse>
}

interface ForgetPasswordInterface{
    @POST("forgot-password")
    suspend fun forgetPassword(@Body forgotPassword: ForgotPassword) : Response<ForgotPasswordResponse>
}
interface DeleteAccount{
    @DELETE("deleteUser")
    suspend fun deleteAccount() : Response<LogoutResponse>
}

interface PostItem{
    @POST("listing")
    suspend fun post(@Body postItemData : PostItemData) : Response<PostItemResponse>
}

interface GetPosts {
    @GET("listing")
    suspend fun getListings(@Query("page") currentPage: Int): ListingsData
}
interface GetFavorites{
    @GET("getFavorites")
    suspend fun getFavorites() : Favorites
}

interface GetIndividualListing{
    @GET("individuallisting/{id}")
    suspend fun getIndividualListings(@Path("id") id: Int): IndividualListing
}

interface AddToFavorites{
    @POST("listing/{id}/add-to-favorites")
    suspend fun favorites(@Path("id") id: Int?) : FavoritesResponse
}

interface RemoveFromFavorites{
    @POST("listing/{id}/remove-from-favorites")
    suspend fun favorites(@Path("id") id: Int?) : FavoritesResponse
}

interface DeleteListing{
    @GET("deletelisting/{id}")
    suspend fun deleteListing(@Path("id") id : Int) : DeleteListingResponse
}

interface GetUserListings{
    @GET("userListing")
    suspend fun getUserListings() :ListingsData
}

interface UpdateProfile{
    @PUT("user")
    suspend fun updateProfile(@Body updateProfileData: UpdateProfileData) : Response<UpdateProfileResponse>
}

interface GetUser{
    @GET("getuser")
    suspend fun getUser() : GetUserData
}

interface FetchProfile{
    @GET("profile")
    suspend fun fetchProfile(@Query("userId") userId:String): OtherProfile
}

interface GetCategories{
    @GET("category")
    suspend fun getCategories() : Categories
}

//messages
interface GetMessages{
    @GET("messages")
    suspend fun getMessages(@Query("userId") userId: String) : Messages
}

interface GetSpecificMessage{
    @GET("message")
    suspend fun getMessage(@Query("chatId") chatId:String) : Messages
}

interface PostMessageInterface{
    @POST("messages")
    suspend fun postMessage(@Body postMessage : PostMessage) : Response<PostMessageResponse>
}

interface GetChatIdInterface{
    @GET("chatId")
    suspend  fun getChatId(@Query("senderId") senderId:String, @Query("receiverId") receiverId:String) : ChatId
}

interface GetReceiverIdInterface{
    @GET("receiverId")
    suspend fun getReceiverId(@Query("userId") userId: String, @Query("chatId") chatId : String) : ReceiverIdResponse
}



