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
    @ColumnInfo(name = "voteCount") var voteCount: Int,
    @ColumnInfo(name = "video") var video: Boolean,
    @ColumnInfo(name = "posterPath") var posterPath: String,
    @ColumnInfo(name = "adult") var adult: Boolean,
    @ColumnInfo(name = "backdropPath") var backdropPath: String,
    @ColumnInfo(name = "originalLanguage") var originalLanguage: String,
    @ColumnInfo(name = "originalTitle") var originalTitle: String,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "voteAverage") var voteAverage: Double,
    @ColumnInfo(name = "overview") var overview: String,
    @ColumnInfo(name = "releaseDate") var releaseDate: Date
) : Serializable












