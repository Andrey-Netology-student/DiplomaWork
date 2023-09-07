package ru.netology.nmedia.repository.post

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.netology.nmedia.dto.FeedItem
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.MediaModel

interface PostRepository {

    val data: Flow<PagingData<FeedItem>>
    fun userWall(id: Int): Flow<PagingData<FeedItem>>
    suspend fun likeById(post: Post)
    suspend fun save(post: Post)
    suspend fun saveWithAttachment(post: Post, media: MediaModel)
    suspend fun removeById(id: Int)
    suspend fun getById(id: Int): Post?
    suspend fun wallRemoveById(id: Int)
}