package com.example.bottomnavigation.api

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * description
 * @date 2019/12/12
 * @author:TaoSi
 */
interface UserApi {
    /** 登录
     * 会拿到一个cookie
     * */
    @FormUrlEncoded
    @POST("user/login")
    fun logIn(@Field("username") userName: String, @Field("password") password: String)

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("user/register")
    fun regeister(@Field("username") userName: String, @Field("password") password: String, @Field("repassword") rePassword: String)

    /**
     * 退出
     */
    @GET("user/logout/json")
    fun logOut()
}