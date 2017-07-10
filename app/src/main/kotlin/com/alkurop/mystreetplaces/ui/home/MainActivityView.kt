package com.alkurop.mystreetplaces.ui.home

import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.StringRes

data class MainActivityView(@StringRes val toolbarTitleRes: Int = -1) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<MainActivityView> = object : Parcelable.Creator<MainActivityView> {
            override fun createFromParcel(source: Parcel): MainActivityView = MainActivityView(source)
            override fun newArray(size: Int): Array<MainActivityView?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
    source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(toolbarTitleRes)
    }
}