package example.com.acl.travelmantics

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.*
import example.com.acl.travelmantics.databinding.ActivityListBinding
import java.util.*

class ListActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private var binding: ActivityListBinding? = null
    private var viewModel: ListViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list)
        viewModel = ListViewModel()
        binding?.setVariable(BR.viewModel, viewModel)
        binding?.executePendingBindings()

        val travelDeals = ArrayList<TravelDeal>()

        database = FirebaseDatabase.getInstance().getReference("deals")
        database.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                viewModel?.travelDeals?.clear()
                dataSnapshot.children.forEach {
                    val deal = it.getValue(TravelDeal::class.java)
                    deal?.id = it.key
                    deal?.let { locDeal -> travelDeals.add(locDeal) }
                }
                viewModel?.travelDeals = travelDeals
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.logout -> {
                true
            }
            R.id.add_deal -> {
                startActivity(Intent(this, DealActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
