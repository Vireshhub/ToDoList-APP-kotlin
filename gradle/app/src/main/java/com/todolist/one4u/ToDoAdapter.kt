package com.todolist.one4u
import android.view.View
import android.content.Context
import android.view.LayoutInflater

import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView



class ToDoAdapter(context: Context,toDoList:MutableList<ToDoModel>):BaseAdapter() {
    private val inflater:LayoutInflater=LayoutInflater.from(context)
    private var itemList=toDoList
    private var updateAndDelete:UpdateAndDelete=context as UpdateAndDelete
    override fun getView(p0: Int,p1: View?,p2: ViewGroup?):View {
        val UID: String=itemList.get(p0) as String
        val itemTextData=itemList.get(p0).itemDataText as String
        val done:Boolean=itemList.get(p0).done as Boolean

        val view :View
        val viewholder:ListViewholder

        if(p1==null){
            view=inflater.inflate(R.layout.row_itemlayout,p2,false)
            viewholder=ListViewholder(view)
            view.tag=viewholder

        }else{
            view=p1
            viewholder=view.tag as ListViewholder
        }
        viewholder.textlabel.text= itemTextData
        viewholder.isDone.isChecked= done

        viewholder.isDone.setOnClickListener {
            updateAndDelete.modifyItem(UID, !done)
        }
        viewholder.isDeleted.setOnClickListener{
            updateAndDelete.onItemDelete(UID)
        }
        return view

    }
   private class ListViewholder(row:View?){
       val textlabel:TextView=row!!.findViewById(R.id.item_textView)as TextView
       val isDone:CheckBox=row!!.findViewById(R.id.checkbox)as CheckBox
       val isDeleted:ImageButton=row!!.findViewById(R.id.close)as ImageButton

   }
    override fun getItem(p0:Int):Any{
        return itemList.get(p0)
    }
    override fun getItemId(p0:Int):Long {
        return p0.toLong()
    }
    override fun getCount():Int{
        return itemList.size

    }
}


