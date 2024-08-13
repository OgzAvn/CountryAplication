package com.oguz.countryapp.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.oguz.countryapp.R

//Extension

//Hangi sınıfa bu eklentriyi yapacaksak onu yazıyoruz
//String objesinden bir extension yarattık


/*
fun String.myExtension(myParameter : String) {


    println(myParameter)


}

//val myString = "oguz"
//myString.myExtension("avanoglu")
//böyle kullanabilirim örneğin herhangi bir fragment  ın içinde
 */


fun ImageView.downloadFromUrl(url : String?,progressDrawable: CircularProgressDrawable){

    val options = RequestOptions()// Placeholder veriler gelene kadar ne tutacağını söyler.
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher_round)


    Glide.with(context) //nerde olduğumuzu bilmediğimiz için aktivite mi fragment mı? O nedenle context dedik direk
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)

}



fun placeholderProgressBar(context : Context) : CircularProgressDrawable {


    return CircularProgressDrawable(context).apply {

        strokeWidth = 8f //ne kadar geniş olsun?

        centerRadius = 40f // Yarı çapı ne kadar olacak

        start()//bu diyip başlatıyoruz


    }


}

@BindingAdapter("android:downloadUrl") //XML de çalıştırmama olanak sağlıyor
fun downloadImage(view : ImageView , url : String){

    view.downloadFromUrl(url, placeholderProgressBar(view.context))

}