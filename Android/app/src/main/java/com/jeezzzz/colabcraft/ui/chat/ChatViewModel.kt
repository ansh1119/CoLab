package com.jeezzzz.colabcraft.ui.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeezzzz.colabcraft.data.model.Message
import com.jeezzzz.colabcraft.data.model.Team
import com.jeezzzz.colabcraft.data.model.User
import com.jeezzzz.colabcraft.data.repository.CollabRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: CollabRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val teamId: Long = checkNotNull(savedStateHandle["teamId"]).toString().toLong()

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages

    val currentUser: StateFlow<User?> = repository.userFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    init {
        startPolling()
    }

    private fun startPolling() {
        viewModelScope.launch {
            while (isActive) {
                repository.getMessages(teamId).onSuccess { msgs ->
                    _messages.value = msgs.reversed()
                }
                delay(3000)
            }
        }
    }

    fun sendMessage(content: String) {
        if (content.isBlank()) return
        viewModelScope.launch {
            val user = repository.userFlow.firstOrNull() ?: return@launch
            val message = Message(
                sender = user,
                team = Team(id = teamId, name = "", hackathon = null),
                content = content
            )
            repository.sendMessage(message).onSuccess {
                repository.getMessages(teamId).onSuccess { msgs ->
                    _messages.value = msgs.reversed()
                }
            }
        }
    }
}
