package example.com.acl.travelmantics

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import example.com.acl.travelmantics.databinding.ActivityListBinding
import java.util.*

private const val REQUEST_CODE_SIGN_IN = -1

class ListActivity : AppCompatActivity() {

    private var auth: FirebaseAuth? = null
    private lateinit var database: DatabaseReference
    private var binding: ActivityListBinding? = null
    private var viewModel: ListViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_list)
        viewModel = ListViewModel()
        binding?.setVariable(BR.viewModel, viewModel)
        binding?.executePendingBindings()

        auth = FirebaseAuth.getInstance()
        auth?.addAuthStateListener {
            if (auth?.currentUser == null) {
                createSignInIntent()
            } else {
                this@ListActivity.runOnUiThread {
                    isAdministrator(auth?.currentUser)
                    setupViews()
                }
            }
        }
    }

    private fun isAdministrator(user: FirebaseUser?) {

        val ref = FirebaseDatabase.getInstance()
                .getReference("Adminstrators")
                .child(user?.uid.toString())

        ref.addValueEventListener(
                object : ValueEventListener {
                    override fun onCancelled(data: DatabaseError) {

                    }

                    override fun onDataChange(data: DataSnapshot) {
                        viewModel?.administrator = data.exists()
                        this@ListActivity.runOnUiThread {
                            invalidateOptionsMenu()
                        }
                    }
                }
        )
    }

    private fun setupViews() {
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


    private fun createSignInIntent() {
        val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build())

        val authUI = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(false)
                .build()

        startActivityForResult(authUI, REQUEST_CODE_SIGN_IN)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_menu, menu)
        menu?.findItem(R.id.add_deal)?.isVisible = viewModel?.administrator!!
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.logout -> {
                signOut()
                true
            }
            R.id.add_deal -> {
                startActivity(Intent(this, DealActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun signOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {

                }
    }
}
