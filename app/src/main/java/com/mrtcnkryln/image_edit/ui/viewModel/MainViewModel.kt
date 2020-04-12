package com.mrtcnkryln.image_edit.ui.viewModel

import androidx.lifecycle.MutableLiveData
import com.mrtcnkryln.image_edit.model.CandidateModel
import com.mrtcnkryln.image_edit.network.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel : BaseViewModel() {
    var apiInterface : ApiInterface? = null
        @Inject set
    private  val compositeDisposable = CompositeDisposable()
    var resultLiveData : MutableLiveData<List<CandidateModel>> = MutableLiveData()

    fun getHotel() {
        apiInterface?.candidates()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.doOnSubscribe {

            }
            ?.subscribe(
                { result ->
                    resultLiveData.value = result
                },
                {
                    // set state
                }
            )?.let { compositeDisposable.add(it) }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}