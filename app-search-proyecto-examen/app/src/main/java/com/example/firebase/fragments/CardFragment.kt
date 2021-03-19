package com.example.firebase.fragments

import android.animation.LayoutTransition
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction
import android.widget.SeekBar
import androidx.annotation.Nullable
import androidx.annotation.VisibleForTesting
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.example.firebase.R
import com.google.android.material.card.MaterialCardView


class CardFragment : Fragment() {


    private val TAG = "CardViewFragment"

    // The [CardView] widget.
    @VisibleForTesting
    lateinit var cardView: CardView

    // SeekBar that changes the cornerRadius attribute for the cardView widget.
    @VisibleForTesting lateinit var radiusSeekBar: SeekBar

    // SeekBar that changes the Elevation attribute for the cardView widget.
    @VisibleForTesting lateinit var elevationSeekBar: SeekBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardView = view.findViewById(R.id.cardview)

        radiusSeekBar = view.findViewById(R.id.cardview_radius_seekbar)
        radiusSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                Log.d(TAG, "SeekBar Radius progress: $progress")
                cardView.radius = progress.toFloat()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) = Unit // Do nothing

            override fun onStopTrackingTouch(seekBar: SeekBar) = Unit // Do nothing
        })

        elevationSeekBar = view.findViewById(R.id.cardview_elevation_seekbar)
        elevationSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                Log.d(TAG, "SeekBar Elevation progress : $progress")
                cardView.elevation = progress.toFloat()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) = Unit // Do nothing

            override fun onStopTrackingTouch(seekBar: SeekBar) = Unit // Do nothing
        })
    }
}