package com.android.hq.ganktoutiao.network;

import com.android.hq.ganktoutiao.data.GankApi;
import com.android.hq.ganktoutiao.data.bean.AddToGankResponse;
import com.android.hq.ganktoutiao.data.bean.DailyDataResponse;
import com.android.hq.ganktoutiao.data.bean.DayHistoryResponse;
import com.android.hq.ganktoutiao.data.bean.GankDataResponse;
import com.android.hq.ganktoutiao.data.bean.SearchDataResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by heqiang on 16-9-6.
 */
public interface GankService {
    @GET("day/{year}/{month}/{day}")
    Observable<DailyDataResponse> getDailyData(
            @Path("year") int year,@Path("month") int month,@Path("day") int day);

    @GET(GankApi.GANK_DAY_HISTORY)
    Observable<DayHistoryResponse> getDayHistory();

    @GET("https://gank.io/api/"+GankApi.GANK_TODAY)
    Observable<DailyDataResponse> getToadyData();

    @GET("data/category/GanHuo/type/{category}/page/{page}/count/{pageCount}")
    Observable<GankDataResponse> getGankData(
            @Path("category") String category, @Path("pageCount") int pageCount, @Path("page") int page);

    @GET("data/category/Girl/type/Girl/page/{page}/count/{pageCount}")
    Observable<GankDataResponse> getGirlData(@Path("pageCount") int pageCount, @Path("page") int page);

    @GET("search/{keyword}/category/GanHuo/type/All/page/{page}/count/{count}")
    Observable<SearchDataResponse> searchData(
            @Path("keyword") String keyword, @Path("count") int count, @Path("page") int page);

    @FormUrlEncoded
    @POST("add2gank")
    Observable<AddToGankResponse> add2Gank(@Field("url") String url, @Field("title") String desc, @Field("author") String who,
                                @Field("type") String type,@Field("debug") boolean debug);
}
