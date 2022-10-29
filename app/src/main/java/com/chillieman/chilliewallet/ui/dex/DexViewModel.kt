package com.chillieman.chilliewallet.ui.dex

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chillieman.chilliewallet.db.entity.Dex
import com.chillieman.chilliewallet.model.PageMode
import com.chillieman.chilliewallet.repository.DexRepository
import com.chillieman.chilliewallet.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class DexViewModel
@Inject constructor(
    private val dexRepo: DexRepository
) : BaseViewModel() {
    private val _pageMode = MutableLiveData<PageMode>()
        .apply { value = PageMode.LIST }
    val pageMode: LiveData<PageMode>
        get() = _pageMode

    private val _dexList = MutableLiveData<List<Dex>>()
    val dexList: LiveData<List<Dex>>
        get() = _dexList

    private val _selectedDex = MutableLiveData<Dex?>()
    val selectedDex: LiveData<Dex?>
        get() = _selectedDex


    fun refreshList(blockChainId: Long) {
        dexRepo.getAllDexByBlockChainId(blockChainId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _dexList.value = it
                Log.d(TAG, "Loaded Dex List")
            }, {
                Log.e(TAG, "Could not fetch Dex List", it)
            }).disposeOnClear()
    }

    fun selectDex(dex: Dex) {
        _selectedDex.value = dex
        _pageMode.value = PageMode.DETAIL
    }

    fun resetSelectedDex() {
        _selectedDex.value = null
        _pageMode.value = PageMode.LIST
    }

    fun onEditButtonPressed(dex: Dex) {
        _selectedDex.value = dex
        _pageMode.value = PageMode.EDIT
    }

    fun onEditBackPressed() {
        _pageMode.value = PageMode.DETAIL
    }

    fun onEditSave(dex: Dex) {
        dexRepo.updateDex(dex)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _selectedDex.value = dex
                _pageMode.value = PageMode.DETAIL
            }, {
                Log.e(TAG, "Could not Update Dex", it)
            }).disposeOnClear()
    }

    fun onNewDexButtonPressed() {
        _selectedDex.value = null
        _pageMode.value = PageMode.CREATE
    }

    fun onBackPressed() {
        when (_pageMode.value) {
            PageMode.DETAIL,
            PageMode.CREATE -> resetSelectedDex()
            PageMode.EDIT -> onEditBackPressed()
            else -> Unit
        }
    }

    companion object {
        private const val TAG = "DexViewModel"
    }
}
