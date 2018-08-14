package com.curbmap.android

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.fotoapparat.result.BitmapPhoto
import io.fotoapparat.result.Photo
import io.fotoapparat.result.PhotoResult
import timber.log.Timber

/**
 * Copyright 2018 curbmap-android-camera
 *
 *
 * ${COPYRIGHT}
 */


class SharedCameraAndPreviewViewModel : ViewModel() {

    private val TAG: String = this.toString()

    private lateinit var bitmapPhoto: BitmapPhoto

    private val bitmap:MutableLiveData<Bitmap> = MutableLiveData()

    private val mphoto: Photo? = null
    private var photodata: ByteArray? = null


    fun setPhotoResult(photoResult: PhotoResult) {
        photoResult.toBitmap().whenAvailable { bitmapPhoto ->
            bitmap.postValue(bitmapPhoto!!.bitmap)
        }

    }

    fun setPhotoByteArray(photo: ByteArray) {
        photodata = photo
    }

    fun setBitmapPhoto(bitmapPhoto: BitmapPhoto?) {
        Timber.d("setBitmap == Null: %b", bitmap.value == null)
        this.bitmapPhoto = bitmapPhoto!!
    }

    fun setBitmap(bitmap: Bitmap) {
        this.bitmap.value = bitmap
    }

    fun isBitmapNull(): Boolean{
        return bitmap.value == null
    }

    fun getBitmap(): Bitmap?{
        return bitmap.value
    }

    fun getBitmapMutableLiveData():MutableLiveData<Bitmap>{
        return bitmap
    }

    fun getBitmapPhoto(): BitmapPhoto? {
        //Timber.d("getBitmapPhoto == Null: %b", bitmapPhoto == null)
        return this.bitmapPhoto
    }

    fun imageSent(): Boolean {
        return false
    }


}

