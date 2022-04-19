package com.kailin.basic_arch.ui.github

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kailin.basic_arch.app.DataStateViewModel
import com.kailin.basic_arch.data.github.GithubPagingRepository
import com.kailin.basic_arch.api.github.GithubService
import com.kailin.basic_arch.model.github.Repo
import com.kailin.basic_arch.utils.connect.ConnectHelper
import kotlinx.coroutines.flow.Flow

class GithubViewModel : DataStateViewModel() {

    private val githubPagingRepository: GithubPagingRepository by lazy {
        val service = ConnectHelper.createService(GithubService::class.java)
        return@lazy GithubPagingRepository(service = service)
    }
    private var currentKeyword: String? = null
    private var mCurrentRepo: Flow<PagingData<Repo>>? = null
    private val _isShowError = MutableLiveData<Boolean>()
    val isShowError: LiveData<Boolean> = _isShowError
    val keyword = MutableLiveData<String>()

    fun searchRepo(): Flow<PagingData<Repo>>? {
        val keywordValue: String = keyword.value ?: ""
        if (currentKeyword == keywordValue) {
            return mCurrentRepo
        }
        currentKeyword = keywordValue
        mCurrentRepo =
            githubPagingRepository.getPagingData(currentKeyword!!).cachedIn(viewModelScope)
        return mCurrentRepo
    }

    fun setShowError(isShowError: Boolean) {
        _isShowError.value = isShowError
    }
}