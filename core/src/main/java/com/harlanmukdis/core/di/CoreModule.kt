package com.harlanmukdis.core.di

import androidx.room.Room
import com.harlanmukdis.core.BuildConfig
import com.harlanmukdis.core.data.MainRepository
import com.harlanmukdis.core.data.MovieRepository
import com.harlanmukdis.core.data.ReviewRepository
import com.harlanmukdis.core.data.source.local.LocalDataSource
import com.harlanmukdis.core.data.source.local.room.MyDataBase
import com.harlanmukdis.core.data.source.remote.MoviesPagingSource
import com.harlanmukdis.core.data.source.remote.MoviesRemoteDataSource
import com.harlanmukdis.core.data.source.remote.RemoteDataSource
import com.harlanmukdis.core.data.source.remote.ReviewsRemoteDataSource
import com.harlanmukdis.core.data.source.remote.network.ApiService
import com.harlanmukdis.core.domain.repository.IMainRepository
import com.harlanmukdis.core.domain.repository.IMovieRepository
import com.harlanmukdis.core.domain.repository.IReviewRepository
import com.harlanmukdis.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<MyDataBase>().genreDao() }
    factory { get<MyDataBase>().movieDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("harlanmukdis".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            MyDataBase::class.java, "mymvvm.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .allowMainThreadQueries()
            .build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
//            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single {
        LocalDataSource(
            get(),
            get()
        )
    }
    single { RemoteDataSource(get()) }
    single { MoviesRemoteDataSource(get()) }
    single { ReviewsRemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IMainRepository> {
        MainRepository(
            get(),
            get(),
            get()
        )
    }
    single<IMovieRepository> {
        MovieRepository(get())
    }
    single<IReviewRepository> {
        ReviewRepository(get())
    }
}