package hayqua.simplestmvvmwithmediawiki

import android.app.Application
import android.arch.persistence.room.Room
import hayqua.simplestmvvmwithmediawiki.dependencyinjection.AppComponent
import hayqua.simplestmvvmwithmediawiki.dependencyinjection.AppModule
import hayqua.simplestmvvmwithmediawiki.dependencyinjection.DaggerAppComponent
import hayqua.simplestmvvmwithmediawiki.room.SimpleRoomDatabase

/**
 * Created by Son Au on 21/03/2018.
 */
class SimplestMVVMApplication : Application() {

    lateinit var daggerAppComponent: AppComponent
    lateinit var db: SimpleRoomDatabase

    override fun onCreate() {
        super.onCreate()
        daggerAppComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
        db = Room.databaseBuilder(applicationContext,
                SimpleRoomDatabase::class.java, "simple_mvvm_room_db").build()
    }
}