package com.kotlin.panel.mvp.model.bean

import com.squareup.moshi.Json

/**
 * 基础类型返回
 */
data class HttpResult<T>(
    @Json(name = "data") val data: T
) : BaseBean()


// 用户个人信息
data class UserInfoBody(
    @Json(name = "coinCount") val coinCount: Int, // 总积分
    @Json(name = "rank") val rank: Int, // 当前排名
    @Json(name = "userId") val userId: Int,
    @Json(name = "username") val username: String
)

