package ru.netology.nmedia.dao.post

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.netology.nmedia.entity.post.PostRemoteKeyEntity

@Dao
interface PostRemoteKeyDao {
    @Query("SELECT COUNT(*) == 0 FROM PostEntity")
    suspend fun isEmpty(): Boolean //Проверяет, является ли таблица PostEntity пустой
    @Query("SELECT max(`id`) FROM PostRemoteKeyEntity")
    suspend fun max(): Int? //Возвращает максимальное значение id из таблицы PostRemoteKeyEntity

    @Query("SELECT min(`id`) FROM PostRemoteKeyEntity")
    suspend fun min(): Int? //Возвращает минимальное значение id из таблицы PostRemoteKeyEntity

    //Вставляет одну запись PostRemoteKeyEntity в базу данных. Если уже существует запись с таким же ключом, она будет заменена.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(key: PostRemoteKeyEntity)

    //Вставляет список записей PostRemoteKeyEntity в базу данных. Если уже существуют записи с такими же ключами, они будут заменены.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keys: List<PostRemoteKeyEntity>)

    @Query("DELETE FROM PostRemoteKeyEntity")
    suspend fun clear() //Удаляет все записи из таблицы PostRemoteKeyEntity в базе данных.
}