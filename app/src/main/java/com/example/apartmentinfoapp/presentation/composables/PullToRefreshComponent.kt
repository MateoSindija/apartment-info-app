package com.example.apartmentinfoapp.presentation.composables

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshComponent(
    isLoadingStates: List<Boolean>,
    modifier: Modifier,
    pullToRefreshState: PullToRefreshState,
    refreshData: () -> Unit,
) {

    Log.d("tu smo", pullToRefreshState.isRefreshing.toString())
    fun isContentLoading(): Boolean {
        return isLoadingStates.any { it }
    }

    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            refreshData()
        }
    }

//
//    LaunchedEffect(pullToRefreshState.isRefreshing) {
//        if (pullToRefreshState.isRefreshing) {
//            refreshData()
//        }
//    }

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