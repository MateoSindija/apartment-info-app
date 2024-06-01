package com.example.apartmentinfoapp.presentation.composables

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshComponent(
    isLoadingStates: List<Boolean>,
    modifier: Modifier,
    refreshData: () -> Unit,
) {
    val pullToRefreshState = rememberPullToRefreshState()

    fun isContentLoading(): Boolean {
        return isLoadingStates.any { it }
    }

    LaunchedEffect(pullToRefreshState.isRefreshing) {
        if (pullToRefreshState.isRefreshing) {
            refreshData()
            pullToRefreshState.endRefresh()
        }
    }

    LaunchedEffect(isLoadingStates) {
        if (isContentLoading()) {
            pullToRefreshState.startRefresh()
        } else {
            pullToRefreshState.endRefresh()
        }
    }

    PullToRefreshContainer(
        state = pullToRefreshState,
        modifier = modifier
    )
}