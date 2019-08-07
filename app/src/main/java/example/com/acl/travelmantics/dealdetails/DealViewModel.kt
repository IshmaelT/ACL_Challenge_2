package example.com.acl.travelmantics.dealdetails

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import android.view.View
import example.com.acl.travelmantics.BR
import example.com.acl.travelmantics.model.TravelDeal
import example.com.acl.travelmantics.utils.bindable

class DealViewModel(
        private var dealView: DealView
) : BaseObservable() {

    @get:Bindable
    var loading by bindable(false, BR.loading)

    @get:Bindable
    var travelDeal by bindable(TravelDeal(), BR.travelDeal)

    fun onSelectImagesClicked(view: View) {
        dealView.onSelectImagesClicked()
    }
}
