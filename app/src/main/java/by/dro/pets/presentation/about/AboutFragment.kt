package by.dro.pets.presentation.about


import androidx.fragment.app.FragmentManager
import by.dro.pets.BuildConfig
import by.dro.pets.R
import by.dro.pets.databinding.FragmentAboutBinding
import by.dro.pets.presentation.base.BaseDialogFragment
import by.dro.pets.util.addNewRecord


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
        binding.apply {
            aboutFragmentVersionTV.text = getString(R.string.version_n, BuildConfig.VERSION_NAME)
            aboutFragmentCloseIV.setOnClickListener { dismiss() }
            aboutFragmentLogoIV.setOnClickListener {
                requireContext().addNewRecord()
            }
        }
    }
}
