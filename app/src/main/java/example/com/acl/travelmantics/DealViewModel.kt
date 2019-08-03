package example.com.acl.travelmantics

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.view.View

class DealViewModel(
        private var dealView: DealView
) : BaseObservable() {
    @get:Bindable
    var travelDeal: TravelDeal by bindable(TravelDeal(), BR.travelDeal)

    fun onSelectImagesClicked(view: View) {
        dealView.onSelectImagesClicked()
    }
}
