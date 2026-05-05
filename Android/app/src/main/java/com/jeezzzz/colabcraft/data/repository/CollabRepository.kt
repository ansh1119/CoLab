package com.jeezzzz.colabcraft.data.repository

import com.jeezzzz.colabcraft.data.local.SessionManager
import com.jeezzzz.colabcraft.data.model.*
import com.jeezzzz.colabcraft.data.remote.CollabApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CollabRepository @Inject constructor(
    private val api: CollabApi,
    private val sessionManager: SessionManager
) {

    val userFlow = sessionManager.userFlow

    suspend fun logout() {
        sessionManager.clearSession()
    }

    suspend fun register(request: RegisterRequest): Result<User> = runCatching {
        val user = api.register(request)
        sessionManager.saveUser(user)
        user
    }

    suspend fun login(request: LoginRequest): Result<User> = runCatching {
        val user = api.login(request)
        sessionManager.saveUser(user)
        user
    }

    suspend fun getHackathons(): Result<List<Hackathon>> = runCatching { api.getHackathons() }

    suspend fun getHackathonById(id: Long): Result<Hackathon> = runCatching { api.getHackathonById(id) }

    suspend fun createHackathon(hackathon: Hackathon): Result<Hackathon> = runCatching { api.createHackathon(hackathon) }

    suspend fun getPosts(): Result<List<Post>> = runCatching { api.getPosts() }

    suspend fun getPostsByHackathon(hackathonId: Long): Result<List<Post>> = runCatching { api.getPostsByHackathon(hackathonId) }

    suspend fun createPost(post: Post): Result<Post> = runCatching { api.createPost(post) }

    suspend fun getRecommendedPosts(userSkills: List<String>): Result<List<Post>> = runCatching { api.getRecommendedPosts(userSkills) }

    suspend fun likePost(id: Long): Result<Post> = runCatching { api.likePost(id) }
    
    suspend fun repostPost(id: Long): Result<Post> = runCatching { api.repostPost(id) }
    
    suspend fun commentPost(id: Long): Result<Post> = runCatching { api.commentPost(id) }

    suspend fun sendMessage(message: Message): Result<Message> = runCatching { api.sendMessage(message) }

    suspend fun getMessages(teamId: Long): Result<List<Message>> = runCatching { api.getMessages(teamId) }

    suspend fun getTeamsByUser(userId: Long): Result<List<Team>> = runCatching { api.getTeamsByUser(userId) }

    suspend fun createDm(user1Id: Long, user2Id: Long): Result<Team> = runCatching { api.createDm(user1Id, user2Id) }

    suspend fun getNotifications(userId: Long): Result<List<Notification>> = runCatching { api.getNotifications(userId) }

    suspend fun createNotification(notification: Notification): Result<Notification> = runCatching { api.createNotification(notification) }

    suspend fun markNotificationRead(id: Long): Result<Unit> = runCatching { api.markNotificationRead(id) }
}
