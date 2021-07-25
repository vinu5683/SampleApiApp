package com.avenger.nobrokerassignment.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.avenger.nobrokerassignment.R
import com.avenger.nobrokerassignment.application.MyApplication
import com.avenger.nobrokerassignment.localdatabase.SampleEntity
import com.avenger.nobrokerassignment.recyclerview.SampleListAdapter
import com.avenger.nobrokerassignment.recyclerview.SampleListAdapterInterface
import com.avenger.nobrokerassignment.repository.SampleRepository
import com.avenger.nobrokerassignment.viewmodels.SampleListViewModel
import com.avenger.nobrokerassignment.viewmodels.SampleListViewModelFactory
import com.bumptech.glide.Glide
import com.todkars.shimmer.ShimmerRecyclerView

class SelectedItemFragment : Fragment(), SampleListAdapterInterface {


    lateinit var v: View
    private lateinit var appClass: MyApplication
    private lateinit var repository: SampleRepository
    private lateinit var sampleViewModel: SampleListViewModel
    private lateinit var mRvHorizontal: ShimmerRecyclerView
    private lateinit var mIvSelectedImage: ImageView
    private lateinit var mTvTitle: TextView
    private lateinit var mTvSubTitle: TextView
    private var list: ArrayList<SampleEntity>? = ArrayList<SampleEntity>()
    private var mRvAdapter = SampleListAdapter(list!!, this)
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
        setRecyclerView()
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(mRvHorizontal)
    }

    private fun setRecyclerView() {
        mRvHorizontal.layoutManager =
            LinearLayoutManager(v.context, LinearLayoutManager.HORIZONTAL, false)
        mRvHorizontal.adapter = mRvAdapter
        sampleViewModel.getMyList()?.observe(viewLifecycleOwner, {
            if (it != null && it.isNotEmpty()) {
                list?.clear()
                list?.addAll(it as ArrayList<SampleEntity>)
                mRvAdapter.notifyDataSetChanged()
            }
        })
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
            mRvHorizontal = findViewById(R.id.rvHorizontal);

            //set Data
            Glide.with(context).load(selectedItem.image).into(mIvSelectedImage)
            mTvTitle.text = selectedItem.title
            mTvSubTitle.text = selectedItem.subTitle
        }
    }

    override fun handleClickEvent(sampleEntity: SampleEntity) {
        sampleViewModel.setSampleEntityObj(sampleEntity)
        val navController = Navigation.findNavController(v)
        navController.navigate(R.id.action_selectedItemFragment_self)
    }


}