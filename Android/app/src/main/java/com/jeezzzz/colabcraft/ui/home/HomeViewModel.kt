package com.jeezzzz.colabcraft.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeezzzz.colabcraft.data.model.Hackathon
import com.jeezzzz.colabcraft.data.model.Post
import com.jeezzzz.colabcraft.data.repository.CollabRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: CollabRepository
) : ViewModel() {

    private val _hackathons = MutableStateFlow<List<Hackathon>>(emptyList())
    val hackathons: StateFlow<List<Hackathon>> = _hackathons.asStateFlow()

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()
    
    private val _recommendedPosts = MutableStateFlow<List<Post>>(emptyList())
    val recommendedPosts: StateFlow<List<Post>> = _recommendedPosts.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadData()
        observeUser()
    }

    private fun observeUser() {
        viewModelScope.launch {
            repository.userFlow.collect { user ->
                if (user != null && !user.techStack.isNullOrEmpty()) {
                    repository.getRecommendedPosts(user.techStack).onSuccess {
                        _recommendedPosts.value = it
                    }
                } else {
                    // Fallback or empty if no user/skills
                    repository.getRecommendedPosts(listOf("Java", "Kotlin", "Android")).onSuccess {
                         _recommendedPosts.value = it
                    }
                }
            }
        }
    }

    fun loadData() {
        viewModelScope.launch {
            _isLoading.value = true
            
            // Load Hackathons
            repository.getHackathons().onSuccess { 
                _hackathons.value = it 
            }
            
            // Load Posts
            repository.getPosts().onSuccess { 
                _posts.value = it 
            }
            
            _isLoading.value = false
        }
    }

    fun startChat(targetUserId: Long, onResult: (Long) -> Unit) {
        viewModelScope.launch {
            val currentUser = repository.userFlow.firstOrNull()
            if (currentUser != null) {
                val currentUserIdLong = currentUser.id
                repository.createDm(currentUserIdLong, targetUserId).onSuccess { team ->
                     team.id?.let { onResult(it) }
                }.onFailure {
                    // Handle failure (maybe show toast, but for now log/ignore)
                    it.printStackTrace()
                }
            }
        }
    }

    fun likePost(postId: Long) {
        viewModelScope.launch {
            repository.likePost(postId).onSuccess { updated -> updatePostInLists(updated) }
        }
    }

    fun repostPost(postId: Long) {
        viewModelScope.launch {
            repository.repostPost(postId).onSuccess { updated -> updatePostInLists(updated) }
        }
    }

    fun commentPost(postId: Long) {
        viewModelScope.launch {
            repository.commentPost(postId).onSuccess { updated -> updatePostInLists(updated) }
        }
    }

    private fun updatePostInLists(updated: Post) {
        _posts.value = _posts.value.map { if (it.id == updated.id) updated else it }
        _recommendedPosts.value = _recommendedPosts.value.map { if (it.id == updated.id) updated else it }
    }
}
