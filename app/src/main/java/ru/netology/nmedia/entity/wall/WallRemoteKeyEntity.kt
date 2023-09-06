package ru.netology.nmedia.entity.wall

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.enumeration.RemoteKeyType

@Entity
data class WallRemoteKeyEntity(
    @PrimaryKey
    val type: RemoteKeyType,
    val id: Int,
)