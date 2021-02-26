package com.raywenderlich.android.gardenplanner

import androidx.navigation.dynamicfeatures.fragment.ui.AbstractProgressFragment
import kotlinx.android.synthetic.main.fragment_progress.view.*

class GardenPlannerProgressFragment : AbstractProgressFragment(R.layout.fragment_progress) {

    override fun onProgress(status: Int, bytesDownloaded: Long, bytesTotal: Long) {
        view?.progressBar?.progress = (bytesDownloaded.toDouble() * 100 / bytesTotal).toInt()
    }

    override fun onFailed(errorCode: Int) {
        view?.message?.text = getString(R.string.installing_module_failed)
    }

    override fun onCancelled() {
        view?.message?.text = getString(R.string.installing_module_cancelled)
    }


}