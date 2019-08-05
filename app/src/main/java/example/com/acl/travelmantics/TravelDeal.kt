package example.com.acl.travelmantics

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TravelDeal(
        var id: String? = null,
        var title: String? = null,
        var description: String? = null,
        var price: String? = null,
        var url: String? = null
): Parcelable