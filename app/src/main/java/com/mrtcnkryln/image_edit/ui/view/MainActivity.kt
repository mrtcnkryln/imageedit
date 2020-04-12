package com.mrtcnkryln.image_edit.ui.view

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.mrtcnkryln.image_edit.R
import com.mrtcnkryln.image_edit.model.CandidateModel
import com.mrtcnkryln.image_edit.ui.view.adapter.CandidateAdapter
import com.mrtcnkryln.image_edit.ui.view.custom.CustomImageView
import com.mrtcnkryln.image_edit.ui.viewModel.MainViewModel
import com.mrtcnkryln.image_edit.util.ImageUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        fun newInstance() = MainActivity()
    }


    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updateUI() //observe the view model
    }

    private fun updateUI() {
        checkselfPermission()
        imgSave.setOnClickListener{
            onImageSave()
        }
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.getHotel()
        mainViewModel.resultLiveData.observe(this, Observer { setCandidate(it)  })

    }

    private fun setCandidate(candidateModels: List<CandidateModel>) {
        //binding candidates model to view
        recyclerImages.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL,false)
        recyclerImages.adapter = CandidateAdapter(setEmptyItem(candidateModels))

    }

    private fun setEmptyItem(candidates: List<CandidateModel>) : MutableList<CandidateModel> {
        val candidateModel = CandidateModel()
        val mutableCandidates = candidates.toMutableList()
        mutableCandidates.add(0, candidateModel)
        return mutableCandidates
    }

    fun selectImage(candidateModel: CandidateModel){
        Glide.with(this)
            .asBitmap()
            .load(candidateModel.overlayUrl)
            .into(object : CustomTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    customImage!!.setIcon(resource)
                }
                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })
    }

    fun noneSelected(){
        customImage!!.iconClear()
    }

    private fun checkselfPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        }
    }

    private fun onImageSave(){
        val imageUtil = ImageUtil(this)
        imageUtil.saveImage(contentLayout)
    }

}
