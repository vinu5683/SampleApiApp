package com.avenger.nobrokerassignment.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.avenger.nobrokerassignment.R
import com.avenger.nobrokerassignment.localdatabase.SampleEntity
import com.bumptech.glide.Glide

class SampleListAdapter(
    val list: ArrayList<SampleEntity>,
    val listenerCommunicator: SampleListAdapterInterface
) : RecyclerView.Adapter<SampleListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleListViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.sample_item_layout, parent, false)
        return SampleListViewHolder(v, listenerCommunicator)
    }

    override fun onBindViewHolder(holder: SampleListViewHolder, position: Int) {
        holder.setData(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}


class SampleListViewHolder(itemView: View, val listenerCommunicator: SampleListAdapterInterface) :
    RecyclerView.ViewHolder(itemView) {

    private val mTvTitle: TextView
    private val mTvSubTitle: TextView
    private val mIvImage: ImageView
    private val mCvItemCard: CardView

    init {
        itemView.apply {
            mTvSubTitle = findViewById(R.id.tvSubTitle)
            mTvTitle = findViewById(R.id.tvTitle)
            mIvImage = findViewById(R.id.item_image)
            mCvItemCard = findViewById(R.id.item_card)
        }
    }

    fun setData(sampleEntity: SampleEntity) {
        mTvTitle.text = sampleEntity.title
        mTvSubTitle.text = sampleEntity.subTitle

        try {
            Glide.with(itemView.context).load(sampleEntity.image).into(mIvImage)
        } catch (e: Exception) {
            Glide.with(itemView.context).load(R.drawable.image_item_place_holder).into(mIvImage)
        }

        mCvItemCard.setOnClickListener {
            listenerCommunicator.handleClickEvent(sampleEntity)
        }
    }
}


interface SampleListAdapterInterface {
    fun handleClickEvent(sampleEntity: SampleEntity)
}
