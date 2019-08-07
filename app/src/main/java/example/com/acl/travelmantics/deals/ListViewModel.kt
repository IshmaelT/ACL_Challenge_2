package example.com.acl.travelmantics.deals

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import example.com.acl.travelmantics.BR
import example.com.acl.travelmantics.model.TravelDeal
import example.com.acl.travelmantics.utils.bindable

class ListViewModel : BaseObservable() {

    @get:Bindable
    var administrator by bindable(false, BR.administrator)

    @get:Bindable
    var travelDeals by bindable(ArrayList<TravelDeal>(), BR.travelDeals)
}