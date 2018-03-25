/*
 * Copyright (c) 2017 Son Viet au
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

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import hayqua.simplestmvvmwithmediawiki.R
import hayqua.simplestmvvmwithmediawiki.room.entities.Entry
import hayqua.simplestmvvmwithmediawiki.utils.parseHtml
import hayqua.simplestmvvmwithmediawiki.views.detail.DetailActivity

class EntryAdapter(private val context: Context, var results: List<Entry?>) : RecyclerView.Adapter<EntryAdapter.EntryHolder>() {

    fun notifyResultsChanged(newResultsList: List<Entry?>) {
        this.results = newResultsList
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): EntryHolder {
        val entryHolder = EntryHolder(LayoutInflater.from(context).inflate(R.layout.wiki_result, parent, false))
        return entryHolder
    }

    override fun onBindViewHolder(holder: EntryHolder?, position: Int) {
        holder?.let {
            it.titleView.text = results[position]?.title
            it.snippetView.text = results[position]?.snippet.parseHtml()
        }
    }

    override fun onViewAttachedToWindow(holder: EntryHolder?) {
        super.onViewAttachedToWindow(holder)
        holder?.itemView?.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("title", results[holder.position]?.title)
            }
            context.startActivity(intent)
        }
    }

    override fun onViewDetachedFromWindow(holder: EntryHolder?) {
        holder?.itemView?.setOnClickListener(null)
        super.onViewDetachedFromWindow(holder)
    }

    override fun getItemCount() = results.size

    inner class EntryHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.findViewById(R.id.title_tv)
        val snippetView: TextView = view.findViewById(R.id.snippet_tv)
    }
}