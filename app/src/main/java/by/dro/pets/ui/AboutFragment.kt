package by.dro.pets.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

import by.dro.pets.R

import by.dro.pets.BuildConfig
import kotlinx.android.synthetic.main.fragment_about.*
import android.content.Intent
import android.net.Uri
import by.dro.pets.Config


class AboutFragment : DialogFragment() {

    companion object {
        private const val FRAGMENT_TAG = "about_dialog"

        fun newInstance() = AboutFragment()

        fun show(fragmentManager: FragmentManager?): AboutFragment? {
            if (fragmentManager == null) return null
            val dialog = newInstance()
            dialog.show(fragmentManager, FRAGMENT_TAG)
            return dialog
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        versionName.text = BuildConfig.VERSION_NAME
        close.setOnClickListener { dismiss() }

        github.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Config.GITHUB_URL))
            startActivity(browserIntent)
        }

        linkedin.setOnClickListener {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(Config.LINKEDIN_URL))
            startActivity(browserIntent)
        }

        versionName.setOnClickListener {
//            val browserIntent = Intent(
//                Intent.ACTION_VIEW,
//                Uri.parse(Config.GOOGLE_PLAY_URL)
//            )
//            startActivity(browserIntent)
        }
        versionNameTxt.setOnClickListener {
//            val browserIntent = Intent(
//                Intent.ACTION_VIEW,
//                Uri.parse(Config.GOOGLE_PLAY_URL)
//            )
//            startActivity(browserIntent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, 0)
    }

    override fun onResume() {
        super.onResume()
//        val params = dialog!!.window!!.attributes
//        params.width = ViewGroup.LayoutParams.MATCH_PARENT
//        params.height = ViewGroup.LayoutParams.MATCH_PARENT
//        dialog!!.window!!.attributes = params as android.view.WindowManager.LayoutParams
    }
}
