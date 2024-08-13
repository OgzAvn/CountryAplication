package com.oguz.countryapp.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oguz.countryapp.adapter.CountryAdapter
import com.oguz.countryapp.R
import com.oguz.countryapp.databinding.CountrylistRowBinding
import com.oguz.countryapp.databinding.FragmentFeedBinding
import com.oguz.countryapp.viewModel.FeedViewModel


class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FeedViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Hangi fragmenttayız hangi aktivitedeyiz ve hangi viewmodule ın sınıfı ile çalışmak istiyoruz onu söyleyebiliyoruz
        viewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        viewModel.refreshData()



        binding.countryList.layoutManager = LinearLayoutManager(context) //context yazıyoruz çünkü fragment içerisindeyiz.
        binding.countryList.adapter = countryAdapter


        binding.swipeRefreshLayout.setOnRefreshListener {

            binding.countryList.visibility = View.GONE

            binding.countryError.visibility = View.GONE

            binding.countriesLoading.visibility = View.VISIBLE

            viewModel.refreshFromAPI()

            binding.swipeRefreshLayout.isRefreshing = false


        }

        observeLiveData()
    }

    private fun observeLiveData() {

        viewModel.countries.observe(viewLifecycleOwner, Observer {countries ->

            countries?.let {

                binding.countryList.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)

            }
        })

        viewModel.countryError.observe(viewLifecycleOwner, Observer {error->

            error?.let {
                if (it){
                    binding.countryError.visibility = View.VISIBLE
                }else{
                    binding.countryError.visibility= View.GONE
                }
            }

        })

        viewModel.countryLoading.observe(viewLifecycleOwner, Observer { loading->

            loading?.let {

                if (it){
                    binding.countriesLoading.visibility = View.VISIBLE
                    binding.countryError.visibility = View.GONE
                    binding.countryList.visibility = View.GONE
                }else{
                    binding.countriesLoading.visibility = View.GONE
                }
            }

        })
    }

}