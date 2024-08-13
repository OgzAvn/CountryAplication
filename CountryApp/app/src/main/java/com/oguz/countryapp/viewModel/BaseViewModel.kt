package com.oguz.countryapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

//AndoridViewModel -> farkı application parametresi kullanarak applicationcontext alabiliyoruz genel ap in context i ile çalışmak daha iyi olur
//Eğer fragment destroy ya da clear olursa herhangi bir sorun yaşamayacağız


//* Bu sınıfan bir obje filan oluşturmayacağız sadece extend edeceğiz o yüzden abstract olarak oluşturuyoruz
abstract class BaseViewModel(application: Application) : AndroidViewModel(application) , CoroutineScope{

    private val job = Job()


    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main //Öncelikle işini yap daha sonra main thread e dön demek


    override fun onCleared() {
        super.onCleared()

        job.cancel() //Eğer app context i giderse bu iş iptal edilecektir.
    }
}