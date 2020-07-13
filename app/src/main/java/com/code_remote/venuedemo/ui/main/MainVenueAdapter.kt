package com.code_remote.venuedemo.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.code_remote.venuedemo.data.local.Venue
import com.code_remote.venuedemo.databinding.MainListVenueItemBinding

class VenueAdapter() :
    ListAdapter<Venue, VenueAdapter.VenueViewHolder>(VenueDiffCallback()) {

    override fun onBindViewHolder(holderVenue: VenueViewHolder, position: Int) {
        val item = getItem(position)
        holderVenue.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenueViewHolder {
        return VenueViewHolder(
            MainListVenueItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    class VenueViewHolder(private val binding: MainListVenueItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Venue) {
            binding.venueName.text = item.name
            binding.venueAddress.text = item.formattedAddress ?: "Address unknown"
            binding.root.setOnClickListener {
                val action = MainFragmentDirections.actionToDetailsFromMain(item.id)
                it.findNavController().navigate(action)
            }
        }
    }
}

private class VenueDiffCallback : DiffUtil.ItemCallback<Venue>() {
    override fun areItemsTheSame(oldItem: Venue, newItem: Venue): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Venue, newItem: Venue): Boolean {
        return oldItem == newItem
    }
}
