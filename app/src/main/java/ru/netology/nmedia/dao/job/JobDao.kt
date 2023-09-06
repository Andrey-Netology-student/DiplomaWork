package ru.netology.nmedia.dao.job

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.netology.nmedia.entity.event.EventEntity
import ru.netology.nmedia.entity.job.JobEntity

@Dao
interface JobDao {

    //Выполняет запрос к базе данных и возвращает поток (Flow) списка объектов JobEntity.
    //Записи сортируются по убыванию идентификатора (id).
    @Query("SELECT * FROM JobEntity ORDER BY id DESC")
    fun getAll(): Flow<List<JobEntity>>

    //Вставляет одну запись JobEntity в базу данных.
    //Если уже существует запись с таким же ключом, она будет заменена.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: JobEntity)

    //Вставляет список записей JobEntity в базу данных.
    // Если уже существуют записи с такими же ключами, они будут заменены.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(posts: List<JobEntity>)

    //Удаляет запись из таблицы JobEntity в базе данных по заданному идентификатору id.
    @Query("DELETE FROM JobEntity WHERE id = :id")
    suspend fun removeById(id: Int)

    //Удаляет все записи из таблицы JobEntity в базе данных.
    @Query("DELETE FROM JobEntity")
    suspend fun removeAll()
}