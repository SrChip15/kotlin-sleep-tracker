package com.example.android.trackmysleepquality.sleepdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.databinding.FragmentSleepDetailBinding

class SleepDetailFragment : Fragment() {

    private lateinit var binding: FragmentSleepDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_sleep_detail, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao

        val nightId = SleepDetailFragmentArgs.fromBundle(requireArguments()).nightId

        val viewModelFactory = SleepDetailViewModelFactory(nightId, dataSource)

        val viewModel = ViewModelProvider(this, viewModelFactory)[SleepDetailViewModel::class.java]

        binding.sleepDetailViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.navigateToSleepTracker.observe(viewLifecycleOwner) {
            if (it == true) {
                this@SleepDetailFragment.findNavController().navigate(
                    SleepDetailFragmentDirections.actionSleepDetailFragmentToSleepTrackerFragment())
                viewModel.doneNavigating()
            }
        }

        return binding.root
    }
}