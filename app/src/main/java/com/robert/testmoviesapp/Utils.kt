package com.robert.testmoviesapp

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.robert.testmoviesapp.data.model.MovieListResponse

object Utils {

    fun readJsonFromAssets(context: Context, fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }

    fun parseJsonToMovieList(jsonString: String): MovieListResponse {
        val gson = Gson()
        return gson.fromJson(jsonString, object : TypeToken<MovieListResponse>() {}.type)
    }
}