package com.example.aStar4cast.ui.weather

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.aStar4cast.MainActivity
import com.example.aStar4cast.databinding.FragmentWeatherBinding
import okio.Utf8
import retrofit2.http.Url
import java.nio.charset.Charset

class WeatherFragment : Fragment() {
    private lateinit var thisContext: Context
    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WeatherViewModel by activityViewModels()

    var displayFavoriteItem: String? = ""
    var checkFavoriteItem: Boolean? = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        thisContext = view.context

        fragmentTextUpdateObserver()
        displayWeatherInFavorites()
        checkWeatherFragment()
        setupClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun fragmentTextUpdateObserver() {

        if (arguments == null) {
            viewModel.uiSuccess.observe(viewLifecycleOwner, { updateSuccess ->
                binding.btnAddFavorite.isVisible = updateSuccess
            })
        }
        viewModel.uiCityName.observe(viewLifecycleOwner, { updateCityName ->
            binding.tvCity.text = updateCityName
        })
        viewModel.uiCityId.observe(viewLifecycleOwner, { updateCityId ->
            binding.tvCityID.text = updateCityId.toString()
        })
        viewModel.uiDesc.observe(viewLifecycleOwner, { updateDesc ->
            binding.tvDescription.text = updateDesc
        })
        viewModel.uiTimeOfUpdate.observe(viewLifecycleOwner, { updateUpdatedAt ->
            binding.tvUpdatedAt.text = updateUpdatedAt
        })
        viewModel.uiTemp.observe(viewLifecycleOwner, { updateTemp ->
            binding.tvTemp.text = updateTemp
        })
        viewModel.uiTempMin.observe(viewLifecycleOwner, { updateTempMin ->
            binding.tvTempMin.text = updateTempMin
        })
        viewModel.uiTempMax.observe(viewLifecycleOwner, { updateTempMax ->
            binding.tvTempMax.text = updateTempMax
        })
        viewModel.uiSunset.observe(viewLifecycleOwner, { updateSunset ->
            binding.tvSunset.text = updateSunset
        })
        viewModel.uiSunrise.observe(viewLifecycleOwner, { updateSunrise ->
            binding.tvSunrise.text = updateSunrise
        })
        viewModel.uiPressure.observe(viewLifecycleOwner, { updatePressure ->
            binding.tvPressure.text = updatePressure
        })
        viewModel.uiHumidity.observe(viewLifecycleOwner, { updateHumidity ->
            binding.tvHumidity.text = updateHumidity
        })
        viewModel.uiWind.observe(viewLifecycleOwner, { updateWind ->
            binding.tvWindSpeed.text = updateWind
        })
    }

    private fun checkWeatherFragment() {
        checkFavoriteItem = arguments?.getBoolean("checkWeatherFragment")
        if (checkFavoriteItem == true) {
            binding.rvLayoutAllWeather.isVisible = false
        }
    }

    private fun displayWeatherInFavorites() {
        displayFavoriteItem = arguments?.getString("cityItem")
        viewModel.getUpdateWeather(displayFavoriteItem.toString())
        if (arguments != null) {
            binding.rvLayoutAllWeather.isVisible = true
            binding.llSearchBar.isVisible = false
        } else {
            binding.llSearchBar.isVisible = true
        }
    }

    private fun setupClickListeners(){
        binding.btnSearch.setOnClickListener{
            if (binding.etCity.text.toString().isNotEmpty()) {
                viewModel.getUpdateWeather(binding.etCity.text.toString())
                binding.etCity.text.clear()
                toggleUi()
            } else {
                unToggleUi()
            }
        }

        binding.btnAddFavorite.setOnClickListener{
            addFavorites()
            binding.btnAddFavorite.isVisible = false
        }
    }

    private fun addFavorites(){
        val city = binding.tvCity.text.toString()
        val cityID = binding.tvCityID.text.toString().toInt()
        viewModel.addFavoriteFromWeatherBtn(thisContext, city, cityID)
    }

    private fun toggleUi(){
        binding.rvLayoutAllWeather.isVisible = true
        binding.detailsContainer.isVisible = true
    }

    private fun unToggleUi(){
        binding.rvLayoutAllWeather.isVisible = false
        binding.btnAddFavorite.isVisible = false
        binding.detailsContainer.isVisible = false
    }
}