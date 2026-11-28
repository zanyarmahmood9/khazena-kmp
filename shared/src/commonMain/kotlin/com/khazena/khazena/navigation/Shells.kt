package com.khazena.khazena.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import com.khazena.khazena.core.designsystem.component.KhazenaIcons
import com.khazena.khazena.feature.business.home.presentation.BusinessHomeRoute
import com.khazena.khazena.feature.business.home.presentation.businessHomeGraph
import com.khazena.khazena.feature.business.profile.presentation.StoreRoute
import com.khazena.khazena.feature.business.profile.presentation.storeGraph
import com.khazena.khazena.feature.business.reports.presentation.ReportsRoute
import com.khazena.khazena.feature.business.reports.presentation.reportsGraph
import com.khazena.khazena.feature.business.services.presentation.ServicesRoute
import com.khazena.khazena.feature.business.services.presentation.servicesGraph
import com.khazena.khazena.feature.home.presentation.HomeRoute
import com.khazena.khazena.feature.home.presentation.homeGraph
import com.khazena.khazena.feature.profile.presentation.ProfileRoute
import com.khazena.khazena.feature.profile.presentation.profileGraph
import com.khazena.khazena.feature.search.presentation.SearchRoute
import com.khazena.khazena.feature.search.presentation.searchGraph
import com.khazena.khazena.feature.wallet.presentation.WalletRoute
import com.khazena.khazena.feature.wallet.presentation.walletGraph
import khazena.core.presentation.generated.resources.Res
import khazena.core.presentation.generated.resources.tab_home
import khazena.core.presentation.generated.resources.tab_profile
import khazena.core.presentation.generated.resources.tab_reports
import khazena.core.presentation.generated.resources.tab_scan_me
import khazena.core.presentation.generated.resources.tab_scan_member
import khazena.core.presentation.generated.resources.tab_search
import khazena.core.presentation.generated.resources.tab_services
import khazena.core.presentation.generated.resources.tab_store
import khazena.core.presentation.generated.resources.tab_wallet
import org.jetbrains.compose.resources.stringResource

private val customerTabs = listOf(
    ShellTab(HomeRoute, Res.string.tab_home, KhazenaIcons.House, KhazenaIcons.HouseFill),
    ShellTab(SearchRoute, Res.string.tab_search, KhazenaIcons.MagnifyingGlass, KhazenaIcons.MagnifyingGlassFill),
    ShellTab(WalletRoute, Res.string.tab_wallet, KhazenaIcons.Wallet, KhazenaIcons.WalletFill),
    ShellTab(ProfileRoute, Res.string.tab_profile, KhazenaIcons.User, KhazenaIcons.UserFill),
)

private val businessTabs = listOf(
    ShellTab(BusinessHomeRoute, Res.string.tab_home, KhazenaIcons.House, KhazenaIcons.HouseFill),
    ShellTab(ServicesRoute, Res.string.tab_services, KhazenaIcons.ListBullets),
    ShellTab(ReportsRoute, Res.string.tab_reports, KhazenaIcons.ChartBar),
    ShellTab(StoreRoute, Res.string.tab_store, KhazenaIcons.Storefront),
)

@Composable
internal fun CustomerShell(
    savedStateHandle: SavedStateHandle,
    onScanMe: () -> Unit,
    onSwitchToBusiness: () -> Unit,
    onSignIn: () -> Unit,
    onOpenGallery: (() -> Unit)?,
    onOpenCategories: () -> Unit,
    onOpenCategory: (String) -> Unit,
    onOpenStore: (String) -> Unit,
) = TabShell(
    tabs = customerTabs,
    savedStateHandle = savedStateHandle,
    actionIcon = KhazenaIcons.QrCode,
    actionLabel = stringResource(Res.string.tab_scan_me),
    onAction = onScanMe,
) { selectTab ->
    homeGraph(
        onSwitchAccount = onSwitchToBusiness,
        onOpenSearch = { selectTab(customerTabs.indexOfFirst { it.route == SearchRoute }) },
        onOpenWallet = { selectTab(customerTabs.indexOfFirst { it.route == WalletRoute }) },
        onScanMe = onScanMe,
        onOpenCategories = onOpenCategories,
        onOpenCategory = onOpenCategory,
        onOpenStore = onOpenStore,
    )
    searchGraph()
    walletGraph()
    profileGraph(onSignIn, onOpenGallery)
}

@Composable
internal fun BusinessShell(
    savedStateHandle: SavedStateHandle,
    onScanMember: () -> Unit,
    onSwitchToPersonal: () -> Unit,
) = TabShell(
    tabs = businessTabs,
    savedStateHandle = savedStateHandle,
    actionIcon = KhazenaIcons.Scan,
    actionLabel = stringResource(Res.string.tab_scan_member),
    onAction = onScanMember,
) {
    businessHomeGraph(onSwitchAccount = onSwitchToPersonal, onScanMember = onScanMember)
    servicesGraph()
    reportsGraph()
    storeGraph()
}
