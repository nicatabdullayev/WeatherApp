package com.example.weatherappfinalproject.ui.settings

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import com.delaroystudios.weatherapp.binding.FragmentDataBindingComponent
import com.delaroystudios.weatherapp.di.Injectable
import com.example.weatherappfinalproject.MainActivity
import com.example.weatherappfinalproject.R
import com.example.weatherappfinalproject.databinding.SettingsFragmentBinding
import com.example.weatherappfinalproject.util.AutoClearedValue
import javax.inject.Inject

class SettingsFragment : DialogFragment(), Injectable {
    @Inject
    var navigationController: NavController? = null
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding: AutoClearedValue<SettingsFragmentBinding>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val dataBinding: SettingsFragmentBinding = inflate(
                inflater, R.layout.settings_fragment, container, false,
                dataBindingComponent
            )
        binding = AutoClearedValue(this, dataBinding)
        return dataBinding.getRoot()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding?.get()?.setSettingsFragment(this)
        binding?.get()?.setCity(SharedPreferences.getInstance(context).getCity())
        binding?.get()?.setNumDays(SharedPreferences.getInstance(context).getNumDays())
        binding?.get()?.executePendingBindings()
    }

    fun didTapCancel(view: View?) {
        dismiss()
    }

    fun didTapOk(view: View?) {
        val newCity: String = binding.get().etSettingsCity.getText().toString()
        val newNumDays: String = binding.get().etSettingsNumDays.getText().toString()
        SharedPreferences.getInstance(context).putStringValue(SharedPreferences.CITY, newCity)
        SharedPreferences.getInstance(context)
            .putStringValue(SharedPreferences.NUM_DAYS, newNumDays)
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
        dismiss()
    }

    companion object {
        fun create(): SettingsFragment {
            return SettingsFragment()
        }
    }
}
