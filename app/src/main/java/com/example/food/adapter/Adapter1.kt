package com.example.food

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class adapter1(private val context:Context,private val zList:ArrayList<data1>,private val cellClickListener: CellClickListener,private val collection:String):RecyclerView.Adapter<adapter1.ViewHolder>() {


    class ViewHolder (itemView: View):RecyclerView.ViewHolder(itemView)
     {
         val itemimage:ImageView=itemView.findViewById(R.id.imgfood)
         val foodn:TextView=itemView.findViewById(R.id.foodname)
         val vegno:TextView=itemView.findViewById(R.id.vegnon)
         val am:TextView=itemView.findViewById(R.id.amount3)
         var layout:ConstraintLayout=itemView.findViewById(R.id.layoutfood)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val View = LayoutInflater.from(parent.context)
            .inflate(R.layout.hotelfood, parent, false)

        return ViewHolder(View)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var dat=zList[position]
        //
        Glide.with(context)
            .load(dat.image).into(holder.itemimage)

        holder.foodn.text=dat.name
        holder.vegno.text=dat.veg
        holder.am.text=dat.price
        holder.layout.setOnClickListener{
            cellClickListener.onCellClickListener(dat.name,collection)
        }
    }

    override fun getItemCount(): Int {
        return zList.size
    }

    
}