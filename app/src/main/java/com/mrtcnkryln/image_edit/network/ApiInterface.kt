package com.mrtcnkryln.image_edit.network

import com.mrtcnkryln.image_edit.model.CandidateModel
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiInterface {
    @GET("candidates/overlay.json")
    fun candidates() : Observable<List<CandidateModel>>

}