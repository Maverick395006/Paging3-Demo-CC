package com.maverick.paging3democc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.maverick.paging3democc.repository.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(quoteRepository: QuoteRepository) : ViewModel() {

    val list = quoteRepository.getQuotes().cachedIn(viewModelScope)

}