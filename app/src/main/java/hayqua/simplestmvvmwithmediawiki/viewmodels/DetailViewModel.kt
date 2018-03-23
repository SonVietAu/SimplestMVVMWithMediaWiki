package hayqua.simplestmvvmwithmediawiki.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import hayqua.simplestmvvmwithmediawiki.SimplestMVVMApplication
import hayqua.simplestmvvmwithmediawiki.models.MediaWikiRepository
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Son Au on 21/03/2018.
 */
class DetailViewModel(application: Application) : AndroidViewModel(application) {

    // Candidate for DI
    @Inject
    lateinit var mediaWikiRepo: MediaWikiRepository

    // Use live data to display stuffs on the mainTV
    val messageToDisplay: MutableLiveData<String> = MutableLiveData()

    // Search result
    val entrySummary: MutableLiveData<String?> = MutableLiveData()

    init {
        getApplication<SimplestMVVMApplication>().daggerAppComponent.inject(this)
    }

    fun getSummary(title: String) {
        mediaWikiRepo.getSummaryCall(title).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                //Everything is ok, show the result if not null
                var summary: String? = null
                if (response?.isSuccessful == true) {

                    summary = response.body()?.string()?.let {
                        JSONObject(it).getString("extract")
                    }

                    messageToDisplay.postValue("Search completed successfully")

                } else {
                    messageToDisplay.postValue("Search was not successful: ${response?.message()}")
                }
                entrySummary.postValue(summary)
            }

            override fun onFailure(call: Call?, t: IOException?) {

                entrySummary.postValue(null)
                messageToDisplay.postValue("Search was not successful: ${t?.message}")
                t?.printStackTrace()

            }
        })
    }
}