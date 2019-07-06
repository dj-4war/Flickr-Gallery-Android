package com.flickr.gallery.android.api.http

import com.flickr.gallery.android.BuildConfig
import com.flickr.gallery.android.utils.FlickrLogger
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory


/**
 * Factory class for creation of the Api Service interface
 */
object FlickrHTTPClient : IHTTPClient {

    private val TAG: String = FlickrHTTPClient::class.java.canonicalName
    private var mRetroClient: Retrofit? = null

    override fun getHttpClient(): Retrofit? {
        if (mRetroClient == null) {
            createHTTPClient()
        }
        return mRetroClient
    }

    override fun bindService(service: Any): Any {
        if (mRetroClient == null) {
            createHTTPClient()
        }
        return mRetroClient!!.create(service::class.java)
    }

    fun initialize() {
        if (mRetroClient == null) {
            createHTTPClient()
        } else {
            FlickrLogger.infoLog(TAG, "initialize() :: Base URL cannot be null")
        }
    }

    private fun createHTTPClient(): Retrofit? {
        FlickrLogger.infoLog(TAG, "createHTTPClient() :: Creating Retrofit HTTP client")

        FlickrLogger.infoLog(TAG, "createHTTPClient() :: Base URL -->" + HttpUrl.parse(BASE_URL))

        // OkHTTPLogging Logging
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG)  // If app is type of Debug we expose logs, for the production builds we never expose
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        else
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        val client = OkHttpClient.Builder().addInterceptor(interceptor)
                        //         .sslSocketFactory(TLSSocketFactory())
                                .build()

        mRetroClient = Retrofit.Builder()
            .baseUrl(HttpUrl.parse(BASE_URL))
            .addConverterFactory(
                SimpleXmlConverterFactory.create(
                    Persister(AnnotationStrategy())
                )
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
        return mRetroClient
    }
}