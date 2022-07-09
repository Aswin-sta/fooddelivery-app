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
import com.example.food.data.cartdata

class Adaptercart(private val context:Context,private val zList:ArrayList<cartdata>):RecyclerView.Adapter<Adaptercart.ViewHolder>() {



    class ViewHolder (itemView: View):RecyclerView.ViewHolder(itemView)
    {
        val itemimage:ImageView=itemView.findViewById(R.id.imgfood)
        val foodn:TextView=itemView.findViewById(R.id.foodname)
        val vegno:TextView=itemView.findViewById(R.id.vegnon)
        val am:TextView=itemView.findViewById(R.id.amount3)
        var quantity:TextView=itemView.findViewById(R.id.totalquantity)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val View = LayoutInflater.from(parent.context)
            .inflate(R.layout.cartcard, parent, false)

        return ViewHolder(View)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var dat = zList[position]
        Glide.with(context)
            .load(dat.image).into(holder.itemimage)
        holder.foodn.text = dat.name
        holder.vegno.text = dat.Vegnon
        holder.am.text = dat.price
        holder.quantity.text=dat.quantity



    }
        override fun getItemCount(): Int {
            return zList.size
        }

    }
