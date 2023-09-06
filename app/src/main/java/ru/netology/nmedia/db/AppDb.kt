package ru.netology.nmedia.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.netology.nmedia.dao.event.EventDao
import ru.netology.nmedia.dao.event.EventRemoteKeyDao
import ru.netology.nmedia.dao.job.JobDao
import ru.netology.nmedia.dao.post.PostDao
import ru.netology.nmedia.dao.post.PostRemoteKeyDao
import ru.netology.nmedia.dao.wall.WallDao
import ru.netology.nmedia.dao.wall.WallRemoteKeyDao
import ru.netology.nmedia.entity.CoordinatesConverter
import ru.netology.nmedia.entity.ListIntConverter
import ru.netology.nmedia.entity.MapUsersPrevConverter
import ru.netology.nmedia.entity.event.EventEntity
import ru.netology.nmedia.entity.event.EventRemoteKeyEntity
import ru.netology.nmedia.entity.job.JobEntity
import ru.netology.nmedia.entity.post.PostEntity
import ru.netology.nmedia.entity.post.PostRemoteKeyEntity
import ru.netology.nmedia.entity.wall.WallEntity
import ru.netology.nmedia.entity.wall.WallRemoteKeyEntity

@Database(
    entities = [
        PostEntity::class, PostRemoteKeyEntity::class,
        WallEntity::class, WallRemoteKeyEntity::class,
        EventEntity::class, EventRemoteKeyEntity::class,
        JobEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(ListIntConverter::class, MapUsersPrevConverter::class, CoordinatesConverter::class)
abstract class AppDb : RoomDatabase() {

    abstract fun postDao(): PostDao
    abstract fun postRemoteKeyDao(): PostRemoteKeyDao
    abstract fun wallDao(): WallDao
    abstract fun wallRemoteKeyDao(): WallRemoteKeyDao
    abstract fun eventDao(): EventDao
    abstract fun eventRemoteKeyDao(): EventRemoteKeyDao
    abstract fun jobDao(): JobDao
}