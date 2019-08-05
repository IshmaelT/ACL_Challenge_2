package example.com.acl.travelmantics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class TravelDealsAdapter(
        var travelDeals: List<TravelDeal>? = null
) : RecyclerView.Adapter<TravelDealsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewDataBinding =
                DataBindingUtil.inflate<ViewDataBinding>(inflater, R.layout.view_deal_item, parent, false)
        return ViewHolder(viewDataBinding)
    }

    override fun getItemCount() = travelDeals?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = travelDeals?.get(position) ?: return
        holder.bind(item)
    }

    class ViewHolder(
            private val binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TravelDeal) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()
        }
    }
}