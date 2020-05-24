package io.ades.data

class User(
    val id : String,
    val phone : String,
    val name : String
){
    companion object{
        val NO_USER = User("", "", "")
    }
}