package com.example.api_cheatsheet.api


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ExchangeDetail(
    @Json(name = "alert_notice")
    val alertNotice: String?,
    @Json(name = "centralized")
    val centralized: Boolean?,
    @Json(name = "country")
    val country: String?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "facebook_url")
    val facebookUrl: String?,
    @Json(name = "has_trading_incentive")
    val hasTradingIncentive: Boolean?,
    @Json(name = "image")
    val image: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "other_url_1")
    val otherUrl1: String?,
    @Json(name = "other_url_2")
    val otherUrl2: String?,
    @Json(name = "public_notice")
    val publicNotice: String?,
    @Json(name = "reddit_url")
    val redditUrl: String?,
    @Json(name = "slack_url")
    val slackUrl: String?,
    @Json(name = "telegram_url")
    val telegramUrl: String?,
    @Json(name = "trade_volume_24h_btc")
    val tradeVolume24hBtc: Double?,
    @Json(name = "trade_volume_24h_btc_normalized")
    val tradeVolume24hBtcNormalized: Double?,
    @Json(name = "trust_score")
    val trustScore: Int?,
    @Json(name = "trust_score_rank")
    val trustScoreRank: Int?,
    @Json(name = "twitter_handle")
    val twitterHandle: String?,
    @Json(name = "url")
    val url: String?,
    @Json(name = "year_established")
    val yearEstablished: Int?
)