package com.example.bottomnavigation.api

import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * description
 * @date 2019/12/12
 * @author:TaoSi
 */
interface CollectApi {
    /**收藏文章列表*/
    @GET("collect/list/{page}/json")
    fun getCollectedPaper(@Path("page") page: Int)

    /**收藏站内文章*/
    @POST("lg/collect/{Id}/json")
    @FormUrlEncoded
    fun collectForumPaper(@Path("Id") paperId: Long)
}