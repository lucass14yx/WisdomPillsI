package com.maestre.wisdompills.Model.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.maestre.wisdompills.View.ImagesFragment
import com.maestre.wisdompills.View.NewActivity
import com.maestre.wisdompills.View.WebFragment

class FragmentAdapter(newActivity: NewActivity) : FragmentStateAdapter(newActivity){
    override fun getItemCount()=2


    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                WebFragment()
            }
            1 -> {
                ImagesFragment()
            }
            else -> {throw IllegalArgumentException("Invalid position: $position") }
        }
    }
}