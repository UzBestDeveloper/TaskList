package com.developeruz.tasklist.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope

abstract class BaseFragment(
    @LayoutRes contentLayoutId: Int
) : Fragment(contentLayoutId) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setup()
        viewLifecycleOwner.lifecycleScope.launchWhenCreated { collect() }
        observe()
        clicks()
    }

    open suspend fun collect() {}

    open fun observe() {}

    open fun setup() {}

    open fun clicks() {}

}