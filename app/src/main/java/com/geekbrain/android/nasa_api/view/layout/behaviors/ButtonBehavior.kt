package com.geekbrain.android.nasa_api.view.layout.behaviors

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import java.lang.Math.abs

class ButtonBehavior(context: Context, attributeSet: AttributeSet? = null)
    : CoordinatorLayout.Behavior<View>(context, attributeSet) {

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
            child.y = dependency.y + dependency.height - child.height/2
            child.x = (dependency.width - child.width*1.5).toFloat()
            child.alpha = 1 - (abs(dependency.y/dependency.height))
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}