package ru.netology.nmedia.dao.post

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.netology.nmedia.entity.post.PostEntity

@Dao
interface PostDao {

    // Получить источник постраничной загрузки показываемых постов
    @Query("SELECT * FROM PostEntity ORDER BY id DESC")
    fun getPagingSource(): PagingSource<Int, PostEntity>

    @Query("SELECT * FROM PostEntity WHERE id = :id")
    suspend fun getPostById(id: Int): PostEntity?

    @Query("SELECT COUNT(*) == 0 FROM PostEntity")
    suspend fun isEmpty(): Boolean // Проверить, пуста ли таблица с постами

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: PostEntity) // Вставить пост в таблицу

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(posts: List<PostEntity>) // Вставить список постов в таблицу

    @Query("DELETE FROM PostEntity WHERE id = :id")
    suspend fun removeById(id: Long) // Удалить пост по его идентификатору

    @Query("DELETE FROM PostEntity")
    suspend fun clear() //Для удаления всех записей из таблицы PostEntity

    @Query("SELECT COUNT(*) FROM PostEntity")
    suspend fun count(): Long // Подсчитать количество постов в таблице
}