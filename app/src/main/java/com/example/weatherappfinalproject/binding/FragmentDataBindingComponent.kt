package com.delaroystudios.weatherapp.binding

import androidx.databinding.DataBindingComponent
import androidx.fragment.app.Fragment

class FragmentDataBindingComponent(fragment: Fragment?) :
    DataBindingComponent {
//    @Override
    val fragmentBindingAdapters: FragmentBindingAdapters

    init {
        fragmentBindingAdapters = FragmentBindingAdapters(fragment!!)
    }
}
