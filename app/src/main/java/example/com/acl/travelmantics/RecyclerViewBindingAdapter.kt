package example.com.acl.travelmantics

import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


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
