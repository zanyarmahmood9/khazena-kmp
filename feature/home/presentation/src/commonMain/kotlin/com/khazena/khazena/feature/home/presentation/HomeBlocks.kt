package com.khazena.khazena.feature.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.khazena.khazena.core.designsystem.KhazenaRadius
import com.khazena.khazena.core.designsystem.KhazenaTheme
import com.khazena.khazena.core.designsystem.component.AccountMode
import com.khazena.khazena.core.designsystem.component.ButtonSize
import com.khazena.khazena.core.designsystem.component.ButtonVariant
import com.khazena.khazena.core.designsystem.component.KhazenaAccountRing
import com.khazena.khazena.core.designsystem.component.KhazenaButton
import com.khazena.khazena.core.designsystem.component.KhazenaIcons
import com.khazena.khazena.core.designsystem.component.KhazenaLogo
import com.khazena.khazena.core.designsystem.component.KhazenaWordmark
import com.khazena.khazena.core.presentation.formatIqd
import com.khazena.khazena.core.presentation.localized
import com.khazena.khazena.feature.home.domain.FeaturedOffer
import com.khazena.khazena.feature.home.domain.HomeFeed
import com.khazena.khazena.feature.home.domain.MemberSummary
import khazena.core.presentation.generated.resources.Res as CoreRes
import khazena.core.presentation.generated.resources.switch_to_business
import khazena.feature.home.presentation.generated.resources.Res
import khazena.feature.home.presentation.generated.resources.home_change_city
import khazena.feature.home.presentation.generated.resources.home_featured_discount
import khazena.feature.home.presentation.generated.resources.home_featured_kicker
import khazena.feature.home.presentation.generated.resources.home_featured_meta
import khazena.feature.home.presentation.generated.resources.home_greeting
import khazena.feature.home.presentation.generated.resources.home_greeting_afternoon
import khazena.feature.home.presentation.generated.resources.home_greeting_evening
import khazena.feature.home.presentation.generated.resources.home_greeting_morning
import khazena.feature.home.presentation.generated.resources.home_invite_kicker
import khazena.feature.home.presentation.generated.resources.home_invite_title
import khazena.feature.home.presentation.generated.resources.home_member_kicker
import khazena.feature.home.presentation.generated.resources.home_my_wallet
import khazena.feature.home.presentation.generated.resources.home_notifications_unread
import khazena.feature.home.presentation.generated.resources.home_saved_summary
import khazena.feature.home.presentation.generated.resources.home_scan_to_save
import khazena.feature.home.presentation.generated.resources.home_search_hint
import khazena.feature.home.presentation.generated.resources.home_share_invite
import khazena.feature.home.presentation.generated.resources.home_sponsored
import khazena.feature.home.presentation.generated.resources.home_view_offer
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

// Positions below are Figma's (02 Home · Customer, 390pt frame), measured from each block's top-start corner.
// Texts whose Figma line height is "normal" use the font's own metrics (TextUnit.Unspecified), as Figma does.

@Composable
internal fun HomeHeader(feed: HomeFeed?, daypart: Daypart, onAction: (HomeAction) -> Unit) {
    val colors = KhazenaTheme.colors
    val type = KhazenaTheme.typography
    Box(Modifier.fillMaxWidth()) {
        Row(Modifier.padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(painterResource(KhazenaLogo.MarkOnPaper), null, Modifier.size(width = 22.9412.dp, height = 26.dp))
            KhazenaWordmark(type.headingH2Tight.copy(lineHeight = TextUnit.Unspecified), colors.textPrimary)
        }
        Row(Modifier.align(Alignment.TopEnd).offset(y = (-8).dp), horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            KhazenaAccountRing(
                mode = AccountMode.Personal,
                initials = feed?.member?.initials.orEmpty(),
                contentDescription = stringResource(CoreRes.string.switch_to_business),
                onClick = { onAction(HomeAction.OnSwitchAccountClick) },
            )
            NotificationsButton(feed?.unreadNotifications ?: 0, Modifier.padding(top = 8.dp))
        }
        if (feed != null) CityToken(feed.city.localized(), Modifier.padding(top = 64.dp))
        val question = stringResource(Res.string.home_greeting)
        val name = feed?.member?.firstName
        // The search field follows the greeting (178dp in Figma), so a longer greeting
        // in Arabic/Kurdish pushes it down instead of hiding under it.
        Column(Modifier.padding(top = 89.dp)) {
            Text(
                if (name == null) question else "${greeting(daypart, name)}\n$question",
                style = type.headingH1,
                color = colors.textPrimary,
                modifier = Modifier.semantics { heading() },
            )
            Spacer(Modifier.height(17.4.dp))
            SearchField({ onAction(HomeAction.OnSearchClick) })
        }
    }
}

