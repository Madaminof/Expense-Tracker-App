
package com.example.expensetracker.presentation.ui

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.expancetracker.R
import com.example.expensetracker.presentation.Screen
import com.example.expensetracker.presentation.getGreetingText
import com.example.expensetracker.presentation.ui.ReklamUI.ReklamScreen
import com.example.expensetracker.presentation.ui.ScaffoldComponents.AppBottomBar
import com.example.expensetracker.presentation.viewmodel.ExpenseViewModel
import com.example.expensetracker.presentation.viewmodel.ProfileViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: ExpenseViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel()

) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val profile by profileViewModel.profile.collectAsState()
    val profileUri = profile?.imageUri?.let { Uri.parse(it) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            HomeTopBar(
                userName = profile?.fullName?:"Foydalanuvchi",
                onNotificationClick = { navController.navigate(Screen.Notification.route) },
                scrollBehavior = scrollBehavior,
                profileImageUri = profileUri,
            )
        },
        contentWindowInsets = WindowInsets.navigationBars, //  muhim qism
        bottomBar = {
            AppBottomBar(
                navController = navController,
                currentRoute = currentRoute
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddEdit.route) },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .offset(y = 50.dp)
                    .size(64.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Expense",
                    tint = MaterialTheme.colorScheme.onPrimary
                )

            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {
            TotalCard(viewModel)
            PostTabRow(viewModel)
        }



    }
}


@SuppressLint("SuspiciousIndentation")
@Composable
fun PostTabRow(viewModel: ExpenseViewModel) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Daromad", "Xarajat")

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .shadow(
                    elevation = 6.dp,
                    shape = RoundedCornerShape(16.dp),
                    clip = false
                )
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.background)
        ) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary,
                indicator = {}, // indikatorni olib tashladik
                divider = {},
            ) {
                tabs.forEachIndexed { index, type ->
                    val isSelected = selectedTabIndex == index

                    val backgroundColor by animateColorAsState(
                        targetValue = if (isSelected)
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                        else
                            Color.Transparent,
                        animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing),
                        label = ""
                    )

                    val borderColor by animateColorAsState(
                        targetValue = if (isSelected)
                            MaterialTheme.colorScheme.primary
                        else
                            Color.Transparent,
                        animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing),
                        label = ""
                    )

                    val textColor by animateColorAsState(
                        targetValue = if (isSelected)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant,
                        animationSpec = tween(durationMillis = 250, easing = LinearEasing),
                        label = ""
                    )

                    // 🔹 Font o‘lchami uchun animatsiya (optional)
                    val fontSize by animateDpAsState(
                        targetValue = if (isSelected) 18.dp else 16.dp,
                        animationSpec = tween(durationMillis = 250, easing = LinearOutSlowInEasing),
                        label = ""
                    )

                    Tab(
                        selected = isSelected,
                        onClick = { selectedTabIndex = index },
                        modifier = Modifier
                            .padding(6.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(backgroundColor)
                            .border(
                                width = 1.dp,
                                color = borderColor,
                                shape = RoundedCornerShape(12.dp)
                            ),
                        text = {
                            Text(
                                text = type,
                                fontSize = with(LocalDensity.current) { fontSize.toSp() },
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = textColor
                            )
                        }
                    )
                }
            }

        }

        Box(
            modifier = Modifier
                .padding(top = 4.dp)
        ) {
            AnimatedContent(
                targetState =selectedTabIndex,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                },
                label = "ExpenseTabAnimation"
            ) { selectedTabIndex ->
                when (selectedTabIndex) {
                    0 -> ExpenseListScreen(viewModel, EnumTypes.income.toString())
                    1 -> ExpenseListScreen(viewModel, EnumTypes.expense.toString())
                }
            }
        }
    }




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    userName: String,
    onNotificationClick: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior,
    profileImageUri: Uri?,
) {
    val painter = rememberAsyncImagePainter(
        model = profileImageUri ?: R.drawable.finance_logo
    )
    LargeTopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Card(
                    shape = CircleShape,
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.tertiaryContainer,
                            shape = CircleShape
                        )

                        .clickable {  },
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Image(
                        painter = painter,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(horizontal = 12.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    // ⭐ 1-TO'G'RILASH: Yuqori matn
                    Text(
                        text = getGreetingText(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onPrimary,
                        lineHeight = 14.sp,
                        modifier = Modifier.padding(bottom = 0.dp)
                    )
                    Text(
                        text = userName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary,
                        lineHeight = 16.sp,
                        modifier = Modifier.padding(top = 0.dp)
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = onNotificationClick) {
                Icon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = "Notifications",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            scrolledContainerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}


@Composable
fun TotalCard(viewModel: ExpenseViewModel) {
    val expense by viewModel.expenseTotal.collectAsState()
    val income by viewModel.incomeTotal.collectAsState()
    val totalBalance = income - expense


    Box(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 32.dp,
                        bottomEnd = 32.dp
                    )
                )
        )

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer // fonni shaffof qilamiz
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = 4.dp)
                .zIndex(1f)
                .padding(horizontal = 32.dp)
                .height(150.dp)
                .fillMaxWidth()
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(16.dp),
                    ambientColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                    spotColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
                )
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                // 🖼️ Pattern rasm (to‘lqin yoki dot texture)
                Image(
                    painter = painterResource(id = R.drawable.bg_pattern_wave), // pattern.png
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    alpha = 0.15f // biroz shaffoflik
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.25f),
                                    MaterialTheme.colorScheme.background.copy(alpha = 0.05f)
                                )
                            )
                        )
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(
                        text = "Balans: ${"%,.1f".format(totalBalance)} so'm",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )

                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .padding(bottom = 8.dp, start = 8.dp, end = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.tertiaryContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        Icons.Filled.ArrowDownward,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.background,
                                        modifier = Modifier
                                            .size(16.dp)
                                    )

                                }
                                Spacer(Modifier.width(4.dp))
                                Text("Income", color = MaterialTheme.colorScheme.onPrimary, fontSize = 14.sp)
                            }
                            Text(
                                "+ ${"%,.1f".format(income)}",
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFC62828)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        Icons.Filled.ArrowUpward,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.background,
                                        modifier = Modifier
                                            .size(16.dp)
                                    )

                                }
                                Spacer(Modifier.width(4.dp))
                                Text("Expense", color = MaterialTheme.colorScheme.onPrimary, fontSize = 14.sp)
                            }
                            Text(
                                "- ${"%,.1f".format(expense)}",
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }


    }
    Spacer(modifier = Modifier.height(12.dp))

}

