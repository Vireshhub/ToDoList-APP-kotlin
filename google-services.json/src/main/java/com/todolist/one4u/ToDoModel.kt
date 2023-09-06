package com.todolist.one4u

class ToDoModel {
    companion object factory{
        fun createList():ToDoMdole=ToDoModel()
    }
    var UTD:String?=null
    var itemDataText :String?=null
    var done:Boolean?=false
}