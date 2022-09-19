package com.harlanmukdis.mymovie

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.harlanmukdis.core.di.databaseModule
import com.harlanmukdis.core.di.networkModule
import com.harlanmukdis.core.di.repositoryModule
import com.harlanmukdis.mymovie.di.useCaseModule
import com.harlanmukdis.mymovie.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyMovieApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyMovieApp)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }

    fun isNetworkAvailable(context: Context? = null): Boolean {
        val lConnectivityManager = context?.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw      = lConnectivityManager.activeNetwork ?: return false
            val actNw = lConnectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            return lConnectivityManager.activeNetworkInfo?.isConnected ?: false
        }
    }
}