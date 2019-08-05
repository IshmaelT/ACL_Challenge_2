package example.com.acl.travelmantics

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BaseObservable
import androidx.databinding.DataBindingUtil
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import example.com.acl.travelmantics.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {

    private var binding: ActivityListBinding? = null
    private var viewModel: BaseObservable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list)
        binding?.setVariable(BR.viewModel, viewModel)
        binding?.executePendingBindings()
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
            else -> super.onOptionsItemSelected(item)
        }
    }
}
