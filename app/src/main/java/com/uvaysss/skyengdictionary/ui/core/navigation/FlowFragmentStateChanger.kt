package com.uvaysss.skyengdictionary.ui.core.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.uvaysss.skyengdictionary.R
import com.zhuinden.simplestack.StateChange
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentStateChanger

/**
 * Created by uvays on 22.08.2021.
 */

class FlowFragmentStateChanger(
    fragmentManager: FragmentManager,
    @IdRes containerId: Int
) : DefaultFragmentStateChanger(fragmentManager, containerId) {

    override fun onForwardNavigation(
        fragmentTransaction: FragmentTransaction,
        stateChange: StateChange
    ) {
        fragmentTransaction.setCustomAnimations(
            R.anim.slide_in,
            R.anim.fade_out,
            R.anim.fade_in,
            R.anim.slide_out
        )
    }

    override fun onBackwardNavigation(
        fragmentTransaction: FragmentTransaction,
        stateChange: StateChange
    ) {
        fragmentTransaction.setCustomAnimations(
            R.anim.fade_in,
            R.anim.slide_out,
            R.anim.fade_out,
            R.anim.fade_out
        )
    }

    override fun onReplaceNavigation(
        fragmentTransaction: FragmentTransaction,
        stateChange: StateChange
    ) {
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
    }

    override fun isNotShowing(fragment: Fragment): Boolean = fragment.isHidden

    override fun startShowing(fragmentTransaction: FragmentTransaction, fragment: Fragment) {
        fragmentTransaction.show(fragment)
    }

    override fun stopShowing(fragmentTransaction: FragmentTransaction, fragment: Fragment) {
        fragmentTransaction.hide(fragment)
    }
}