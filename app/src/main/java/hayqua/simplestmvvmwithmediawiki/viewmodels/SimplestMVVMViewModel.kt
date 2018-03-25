/*
 * Copyright (c) 2017 Son Viet Au
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package hayqua.simplestmvvmwithmediawiki.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import hayqua.simplestmvvmwithmediawiki.SimplestMVVMApplication
import hayqua.simplestmvvmwithmediawiki.models.MediaWikiRepository
import hayqua.simplestmvvmwithmediawiki.room.entities.Entry
import hayqua.simplestmvvmwithmediawiki.room.entities.Search
import hayqua.simplestmvvmwithmediawiki.utils.getByKeywords
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.util.*
import javax.inject.Inject


/**
 * Created by Son Au on 18/03/2018.
 */
class SimplestMVVMViewModel(application: Application) : AndroidViewModel(application) {

    // Candidate for DI
    @Inject
    lateinit var mediaWikiRepo: MediaWikiRepository

    // Use live data to display stuffs on the mainTV
    val messageToDisplay: MutableLiveData<String> = MutableLiveData()

    val searchedKeywords: MutableLiveData<Vector<Search>> = MutableLiveData()

    // Search result
    val searchWikiResult: MutableLiveData<List<Entry?>> = MutableLiveData()

    init {
        getApplication<SimplestMVVMApplication>().daggerAppComponent.inject(this)

        Thread {
            val persistedKeywordsV = Vector<Search>()
            getApplication<SimplestMVVMApplication>().db.searchDao().getAll().let {list ->
                (0 until list.size).map {index ->
                    persistedKeywordsV.addElement(list.get(index))
                }
            }
            searchedKeywords.postValue(persistedKeywordsV)
        }.start()
    }

    fun searchWiki2(query: String) {

        val search = searchedKeywords.value!!.getByKeywords(query)
        if (search != null) {
            Thread {
                val list = getApplication<SimplestMVVMApplication>().db.entryDao().getSearchedEntries(search.id)
                searchWikiResult.postValue(list)
            }.start()

        } else {
            mediaWikiRepo.getSearchWikiCall(query).enqueue(object : Callback {
                override fun onResponse(call: Call?, response: Response?) {
                    //Everything is ok, show the result if not null
                    var list: List<Entry?>? = null
                    if (response?.isSuccessful == true) {

                        val newSearch = Search(query)
                        searchedKeywords.value!!.addElement(newSearch)
                        newSearch.id = getApplication<SimplestMVVMApplication>().db.searchDao().insert(newSearch)

                        list = response.body()?.string()?.let {
                            JSONObject(it)
                                    .getJSONObject("query")
                                    .getJSONArray("search")
                                    .let { array ->
                                        (0 until array.length()).map {
                                            array.getJSONObject(it)
                                        }.map {
                                            val entry = Entry(it.getString("title"), it.getString("snippet"), newSearch.id)
                                            getApplication<SimplestMVVMApplication>().db.entryDao().insert(entry)
                                            entry
                                        }
                                    }
                        }

                        messageToDisplay.postValue("Search completed successfully")

                    } else {
                        messageToDisplay.postValue("Search was not successful: ${response?.message()}")
                    }
                    if (list == null) list = List(0, { null })
                    searchWikiResult.postValue(list)
                }

                override fun onFailure(call: Call?, t: IOException?) {

                    searchWikiResult.postValue(List(0, { Entry("abc", "def", 0) }))
                    messageToDisplay.postValue("Search was not successful: ${t?.message}")
                    t?.printStackTrace()

                }
            })

        }

    }

}

