package com.example.lab3

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


class CustomAdapter(private val context : Context) : RecyclerView.Adapter<CustomAdapter.BaseViewHolder>() {

    private val items = ArrayList<ListItem>()



    abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    class ViewHolderSong(itemView: View) : BaseViewHolder(itemView){
        private val name: TextView = itemView.findViewById(R.id.songName)
        private val author: TextView = itemView.findViewById(R.id.authorName)

        fun bind(item: Song){
            name.text = item.name
            author.text = item.authorName
        }
    }

    class ViewHolderAdvertisement(itemView: View) : BaseViewHolder(itemView){
        private val theme: TextView = itemView.findViewById(R.id.bannerTheme)
        private val title: TextView = itemView.findViewById(R.id.bannerTitle)
        private val disc: TextView = itemView.findViewById(R.id.bannerDisc)

        fun bind(item: Advertisement){
            theme.text = item.theme
            title.text = item.title
            disc.text = item.disc
        }
    }

    fun setItems(newItems: List<ListItem>) {
        val diffCallback = ItemsCallback(items, newItems)
        val diffCourses = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(newItems)
        diffCourses.dispatchUpdatesTo(this)
        notifyItemRangeChanged(0, newItems.size)
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            ListItem.Type.SONG.value -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.song_in_list, parent, false)
                return ViewHolderSong(view)
            }
            ListItem.Type.ADVERTISEMENT.value -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.advertisement_item, parent, false)
                return ViewHolderAdvertisement(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.song_in_list, parent, false)
                return ViewHolderSong(view)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (items[position] is Song){
            val h = holder as ViewHolderSong
            h.bind(items[position] as Song)
        }
        else if (items[position] is Advertisement){
            val h = holder as ViewHolderAdvertisement
            h.bind(items[position] as Advertisement)
        }
        holder.itemView.setOnClickListener{
            val intent = Intent(context, ItemActivity::class.java)
            if (items[position] is Song) {
                val item = items[position] as Song
                intent.putExtra(ItemActivity.UP_TEXT_KEY, item.name)
                intent.putExtra(ItemActivity.DOWN_TEXT_KEY, item.authorName)
            }
            else if (items[position] is Advertisement) {
                val item = items[position] as Advertisement
                intent.putExtra(ItemActivity.UP_TEXT_KEY, item.title)
                intent.putExtra(ItemActivity.DOWN_TEXT_KEY, item.disc)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = items.size
}