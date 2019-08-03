package example.com.acl.travelmantics

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import example.com.acl.travelmantics.databinding.ActivityDealBinding

class DealActivity : AppCompatActivity(), DealView {

    private var binding: ActivityDealBinding? = null
    private var viewModel: DealViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_deal)
        viewModel = DealViewModel(this)
        binding?.setVariable(BR.viewModel, viewModel)
        binding?.executePendingBindings()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.mail -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSelectImagesClicked() {

    }
}

