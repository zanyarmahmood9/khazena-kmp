package com.khazena.khazena.feature.home.presentation.categories

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.khazena.khazena.core.presentation.state.ContentLoader
import com.khazena.khazena.feature.home.domain.HomeRepository
import com.khazena.khazena.feature.home.presentation.CategoryRoute
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

private const val SUBCATEGORY_KEY = "subcategory"
private const val SORT_KEY = "sort"

class CategoryViewModel(
    repository: HomeRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val categoryId = savedStateHandle.toRoute<CategoryRoute>().id

    private val eventChannel = Channel<CategoryEvent>()
    val events = eventChannel.receiveAsFlow()

    private val subcategoryId = savedStateHandle.getStateFlow<String?>(SUBCATEGORY_KEY, null)
    private val sort = savedStateHandle.getStateFlow(SORT_KEY, PlaceSort.Discount.name)
    private val sortSheetOpen = MutableStateFlow(false)

    private val detail = ContentLoader(
        scope = viewModelScope,
        // An empty category still has its hero; the list says there are no places.
        isEmpty = { false },
        onRefreshFailed = { eventChannel.send(CategoryEvent.ShowMessage(it)) },
        fetch = { repository.getCategory(categoryId) },
    )

    val state = combine(detail.state, subcategoryId, sort, sortSheetOpen) { detail, subcategory, sort, sheetOpen ->
        CategoryState(detail, subcategory, PlaceSort.valueOf(sort), sheetOpen)
    }
        .onStart { detail.loadOnce() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5.seconds), CategoryState())

    fun onAction(action: CategoryAction) {
        when (action) {
            is CategoryAction.OnSubcategoryClick -> savedStateHandle[SUBCATEGORY_KEY] = action.id
            is CategoryAction.OnPickSort -> {
                savedStateHandle[SORT_KEY] = action.sort.name
                sortSheetOpen.value = false
            }
            CategoryAction.OnSortClick -> sortSheetOpen.value = true
            CategoryAction.OnDismissSort -> sortSheetOpen.value = false
            CategoryAction.OnRetry -> detail.load()
            CategoryAction.OnRefresh -> detail.refresh()
            else -> Unit
        }
    }
}
