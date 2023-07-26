package com.longhrk.app.ui.screen

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Light
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import com.longhrk.app.core.GetDimension
import com.longhrk.app.ui.components.SearchApp
import com.longhrk.app.ui.theme.black
import com.longhrk.app.ui.theme.blue
import com.longhrk.app.ui.theme.gray
import com.longhrk.matrix.viewmodel.MatrixViewModel
import org.matrix.android.sdk.api.session.getRoom
import org.matrix.android.sdk.api.session.getUser
import com.longhrk.app.R as resApp
import com.longhrk.dimension.R as resDimension

@Composable
fun HomeScreen(
    onDetailChatScreen: () -> Unit,
    onBackPress: () -> Unit,
    matrixViewModel: MatrixViewModel
) {
    val notifyBackOutApp = stringResource(id = resApp.string.notify_back_out_app)
    val context = LocalContext.current
    var backPressTime = 0L

    val currentSession by matrixViewModel.currentSession.collectAsState()

    BackHandler {
        //handle when user back to out app
        if (backPressTime + 1500 > System.currentTimeMillis()) {
            onBackPress()
        } else {
            Toast.makeText(context, notifyBackOutApp, Toast.LENGTH_LONG).show()
        }
        backPressTime = System.currentTimeMillis()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = GetDimension.dimensionOf(res = resDimension.dimen._10sdp),
                start = GetDimension.dimensionOf(res = resDimension.dimen._10sdp),
                end = GetDimension.dimensionOf(res = resDimension.dimen._10sdp),
            )
    ) {
        SearchApp(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(50))
                .border(
                    width = GetDimension.dimensionOf(res = resDimension.dimen._2sdp),
                    color = blue,
                    shape = RoundedCornerShape(50)
                )
                .fillMaxWidth()
                .background(gray)
                .padding(horizontal = GetDimension.dimensionOf(res = resDimension.dimen._15sdp)),
            onValueChange = {},
            onButtonClickSearch = { },
            onButtonClick = { },
            matrixViewModel = matrixViewModel
        )

        Spacer(modifier = Modifier.size(GetDimension.dimensionOf(res = resDimension.dimen._10sdp)))

        ContestMessages(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )
    }
}

@Composable
fun ContestMessages(
    modifier: Modifier
) {
    val listTestUI = mutableListOf(
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
    )

    Column(modifier = modifier) {
        Text(
            modifier = Modifier
                .padding(vertical = GetDimension.dimensionOf(res = resDimension.dimen._10sdp))
                .fillMaxWidth(),
            text = stringResource(id = resApp.string.chats),
            style = TextStyle(
                fontSize = GetDimension.dimensionOfText(res = resDimension.dimen._16sdp),
                fontWeight = W700,
                textAlign = TextAlign.Start,
                color = black
            )
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(listTestUI) {
                MessageItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            all = GetDimension.dimensionOf(res = resDimension.dimen._10sdp),
                        ),
                    photo = Uri.EMPTY
                )
            }
        }
    }
}

@Composable
fun MessageItem(
    modifier: Modifier,
    photo: Uri
) {
    val painter = rememberAsyncImagePainter(
        model = photo,
        contentScale = ContentScale.Crop,
        error = painterResource(id = resApp.drawable.load_fail),
    )

    val localDensity = LocalDensity.current
    var heightComponent by remember {
        mutableStateOf(0.dp)
    }

    ConstraintLayout(modifier = modifier) {
        val (icMore, inputSearch, componentPhoto) = createRefs()

        Image(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(50))
                .border(
                    width = GetDimension.dimensionOf(res = resDimension.dimen._1sdp),
                    color = blue,
                    shape = RoundedCornerShape(50)
                )
                .constrainAs(componentPhoto) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)

                    height = Dimension.value(heightComponent)
                    width = Dimension.ratio("1:1")
                }
                .background(gray.copy(0.5f)),
            painter = painter,
            contentDescription = null
        )

        Column(modifier = Modifier
            .constrainAs(inputSearch) {
                start.linkTo(componentPhoto.end)
                end.linkTo(icMore.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)

                width = Dimension.fillToConstraints
            }
            .onGloballyPositioned {
                heightComponent = with(localDensity) {
                    it.size.height.toDp()
                }
            }) {

            Text(
                modifier = Modifier
                    .padding(
                        top = GetDimension.dimensionOf(res = resDimension.dimen._7sdp),
                        start = GetDimension.dimensionOf(res = resDimension.dimen._10sdp),
                    )
                    .fillMaxWidth(),
                text = "Nguyễn Đức Long",
                style = TextStyle(
                    fontSize = GetDimension.dimensionOfText(res = resDimension.dimen._12sdp),
                    fontWeight = Bold,
                    textAlign = TextAlign.Start,
                    color = black
                )
            )

            Text(
                modifier = Modifier
                    .padding(
                        bottom = GetDimension.dimensionOf(res = resDimension.dimen._7sdp),
                        start = GetDimension.dimensionOf(res = resDimension.dimen._10sdp),
                    )
                    .fillMaxWidth(),
                text = "This is content message to test - 10:20",
                style = TextStyle(
                    fontSize = GetDimension.dimensionOfText(res = resDimension.dimen._8sdp),
                    fontWeight = Light,
                    textAlign = TextAlign.Start,
                    color = black
                )
            )

        }

        Icon(
            modifier = Modifier
                .constrainAs(icMore) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            painter = painterResource(id = resApp.drawable.ic_more_vertical),
            contentDescription = null
        )
    }
}
