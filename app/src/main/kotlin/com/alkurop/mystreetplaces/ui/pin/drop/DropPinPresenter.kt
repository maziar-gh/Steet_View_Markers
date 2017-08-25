package com.alkurop.mystreetplaces.ui.pin.drop

import android.location.Address
import com.alkurop.mystreetplaces.data.pin.PictureWrapper
import com.alkurop.mystreetplaces.domain.pin.PinDto
import com.alkurop.mystreetplaces.ui.navigation.NavigationAction
import com.google.android.gms.maps.model.LatLng
import io.reactivex.subjects.Subject
import java.io.File

/**
 * Created by alkurop on 7/21/17.
 */
interface DropPinPresenter {
    val viewBus: Subject<DropPinViewModel>
    val navBus: Subject<NavigationAction>
    var pinDto: PinDto

    fun unsubscribe()

    fun start(pinId: String)

    fun start(location: LatLng)

    fun onTitleChange(title: String)

    fun onDescriptionChange(title: String)

    fun submit()

    fun deletePin()

    fun onAddPicture(file: File)

    fun onPictureClick(position: Int, items: List<PictureWrapper>)

    fun reloadPictureList(pictures:List<PictureWrapper>)
    fun lookForAddress()
    fun onAddressSelected(address: Address)
}
