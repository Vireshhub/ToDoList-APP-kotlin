package com.todolist.one4u

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    lateinit var database:DatabaseReference
    var toDoList:MutableList<ToDoModel>?=null

    lateinit var adapter: ToDoAdapter

    private var listViewItem:ListView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fab= findViewById<View>(R.id.fab)as FloatingActionButton
        listViewItem=findViewById<ListView>(R.id.item_textView)
        database=FirebaseDatabase.getInstance().reference
        fab.setOnClickListener{ view ->
            val alertDialog=AlertDialog.Builder(this)
            val textEditText= EditText(this)
            alertDialog.setMessage("Add ToDo item")
            alertDialog.setTitle("Enter To Do item")
            alertDialog.setView("textEditText")
            alertDialog.setPositiveButton("Add"){dialog,i ->
                val todoItemData=ToDoModel.createList()
                todoItemData.itemDataText=textEditText.text.toString()
                todoItemData.done=false

                val newItemData=database.child("todo").push()
                todoItemData.UID=newItemData.key

                newItemData.setValue(todoItemData)

                dialog.dismiss()
               Toast.makeText(this,"item saved",Toast.LENGTH_LONG).show()


            }
            alertDialog.show()
        }
        toDOList= mutableListOf<ToDoModel>()
        adapter=ToDoAdapter(this,toDoList!!)
        listViewItem!!.adapter=adapter
        database.addValueEventListener(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,"No item Added",Toast.LENGTH_LONG).show()

            }
            override fun onDataChange(snapshot:DataSnapshot){
                toDoList!!.clear()
                addItemToList(snapshot)
            }


        })
    }
    private fun addItemTolist(snapshot: DataSnapshot){
        val items=snapshot.children.iterator()
        if(items.hasNext()){
            val toDoindexedValue=items.next()
            val itemsIterator=toDoindexedValue.children.iterator()
            while(itemsIterator.hasNext()){
                val currentItem=itemsIterator.next()
                val toDoItemData =ToDoModel.createList()
                val map =currentItem.getvalue() as HashMap<String,Any>

                toDoItemData.UID=currentItem.key
                toDoItemData.done=map.get("done") as Boolean?
                toDoItemData.itemDataText=map.get("itemTextData") as String?
                toDoList!!.add(toDoItemData)


            }
        }
        adapter.notifyDataSetChanged()




    }
}