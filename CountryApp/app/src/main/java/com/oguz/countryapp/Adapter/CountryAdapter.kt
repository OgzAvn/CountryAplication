package com.oguz.countryapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.oguz.countryapp.model.Country
import com.oguz.countryapp.R
import com.oguz.countryapp.View.FeedFragmentDirections
import com.oguz.countryapp.databinding.CountrylistRowBinding
import com.oguz.countryapp.util.downloadFromUrl
import com.oguz.countryapp.util.placeholderProgressBar


class CountryAdapter(val countryList : ArrayList<Country>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() , CountryClickListener{


    class CountryViewHolder(var view :  CountrylistRowBinding ) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.countrylist_row,parent,false)
        //Databinding e göre view tanımlamamız gerekiyor
        val view = DataBindingUtil.inflate<CountrylistRowBinding>(inflater,R.layout.countrylist_row,parent,false)
        return CountryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {

        //DataBinding kullanacağız

        holder.view.country = countryList[position]
        holder.view.listener = this
        /*


               holder.view.name.text = countryList[position].countryName
               holder.view.region.text = countryList[position].countryRegion

               holder.view.setOnClickListener {
                   val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuid)
                   //action.countryUuid
                   Navigation.findNavController(it).navigate(action)
               }

               holder.view.imageView.downloadFromUrl(countryList[position].imageUrl, placeholderProgressBar(holder.view.context))
        */

    }

    //FeedFragment layout da swipe yaptık eğer bir şekilde veriler güncellenirse adapter da bunu bilmesi laızm o yüzden bir fonk oluşuturoyurz

    @SuppressLint("NotifyDataSetChanged")
    fun updateCountryList(newCountryList: List<Country>){

        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged() //Adapterü yenilemek için kullandığımız method.
    }

    override fun onCountryClicked(v: View) {

        val textView = v.findViewById<TextView>(R.id.countryUuidText)
        val uuid = textView.text.toString().toInt()
        val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(uuid)
        //action.countryUuid
        Navigation.findNavController(v).navigate(action)

    }

}