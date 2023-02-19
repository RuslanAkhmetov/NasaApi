package com.geekbrain.android.nasa_api.view.layout.behaviors

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import com.google.android.material.appbar.AppBarLayout

class NestedBehavior (context: Context, attributeSet: AttributeSet? = null)
    : CoordinatorLayout.Behavior<NestedScrollView>(context, attributeSet) {

    private val TAG = "NestedBehavior"

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: NestedScrollView,
        dependency: View
    ): Boolean {
        return (dependency is AppBarLayout)
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: NestedScrollView,
        dependency: View
    ): Boolean {
        if(dependency is AppBarLayout)
            child.y = dependency.y + dependency.height

        return super.onDependentViewChanged(parent, child, dependency)
    }
}