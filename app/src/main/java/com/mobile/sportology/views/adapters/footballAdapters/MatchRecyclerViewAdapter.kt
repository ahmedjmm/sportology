package com.mobile.sportology.views.adapters.footballAdapters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobile.sportology.Constants
import com.mobile.sportology.R
import com.mobile.sportology.databinding.LeagueMatchResultItemBinding
import com.mobile.sportology.models.football.Matches

class MatchRecyclerViewAdapter:
    RecyclerView.Adapter<MatchRecyclerViewAdapter.MatchViewHolder>() {
    private val _diffCallback = object : DiffUtil.ItemCallback<Matches.Result>() {
        override fun areItemsTheSame(
            oldItem: Matches.Result,
            newItem: Matches.Result
        ): Boolean = oldItem.event_key == newItem.event_key

        override fun areContentsTheSame(
            oldItem: Matches.Result,
            newItem: Matches.Result
        ): Boolean = oldItem == newItem
    }
    val differ = AsyncListDiffer(this, _diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val binding = LeagueMatchResultItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MatchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.itemView.apply {
            setOnClickListener{
                val bundle = Bundle().apply {
                    putInt("eventId", differ.currentList[position].event_key)
                }
                findNavController().navigate(R.id.action_football_to_matchDetailsActivity, bundle)
            }
        }
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class MatchViewHolder(
        private val _itemViewBinding: LeagueMatchResultItemBinding,
    ) : RecyclerView.ViewHolder(_itemViewBinding.root),
        PopupMenu.OnMenuItemClickListener {

        fun bind(item: Matches.Result) {
            _itemViewBinding.match = item
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
}