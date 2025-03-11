package com.example.productapp.model

import android.os.Parcel
import android.os.Parcelable

data class Product(
    val pid: Int,
    val pname: String?,
    val pprice: String?,
    val pdesc: String?,
    val pimage: String?,
    val pstatus: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(pid)
        parcel.writeString(pname)
        parcel.writeString(pprice)
        parcel.writeString(pdesc)
        parcel.writeString(pimage)
        parcel.writeString(pstatus)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}
