package com.mrtcnkryln.image_edit.ui.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mrtcnkryln.image_edit.R
import com.mrtcnkryln.image_edit.model.CandidateModel
import com.mrtcnkryln.image_edit.ui.view.MainActivity
import kotlinx.android.synthetic.main.column_item_image.view.*



class CandidateAdapter(val candidates : MutableList<CandidateModel>) : RecyclerView.Adapter<CandidateAdapter.CandidateViewHolder>() {
    private var context : Context? = null
    private var noneSelected = true
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CandidateViewHolder {
        this.context = parent.context
        val v = LayoutInflater.from(parent.context).inflate(R.layout.column_item_image,parent,false)
        return CandidateViewHolder(v)

    }

    override fun getItemCount(): Int {
        return candidates.size
    }


    override fun onBindViewHolder(holder: CandidateViewHolder, position: Int) {
        val candidate = candidates[position]
        if(candidate.overlayId != null) {
            holder.itemView.text.text = candidate.overlayName
            Glide.with(this.context!!).load(candidate.overlayPreviewIconUrl)
                .into(holder.itemView.image)
            if (candidate.selected!!) {
                holder.itemView.text.setTextColor(
                    ContextCompat.getColor(
                        this.context!!,
                        R.color.colorBlue
                    )
                )
                holder.itemView.imageSelected.visibility = View.VISIBLE
            } else {
                holder.itemView.text.setTextColor(
                    ContextCompat.getColor(
                        this.context!!,
                        R.color.colorWhite
                    )
                )
                holder.itemView.imageSelected.visibility = View.GONE
            }
            holder.itemView.setOnClickListener {
                selectItem(candidate)
            }
        } else {
            holder.itemView.text.text = "None"
            holder.itemView.image.setImageResource(R.mipmap.forbidden)
            if (noneSelected) {
                holder.itemView.text.setTextColor(
                    ContextCompat.getColor(
                        this.context!!,
                        R.color.colorBlue
                    )
                )
                holder.itemView.imageSelected.visibility = View.VISIBLE
            } else {
                holder.itemView.text.setTextColor(
                    ContextCompat.getColor(
                        this.context!!,
                        R.color.colorWhite
                    )
                )
                holder.itemView.imageSelected.visibility = View.GONE
            }
            holder.itemView.setOnClickListener {
                selectNone()
            }
        }

    }

    private fun selectItem(candidate : CandidateModel){
        if (this.context is MainActivity) {
            (this.context as MainActivity).selectImage(candidate)
        }
        noneSelected = false
        for (c in this.candidates) {
            c.selected = c.overlayId == candidate.overlayId
        }
        notifyDataSetChanged()
    }

    private fun selectNone(){
        if (this.context is MainActivity) {
            (this.context as MainActivity).noneSelected()
        }
        this.noneSelected = true
        for (c in this.candidates) {
            c.selected = false
        }
        notifyDataSetChanged()
    }


    class CandidateViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}