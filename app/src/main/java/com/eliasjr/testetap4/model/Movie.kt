package com.eliasjr.testetap4.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity
data class Movie(
    @PrimaryKey(autoGenerate = false) var id: Int,
    @ColumnInfo(name = "popularity") var popularity: Double,
    @ColumnInfo(name = "vote_count") var vote_count: Int,
    @ColumnInfo(name = "video") var video: Boolean,
    @ColumnInfo(name = "poster_path") var poster_path: String,
    @ColumnInfo(name = "adult") var adult: Boolean,
    @ColumnInfo(name = "backdrop_path") var backdrop_path: String,
    @ColumnInfo(name = "original_language") var original_language: String,
    @ColumnInfo(name = "original_title") var original_title: String,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "vote_average") var vote_average: Double,
    @ColumnInfo(name = "overview") var overview: String,
    @ColumnInfo(name = "release_date") var release_date: Date
) : Serializable












