package com.oguz.countryapp.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oguz.countryapp.model.Country
import com.oguz.countryapp.service.CountryDatabase
import kotlinx.coroutines.launch

class CountryViewModel(application: Application) : BaseViewModel(application) {

    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom(uuid : Int){

        launch {

            val dao = CountryDatabase(getApplication()).countryDao().getCountry(uuid)
            countryLiveData.value = dao
        }

    }
}