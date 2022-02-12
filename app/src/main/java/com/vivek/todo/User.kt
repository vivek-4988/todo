package com.vivek.todo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = 0,
    val name: String,
    val number: String,
    val address: String,
    val age: Int
)