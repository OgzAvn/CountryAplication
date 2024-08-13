package com.oguz.countryapp.service


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.oguz.countryapp.model.Country
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized


@Database(entities = [Country::class], version = 1, exportSchema = false)
abstract class CountryDatabase : RoomDatabase() {

    abstract fun countryDao() : CountryDao


    //Singleton

    companion object{ //Heryerde bu objeye ulaşabiliriz

       //farklı thread lerede görünür hale getirdik Corotune kullandığımız için farklı threadlerde çağrılabilir o sebeple kullanıyoruz
       //zaten singleton yapma amacımız da farklı threadlerde çağırabilmek için
       @Volatile private var instance : CountryDatabase? = null

        private val lock = Any()
        //instance var mı diye kontrol edeceğiz yoksa oluşturacağız
        @OptIn(InternalCoroutinesApi::class)
        operator fun invoke(context: Context) = instance ?: synchronized(lock){//Aynı anda sadece tek bir thread de işlem yapabilecek

            instance ?: makeDatabase(context).also {

                instance = it
            } //instance yoksa makedatabse çağır database i oluştur ondan sonra also kullnarak ayrıca şunu yap diyoruz it e eşitle countrydatabse e eşitle

        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,CountryDatabase::class.java,"countryDatabase"
        ).build() //Database imizi oluşturacağımız fonksyon

    }
}

//Bu veri tabanımızdan birden fazla obje oluşturulmaısnı istemiyoruz sadace tek bir instance tek bir obje olmasını istiyoruz.
//Çünkü eğer farklı zamanlarda ya da aynı zamanlarda farklı threadlerden bizim veri tabanımıza ulaşılmaya çalışılırsa bu conflict oluşturacaktır anlaşmazlık
//o sebeple burada oluşturduğumuz CountryDatabase i singleton mantığı ile oluşturacağız.

//Sİngleton -> İçeriisnden tek bir obje oluştuabilen bir sınıftı obje varsa oluşturmuyoruz yoksa bir tane oluşuturup heryerde ulaşabiliyoruz