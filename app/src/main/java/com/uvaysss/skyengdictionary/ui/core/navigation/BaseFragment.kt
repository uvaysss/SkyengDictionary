package com.uvaysss.skyengdictionary.ui.core.navigation

import com.zhuinden.simplestackextensions.fragments.DefaultFragmentKey
import moxy.MvpAppCompatFragment
import java.lang.NullPointerException
import javax.annotation.Nonnull

/**
 * A default base fragment that contains a method to get the key from the arguments.
 */

open class BaseFragment : MvpAppCompatFragment() {

    /**
     * Returns the key added to the arguments of the Fragment.
     *
     * @param <T> the type of the key
     * @return the key
     */
    @Nonnull
    fun <T : DefaultFragmentKey?> getKey(): T {
        return requireArguments().getParcelable(DefaultFragmentKey.ARGS_KEY)
            ?: throw NullPointerException("The key provided as fragment argument should not be null!")
    }
}