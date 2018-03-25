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

package hayqua.simplestmvvmwithmediawiki.views.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import hayqua.simplestmvvmwithmediawiki.R
import hayqua.simplestmvvmwithmediawiki.room.entities.Entry
import hayqua.simplestmvvmwithmediawiki.viewmodels.SimplestMVVMViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProviders.of(this).get(SimplestMVVMViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainRV.layoutManager = LinearLayoutManager(this)

        // Create the observer which updates the UI.
        val nameObserver = Observer<List<Entry?>> {
            // update the recycler adapter with the new data or call data refresh function
            if (it != null) {
                if (mainRV.adapter !is EntryAdapter) {
                    mainRV.adapter = EntryAdapter(this@MainActivity, it)
                } else {
                    (mainRV.adapter as EntryAdapter).notifyResultsChanged(it)
                }
            }
        }
        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        viewModel.searchWikiResult.observe(this, nameObserver)

        val messageObserver = Observer<String> {
            messageTV.text = it
        }
        viewModel.messageToDisplay.observe(this, messageObserver)

    }

    fun searchWiki(clickedView: View) {

        val query = keywordET.text.toString()
        viewModel.searchWiki2(query)

    }
}

