package com.newtech.newtech_sfm.merchandising


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.newtech.newtech_sfm.Activity.ClientActivity
import com.newtech.newtech_sfm.R
import com.newtech.newtech_sfm.databinding.ActivityMerchandisingBinding
import com.newtech.newtech_sfm.model.VisibiliteViewModel
import kotlinx.android.synthetic.main.activity_merchandising.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MerchandisingActivity : AppCompatActivity() {

    private val sharedViewModel: VisibiliteViewModel = VisibiliteViewModel()
    val df_code: DateFormat = SimpleDateFormat("yyMMddHHmmss")
    val date_code = df_code.format(Calendar.getInstance().time)


    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMerchandisingBinding>(
            this,
            R.layout.activity_merchandising
        )

        sharedViewModel.setVisibliteCode("VC" + date_code)

        Log.d("MERCHANSISING", "onCreate: " + sharedViewModel.getVisibliteCode())

        setContentView(binding.root)

        setSupportActionBar(toolbar)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        navController = navHostFragment.navController


        setupActionBarWithNavController(navController)

    }

    override fun onNavigateUp(): Boolean {

        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        val id = navController.currentDestination!!.id

        if (id == R.id.articleFragment) {
            val intent = Intent(this, ClientActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            onNavigateUp()
        }

    }

}