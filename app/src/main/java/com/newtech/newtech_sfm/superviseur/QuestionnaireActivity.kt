package com.newtech.newtech_sfm.superviseur


import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class QuestionnaireActivity : AppCompatActivity()  {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(binding.root)

        val parentLinLayout = LinearLayout(this)
        parentLinLayout.orientation = LinearLayout.VERTICAL

        val linLayoutParam =
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        setContentView(parentLinLayout, linLayoutParam)

        val lpView = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        /*TEXT VIEW*/
        val tv = TextView(this)
        tv.text = "TextView"
        tv.layoutParams = lpView
        parentLinLayout.addView(tv)

        /*RADIO GROUP*/
        val rg = RadioGroup(this)
        rg.orientation = RadioGroup.VERTICAL
        rg.layoutParams = lpView
        val options = arrayOf("Oui", "Non")
        for (i in options.indices) {
            // create a radio button
            val rb = RadioButton(this)
            // set text for the radio button
            rb.text = options[i]
            // assign an automatically generated id to the radio button
            rb.id = View.generateViewId()
            // add radio button to the radio group
            rg.addView(rb)
        }
        parentLinLayout.addView(rg)

        /*CHECKBOX*/

        val childLinLayout = LinearLayout(this)
        childLinLayout.orientation = LinearLayout.HORIZONTAL

        childLinLayout.layoutParams = lpView
        val firstCb = CheckBox(this)
        firstCb.layoutParams = lpView
        firstCb.text = "KALBI"
        firstCb.isChecked = false

        val secondCb = CheckBox(this)
        secondCb.layoutParams = lpView
        secondCb.text = "TEKHMAMI"
        secondCb.isChecked = true


        val thirdCb = CheckBox(this)
        thirdCb.layoutParams = lpView
        thirdCb.text = "BACH YATINI ALLAH"
        thirdCb.isChecked = true


        childLinLayout.addView(firstCb)
        childLinLayout.addView(secondCb)
        childLinLayout.addView(thirdCb)

        parentLinLayout.addView(childLinLayout)

        val spinner : Spinner = Spinner(this)
        parentLinLayout.addView(spinner)


    }

}