package com.geekbrain.android.nasa_api.view.layout.behaviors

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import java.lang.Math.abs

class TextBehavior(context: Context, attributeSet: AttributeSet? = null)
    : CoordinatorLayout.Behavior<View>(context, attributeSet) {

    private val TAG = "TextBehavior"

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return (dependency is AppBarLayout)
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        if(dependency is AppBarLayout){
            (child as TextView).textSize = abs(dependency.y  + dependency.height) * 0.1f
            child.y = dependency.y + dependency.height - child.height
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}