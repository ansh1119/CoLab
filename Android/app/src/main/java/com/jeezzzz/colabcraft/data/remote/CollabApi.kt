package com.jeezzzz.colabcraft.data.remote

import com.jeezzzz.colabcraft.data.model.*
import retrofit2.http.*

interface CollabApi {

    // Auth
    @POST("/api/auth/register")
    suspend fun register(@Body request: RegisterRequest): User

    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): User

    // Hackathons
    @GET("/api/hackathons")
    suspend fun getHackathons(): List<Hackathon>

    @GET("/api/hackathons/{id}")
    suspend fun getHackathonById(@Path("id") id: Long): Hackathon

    @POST("/api/hackathons")
    suspend fun createHackathon(@Body hackathon: Hackathon): Hackathon

    // Posts
    @GET("/api/posts")
    suspend fun getPosts(): List<Post>

    @GET("/api/posts/hackathon/{hackathonId}")
    suspend fun getPostsByHackathon(@Path("hackathonId") hackathonId: Long): List<Post>

    @POST("/api/posts")
    suspend fun createPost(@Body post: Post): Post

    @POST("/api/posts/recommend")
    suspend fun getRecommendedPosts(@Body userSkills: List<String>): List<Post>

    // Teams
    @POST("/api/teams")
    suspend fun createTeam(@Body team: Team): Team

    @POST("/api/teams/{teamId}/members/{userId}")
    suspend fun joinTeam(@Path("teamId") teamId: Long, @Path("userId") userId: Long): Team

    @GET("/api/teams/user/{userId}")
    suspend fun getTeamsByUser(@Path("userId") userId: Long): List<Team>

    @POST("/api/teams/dm")
    suspend fun createDm(@Query("user1Id") user1Id: Long, @Query("user2Id") user2Id: Long): Team

    // Messages
    @POST("/api/messages")
    suspend fun sendMessage(@Body message: Message): Message

    @GET("/api/messages/team/{teamId}")
    suspend fun getMessages(@Path("teamId") teamId: Long): List<Message>

    // Notifications
    @GET("/api/notifications/user/{userId}")
    suspend fun getNotifications(@Path("userId") userId: Long): List<Notification>

    @POST("/api/notifications")
    suspend fun createNotification(@Body notification: Notification): Notification

    @PUT("/api/notifications/{id}/read")
    suspend fun markNotificationRead(@Path("id") id: Long)
}
