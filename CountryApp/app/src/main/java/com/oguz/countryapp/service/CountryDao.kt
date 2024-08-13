package com.oguz.countryapp.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.oguz.countryapp.model.Country

@Dao
interface CountryDao {

    //Data Access Object

    @Insert
    suspend fun insertAll(vararg countries : Country) : List<Long>

    //Insert  -> INSERT INTO
    //suspend -> coroutine, pause & resume
    // vararg -> multiple country objects döndürüyor
    //List<Long> -> Primary keys döndürüyor

    @Query("SELECT * FROM country")//Sınıf ismi country oradan çek dedik
    suspend fun getAllCountries() : List<Country>

    @Query("SELECT * FROM country WHERE uuid = :countryId")
    suspend fun getCountry(countryId : Int) : Country

    @Query("DELETE FROM country")
    suspend fun deleteAllCountries()

}