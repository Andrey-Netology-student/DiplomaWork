package ru.netology.nmedia.dao

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.netology.nmedia.dao.event.EventDao
import ru.netology.nmedia.dao.event.EventRemoteKeyDao
import ru.netology.nmedia.dao.job.JobDao
import ru.netology.nmedia.dao.post.PostDao
import ru.netology.nmedia.dao.post.PostRemoteKeyDao
import ru.netology.nmedia.dao.wall.WallDao
import ru.netology.nmedia.dao.wall.WallRemoteKeyDao
import ru.netology.nmedia.db.AppDb

//Представляет модуль DaoModule, который предоставляет зависимости для доступа к базе данных.
@InstallIn(SingletonComponent::class)
@Module
object DaoModule {
    //Предоставляет экземпляр класса PostDao
    @Provides
    fun providePostDao(db: AppDb): PostDao = db.postDao()

    //Предоставляет экземпляр класса PostRemoteKeyDao
    @Provides
    fun providePostRemoteKeyDao(db: AppDb): PostRemoteKeyDao = db.postRemoteKeyDao()

    //Предоставляет экземпляр класса WallDao, используя объект db типа AppDb.
    @Provides
    fun provideWallKeyDao(db: AppDb): WallDao = db.wallDao()

    //Предоставляет экземпляр класса WallRemoteKeyDao
    @Provides
    fun provideWallRemoteKeyDao(db: AppDb): WallRemoteKeyDao = db.wallRemoteKeyDao()

    //Предоставляет экземпляр класса EventDao
    @Provides
    fun provideEventDao(db: AppDb): EventDao = db.eventDao()

    //Предоставляет экземпляр класса EventRemoteKeyDao
    @Provides
    fun provideEventRemoteKeyDao(db: AppDb): EventRemoteKeyDao = db.eventRemoteKeyDao()

    //Предоставляет экземпляр класса JobDao
    @Provides
    fun provideJobDao(db: AppDb): JobDao = db.jobDao()
}