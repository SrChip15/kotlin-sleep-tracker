package com.example.android.trackmysleepquality.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.ListItemSleepNightBinding

class SleepNightAdapter : ListAdapter<SleepNight, SleepNightVH>(SleepNightDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SleepNightVH {
        return SleepNightVH.from(parent)
    }

    override fun onBindViewHolder(holder: SleepNightVH, position: Int) {
        val currentNight = getItem(position)
        holder.bind(currentNight)
    }
}

class SleepNightVH private constructor
    (private val binding: ListItemSleepNightBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: SleepNight) {
        val res = this.itemView.context.resources
        binding.qualityImage.setImageResource(
            when (item.sleepQuality) {
                0 -> R.drawable.ic_sleep_0
                1 -> R.drawable.ic_sleep_1
                2 -> R.drawable.ic_sleep_2
                3 -> R.drawable.ic_sleep_3
                4 -> R.drawable.ic_sleep_4
                5 -> R.drawable.ic_sleep_5
                else -> R.drawable.ic_sleep_active
            }
        )
        binding.sleepLength.text =
            convertDurationToFormatted(item.startTimeMilli, item.stopTimeMilli, res)
        binding.qualityString.text = convertNumericQualityToString(item.sleepQuality, res)
    }
    companion object {
        fun from(parent: ViewGroup): SleepNightVH {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
            return SleepNightVH(binding)
        }
    }
}

class SleepNightDiffCallback : DiffUtil.ItemCallback<SleepNight>() {
    override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem.nightId == newItem.nightId
    }

    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem == newItem
    }

}
