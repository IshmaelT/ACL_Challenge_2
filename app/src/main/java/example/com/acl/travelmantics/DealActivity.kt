package example.com.acl.travelmantics

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import example.com.acl.travelmantics.databinding.ActivityDealBinding
import java.util.*

private const val EXTRA_TRAVEL_DEAL = "extra_travel_deal"

class DealActivity : AppCompatActivity(), DealView {

    private lateinit var storageRef: StorageReference
    private var binding: ActivityDealBinding? = null
    private var viewModel: DealViewModel? = null
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_deal)

        viewModel = DealViewModel(this)
        intent?.extras?.let {
            viewModel?.travelDeal = it[EXTRA_TRAVEL_DEAL] as TravelDeal
        }

        binding?.setVariable(BR.viewModel, viewModel)
        binding?.executePendingBindings()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.mail -> {
                val deal = viewModel?.travelDeal
                database = FirebaseDatabase.getInstance().getReference("deals")
                if(deal?.id != null) {
                    database.child(deal.id!!).setValue(deal)
                } else {
                    database.push().setValue(deal)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSelectImagesClicked() {
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }
        startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE
        )
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE) {
            viewModel?.loading = true

            storageRef = FirebaseStorage.getInstance().reference
            val ref = storageRef.child("images/" + UUID.randomUUID().toString())
            val uri = data?.data ?: return

            val uploadTask = ref.putFile(uri)

            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful)
                    throw task.exception as Exception
                ref.downloadUrl

            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    viewModel?.travelDeal?.url = task.result?.toString()
                    viewModel?.travelDeal = viewModel?.travelDeal!!
                }
                viewModel?.loading = false
            }
        }
    }

    companion object {
        const val PICK_IMAGE = 1
    }
}

