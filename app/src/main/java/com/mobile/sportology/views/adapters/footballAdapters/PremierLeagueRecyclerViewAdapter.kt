package com.mobile.sportology.views.adapters.footballAdapters

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.sportology.databinding.LeagueMatchDateItemBinding
import com.mobile.sportology.models.football.Matches
import com.mobile.sportology.viewModels.FootBallViewModel

class PremierLeagueRecyclerViewAdapter(private val viewModel: FootBallViewModel):
    RecyclerView.Adapter<PremierLeagueRecyclerViewAdapter.DateViewHolder>() {
    private lateinit var premierLeagueMatchDateItemBinding: LeagueMatchDateItemBinding
    private val _diffCallback = object : DiffUtil.ItemCallback<Matches.Data.Date>() {
        override fun areItemsTheSame(
            oldItem: Matches.Data.Date,
            newItem: Matches.Data.Date
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: Matches.Data.Date,
            newItem: Matches.Data.Date
        ): Boolean = oldItem == newItem
    }
    val differ = AsyncListDiffer(this, _diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        premierLeagueMatchDateItemBinding = LeagueMatchDateItemBinding.inflate(
            inflater, parent, false)
        return DateViewHolder(premierLeagueMatchDateItemBinding)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        val matchRecyclerViewAdapter = MatchRecyclerViewAdapter()
        premierLeagueMatchDateItemBinding.childRecyclerView.apply {
            adapter = matchRecyclerViewAdapter
            layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false)
            itemAnimator = null
        }
        val list = viewModel.premierLeagueMatchesLiveData.value?.data?.data?.toMutableList()
        val childList = mutableListOf<Matches.Data>()
        list?.forEach {
            if (it.matchDate == differ.currentList[position].date)
                childList.add(it)
        }
        matchRecyclerViewAdapter.differ.submitList(childList)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun getItemViewType(position: Int): Int = position

    /////////////////////////////////////////////////////////////////////////////////////////////////

    inner class DateViewHolder(
        _itemViewBinding: LeagueMatchDateItemBinding,
    ): RecyclerView.ViewHolder(_itemViewBinding.root),
        PopupMenu.OnMenuItemClickListener {
        private val itemBinding = _itemViewBinding

        fun bind(item: Matches.Data.Date) {
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
            when(p0?.itemId){
//                R.id.copy -> {
//                    Toast.makeText(viewHolder.itemBinding.arrowDown.context, "item selected", Toast.LENGTH_LONG).show()
//                    return true
//                }
            }
            return false
        }
    }
}