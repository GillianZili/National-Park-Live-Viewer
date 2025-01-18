package com.example.nationalparkliveviewerapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PK_RecyclerViewAdapter public constructor(
    val context: Context,
    val parkModels: ArrayList<ParkModel>
) : RecyclerView.Adapter<PK_RecyclerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        // This is where you inflate the layout (Giving a look to our rows)
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_view_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: PK_RecyclerViewAdapter.MyViewHolder, position: Int) {
        //assigning values to the views we created in the recycler_view_row layout file when it comes back to the screen
        holder.tvName.text = parkModels[position].parkName
        holder.tvLocation.text = parkModels[position].parkLocation
        holder.imageView.setImageResource(parkModels[position].image)
    }

    override fun getItemCount(): Int {
        //how many items do you have in total
        return parkModels.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        // grabbing the views from out recycler_view_row layout file
        // kinda like in the onCreate method
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val tvName: TextView = itemView.findViewById(R.id.parkName)
        val tvLocation: TextView = itemView.findViewById(R.id.parkLocation)
    }
}