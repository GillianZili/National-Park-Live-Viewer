package com.example.nationalparkliveviewerapp.ui

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.nationalparkliveviewerapp.R

class PK_RecyclerViewAdapter public constructor(
    val context: Context,
    val parkModels: ArrayList<ParkModel>
) : RecyclerView.Adapter<PK_RecyclerViewAdapter.MyViewHolder>() {
    // <> generic
    // Types of RecyclerView.Adapter:
    //    The ViewHolder type is used to tell the adapter which view container type you will use to display each list item.

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        // This is where you inflate the layout (Giving a look to our rows)
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_view_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //assigning values to the views we created in the recycler_view_row layout file
        holder.tvName.text = parkModels[position].parkName
        holder.tvLocation.text = parkModels[position].parkLocation
        holder.title.text=parkModels[position].title
//        holder.imageView.setImageResource(parkModels[position].image)
        Glide.with(holder.itemView.context)
            .load(parkModels[position].imageUrl)
            .placeholder(R.drawable.not_found)
            .error(R.drawable.waterfall)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
            .into(holder.imageView)


        holder.itemView.setOnClickListener{
            val park = parkModels[position]
            val intent = Intent(holder.itemView.context, WebViewActivity::class.java)
            intent.putExtra("URL", parkModels[position].webcamUrl)
            holder.itemView.context.startActivity(intent)
//            Toast.makeText(holder.itemView.context, "Clicked on: ${park.parkName}", Toast.LENGTH_SHORT).show()
            Log.d("ButtonClick", "Button clicked!")
        }
    }

    override fun getItemCount(): Int {
        //how many items do you have in total
        return parkModels.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        // grabbing the views from out recycler_view_row layout file
        // kinda like in the onCreate method
        // Finds a view that was identified by the android:id XML attribute that was processed in #onCreate.
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val tvName: TextView = itemView.findViewById(R.id.parkName)
        val tvLocation: TextView = itemView.findViewById(R.id.parkLocation)
        val title: TextView = itemView.findViewById(R.id.Title)
    }
}