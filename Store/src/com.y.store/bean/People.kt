package com.y.store.bean

data class People(var nick: String, var age: Int, var sex: Int,var address: String){

    constructor(nick: String,age: Int,sex: Int) : this(nick,age, sex, "")

}
