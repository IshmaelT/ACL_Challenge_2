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


class DealActivity : AppCompatActivity(), DealView {


    private lateinit var storageRef: StorageReference
    private var binding: ActivityDealBinding? = null
    private var viewModel: DealViewModel? = null
    private lateinit var database: DatabaseReference
    private var travelDeal = TravelDeal()

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
                database = FirebaseDatabase.getInstance().getReference("deals")
                database.setValue(viewModel?.travelDeal)
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
                    travelDeal.url = task.result?.toString()
                    viewModel?.travelDeal = travelDeal
                }
                viewModel?.loading = false
            }
        }
    }

    companion object {
        const val PICK_IMAGE = 1
    }
}

