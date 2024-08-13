package com.oguz.countryapp.service

import com.oguz.countryapp.model.Country
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET

interface CountryAPI {

    //GET , POST

    //Verileri alırken GET verileri değiştirirken genelde POST kullanırız

    //https://raw.githubusercontent.com/atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries():Single<List<Country>>
    //Observable -> devamlı elemanları internetten alır bir veriyi devamlı bir şekilde güncelleyip devamlı almak istiyorsak observable kullanırıız
    //Single -> eğer bir veriyi bir defa alıp ama garanti bir şekilde almamız gerekiyorsa Single kullanabilirz.

}