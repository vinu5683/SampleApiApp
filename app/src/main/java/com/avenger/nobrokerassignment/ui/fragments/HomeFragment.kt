package com.avenger.nobrokerassignment.ui.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.avenger.nobrokerassignment.R
import com.avenger.nobrokerassignment.application.MyApplication
import com.avenger.nobrokerassignment.localdatabase.SampleEntity
import com.avenger.nobrokerassignment.recyclerview.SampleListAdapter
import com.avenger.nobrokerassignment.recyclerview.SampleListAdapterInterface
import com.avenger.nobrokerassignment.repository.SampleRepository
import com.avenger.nobrokerassignment.viewmodels.SampleListViewModel
import com.avenger.nobrokerassignment.viewmodels.SampleListViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.todkars.shimmer.ShimmerRecyclerView
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), SampleListAdapterInterface {

    lateinit var mRvHomeItemList: ShimmerRecyclerView
    lateinit var mEtSearch: TextInputLayout
    lateinit var mEtSearchTextBox: TextInputEditText
    lateinit var mLottieAnimationView: LottieAnimationView
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
        if (!isNetworkConnected()) {
            Toast.makeText(v.context, "turn on data", Toast.LENGTH_SHORT).show()
        }
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
        mEtSearchTextBox = v.findViewById(R.id.searchTextBox)
        mLottieAnimationView = v.findViewById(R.id.noDataAnimation)
        mRvHomeItemList.layoutManager = LinearLayoutManager(v.context)
        mRvHomeItemList.adapter = mRvAdapter
        setShimmerViewType()

    }

    //todo: For Shimmer Effect
    private fun setShimmerViewType() {
        mRvHomeItemList.setItemViewType { layoutManagerType, position ->
            when (layoutManagerType) {
                ShimmerRecyclerView.LAYOUT_GRID -> {
                    if (position % 2 == 0) {
                        return@setItemViewType R.layout.demo_shimmer_grid
                    } else
                        return@setItemViewType R.layout.demo_shimmer_grid
                }
                ShimmerRecyclerView.LAYOUT_LIST -> {
                    if (position == 0 && position % 2 == 0) {
                        return@setItemViewType R.layout.demo_shimmer_grid
                    } else
                        return@setItemViewType R.layout.demo_shimmer_grid
                }
                else -> return@setItemViewType R.layout.demo_shimmer_grid
            }
        }
        mRvHomeItemList.showShimmer()
    }

    private fun showDataOnRecyclerView() {

        //clear the old list to update new
        sampleViewModel.getAllResponse().observe(viewLifecycleOwner, { })
        try {
            sampleViewModel.getMyList()?.observe(viewLifecycleOwner, {
                if (it != null && it.isNotEmpty()) {
                    list?.clear()
                    Log.d("TAG", "showDataOnRecyclerView: $it")
                    list?.addAll(it as ArrayList<SampleEntity>)
                    mRvHomeItemList.adapter = mRvAdapter
                    mRvAdapter.notifyDataSetChanged()
                    rvHomeItemList.hideShimmer()
                    hideEmptyAnimation()
                } else {
                    showEmptyAnimation()
                }
            })
        } catch (e: Exception) {
            Toast.makeText(v.context, "turn on Internet", Toast.LENGTH_SHORT).show()
        }

        //searching feature.
        mEtSearchTextBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                try {
                    if (mEtSearchTextBox.text.toString().trim().length >= 3) {
                        sampleViewModel.searchByText(mEtSearchTextBox.text.toString().trim())
                            ?.observe(viewLifecycleOwner, {
                                if (it != null && it.isNotEmpty()) {
                                    hideEmptyAnimation()
                                    list?.clear()
                                    list?.addAll(it as ArrayList<SampleEntity>)
                                    mRvAdapter.notifyDataSetChanged()
                                } else {
                                    showEmptyAnimation()
                                }
                            })
                    } else {
                        sampleViewModel.getMyList()?.observe(viewLifecycleOwner, {
                            if (it != null && it.isNotEmpty()) {
                                hideEmptyAnimation()
                                list?.clear()
                                list?.addAll(it as ArrayList<SampleEntity>)
                                mRvAdapter.notifyDataSetChanged()
                            } else {
                                showEmptyAnimation()
                            }
                        })
                    }
                } catch (e: Exception) {
                    Log.d("TAG", "onTextChanged: $e")
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun showEmptyAnimation() {
        mRvHomeItemList.visibility = View.GONE
        mLottieAnimationView.visibility = View.VISIBLE
    }

    private fun hideEmptyAnimation() {
        mRvHomeItemList.visibility = View.VISIBLE
        mLottieAnimationView.visibility = View.GONE
        mLottieAnimationView.setAnimation(R.raw.nodata)
        mLottieAnimationView.playAnimation()
    }

    override fun handleClickEvent(sampleEntity: SampleEntity) {
        sampleViewModel.setSampleEntityObj(sampleEntity)
        val navController = Navigation.findNavController(v)
        navController.navigate(R.id.action_homeFragment_to_selectedItemFragment)
    }

    private fun isNetworkConnected(): Boolean {
        val cm = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }


}