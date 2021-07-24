package com.avenger.nobrokerassignment.ui.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.avenger.nobrokerassignment.R


class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lottieAnimation = view.findViewById<LottieAnimationView>(R.id.dataAnimation)
        lottieAnimation.setAnimation(R.raw.datasplash)
        lottieAnimation.playAnimation()

        val navController = Navigation.findNavController(view)

        Handler().postDelayed({
            navController.navigate(R.id.action_splashFragment_to_homeFragment)
        }, 3000)
    }
}