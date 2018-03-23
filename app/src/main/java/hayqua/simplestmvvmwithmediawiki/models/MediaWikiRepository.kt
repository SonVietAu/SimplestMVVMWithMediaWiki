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

package hayqua.simplestmvvmwithmediawiki.models

import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

/**
 * Created by Son Au on 18/03/2018. Modified/Extracted from RayWenderlich.com, 'Dependency Injection in Android with Dagger 2 and Kotlin' tutorial by Joe Howard and Dario Coletto. Thanks Ray, Joe and Dario :)
 */
class MediaWikiRepository @Inject constructor(private val client: OkHttpClient) {

    private val PROTOCOL = "https"
    private val LANGUAGE = "en"
    private val BASE_URL = "wikipedia.org/w/api.php"
    private val SUMMARY_URL = "wikipedia.org/api/rest_v1/page/summary"

    fun getSearchWikiCall(query: String): Call {
        val requestBuilder = HttpUrl.parse("${PROTOCOL}://${LANGUAGE}.${BASE_URL}")?.newBuilder()

        val urlBuilder = requestBuilder
                ?.addQueryParameter("action", "query")
                ?.addQueryParameter("list", "search")
                ?.addQueryParameter("format", "json")
                ?.addQueryParameter("srsearch", query)

        return Request.Builder()
                .url(urlBuilder?.build())
                .get()
                .build()
                .let {
                    client.newCall(it)
                }
    }

    fun getSummaryCall(title: String): Call {

        val requestURLBuilder = HttpUrl.parse("${PROTOCOL}://${LANGUAGE}.${SUMMARY_URL}/$title")?.newBuilder()

        // OkHttpClient will be use a second time in this object, will inject
        return Request.Builder()
                .url(requestURLBuilder?.build())
                .get()
                .build()
                .let {
                    client.newCall(it)
                }
    }
}

