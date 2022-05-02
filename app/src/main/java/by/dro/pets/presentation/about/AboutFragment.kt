package by.dro.pets.presentation.about


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import by.dro.pets.BuildConfig
import by.dro.pets.Config
import by.dro.pets.databinding.FragmentAboutBinding
import by.dro.pets.presentation.base.BaseDialogFragment


class AboutFragment : BaseDialogFragment<FragmentAboutBinding>(FragmentAboutBinding::inflate) {

    companion object {
        private const val FRAGMENT_TAG = "about_dialog"

        fun show(fragmentManager: FragmentManager?): AboutFragment? {
            if (fragmentManager == null) return null
            val dialog = AboutFragment()
            dialog.show(fragmentManager, FRAGMENT_TAG)
            return dialog
        }
    }

    override fun initView(vb: FragmentAboutBinding) {
        vb.versionName.text = BuildConfig.VERSION_NAME
        vb.close.setOnClickListener { dismiss() }
        vb.github.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Config.GITHUB_URL))
            startActivity(browserIntent)
        }
        vb.linkedin.setOnClickListener {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(Config.LINKEDIN_URL))
            startActivity(browserIntent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, 0)
    }
}
