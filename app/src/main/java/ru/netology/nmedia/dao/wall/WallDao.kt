package ru.netology.nmedia.dao.wall

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.netology.nmedia.entity.wall.WallEntity

// Интерфейс Dao для доступа к данным таблицы "WallEntity" в базе данных
@Dao
interface WallDao {

    // Запрос для получения PagingSource, который предоставляет постраничный доступ к данным
    @Query("SELECT * FROM WallEntity ORDER BY id DESC")
    fun getPagingSource(): PagingSource<Int, WallEntity>

    // Запрос для получения записи с определенным идентификатором
    @Query("SELECT * FROM WallEntity WHERE id = :id")
    suspend fun getPostById(id: Long): WallEntity?

    // Запрос для проверки, пуста ли таблица "WallEntity"
    @Query("SELECT COUNT(*) == 0 FROM WallEntity")
    suspend fun isEmpty(): Boolean

    // Метод для вставки записи в таблицу "WallEntity", с заменой конфликтующих записей при совпадении идентификатора
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: WallEntity)

    // Метод для вставки списка записей в таблицу "WallEntity", с заменой конфликтующих записей при совпадении идентификатора
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(posts: List<WallEntity>)

    // Метод для удаления всех записей из таблицы "WallEntity"
    @Query("DELETE FROM WallEntity")
    suspend fun removeAll()

    // Метод для удаления записи с определенным идентификатором из таблицы "WallEntity"
    @Query("DELETE FROM WallEntity WHERE id = :id")
    suspend fun removeById(id: kotlin.Int)
}