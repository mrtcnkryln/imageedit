package com.mrtcnkryln.image_edit.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CandidateModel(

    @SerializedName("overlayId")
    @Expose
    val overlayId : Int? = null,

    @SerializedName("overlayName")
    @Expose
    val overlayName : String? = null,

    @SerializedName("overlayPreviewIconUrl")
    @Expose
    val overlayPreviewIconUrl : String? = null,

    @SerializedName("overlayUrl")
    @Expose
    val overlayUrl : String? = null,

    var selected : Boolean? = false
) : Serializable

