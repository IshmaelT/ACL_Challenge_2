package example.com.acl.travelmantics.deals

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import example.com.acl.travelmantics.BR
import example.com.acl.travelmantics.R
import example.com.acl.travelmantics.dealdetails.DealActivity
import example.com.acl.travelmantics.model.TravelDeal

private const val EXTRA_TRAVEL_DEAL = "extra_travel_deal"

class TravelDealsAdapter(
        var travelDeals: List<TravelDeal>? = null,
        var isAdministrator: Boolean = false
) : RecyclerView.Adapter<TravelDealsAdapter.ViewHolder>(), DealClickListener {

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

    inner class ViewHolder(
            private val binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TravelDeal) {
            binding.setVariable(BR.item, item)
            binding.setVariable(BR.listener, this@TravelDealsAdapter)
            binding.executePendingBindings()
        }
    }

    override fun onDealClick(view: View, travelDeal: TravelDeal) {
        val context = view.context
        val intent = Intent().apply {
            setClass(context, DealActivity::class.java)
            putExtra(EXTRA_TRAVEL_DEAL, travelDeal)
        }
        if (isAdministrator) {
            context.startActivity(intent)
        }
    }
}

interface DealClickListener {
    fun onDealClick(view: View, travelDeal: TravelDeal)
}