@Composable
private fun greeting(daypart: Daypart, name: String): String = stringResource(
    when (daypart) {
        Daypart.Morning -> Res.string.home_greeting_morning
        Daypart.Afternoon -> Res.string.home_greeting_afternoon
        Daypart.Evening -> Res.string.home_greeting_evening
    },
    name,
)

@Composable
private fun NotificationsButton(unread: Int, modifier: Modifier = Modifier) {
    val colors = KhazenaTheme.colors
    val label = stringResource(Res.string.home_notifications_unread, unread)
    Box(modifier.size(56.dp).clearAndSetSemantics { contentDescription = label }) {
        Box(Modifier.matchParentSize().clip(CircleShape).background(colors.surfaceCard), contentAlignment = Alignment.Center) {
            Icon(painterResource(KhazenaIcons.Bell), null, Modifier.size(28.6.dp), tint = colors.textPrimary)
        }
        if (unread > 0) {
            Box(
                Modifier
                    .offset(x = 38.dp, y = (-4).dp)
                    .size(22.dp)
                    .clip(CircleShape)
                    .background(colors.actionPrimary)
                    .border(2.5.dp, colors.surfaceBackground, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    unread.coerceAtMost(9).toString(),
                    style = KhazenaTheme.typography.monoTagS.copy(fontSize = 12.sp, letterSpacing = 0.sp, lineHeight = TextUnit.Unspecified),
                    color = colors.textPrimary,
                )
            }
        }
    }
}

