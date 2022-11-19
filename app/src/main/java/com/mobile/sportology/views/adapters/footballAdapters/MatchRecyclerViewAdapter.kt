package com.mobile.sportology.views.adapters.footballAdapters

import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobile.sportology.databinding.LeagueMatchResultItemBinding
import com.mobile.sportology.models.football.Matches

class MatchRecyclerViewAdapter(private val interaction: Interaction? = null):
    RecyclerView.Adapter<MatchRecyclerViewAdapter.MatchViewHolder>() {

    private val _diffCallback = object : DiffUtil.ItemCallback<Matches.Data>() {
        override fun areItemsTheSame(
            oldItem: Matches.Data,
            newItem: Matches.Data
        ): Boolean = oldItem.match_id == newItem.match_id

        override fun areContentsTheSame(
            oldItem: Matches.Data,
            newItem: Matches.Data
        ): Boolean = oldItem == newItem
    }
    val differ = AsyncListDiffer(this, _diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
        val binding = LeagueMatchResultItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatchViewHolder(binding, interaction)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) = holder.bind(differ.currentList[position])

    override fun getItemCount(): Int = differ.currentList.size

    inner class MatchViewHolder(
        _itemViewBinding: LeagueMatchResultItemBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(_itemViewBinding.root),
        PopupMenu.OnMenuItemClickListener {
        private val itemBinding = _itemViewBinding

        fun bind(item: Matches.Data) {
            itemBinding.root.setOnClickListener {
                interaction?.onItemClicked(adapterPosition, differ.currentList[adapterPosition])
            }
            itemBinding.match = item
        }

//        private fun showPopUpMenu(v: View?, position: Int) {
//            val popup = PopupMenu(v?.context, v)
//            popup.inflate(R.menu.list_item_menu)
//            val menu: Menu = popup.menu
//            menu.removeItem(R.id.select_all)
//            menu.removeItem(R.id.dismiss)
//            menu.removeItem(R.id.create_folder)
//            if (differ.currentList[position].file.isDirectory) menu.removeItem(R.id.share)
//            popup.setOnMenuItemClickListener(this)
//            popup.show()
//        }

        override fun onMenuItemClick(p0: MenuItem?): Boolean {
            when (p0?.itemId) {
//                R.id.copy -> {
//                    Toast.makeText(viewHolder.itemBinding.arrowDown.context, "item selected", Toast.LENGTH_LONG).show()
//                    return true
//                }
            }
            return false
        }
    }

    interface Interaction {
        fun onItemClicked(position: Int, match: Matches.Data)
    }
}