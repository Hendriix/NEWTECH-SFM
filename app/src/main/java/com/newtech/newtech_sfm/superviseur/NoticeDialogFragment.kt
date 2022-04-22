package com.newtech.newtech_sfm.superviseur

import android.content.Context
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.newtech.newtech_sfm.R


class NoticeDialogFragment(dialogText: String, noticeDialogListener: NoticeDialogListener) : DialogFragment() {
    // Use this instance of the interface to deliver action events
    var listener: NoticeDialogListener = noticeDialogListener
    val messageDialog : String = dialogText

    /*fun newInstance(textDialog : String): NoticeDialogFragment {
        messageDialog = textDialog
        return NoticeDialogFragment(R.string.cancel_message)
    }*/


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.custom_confirm_dialog, container, false)
        val dialogTextView : TextView = v.findViewById(R.id.alertencaissement)
        val confirmButton : Button = v.findViewById(R.id.btn_oui)
        val cancelButton : Button = v.findViewById(R.id.btn_non)

        dialogTextView.text = messageDialog

        confirmButton.setOnClickListener {
            listener.onDialogPositiveClick(this)
        }

        cancelButton.setOnClickListener {
            listener.onDialogNegativeClick(this)
        }
        return v
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = context as NoticeDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException(
                (context.toString() +
                        " must implement NoticeDialogListener")
            )
        }
    }

    interface NoticeDialogListener {
        fun onDialogPositiveClick(dialog: NoticeDialogFragment)
        fun onDialogNegativeClick(dialog: NoticeDialogFragment)
    }
}
