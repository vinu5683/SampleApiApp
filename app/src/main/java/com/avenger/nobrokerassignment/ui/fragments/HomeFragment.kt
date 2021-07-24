package com.avenger.nobrokerassignment.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.avenger.nobrokerassignment.R
import com.avenger.nobrokerassignment.application.MyApplication
import com.avenger.nobrokerassignment.localdatabase.SampleEntity
import com.avenger.nobrokerassignment.recyclerview.SampleListAdapter
import com.avenger.nobrokerassignment.recyclerview.SampleListAdapterInterface
import com.avenger.nobrokerassignment.repository.SampleRepository
import com.avenger.nobrokerassignment.ui.singleactivity.MainActivity
import com.avenger.nobrokerassignment.viewmodels.SampleListViewModel
import com.avenger.nobrokerassignment.viewmodels.SampleListViewModelFactory
import com.google.android.material.textfield.TextInputLayout
import com.todkars.shimmer.ShimmerRecyclerView

class HomeFragment : Fragment(), SampleListAdapterInterface {

    lateinit var mRvHomeItemList: ShimmerRecyclerView
    lateinit var mEtSearch: TextInputLayout
    private var list: ArrayList<SampleEntity>? = ArrayList<SampleEntity>()
    private var mRvAdapter = SampleListAdapter(list!!, this)
    private lateinit var v: View
    private lateinit var appClass: MyApplication
    private lateinit var repository: SampleRepository
    private lateinit var sampleViewModel: SampleListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        v = view;
        initViewModel()
        initializeTheViewsAndListeners()
        showDataOnRecyclerView()
    }

    private fun initViewModel() {
        appClass = activity?.application as MyApplication
        repository = appClass.repository
        val viewModelFactory = SampleListViewModelFactory(repository)
        sampleViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SampleListViewModel::class.java)
    }

    private fun initializeTheViewsAndListeners() {
        mRvHomeItemList = v.findViewById(R.id.rvHomeItemList);
        mEtSearch = v.findViewById(R.id.etSearch)

    }

    private fun showDataOnRecyclerView() {
        mRvHomeItemList.layoutManager = LinearLayoutManager(v.context)

        //clear the old list to update new
        sampleViewModel.getAllResponse()
        sampleViewModel.getMyList().observe(viewLifecycleOwner, {
            if (it != null) {
                list?.clear()
                Log.d("TAG", "showDataOnRecyclerView: $it")
                list?.addAll(it as ArrayList<SampleEntity>)
                mRvHomeItemList.adapter = mRvAdapter
                mRvAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun handleClickEvent(sampleEntity: SampleEntity) {

    }

}