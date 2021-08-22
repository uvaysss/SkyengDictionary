package com.uvaysss.skyengdictionary.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.ViewGroup
import androidx.core.view.WindowCompat
import androidx.core.view.children
import com.uvaysss.skyengdictionary.R
import com.uvaysss.skyengdictionary.databinding.ActivityAppBinding
import com.uvaysss.skyengdictionary.ui.core.hideKeyboard
import com.uvaysss.skyengdictionary.ui.core.navigation.FlowFragmentStateChanger
import com.uvaysss.skyengdictionary.ui.wordlist.WordListKey
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.SimpleStateChanger
import com.zhuinden.simplestack.navigator.Navigator
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider

class AppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fixes issue when starting the app again from icon on launcher
        if (isTaskRoot.not()
            && intent.hasCategory(Intent.CATEGORY_LAUNCHER)
            && intent.action != null
            && intent.action == Intent.ACTION_MAIN
        ) {
            finish()
            return
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setTheme(R.style.Theme_App)

        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewAppRoot.setOnApplyWindowInsetsListener { view, insets ->
            var consumed = false

            (view as ViewGroup).children.forEach { child ->
                val childResult = child.dispatchApplyWindowInsets(insets)
                if (childResult.isConsumed) {
                    consumed = true
                }
            }

            if (consumed) insets.consumeSystemWindowInsets() else insets
        }

        val fragmentStateChanger = FlowFragmentStateChanger(
            supportFragmentManager,
            R.id.viewAppRoot
        )

        Navigator
            .configure()
            .setScopedServices(DefaultServiceProvider())
            .setStateChanger(SimpleStateChanger {
                hideKeyboard()
                fragmentStateChanger.handleStateChange(it)
            })
            .install(this, binding.viewAppRoot, History.of(WordListKey()))
    }

    override fun onBackPressed() {
        if (!Navigator.onBackPressed(this)) {
            super.onBackPressed()
        }
    }
}