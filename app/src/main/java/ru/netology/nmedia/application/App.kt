package ru.netology.nmedia.application

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp
import ru.netology.nmedia.BuildConfig

// Класс приложения, расширяющий Application
@HiltAndroidApp
class App : Application() {
    private val mapkitApiKey = BuildConfig.API_KEY
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(mapkitApiKey)
    }
}