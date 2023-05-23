package com.jvb93.foody.ui.fragments.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.jvb93.foody.R
import com.jvb93.foody.bindingadapters.RecipesRowBinding
import com.jvb93.foody.databinding.FragmentOverviewBinding
import com.jvb93.foody.models.Result
import com.jvb93.foody.util.retrieveParcelable

class OverviewFragment : Fragment() {
    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Result? = args!!.retrieveParcelable("recipeBundle") as Result?

        if (myBundle != null) {
            binding.mainImageView.load(myBundle.image)
            binding.titleTextView.text = myBundle.title
            binding.likesTextView.text = myBundle.aggregateLikes.toString()
            binding.timeTextView.text = myBundle.readyInMinutes.toString()
            RecipesRowBinding.parseHtml(binding.summaryTextView, myBundle.summary)
        }

        return binding.root
    }

}