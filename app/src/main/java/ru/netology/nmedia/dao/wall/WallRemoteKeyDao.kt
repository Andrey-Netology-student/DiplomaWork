package ru.netology.nmedia.dao.wall

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.netology.nmedia.entity.wall.WallRemoteKeyEntity

//Определяет интерфейс DAO (Data Access Object) для таблицы WallRemoteKeyEntity в базе данных.
@Dao
interface WallRemoteKeyDao {

    //Выполняет запрос к таблице WallRemoteKeyEntity и возвращает максимальное значение в колонке "id"
    @Query("SELECT max(`id`) FROM WallRemoteKeyEntity")
    suspend fun max(): Int? //Возвращает значение типа Integer или null, если записей в таблице нет

    //Выполняет запрос к таблице WallRemoteKeyEntity и возвращает минимальное значение в колонке "id"
    @Query("SELECT min(`id`) FROM WallRemoteKeyEntity")
    suspend fun min(): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(key: WallRemoteKeyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keys: List<WallRemoteKeyEntity>)

    @Query("DELETE FROM WallRemoteKeyEntity")
    suspend fun removeAll()
}