package com.newtech.newtech_sfm.superviseur


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.newtech.newtech_sfm.Activity.ClientActivity
import com.newtech.newtech_sfm.R
import com.newtech.newtech_sfm.databinding.ActivityMerchandisingBinding
import com.newtech.newtech_sfm.databinding.ActivityQuestionnaireBinding
import kotlinx.android.synthetic.main.activity_merchandising.*
import androidx.lifecycle.ViewModelProvider





class QuestionnaireActivity : AppCompatActivity()  {

    private lateinit var navController: NavController
    private lateinit var questionnaireViewModel: QuestionnaireViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityQuestionnaireBinding>(
            this,
            R.layout.activity_questionnaire
        )

        setContentView(binding.root)

        setSupportActionBar(toolbar)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        questionnaireViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(
            this.application
        ).create(QuestionnaireViewModel::class.java)


        setupActionBarWithNavController(navController)

    }

    override fun onNavigateUp(): Boolean {

        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        val id = navController.currentDestination!!.id

        if (id == R.id.questionnaireFragment) {
            val intent = Intent(this, ClientActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            onNavigateUp()
        }

    }

}