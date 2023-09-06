package com.todolist.one4u

interface UpdateAndDelete{
    fun modifyItem(itemUID :String,isDone:Boolean)
    fun onItemDelete(itemUID : String)
}