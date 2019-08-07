package example.com.acl.travelmantics.viewbindings

import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import example.com.acl.travelmantics.model.TravelDeal
import example.com.acl.travelmantics.deals.TravelDealsAdapter


object RecyclerViewBindingAdapter {

    @BindingAdapter(value = ["entries", "layout", "isAdministrator"], requireAll = true)
    @JvmStatic
    fun setupRecyclerView(
            viewGroup: ViewGroup,
            travelDeals: ArrayList<TravelDeal>,
            recyclerView: RecyclerView,
            isAdministrator: Boolean
    ) {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(viewGroup.context)
        recyclerView.adapter = TravelDealsAdapter(travelDeals, isAdministrator)
    }
}
