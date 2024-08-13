package com.oguz.countryapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "country")//SQLite da RoomData base
data class Country(

    //veri hangi isimle gelecekse onu yazıyorum ve countryName e atıyorum serializedName kullanma sebebim bu isimler farklı.

    @ColumnInfo(name ="name")
    @SerializedName("name")
    val countryName : String?,

    @ColumnInfo(name ="region")
    @SerializedName("region")
    val countryRegion : String?,

    @ColumnInfo(name ="capital")
    @SerializedName("capital")
    val countryCapital : String?,

    @ColumnInfo(name="currency")
    @SerializedName("currency")
    val countryCurrency : String?,

    @ColumnInfo(name="language")
    @SerializedName("language")
    val countryLanguage : String?,

    @ColumnInfo(name="flag")
    @SerializedName("flag")
    val imageUrl : String?

)  {

    //model kendisi bunu oluşturacak ve ben id neymiş nereden geliyormuş ilgilenmeyeceğim en fazla bu id yi
    //bir fragment dan diğer fragment a yollayabilirim ki veri çekmek istersek kolaylık olsun
    @PrimaryKey(true)
    var uuid : Int = 0

}

