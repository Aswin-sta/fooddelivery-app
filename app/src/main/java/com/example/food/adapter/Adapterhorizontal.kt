package com.example.food.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food.CellClickListener
import com.example.food.R
import com.example.food.data.data

data class adapterhorizontal(private val context: Context, private val mList: ArrayList<data>, private val cellClickListener: CellClickListener,private val collection:String):RecyclerView.Adapter<adapterhorizontal.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val View = LayoutInflater.from(parent.context)
            .inflate(R.layout.hotelview, parent, false)

        return ViewHolder(View)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]
        //
        Glide.with(context)
            .load(ItemsViewModel.image).into(holder.foodimage)
        holder.foodname.text=ItemsViewModel.name
        holder.hotelname.text=ItemsViewModel.hname
        holder.pricee.text=ItemsViewModel.price
         holder.card.setOnClickListener {
             cellClickListener.onCellClickListener((ItemsViewModel.name),collection)


         }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder (itemView:View):RecyclerView.ViewHolder(itemView)
    {
        var foodimage:ImageView=itemView.findViewById(R.id.logo)
        var foodname:TextView=itemView.findViewById(R.id.foodname)
        var hotelname:TextView=itemView.findViewById(R.id.hotelname)
        var pricee:TextView=itemView.findViewById(R.id.amount1)
        var  card:ConstraintLayout=itemView.findViewById(R.id.hotelview)
        }


        }


