package com.oguz.countryapp.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oguz.countryapp.model.Country
import com.oguz.countryapp.service.CountryApiService
import com.oguz.countryapp.service.CountryDatabase
import com.oguz.countryapp.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.jetbrains.annotations.Async.Schedule

class FeedViewModel(application: Application) : BaseViewModel (application) {


    private var customPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L //Bu bana 10dk verecek nanosaniye cinsinden.

    private val countryApiService = CountryApiService()
    private val disposable = CompositeDisposable()//Biz bu tarz call lar yaparken bişey indiriken internetten
    //retrofitle her yaptığımız call un aslında hafızada bir yer tutduğunu biliyoruz ve
    //bu fragmentlar temizlendiğinde işte başka fragmenta geçildiğinde kapandığında bu call lardan kurtulmamız gerekiyor
    //ve kurtulmazsak hafızada yer tutar.  CompositeDisposable bir tane büyük bir obje oluşturuyor
    //biz call yaptıkça veri indikçe bu disposible ın içine atıyoruz en sonunda disposable ile tamamen kurtuluyoruz


    //Verilerimi livedata olarak tutacağız

    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData(){

        //Burada zamanı alabilir ve ne kadar zaman geçtiğini anlayabiliriz.10dk da bir yenilensin
        val updateTime = customPreferences.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime()- updateTime < refreshTime){
             //O zaman sqlite dan çek 10 dk yı geçmediyse
            getDataFromSQLite()

        }else {

            getDataFromAPI() // 10dk yı geçtiyse api den alsın
        }

    }

    fun refreshFromAPI(){//Hertürlü api den alalım swiperefresh de hep bunu kullanalım.

        getDataFromAPI()

    }

    private fun getDataFromSQLite(){

        countryLoading.value = true

        //Bunu coroutine içerisinde yapıyoruz tam anlamadım ???
        launch {

            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(),"Countries From SQLite" , Toast.LENGTH_LONG).show()

        }

    }

    private fun getDataFromAPI(){

        countryLoading.value = true

        disposable.add(
            countryApiService.getData()
                .subscribeOn(Schedulers.newThread())//nerde yapacağız
                .observeOn(AndroidSchedulers.mainThread())//nerde gözlemleyeceğiz
                .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {


                        storeInSQLite(t)
                        Toast.makeText(getApplication(),"Countries From API" , Toast.LENGTH_LONG).show()

                    }

                    override fun onError(e: Throwable) {


                        countryLoading.value = false
                        countryError.value = true
                        e.printStackTrace()

                    }

                })
        )
    }

    private fun showCountries(list : List<Country>) {


        countries.value = list
        countryError.value = false
        countryLoading.value = false

    }

    private fun storeInSQLite(list : List<Country>) {

        //Suspend fonksyonları kullanacağız coroutine kullanacağız burada
        //Öncelikle bir iş oluşturmamız gerekiyor ve bu işe ne yapacağını söylememiz gerekiyor.
        //Bütün bunları yeni bir CoroutinenScope sınıfta yapacağız bunu direk extend etmek yerine farklı bir sınıfta yapıp extend etmek daha mantıklı. BaseViewModeli yazdık artık coroutine kullanabiliriz

        launch {

            val dao = CountryDatabase(getApplication()).countryDao()

            dao.deleteAllCountries() //Önce verileri silelim daha sonra yenilerini ekleyelim

            val listlong = dao.insertAll(*list.toTypedArray()) //->list  -> indivudual Listedeki hedefleri tek tek haline getir tekil elemanlar yap indivudual elemanlar yap

            var i = 0
            while (i < list.size){

                list[i].uuid = listlong[i].toInt()

                i += 1

            }

            showCountries(list) //En sonda atamaları yapıyoruz ve gösteriyoruz.
        }


        customPreferences.saveTime(System.nanoTime())

    }

    override fun onCleared() {
        super.onCleared()

        disposable.clear()
    }
}