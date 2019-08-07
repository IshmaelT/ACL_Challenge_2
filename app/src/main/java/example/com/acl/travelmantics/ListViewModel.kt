package example.com.acl.travelmantics

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class ListViewModel : BaseObservable() {

    @get:Bindable
    var administrator by bindable(false, BR.administrator)

    @get:Bindable
    var travelDeals by bindable(ArrayList<TravelDeal>(), BR.travelDeals)
}