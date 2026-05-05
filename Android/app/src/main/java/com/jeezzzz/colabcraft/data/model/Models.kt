package com.jeezzzz.colabcraft.data.model

data class User(
    val id: Long,
    val username: String,
    val email: String,
    val bio: String?,
    val techStack: List<String>?
)

data class Hackathon(
    val id: Long? = null,
    val title: String,
    val description: String,
    val date: String,
    val organizerName: String?
)

data class Team(
    val id: Long? = null,
    val name: String,
    val hackathon: Hackathon?,
    val members: List<User> = emptyList()
)

data class Post(
    val id: Long? = null,
    val type: String, // "LOOKING_FOR_TEAM" or "LOOKING_FOR_MEMBER"
    val description: String,
    val requiredTechStack: List<String>?,
    val user: User?,
    val hackathon: Hackathon?
)

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val bio: String,
    val techStack: List<String>
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class Message(
    val id: Long? = null,
    val sender: User?,
    val team: Team?,
    val content: String,
    val timestamp: String? = null
)

data class Notification(
    val id: Long? = null,
    val message: String,
    val read: Boolean = false,
    val recipient: User? = null,
    val type: String? = null,
    val relatedEntityId: Long? = null
)
