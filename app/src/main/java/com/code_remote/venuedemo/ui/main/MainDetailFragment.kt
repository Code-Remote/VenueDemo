package com.code_remote.venuedemo.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.code_remote.venuedemo.data.local.VenueDetails
import com.code_remote.venuedemo.databinding.MainDetailFragmentBinding
import com.code_remote.venuedemo.network.Result.Companion.Status.*

class MainDetailFragment : Fragment() {

    private lateinit var binding: MainDetailFragmentBinding
    private val sharedViewModel: MainViewModel by activityViewModels()
    private val args: MainDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainDetailFragmentBinding.inflate(inflater, container, false)

        sharedViewModel.getVenueDetails(args.venueId)
        sharedViewModel.venueDetails.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                SUCCESS -> showVenueDetails(it.data)
                ERROR -> showError(it.message)
                LOADING -> showLoading()
            }
        })
        return binding.root
    }

    private fun showVenueDetails(venueDetails: VenueDetails?) {
        venueDetails?.let {
            binding.venueName.text = it.name
            binding.venueDescription.text = it.description ?: "No description"
            binding.venueAddress.text = it.formattedAddress
            binding.venueContactInfo.text = it.contactInfo?.formattedPhone
            binding.venueRatingBar.rating = it.rating ?: 0f
            if (it.photos.isNotEmpty()) {
                Glide.with(requireContext())
                    .load(it.photos.random())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.venuePhoto)
            }

        }
    }

    private fun showError(message: String?) {
        Toast.makeText(requireContext(), "Error: $message", Toast.LENGTH_SHORT).show()
    }

    private fun showLoading() {
        Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
    }
}