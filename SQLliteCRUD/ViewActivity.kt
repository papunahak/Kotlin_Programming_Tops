package com.example.sqlliteex

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class ViewActivity : AppCompatActivity()
{
    lateinit var listview:ListView
    lateinit var list:MutableList<Model>
    var arrayList:ArrayList<HashMap<String,String>> = ArrayList()
    lateinit var dbHelper: DbHelper

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        listview = findViewById(R.id.list)
        list = ArrayList()

        dbHelper = DbHelper(applicationContext)

        list = dbHelper.viewdata()

        for(i in list)
        {
            var hm = HashMap<String,String>()
            hm["name"] = i.name
            hm["number"] = i.num

            arrayList.add(hm)
        }

        var from = arrayOf("name","number")
        var to = intArrayOf(R.id.txt1,R.id.txt2)

        var adapter = SimpleAdapter(applicationContext,arrayList,R.layout.design,from,to)
        listview.adapter=adapter

        registerForContextMenu(listview)

    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?)
    {
        var m1 = menu!!.add(0,1,0,"update")
        var m2 = menu!!.add(0,2,0,"delete")
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean
    {

        var acm:AdapterContextMenuInfo = item.menuInfo as AdapterContextMenuInfo
        var id  = acm.position
        var id2 = list.get(id).id
        when(item.itemId)
        {
            1->
            {
                var i = Intent(applicationContext,UpdateActivity::class.java)
                i.putExtra("id",list.get(id).id)
                i.putExtra("name",list.get(id).name)
                i.putExtra("num",list.get(id).num)
                startActivity(i)
            }
            2->
            {
                var alertDialog = AlertDialog.Builder(this)

                alertDialog.setTitle("Are you sure you want to delete?")

                alertDialog.setPositiveButton("YES",{ dialogInterface: DialogInterface, i: Int ->

                    dbHelper.deletedata(id2)
                    startActivity(Intent(applicationContext,ViewActivity::class.java))

                })
                alertDialog.setNegativeButton("NO",{ dialogInterface: DialogInterface, i: Int ->

                    dialogInterface.cancel()

                })

                alertDialog.show()
            }
        }

        return super.onContextItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


}