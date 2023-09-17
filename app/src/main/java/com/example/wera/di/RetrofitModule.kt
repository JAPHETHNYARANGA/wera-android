package com.example.wera.di

import android.content.Context
import android.content.SharedPreferences
import com.example.wera.data.network.DeleteAccount
import com.example.wera.data.network.DeleteListing
import com.example.wera.data.network.FetchProfile
import com.example.wera.data.network.ForgetPasswordInterface
import com.example.wera.data.network.GetCategories
import com.example.wera.data.network.GetChatIdInterface
import com.example.wera.data.network.GetIndividualListing
import com.example.wera.data.network.GetMessages
import com.example.wera.data.network.GetPosts
import com.example.wera.data.network.GetReceiverIdInterface
import com.example.wera.data.network.GetSpecificMessage
import com.example.wera.data.network.GetUser
import com.example.wera.data.network.GetUserListings
import com.example.wera.data.network.LoginUser
import com.example.wera.data.network.Logout
import com.example.wera.data.network.PostItem
import com.example.wera.data.network.PostMessageInterface
import com.example.wera.data.network.RegisterUser
import com.example.wera.data.network.UpdateProfile
import com.example.wera.domain.models.ForgotPassword
import com.example.wera.domain.repository.DeleteAccountRepository
import com.example.wera.domain.repository.DeleteListingRepository
import com.example.wera.domain.repository.FetchProfileRepository
import com.example.wera.domain.repository.ForgetPasswordRepository
import com.example.wera.domain.repository.GetCategoriesDataRepository
import com.example.wera.domain.repository.GetChatIdRepository
import com.example.wera.domain.repository.GetIndividualItemRepository
import com.example.wera.domain.repository.GetListingsRepository
import com.example.wera.domain.repository.GetMessagesRepository
import com.example.wera.domain.repository.GetReceiverIdRepository
import com.example.wera.domain.repository.GetSpecifiMessageRepository
import com.example.wera.domain.repository.GetUserListingsRepository
import com.example.wera.domain.repository.GetUserRepository
import com.example.wera.domain.repository.LoginUserRepository
import com.example.wera.domain.repository.LogoutRepository
import com.example.wera.domain.repository.PostMessageRepository
import com.example.wera.domain.repository.RegisterUserRepository
import com.example.wera.domain.repository.UpdateProfileRepository
import com.example.wera.domain.useCase.DeleteAccountUseCase
import com.example.wera.domain.useCase.DeleteListingUseCase
import com.example.wera.domain.useCase.FetchProfileUseCase
import com.example.wera.domain.useCase.ForgetPasswordUseCase
import com.example.wera.domain.useCase.GetCategoriesDataUseCase
import com.example.wera.domain.useCase.GetIndividualItemUseCase
import com.example.wera.domain.useCase.GetListingUseCase
import com.example.wera.domain.useCase.GetMessagesUseCase
import com.example.wera.domain.useCase.GetReceiverIdUseCase
import com.example.wera.domain.useCase.GetSpecificMessageUseCase
import com.example.wera.domain.useCase.GetUserListingsUseCase
import com.example.wera.domain.useCase.GetUserUseCase
import com.example.wera.domain.useCase.LoginUserUseCase
import com.example.wera.domain.useCase.LogoutUseCase
import com.example.wera.domain.useCase.PostMessageUseCase
import com.example.wera.domain.useCase.RegisterUserUseCase
import com.example.wera.domain.useCase.UpdateProfileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("loginPreference", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(sharedPreferences: SharedPreferences): OkHttpClient {
        val token = sharedPreferences.getString("loginPreference", "") ?: ""
        val interceptor = Interceptor { chain ->
            val request: Request = chain.request().newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
            chain.proceed(request)
        }
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://79a4-197-232-87-139.ngrok-free.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    //Interfaces
    @Provides
    fun provideLoginInterface(retrofit: Retrofit):LoginUser{
        return retrofit.create(LoginUser::class.java)
    }

    @Provides
    fun provideLogoutInterface(retrofit: Retrofit) : Logout{
        return retrofit.create(Logout::class.java)
    }

    @Provides
    fun provideDeleteUserInterface(retrofit: Retrofit) : DeleteAccount{
        return retrofit.create(DeleteAccount::class.java)
    }

    @Provides
    fun provideForgetPasswordInterface(retrofit: Retrofit) : ForgetPasswordInterface{
        return retrofit.create(ForgetPasswordInterface::class.java)
    }

    @Provides
    fun provideRegisterInterface(retrofit: Retrofit): RegisterUser{
        return retrofit.create(RegisterUser::class.java)
    }

    @Provides
    fun provideCreateListing(retrofit: Retrofit) : PostItem{
        return retrofit.create(PostItem::class.java)
    }

    @Provides
    fun provideGetListingData(retrofit: Retrofit) : GetPosts{
        return retrofit.create(GetPosts::class.java)
    }

    @Provides
    fun provideGetIndividualListings(retrofit: Retrofit) : GetUserListings{
        return retrofit.create(GetUserListings::class.java)
    }

    @Provides
    fun provideUpdateProfile(retrofit: Retrofit): UpdateProfile{
        return retrofit.create(UpdateProfile::class.java)
    }

    @Provides
    fun provideGetUserProfile(retrofit: Retrofit):GetUser{
        return retrofit.create(GetUser::class.java)
    }

    @Provides
    fun provideIndividualListing(retrofit: Retrofit) : GetIndividualListing{
        return retrofit.create(GetIndividualListing::class.java)
    }

    @Provides
    fun provideDeleteListingResponse(retrofit: Retrofit) : DeleteListing{
        return retrofit.create(DeleteListing::class.java)
    }

    @Provides
    fun provideCategoryData(retrofit: Retrofit) : GetCategories{
        return retrofit.create(GetCategories::class.java)
    }

    @Provides
    fun providesMessagesData(retrofit: Retrofit) : GetMessages{
        return retrofit.create(GetMessages::class.java)
    }

    @Provides
    fun GetSpecificMessagesData(retrofit : Retrofit) : GetSpecificMessage{
        return retrofit.create(GetSpecificMessage::class.java)
    }

    @Provides
    fun providePostMessage(retrofit: Retrofit) : PostMessageInterface {
        return retrofit.create(PostMessageInterface::class.java)
    }

    @Provides
    fun providesGetChatId(retrofit: Retrofit) : GetChatIdInterface{
        return retrofit.create(GetChatIdInterface::class.java)
    }

    @Provides
    fun providesGetReceiverId(retrofit: Retrofit) : GetReceiverIdInterface{
        return retrofit.create(GetReceiverIdInterface::class.java)
    }
    @Provides
    fun provideFetchOtherProfile(retrofit: Retrofit) : FetchProfile{
        return retrofit.create(FetchProfile::class.java)
    }

    //Repository

    @Provides
    fun provideLoginRepository(loginUser: LoginUser):LoginUserRepository{
        return LoginUserRepository(loginUser)
    }

    @Provides
    fun provideLogoutRepository(logout: Logout): LogoutRepository{
        return LogoutRepository(logout)
    }

    @Provides
    fun provideDeleteAccountRepository(deleteAccount: DeleteAccount): DeleteAccountRepository{
        return DeleteAccountRepository(deleteAccount)
    }
    @Provides
    fun provideForgetPasswordRepository(forgetPasswordInterface: ForgetPasswordInterface):ForgetPasswordRepository{
        return ForgetPasswordRepository(forgetPasswordInterface)
    }

    @Provides
    fun provideRegisterRepository(registerUser: RegisterUser):RegisterUserRepository{
        return RegisterUserRepository(registerUser)
    }

    @Provides
    fun providePostsRepository(getPosts: GetPosts) : GetListingsRepository{
        return GetListingsRepository(getPosts)
    }

    @Provides
    fun providesGetUserPostsRepository(getUserListings: GetUserListings):GetUserListingsRepository{
        return GetUserListingsRepository(getUserListings)
    }

    @Provides
    fun provideUpdateProfileRepository(updateProfile: UpdateProfile) : UpdateProfileRepository{
        return UpdateProfileRepository(updateProfile)
    }

    @Provides
    fun provideGetUserData(getUser: GetUser) : GetUserRepository{
        return GetUserRepository(getUser)
    }


    @Provides
    fun provideGetIndividualListing(getIndividualListing: GetIndividualListing) : GetIndividualItemRepository{
        return GetIndividualItemRepository(getIndividualListing)
    }

    @Provides
    fun provideDeleteListing(deleteListing: DeleteListing) : DeleteListingRepository{
        return  DeleteListingRepository(deleteListing)
    }

    @Provides
    fun providesGetCategories(getCategories: GetCategories): GetCategoriesDataRepository{
        return GetCategoriesDataRepository(getCategories)
    }

    @Provides
    fun providesGetMessagesRepository(getMessages: GetMessages) : GetMessagesRepository{
        return GetMessagesRepository(getMessages)
    }

    @Provides
    fun provideGetSpecificMessageRepository(getSpecificMessage: GetSpecificMessage) : GetSpecifiMessageRepository{
        return GetSpecifiMessageRepository(getSpecificMessage)
    }

    @Provides
    fun providePostMessageRepository(postMessageInterface: PostMessageInterface) : PostMessageRepository{
        return PostMessageRepository(postMessageInterface)
    }

    @Provides
    fun providesGetChatIdRepository(getChatIdInterface: GetChatIdInterface) : GetChatIdRepository{
        return GetChatIdRepository(getChatIdInterface)
    }

    @Provides
    fun providesGetReceiverIdRepository(getReceiverIdInterface: GetReceiverIdInterface) : GetReceiverIdRepository{
        return GetReceiverIdRepository(getReceiverIdInterface)
    }

    @Provides
    fun provideFetchProfileRepository(fetchProfile: FetchProfile) : FetchProfileRepository{
        return FetchProfileRepository(fetchProfile)
    }
    //UseCase

    @Provides
    fun provideLoginUseCase(loginUserRepository: LoginUserRepository):LoginUserUseCase{
        return LoginUserUseCase(loginUserRepository)
    }

    @Provides
    fun provideLogoutUseCase(logoutRepository: LogoutRepository) : LogoutUseCase{
        return LogoutUseCase(logoutRepository)
    }

    @Provides
    fun provideDeleteAccountUseCase(deleteAccountRepository: DeleteAccountRepository) : DeleteAccountUseCase{
        return DeleteAccountUseCase(deleteAccountRepository)
    }

    @Provides
    fun providesForgetPasswordUseCase(forgetPasswordRepository: ForgetPasswordRepository) : ForgetPasswordUseCase{
        return ForgetPasswordUseCase(forgetPasswordRepository)
    }

    @Provides
    fun provideRegisterUseCase(registerUserRepository: RegisterUserRepository): RegisterUserUseCase{
        return RegisterUserUseCase(registerUserRepository)
    }

    @Provides
    fun provideGetListingUseCase(getListingsRepository: GetListingsRepository) : GetListingUseCase{
        return GetListingUseCase(getListingsRepository)
    }

    @Provides
    fun providesGetUserListingsUseCase(getUserListingsRepository: GetUserListingsRepository) : GetUserListingsUseCase{
        return GetUserListingsUseCase(getUserListingsRepository)
    }

    @Provides
    fun providesUpdateProfileUseCase(updateProfileRepository: UpdateProfileRepository) : UpdateProfileUseCase{
        return UpdateProfileUseCase(updateProfileRepository)
    }

    @Provides
    fun providesGetUserUseCase(getUserRepository: GetUserRepository) : GetUserUseCase{
        return GetUserUseCase(getUserRepository)
    }

    @Provides
    fun providesGetIndividualDataUseCase(getIndividualItemRepository: GetIndividualItemRepository) : GetIndividualItemUseCase{
        return GetIndividualItemUseCase(getIndividualItemRepository)
    }

    @Provides
    fun provideDeleteListingUseCase(deleteListingRepository: DeleteListingRepository) : DeleteListingUseCase{
        return DeleteListingUseCase(deleteListingRepository)
    }

    @Provides
    fun providesGetCategoriesData(getCategoriesDataRepository: GetCategoriesDataRepository): GetCategoriesDataUseCase{
        return GetCategoriesDataUseCase(getCategoriesDataRepository)
    }

    @Provides
    fun providesMessagesUseCase(getMessagesRepository: GetMessagesRepository) : GetMessagesUseCase{
        return GetMessagesUseCase(getMessagesRepository)
    }

    @Provides
    fun provideSpecificMessagesUseCase(getSpecifiMessageRepository: GetSpecifiMessageRepository) : GetSpecificMessageUseCase{
        return GetSpecificMessageUseCase(getSpecifiMessageRepository)
    }

    @Provides
    fun providesPostMessageUseCase(postMessageRepository: PostMessageRepository) : PostMessageUseCase{
        return PostMessageUseCase(postMessageRepository)
    }

    @Provides
    fun providesGetReceiverIdUseCase(getReceiverIdRepository: GetReceiverIdRepository) : GetReceiverIdUseCase{
        return GetReceiverIdUseCase(getReceiverIdRepository)
    }

    @Provides
    fun providesFetchProfileUseCase(fetchProfileRepository: FetchProfileRepository) : FetchProfileUseCase{
        return FetchProfileUseCase(fetchProfileRepository)
    }
}