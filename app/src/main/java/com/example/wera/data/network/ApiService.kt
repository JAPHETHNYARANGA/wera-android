package com.example.wera.data.network

import com.example.wera.domain.models.Categories
import com.example.wera.domain.models.DeleteListingResponse
import com.example.wera.domain.models.GetUserData
import com.example.wera.domain.models.IndividualListing
import com.example.wera.domain.models.Listings
import com.example.wera.domain.models.ListingsData
import com.example.wera.domain.models.LoginResponse
import com.example.wera.domain.models.LogoutResponse
import com.example.wera.domain.models.Messages
import com.example.wera.domain.models.PostItemData
import com.example.wera.domain.models.PostItemResponse
import com.example.wera.domain.models.RegisterRequest
import com.example.wera.domain.models.RegisterResponse
import com.example.wera.domain.models.UpdateProfileData
import com.example.wera.domain.models.UpdateProfileResponse
import com.example.wera.domain.models.UserLogin
import retrofit2.Response
import retrofit2.http.Body
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

interface DeleteAccount{
    @GET("deleteUser")
    suspend fun deleteAccount() : Response<LogoutResponse>
}

interface PostItem{
    @POST("listing")
    suspend fun post(@Body postItemData : PostItemData) : Response<PostItemResponse>
}



interface GetPosts{
    @GET("listing")
    suspend fun getListings() : ListingsData
}

interface GetIndividualListing{
    @GET("individuallisting/{id}")
    suspend fun getIndividualListings(@Path("id") id: Int): IndividualListing
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

interface GetCategories{
    @GET("category")
    suspend fun getCategories() : Categories
}

//messages
interface GetMessages{
    @GET("messages")
    suspend fun getMessages(@Query("userId") userId: String) : Messages
}