package com.jeezzzz.colabcraft.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.jeezzzz.colabcraft.data.model.User
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_session")

@Singleton
class SessionManager @Inject constructor(
    @param:ApplicationContext private val context: Context
) {

    companion object {
        private val KEY_USER_ID = longPreferencesKey("user_id")
        private val KEY_USERNAME = stringPreferencesKey("username")
        private val KEY_EMAIL = stringPreferencesKey("email")
        private val KEY_BIO = stringPreferencesKey("bio")
        private val KEY_TECH_STACK = stringPreferencesKey("tech_stack")
    }

    val userFlow: Flow<User?> = context.dataStore.data.map { preferences ->
        val id = preferences[KEY_USER_ID]
        if (id != null) {
            User(
                id = id,
                username = preferences[KEY_USERNAME] ?: "",
                email = preferences[KEY_EMAIL] ?: "",
                bio = preferences[KEY_BIO] ?: "",
                techStack = preferences[KEY_TECH_STACK]?.split(",")?.map { it.trim() } ?: emptyList()
            )
        } else {
            null
        }
    }
    
    val userIdFlow: Flow<Long?> = context.dataStore.data.map { preferences ->
        preferences[KEY_USER_ID]
    }

    suspend fun saveUser(user: User) {
        context.dataStore.edit { preferences ->
            user.id.let { preferences[KEY_USER_ID] = it }
            preferences[KEY_USERNAME] = user.username
            preferences[KEY_EMAIL] = user.email
            user.bio?.let { preferences[KEY_BIO] = it }
            user.techStack?.let { preferences[KEY_TECH_STACK] = it.joinToString(",") }
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
