package com.example.android.trackmysleepquality.sleeptracker

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.ListItemSleepNightBinding

class SleepNightAdapter : RecyclerView.Adapter<SleepNightVH>() {

    private lateinit var binding: ListItemSleepNightBinding

    var data = listOf<SleepNight>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SleepNightVH {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_sleep_night,
            parent,
            false
        )

        return SleepNightVH(binding.root)
    }

    override fun onBindViewHolder(holder: SleepNightVH, position: Int) {
        val currentNight = data[position]
        val res = holder.itemView.context.resources

        binding.qualityImage.setImageResource(
            when (currentNight.sleepQuality) {
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
            convertDurationToFormatted(currentNight.startTimeMilli, currentNight.stopTimeMilli, res)
        binding.qualityString.text = convertNumericQualityToString(currentNight.sleepQuality, res)
    }

    override fun getItemCount(): Int {
        return data.size
    }

}

class SleepNightVH(itemView: View) : RecyclerView.ViewHolder(itemView)
