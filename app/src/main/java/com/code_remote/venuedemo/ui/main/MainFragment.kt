package com.code_remote.venuedemo.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.code_remote.venuedemo.databinding.MainFragmentBinding
import com.code_remote.venuedemo.network.Result.Companion.Status.*
import com.code_remote.venuedemo.utilities.bindImeAction
import com.code_remote.venuedemo.utilities.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: MainFragmentBinding
    private val sharedViewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val venueAdapter = VenueAdapter()

        binding = MainFragmentBinding.inflate(inflater, container, false)
        binding.venueRecyclerList.adapter = venueAdapter

        binding.searchButton.setOnClickListener {
            binding.searchInput.editText?.text?.let {
                sharedViewModel.searchForVenue(it.toString())
            }
            binding.searchInput.hideKeyboard()
        }

        binding.searchInput.bindImeAction(EditorInfo.IME_ACTION_SEARCH) {
            binding.searchInput.editText?.text?.let {
                sharedViewModel.searchForVenue(it.toString())
            }
            binding.searchInput.hideKeyboard()
        }

        sharedViewModel.venueList.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                SUCCESS -> venueAdapter.submitList(it.data)
                ERROR -> showError(it.message)
                LOADING -> showLoader()
            }
        })

        return binding.root
    }

    private fun showLoader() {
        Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
    }

    private fun showError(message: String?) {
        Toast.makeText(requireContext(), "Error: $message", Toast.LENGTH_SHORT).show()
    }
}