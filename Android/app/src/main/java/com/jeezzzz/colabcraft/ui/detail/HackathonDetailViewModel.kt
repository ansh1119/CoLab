package com.jeezzzz.colabcraft.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeezzzz.colabcraft.data.model.Hackathon
import com.jeezzzz.colabcraft.data.model.Post
import com.jeezzzz.colabcraft.data.repository.CollabRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HackathonDetailViewModel @Inject constructor(
    private val repository: CollabRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val hackathonId: Long = checkNotNull(savedStateHandle["hackathonId"]).toString().toLong()

    private val _hackathon = MutableStateFlow<Hackathon?>(null)
    val hackathon: StateFlow<Hackathon?> = _hackathon

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val teams: StateFlow<List<Post>> = _posts

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadHackathon()
    }

    private fun loadHackathon() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getHackathonById(hackathonId).onSuccess { event ->
                _hackathon.value = event
            }.onFailure {
                // Fallback: fetch from list
                repository.getHackathons().onSuccess { list ->
                    _hackathon.value = list.find { it.id == hackathonId }
                }
            }

            repository.getPostsByHackathon(hackathonId).onSuccess { posts ->
                _posts.value = posts.filter { it.type == "LOOKING_FOR_MEMBER" }
            }
            _isLoading.value = false
        }
    }

    fun startChat(targetUserId: Long, onResult: (Long) -> Unit) {
        viewModelScope.launch {
            val currentUser = repository.userFlow.firstOrNull() ?: return@launch
            repository.createDm(currentUser.id, targetUserId).onSuccess { team ->
                team.id?.let { onResult(it) }
            }
        }
    }
}
