package com.avenger.nobrokerassignment.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.avenger.nobrokerassignment.R
import com.avenger.nobrokerassignment.application.MyApplication
import com.avenger.nobrokerassignment.localdatabase.SampleEntity
import com.avenger.nobrokerassignment.repository.SampleRepository
import com.avenger.nobrokerassignment.viewmodels.SampleListViewModel
import com.avenger.nobrokerassignment.viewmodels.SampleListViewModelFactory
import com.bumptech.glide.Glide

class SelectedItemFragment : Fragment() {


    lateinit var v: View
    private lateinit var appClass: MyApplication
    private lateinit var repository: SampleRepository
    private lateinit var sampleViewModel: SampleListViewModel

    private lateinit var mIvSelectedImage: ImageView
    private lateinit var mTvTitle: TextView
    private lateinit var mTvSubTitle: TextView

    private lateinit var selectedItem: SampleEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selected_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        v = view;
        initViewModel()
        selectedItem = sampleViewModel.getSampleEntityObj()
        initializeTheViewsAndListeners()

    }

    private fun initViewModel() {
        appClass = activity?.application as MyApplication
        repository = appClass.repository
        val viewModelFactory = SampleListViewModelFactory(repository)
        sampleViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SampleListViewModel::class.java)
    }


    private fun initializeTheViewsAndListeners() {
        v.apply {
            mIvSelectedImage = findViewById(R.id.ivSelected)
            mTvTitle = findViewById(R.id.tvTitleDetails)
            mTvSubTitle = findViewById(R.id.tvSubTitleDetails)

            //set Data
            Glide.with(context).load(selectedItem.image).into(mIvSelectedImage)
            mTvTitle.text = selectedItem.title
            mTvSubTitle.text = selectedItem.subTitle
        }
    }


}