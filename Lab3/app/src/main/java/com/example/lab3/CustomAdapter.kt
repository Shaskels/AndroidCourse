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
        abstract fun bind(item: ListItem)
    }

    class ViewHolderSong(itemView: View) : BaseViewHolder(itemView){
        private val name: TextView = itemView.findViewById(R.id.songName)
        private val author: TextView = itemView.findViewById(R.id.authorName)

        override fun bind(item: ListItem){
            val song = item as Song
            name.text = song.name
            author.text = song.authorName
        }
    }

    class ViewHolderAdvertisement(itemView: View) : BaseViewHolder(itemView){
        private val theme: TextView = itemView.findViewById(R.id.bannerTheme)
        private val title: TextView = itemView.findViewById(R.id.bannerTitle)
        private val disc: TextView = itemView.findViewById(R.id.bannerDisc)

        override fun bind(item: ListItem){
            val advertisement = item as Advertisement
            theme.text = advertisement.theme
            title.text = advertisement.title
            disc.text = advertisement.disc
        }
    }

    fun setItems(newItems: List<ListItem>) {
        val diffCallback = ItemsCallback(items, newItems)
        val diffCourses = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(newItems)
        diffCourses.dispatchUpdatesTo(this)
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].getListItemType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            ListItem.Type.Song.ordinal -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.song_in_list, parent, false)
                return ViewHolderSong(view)
            }
            ListItem.Type.Advertisement.ordinal -> {
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
        holder.bind(items[position])
        holder.itemView.setOnClickListener{
            val intent = Intent(context, ItemActivity::class.java)
            if (items[position] is Song) {
                val item = items[position] as Song
                intent.putExtra("text1", item.name)
                intent.putExtra("text2", item.authorName)
            }
            else {
                val item = items[position] as Advertisement
                intent.putExtra("text1", item.title)
                intent.putExtra("text2", item.disc)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = items.size
}