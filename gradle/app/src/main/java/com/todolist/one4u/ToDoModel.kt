package com.todolist.one4u

class ToDoModel {
    companion object Factory{
        fun createList():ToDoModel=ToDoModel()
    }
    var UTD:String?=null
    var itemDataText :String?=null
    var done:Boolean?=false
}