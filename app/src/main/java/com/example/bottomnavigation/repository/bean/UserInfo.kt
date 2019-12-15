package com.example.bottomnavigation.repository.bean

/**
 * description
 * @date 2019/12/13
 * @author:TaoSi
 */
data class UserInfo(
    val data: Data?,
    val errorCode: Int?,
    val errorMsg: String?
)

data class Data(
    val admin: Boolean?,
    val chapterTops: List<Any?>?,
    val collectIds: List<Any?>?,
    val email: String?,
    val icon: String?,
    val id: Int?,
    val nickname: String?,
    val password: String?,
    val publicName: String?,
    val token: String?,
    val type: Int?,
    val username: String?
)