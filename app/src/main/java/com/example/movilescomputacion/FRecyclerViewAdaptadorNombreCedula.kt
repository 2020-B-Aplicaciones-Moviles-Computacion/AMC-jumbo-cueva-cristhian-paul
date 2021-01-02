package com.example.movilescomputacion

import android.os.Parcel
import android.os.Parcelable
import androidx.recyclerview.widget.RecyclerView


class FRecyclerViewAdaptadorNombreCedula(
    private val listaEntrendor: List<BEntrenador>,
    private val contexto: GRecyclerView,
    private val recyclerView: RecyclerView
) : Parcelable {

    var numeroLikes: Int = 0;
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(BEntrenador)!!,
        TODO("contexto"),
        TODO("recyclerView")) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(listaEntrendor)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FRecyclerViewAdaptadorNombreCedula> {
        override fun createFromParcel(parcel: Parcel): FRecyclerViewAdaptadorNombreCedula {
            return FRecyclerViewAdaptadorNombreCedula(parcel)
        }

        override fun newArray(size: Int): Array<FRecyclerViewAdaptadorNombreCedula?> {
            return arrayOfNulls(size)
        }
    }

    fun anadirLike () {
//        this.numeroLikes = this.numeroLikes +1
//        likesTextView.text = this.numeroLikes.toString()
//        contexto.aumentarTotalLikes()
    }

}