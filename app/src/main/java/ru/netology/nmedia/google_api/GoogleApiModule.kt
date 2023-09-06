package ru.netology.nmedia.google_api

import com.google.android.gms.common.GoogleApiAvailability
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class) //Указывает, в каком компоненте Hilt должен быть установлен этот модуль.
@Module //Аннотация "@Module" указывает, что это модуль Dagger
class GoogleApiModule {

    @Provides //Предоставляет зависимость для класса "GoogleApiAvailability"
    fun provideGoogleApiAvailability(): GoogleApiAvailability = GoogleApiAvailability.getInstance()
}