@Composable
private fun CityToken(city: String, modifier: Modifier = Modifier) {
    val colors = KhazenaTheme.colors
    val label = stringResource(Res.string.home_change_city, city)
    Row(
        modifier
            .clip(KhazenaRadius.pill)
            .background(colors.surfaceBackground)
            .clearAndSetSemantics { contentDescription = label }
            .padding(end = 3.dp, top = 3.dp, bottom = 3.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(city.uppercase(), style = KhazenaTheme.typography.monoKicker.copy(lineHeight = TextUnit.Unspecified), color = colors.textPrimary, maxLines = 1)
        Box(Modifier.size(15.dp).background(colors.actionPrimary, RoundedCornerShape(10.dp)), contentAlignment = Alignment.Center) {
            Image(painterResource(KhazenaIcons.CaretDownSmall), null, Modifier.size(9.dp))
        }
    }
}

@Composable
private fun SearchField(onClick: () -> Unit, modifier: Modifier = Modifier) {
    val colors = KhazenaTheme.colors
    val shape = RoundedCornerShape(18.dp)
    Row(
        modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(shape)
            .background(colors.surfaceCard)
            .border(1.dp, colors.borderDefault, shape)
            .clickable(role = Role.Button, onClick = onClick)
            .padding(start = 17.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(painterResource(KhazenaIcons.MagnifyingGlass), null, Modifier.size(20.dp), tint = colors.textPrimary)
        Text(
            stringResource(Res.string.home_search_hint),
            style = KhazenaTheme.typography.bodyM.copy(lineHeight = 19.sp),
            color = colors.textSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
internal fun MembershipCard(member: MemberSummary, onAction: (HomeAction) -> Unit, modifier: Modifier = Modifier) {
    val colors = KhazenaTheme.colors
    val type = KhazenaTheme.typography
    val onViolet = Color.White
    Box(
        modifier
            .fillMaxWidth()
            .height(196.dp)
            .clip(KhazenaRadius.block)
            .background(colors.blockViolet)
            .clickable(role = Role.Button) { onAction(HomeAction.OnWalletClick) },
    ) {
        Image(painterResource(KhazenaLogo.Watermark), null, Modifier.offset(251.5.dp, 86.5.dp).size(112.5.dp, 127.5.dp))
        val kicker = type.monoKicker.copy(lineHeight = TextUnit.Unspecified)
        Text(
            stringResource(Res.string.home_member_kicker, member.plan.localized()).uppercase(),
            style = kicker,
            color = onViolet.copy(alpha = 0.85f),
            maxLines = 1,
            modifier = Modifier.padding(start = 20.dp, top = 20.dp),
        )
        Text(
            member.period.localized().uppercase(),
            style = kicker,
            color = onViolet.copy(alpha = 0.85f),
            maxLines = 1,
            modifier = Modifier.align(Alignment.TopEnd).padding(top = 20.dp, end = 28.5.dp),
        )
        Text(
            formatIqd(member.savedIqd),
            // Figma tracks this -4%; Arabic-script text keeps the design system's 0 so its letters still join.
            style = type.displayL.copy(
                lineHeight = TextUnit.Unspecified,
                letterSpacing = if (type.displayL.letterSpacing.value == 0f) type.displayL.letterSpacing else (-0.04).em,
            ),
            color = onViolet,
            maxLines = 1,
            modifier = Modifier.padding(start = 20.dp, top = 46.dp),
        )
        Text(
            stringResource(Res.string.home_saved_summary, member.visits, member.stores),
            style = type.labelMSemiBold.copy(fontWeight = FontWeight.Medium, lineHeight = TextUnit.Unspecified),
            color = onViolet.copy(alpha = 0.9f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 20.dp, top = 96.dp, end = 20.dp),
        )
        Row(Modifier.padding(start = 20.dp, top = 128.dp), horizontalArrangement = Arrangement.spacedBy(MembershipButtonGap)) {
            KhazenaButton(stringResource(Res.string.home_scan_to_save), { onAction(HomeAction.OnScanToSaveClick) }, size = ButtonSize.Block)
            KhazenaButton(
                stringResource(Res.string.home_my_wallet),
                { onAction(HomeAction.OnWalletClick) },
                variant = ButtonVariant.Paper,
                size = ButtonSize.Block,
                paddingX = 20.dp,
            )
        }
    }
}

private val MembershipButtonGap = 8.dp

@Composable
internal fun FeaturedOfferCard(offer: FeaturedOffer, modifier: Modifier = Modifier) {
    val colors = KhazenaTheme.colors
    val type = KhazenaTheme.typography
    val kicker = type.monoKicker.copy(lineHeight = TextUnit.Unspecified)
    Box(modifier.fillMaxWidth().height(236.dp).clip(KhazenaRadius.block).background(colors.blockSun)) {
        Text(
            stringResource(Res.string.home_featured_kicker).uppercase(),
            style = kicker,
            color = colors.textPrimary,
            modifier = Modifier.padding(start = 20.dp, top = 20.dp),
        )
        if (offer.sponsored) {
            Text(
                stringResource(Res.string.home_sponsored).uppercase(),
                style = kicker,
                color = colors.textPrimary.copy(alpha = 0.7f),
                modifier = Modifier.align(Alignment.TopEnd).padding(top = 20.dp, end = 28.5.dp),
            )
        }
        Box(
            Modifier
                .padding(start = 16.dp, end = 16.dp, top = 46.dp)
                .fillMaxWidth()
                .height(112.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(colors.surfaceCard),
        ) {
            Image(
                painterResource(homeImage(offer.imageKey)),
                null,
                Modifier.padding(14.dp).size(84.dp).clip(KhazenaRadius.sm),
                contentScale = ContentScale.Crop,
            )
            val textModifier = Modifier.padding(start = 112.dp, end = 16.dp).width(190.dp)
            Text(
                offer.title.localized(),
                style = type.labelLBold.copy(fontSize = 17.sp, lineHeight = 1.18.em),
                color = colors.textPrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = textModifier.padding(top = 16.dp),
            )
            Text(
                stringResource(Res.string.home_featured_meta, offer.merchant.localized(), offer.note.localized()),
                style = type.labelSMedium.copy(lineHeight = 1.18.em),
                color = colors.textSecondary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = textModifier.padding(top = 60.dp),
            )
        }
        KhazenaButton(
            stringResource(Res.string.home_view_offer),
            {},
            Modifier.padding(start = 16.dp, top = 172.dp),
            size = ButtonSize.Block,
        )
        Text(
            "⁦" + stringResource(Res.string.home_featured_discount, offer.discountPercent) + "⁩",
            style = type.headingH2.copy(lineHeight = TextUnit.Unspecified),
            color = colors.textPrimary,
            modifier = Modifier.align(Alignment.TopEnd).padding(top = 178.dp, end = 16.dp),
        )
    }
}

@Composable
internal fun InviteCard(modifier: Modifier = Modifier) {
    val colors = KhazenaTheme.colors
    val type = KhazenaTheme.typography
    Box(modifier.fillMaxWidth().height(150.dp).clip(KhazenaRadius.block).background(colors.blockEmber)) {
        Text(
            stringResource(Res.string.home_invite_kicker).uppercase(),
            style = type.monoKicker.copy(lineHeight = TextUnit.Unspecified),
            color = colors.textPrimary,
            modifier = Modifier.padding(start = 20.dp, top = 20.dp),
        )
        Text(
            stringResource(Res.string.home_invite_title),
            style = type.headingH2Tight.copy(lineHeight = 1.04.em),
            color = colors.textPrimary,
            modifier = Modifier.padding(start = 20.dp, top = 42.dp).width(240.dp),
        )
        Image(
            painterResource(KhazenaLogo.MarkOnEmber),
            null,
            Modifier.align(Alignment.TopEnd).padding(top = 20.dp, end = 14.dp).size(48.dp, 54.4.dp),
        )
        KhazenaButton(
            stringResource(Res.string.home_share_invite),
            {},
            Modifier.padding(start = 20.dp, top = 100.dp),
            variant = ButtonVariant.Dark,
            size = ButtonSize.Inline,
        )
    }
}
