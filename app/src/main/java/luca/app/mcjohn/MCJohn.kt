package luca.app.mcjohn


import android.app.Application
import org.koin.android.ext.android.startKoin

class MCJohn : Application() {
    override fun onCreate() {
        super.onCreate()

        // dependency injection
        startKoin(this, listOf(appModule))
    }
}
