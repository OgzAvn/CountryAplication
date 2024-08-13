package com.oguz.countryapp.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.oguz.countryapp.adapter.CountryAdapter
import com.oguz.countryapp.R
import com.oguz.countryapp.databinding.FragmentCountryBinding
import com.oguz.countryapp.databinding.FragmentFeedBinding
import com.oguz.countryapp.util.downloadFromUrl
import com.oguz.countryapp.util.placeholderProgressBar
import com.oguz.countryapp.viewModel.CountryViewModel


class CountryFragment : Fragment() {

    private var _binding: FragmentCountryBinding? = null
    private val binding get() = _binding!!
    private lateinit var databinding : FragmentCountryBinding

    private lateinit var viewmodel : CountryViewModel

    private var countryUuid = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        databinding = DataBindingUtil.inflate(inflater,R.layout.fragment_country,container,false)
        return databinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
        }

        viewmodel = ViewModelProvider(this).get(CountryViewModel::class.java)
        viewmodel.getDataFromRoom(countryUuid)

        observeLiveData()
    }

    private fun observeLiveData(){

        viewmodel.countryLiveData.observe(viewLifecycleOwner, Observer { country->

            country?.let {

                databinding.selectedCountry = it
                /*
                binding.countryName.text = country.countryName
                binding.countryCapital.text = country.countryCapital
                binding.countryCurrency.text = country.countryCurrency
                binding.countryLanguage.text = country.countryLanguage
                binding.countryRegion.text = country.countryRegion
                context?.let {

                    binding.countryFlagImage.downloadFromUrl(country.imageUrl,
                        placeholderProgressBar(it)

                    )
                }

                 */

            }

        })
    }

}