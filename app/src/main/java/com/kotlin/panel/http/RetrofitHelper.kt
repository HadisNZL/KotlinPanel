package com.kotlin.panel.http

import android.R
import com.cxz.wanandroid.constant.Constant
import com.kotlin.panel.BuildConfig
import com.kotlin.panel.api.ApiService
import com.kotlin.panel.app.App
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.security.KeyManagementException
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.cert.Certificate
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManagerFactory


object RetrofitHelper {

    private var retrofit: Retrofit? = null

    val service: ApiService by lazy { getRetrofit()!!.create(ApiService::class.java) }

    private fun getRetrofit(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)  // baseUrl
                .client(getOkHttpClient())
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
        }
        return retrofit
    }

    /**
     * 获取 OkHttpClient
     */
    private fun getOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        //设置 请求的缓存的大小跟位置
        val cacheFile = File(App.context.cacheDir, "cache")
        val cache = Cache(cacheFile, HttpConstant.MAX_CACHE_SIZE)

        builder.run {
            addInterceptor(httpLoggingInterceptor)
//            addInterceptor(HeaderInterceptor())
//            addInterceptor(SaveCookieInterceptor())
//            addInterceptor(CacheInterceptor())
            cache(cache)  //添加缓存
//            getCertificata()?.let { certificatePinner(it) }
            connectTimeout(HttpConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(HttpConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(HttpConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            retryOnConnectionFailure(true) // 错误重连
            // cookieJar(CookieManager())
        }
        return builder.build()
    }

    //创建证书 https://www.jianshu.com/p/93ccaf149ae6

    /**
     * SSL Pinning 获取证书
     * @return certificata
     */
//    fun getCertificata(): CertificatePinner? {
//        var ca: Certificate? = null
//        try {
//            val cf = CertificateFactory.getInstance("X.509")
//            val caInput: InputStream =
//                App.context.resources.openRawResource(R.raw.test)
//            ca = caInput.use { caInput ->
//                cf.generateCertificate(caInput)
//            }
//        } catch (e: CertificateException) {
//            e.printStackTrace()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        var certPin = ""
//        if (ca != null) {
//            certPin = CertificatePinner.pin(ca)
//        }
//        return CertificatePinner.Builder()
//            .add(Constant.BASE_URL, certPin)
//            .build()
//    }

//    fun getSSLSocketFactory(): Unit? {
//        var sslSocketFactory: SSLSocketFactory? = null
//        try {
//            val certificateFactory =
//                CertificateFactory.getInstance("X.509")
//            val caInput: InputStream = App.context.resources.openRawResource(R.raw.ca)
//            var ca: Certificate? = null
//            try {
//                ca = certificateFactory.generateCertificate(caInput)
//            } catch (e: CertificateException) {
//                e.printStackTrace()
//            } finally {
//                caInput.close()
//            }
//            val keyStoreType: String = KeyStore.getDefaultType()
//            val keyStore: KeyStore = KeyStore.getInstance(keyStoreType)
//            keyStore.load(null, null)
//            if (ca == null) {
//                return null
//            }
//            keyStore.setCertificateEntry("ca", ca)
//            val algorithm: String = TrustManagerFactory.getDefaultAlgorithm()
//            val trustManagerFactory: TrustManagerFactory =
//                TrustManagerFactory.getInstance(algorithm)
//            trustManagerFactory.init(keyStore)
//            val sslContext: SSLContext = SSLContext.getInstance("TLS")
//            sslContext.init(null, trustManagerFactory.trustManagers, null)
//            sslSocketFactory = sslContext.socketFactory
//
//          sslSocketFactory
//        } catch (e: CertificateException) {
//            e.printStackTrace()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        } catch (e: KeyStoreException) {
//            e.printStackTrace()
//        } catch (e: NoSuchAlgorithmException) {
//            e.printStackTrace()
//        } catch (e: KeyManagementException) {
//            e.printStackTrace()
//        }
//
//    }
}