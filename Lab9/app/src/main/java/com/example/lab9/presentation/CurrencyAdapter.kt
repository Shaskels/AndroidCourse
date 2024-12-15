package com.example.lab9.presentation

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lab9.R
import com.example.lab9.domain.Currency

class CurrencyAdapter(
    private var context: Context,
    private val onClickListener: OnStateClickListener
) : RecyclerView.Adapter<CurrencyAdapter.MyViewHolder>() {

    private var currencies = ArrayList<Currency>()
    private var clickedView: View? = null
    private var clickedPosition: Int = -1


    interface OnStateClickListener {
        fun onStateClick(currency: Currency, position: Int)
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val charCode: TextView = view.findViewById(R.id.charCode)
        val name: TextView = view.findViewById(R.id.name)
        val value: TextView = view.findViewById(R.id.value)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.currency_in_list, parent, false)
        return MyViewHolder(view)
    }

    fun setItems(newItems: List<Currency>) {
        val diffCallback = CurrencyCallback(currencies, newItems)
        val diffCourses = DiffUtil.calculateDiff(diffCallback)
        val position = clickedPosition
        currencies.clear()
        clickedView = null
        clickedPosition = -1
        currencies.addAll(newItems)
        diffCourses.dispatchUpdatesTo(this)
        notifyItemChanged(position)
    }

    override fun getItemCount(): Int {
        return currencies.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currency = currencies[position]
        holder.charCode.text = currencies[position].charCode
        holder.value.text = currencies[position].value.toString()
        holder.name.text = currencies[position].name

        holder.itemView.setOnClickListener {
            if (clickedView != null)
                clickedView!!.setBackgroundColor(ContextCompat.getColor(context, R.color.blueDark))
            holder.itemView.setBackgroundColor(Color.WHITE)
            clickedPosition = holder.adapterPosition
            clickedView = holder.itemView
            onClickListener.onStateClick(currency, position)
        }

        if (position == clickedPosition)
            holder.itemView.setBackgroundColor(Color.WHITE)
        else
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.blueDark))

    }
}