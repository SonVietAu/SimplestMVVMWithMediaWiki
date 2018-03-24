package hayqua.simplestmvvmwithmediawiki

import android.app.Application
import hayqua.simplestmvvmwithmediawiki.dependencyinjection.AppComponent
import hayqua.simplestmvvmwithmediawiki.dependencyinjection.AppModule
import hayqua.simplestmvvmwithmediawiki.dependencyinjection.DaggerAppComponent

/**
 * Created by Son Au on 21/03/2018.
 */
class SimplestMVVMApplication : Application() {

    lateinit var daggerAppComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        daggerAppComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}