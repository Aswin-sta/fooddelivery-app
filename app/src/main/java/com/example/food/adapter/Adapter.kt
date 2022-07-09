package com.example.food.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food.CellClickListener
import com.example.food.R
import com.example.food.data.hoteldata

data class adapter(private val context:Context, private val list:ArrayList<hoteldata>, private val cellClickListener: CellClickListener,private val collection:String) :RecyclerView.Adapter<adapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        val View = LayoutInflater.from(parent.context)
            .inflate(R.layout.items_view, parent, false)

        return ViewHolder(View)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var iitem = list[position]
        holder.hotelplace.text = iitem.place
        holder.hotelname.text = iitem.hotelname
        holder.price.text = iitem.price
        holder.rating.text=iitem.rating
        //images
        Glide.with(context)
            .load(iitem.images).into(holder.itemimage)
        holder.toucharea.setOnClickListener {
            cellClickListener.onCellClickListener1(iitem.hotelname,collection)
        }
    }
    override fun getItemCount(): Int {

        return list.size


    }



    class ViewHolder (itemView: View):RecyclerView.ViewHolder(itemView){
        var itemimage:ImageView=itemView.findViewById(R.id.hotelimage)
        var hotelplace:TextView=itemView.findViewById(R.id.address)
        var hotelname:TextView=itemView.findViewById(R.id.hotelname2)
        var price:TextView=itemView.findViewById(R.id.amount2)
        var rating:TextView=itemView.findViewById(R.id.rating)
        var toucharea:CardView=itemView.findViewById(R.id.hotellisttouch)

        }

    }

