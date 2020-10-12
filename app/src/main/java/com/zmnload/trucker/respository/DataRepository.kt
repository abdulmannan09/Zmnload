package com.zmnload.trucker.respository


import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.MutableLiveData
import com.github.ybq.android.spinkit.SpinKitView
import com.github.ybq.android.spinkit.SpriteFactory
import com.github.ybq.android.spinkit.Style
import com.zmnload.trucker.R
import com.zmnload.trucker.network.Network
import com.zmnload.trucker.network.OnNetworkResponse
import com.zmnload.trucker.network.serializer.BaseResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers


class DataRepository(
    val network: Network
) {
    var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var mainResponseObservable: Observable<BaseResponse>
    private lateinit var mainResponseLifeData: MutableLiveData<BaseResponse>
    private var response: BaseResponse = BaseResponse()

    companion object {
        var activity: Activity = Activity()
        var progressDialog: Dialog? = null
    }


    fun callApi(
        responseObservable: Observable<BaseResponse>,
        tag: Int, callback: OnNetworkResponse
    ) {
        showDialog()
        mainResponseLifeData = MutableLiveData()
        mainResponseObservable = responseObservable
        compositeDisposable.add(
            mainResponseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BaseResponse>() {
                    override fun onComplete() {
                        hideDialog()
                        callback.onSuccess(response, tag)
                        mainResponseLifeData.value = response
                    }

                    override fun onNext(t: BaseResponse) {
                        response = t
                    }

                    override fun onError(e: Throwable) {
                        hideDialog()
                        callback.onFailure(response, tag)
                    }
                })
        )
    }

    fun showDialog() {
        progressDialog = Dialog(activity, R.style.full_screen_dialog)
        progressDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog?.setCancelable(false)
        progressDialog?.setContentView(R.layout.progress_dialog_layout)
        progressDialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog?.getWindow()?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        val spinKitView: SpinKitView? = progressDialog?.findViewById(R.id.spin_kit)
        val style = Style.values()[12]
        val drawable = SpriteFactory.create(style)
        spinKitView?.setIndeterminateDrawable(drawable)
        if (progressDialog != null) {
            progressDialog?.show()
        }

    }

    fun hideDialog() {
        if (progressDialog != null) {
            progressDialog?.dismiss()
        }
    }
}