package com.tmdb.android.data.api;

import com.tmdb.android.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ronel on 20/03/2017.
 */
public class TmdbApi {

    private TmdbService mTmdbService;
    private static TmdbApi sSingleton;

    public static TmdbApi getInstanse() {
        if (sSingleton == null) {
            synchronized (TmdbApi.class) {
                sSingleton = new TmdbApi();
            }
        }
        return sSingleton;
    }

    private TmdbApi() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        if(BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        }else {
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        final OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ClientAuthInterceptor(BuildConfig.TMDB_API_KEY))
                .addInterceptor(logging)
                .build();

        mTmdbService = new Retrofit.Builder()
                .baseUrl(TmdbService.ENDPOINT)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(TmdbService.class);
    }

    public TmdbService getTmdbService() {
        return mTmdbService;
    }
}
