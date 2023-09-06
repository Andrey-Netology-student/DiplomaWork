package ru.netology.nmedia.dao.event

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.netology.nmedia.entity.event.EventRemoteKeyEntity

@Dao
interface EventRemoteKeyDao {

    // Проверяет, пуста ли таблица EventEntity
    @Query("SELECT COUNT(*) == 0 FROM EventEntity")
    suspend fun isEmpty(): Boolean

    // Возвращает максимальное значение id в таблице EventRemoteKeyEntity
    @Query("SELECT max(`id`) FROM EventRemoteKeyEntity")
    suspend fun max(): Int?

    // Возвращает минимальное значение id в таблице EventRemoteKeyEntity
    @Query("SELECT min(`id`) FROM EventRemoteKeyEntity")
    suspend fun min(): Int?

    // Вставляет объект EventRemoteKeyEntity в таблицу
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(key: EventRemoteKeyEntity)

    // Вставляет список объектов EventRemoteKeyEntity в таблицу
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keys: List<EventRemoteKeyEntity>)

    @Query("DELETE FROM EventRemoteKeyEntity")
    suspend fun removeAll() //Удаляет все записи из таблицы EventRemoteKeyEntity
}