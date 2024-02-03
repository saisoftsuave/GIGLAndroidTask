package com.gigl.androidtask.models

import com.gigl.androidtask.base.ApplicationModels

@ApplicationModels
data class ImageResponse(
    val alt_description: String,
    val blur_hash: String,
    val breadcrumbs: List<Any>,
    val color: String,
    val created_at: String,
    val current_user_collections: List<Any>,
    val description: Any,
    val height: Int,
    val id: String,
    val liked_by_user: Boolean,
    val likes: Int,
    val links: Links,
    val promoted_at: String,
    val slug: String,
    val sponsorship: Any,
    val topic_submissions: TopicSubmissions,
    val updated_at: String,
    val urls: Urls,
    val user: User,
    val width: Int
)

@ApplicationModels
data class Links(
    val download: String,
    val download_location: String,
    val html: String,
    val self: String
)

@ApplicationModels
data class LinksX(
    val followers: String,
    val following: String,
    val html: String,
    val likes: String,
    val photos: String,
    val portfolio: String,
    val self: String
)

@ApplicationModels
data class ProfileImage(
    val large: String,
    val medium: String,
    val small: String
)

@ApplicationModels
data class Social(
    val instagram_username: Any,
    val paypal_email: Any,
    val portfolio_url: String,
    val twitter_username: Any
)

@ApplicationModels
data class Urls(
    val full: String,
    val raw: String,
    val regular: String,
    val small: String,
    val small_s3: String,
    val thumb: String
)

@ApplicationModels

data class User(
    val accepted_tos: Boolean,
    val bio: String,
    val first_name: String,
    val for_hire: Boolean,
    val id: String,
    val instagram_username: Any,
    val last_name: String,
    val links: LinksX,
    val location: String,
    val name: String,
    val portfolio_url: String,
    val profile_image: ProfileImage,
    val social: Social,
    val total_collections: Int,
    val total_likes: Int,
    val total_photos: Int,
    val total_promoted_photos: Int,
    val twitter_username: Any,
    val updated_at: String,
    val username: String
)