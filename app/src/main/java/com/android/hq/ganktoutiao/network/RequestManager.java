package com.android.hq.ganktoutiao.network;

import android.util.Log;

import com.android.hq.ganktoutiao.BuildConfig;
import com.android.hq.ganktoutiao.data.GankApi;
import com.android.hq.ganktoutiao.data.bean.AddToGankResponse;
import com.android.hq.ganktoutiao.data.bean.DailyDataResponse;
import com.android.hq.ganktoutiao.data.bean.GankDataResponse;
import com.android.hq.ganktoutiao.data.bean.SearchDataResponse;
import com.android.hq.ganktoutiao.utils.AppUtils;
import com.facebook.stetho.okhttp3.StethoInterceptor;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Locale;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by heqiang on 16-9-6.
 */
public class RequestManager {
    private static final String TAG = "RequestManager";
    private static final int MAX_AGE = 4 * 60 * 60;//缓存4个小时；
    private static final int CACHE_SIZE = 10 * 1024 * 1024;//缓存10M；
    private static SimpleDateFormat sDataFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    private static RequestManager sInstance;
    private GankService mGankService;
    private RequestManager(){
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) {
                Request request = chain.request();
                Response response = null;
                try {
                    response = chain.proceed(request);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(request.url().toString().startsWith(GankApi.GANK_BASE_URL) && !request.url().toString().startsWith(GankApi.GANK_SEARCH_URL)) {
                    return response.newBuilder()
                            .header("Cache-Control", "max-age=" + MAX_AGE)
                            .build();
                }
                return response;
            }
        };

        File cacheDirectory = new File(AppUtils.getCacheDir(), "responses");
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
                //.cache(new Cache(cacheDirectory, CACHE_SIZE))
                //.addNetworkInterceptor(interceptor);
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(new StethoInterceptor());
        }
        OkHttpClient client = builder.build();
        //client.setConnectTimeout(2000, TimeUnit.MILLISECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(GankApi.GANK_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mGankService = retrofit.create(GankService.class);
    }
    public static RequestManager getInstance(){
        if(sInstance == null){
            synchronized (RequestManager.class){
                if(sInstance == null){
                    sInstance = new RequestManager();
                }
            }
        }
        return sInstance;
    }

    public void getDailyData(final CallBack<DailyDataResponse> callback){
        mGankService.getToadyData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DailyDataResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "getDailyData onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail();
                        Log.e(TAG, "getDailyData onError");
                    }

                    @Override
                    public void onNext(DailyDataResponse dailyDataResponse) {
                        callback.onSuccess(dailyDataResponse);
                        Log.d(TAG, "getDailyData onNext");
                    }
                });
        /*
        mGankService.getDayHistory()
                .filter(new Func1<DayHistoryResponse, Boolean>() {

                    @Override
                    public Boolean call(DayHistoryResponse dayHistoryResponse) {
                        return dayHistoryResponse != null && dayHistoryResponse.results != null
                                && dayHistoryResponse.results.size() > 0;
                    }
                })
                .map(new Func1<DayHistoryResponse, Calendar>() {
                    @Override
                    public Calendar call(DayHistoryResponse dayHistoryResponse) {
                        Calendar c = Calendar.getInstance(Locale.CHINA);
                        try {
                            c.setTime(sDataFormat.parse(dayHistoryResponse.results.get(0)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                            c = null;
                        }
                        return c;
                    }
                })
                .filter(new Func1<Calendar, Boolean>() {
                    @Override
                    public Boolean call(Calendar calendar) {
                        return calendar != null;
                    }
                })
                .flatMap(new Func1<Calendar, Observable<DailyDataResponse>>() {
                    @Override
                    public Observable<DailyDataResponse> call(Calendar calendar) {
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH) + 1;
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        Log.e("Test", "year = " + year + ",month=" + month + ",day=" + day);
                        return mGankService.getDailyData(year, month, day);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DailyDataResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "getDailyData onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail();
                        Log.e(TAG, "getDailyData onError");
                    }

                    @Override
                    public void onNext(DailyDataResponse dailyDataResponse) {
                        callback.onSuccess(dailyDataResponse);
                        Log.d(TAG, "getDailyData onNext");
                    }
                });
                */
    }

    public void getGankData(String category, int pageCount, int page, final CallBack<GankDataResponse> callback){
        mGankService.getGankData(category, pageCount, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankDataResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail();
                    }

                    @Override
                    public void onNext(GankDataResponse gankDataResponse) {
                        callback.onSuccess(gankDataResponse);
                    }
                });
    }

    public void getSearchData(String keyWord, int pageCount, int page, final CallBack<SearchDataResponse> callback){
        mGankService.searchData(keyWord, pageCount, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchDataResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail();
                    }

                    @Override
                    public void onNext(SearchDataResponse gankDataResponse) {
                        callback.onSuccess(gankDataResponse);
                    }
                });
    }

    public void add2Gank(String url, String desc, String who, String type, final CallBack<AddToGankResponse> callback){
        String desc_encode = "";
        String who_encode = "";

        try {
            desc_encode = URLEncoder.encode(desc,"UTF-8");
            who_encode = URLEncoder.encode(who,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mGankService.add2Gank(url, desc_encode, who_encode, type, false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AddToGankResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail();
                    }

                    @Override
                    public void onNext(AddToGankResponse addToGankResponse) {
                        callback.onSuccess(addToGankResponse);
                    }
                });
    }
}
