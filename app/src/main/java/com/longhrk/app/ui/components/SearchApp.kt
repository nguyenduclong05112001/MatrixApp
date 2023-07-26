package com.longhrk.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import com.longhrk.app.core.GetDimension
import com.longhrk.app.ui.theme.black
import com.longhrk.app.ui.theme.blue
import com.longhrk.app.ui.theme.gray
import com.longhrk.app.ui.theme.grayLight
import com.longhrk.matrix.viewmodel.MatrixViewModel
import com.longhrk.app.R as resApp
import com.longhrk.dimension.R as resDimension

@Composable
fun SearchApp(
    modifier: Modifier,
    onValueChange: (String) -> Unit,
    onButtonClickSearch: () -> Unit,
    onButtonClick: () -> Unit,
    matrixViewModel: MatrixViewModel
) {
    val context = LocalContext.current
    val currentSession by matrixViewModel.currentSession.collectAsState()
    val photo by matrixViewModel.uriPhoto.collectAsState()

    LaunchedEffect(Unit) {
        matrixViewModel.getUriPhoto(currentSession)
    }

    val painter = rememberAsyncImagePainter(
        model = photo,
        contentScale = ContentScale.Crop,
        error = painterResource(id = resApp.drawable.load_fail),
    )

    var searchValues by remember {
        mutableStateOf("")
    }

    val localDensity = LocalDensity.current
    var heightcomponent by remember {
        mutableStateOf(0.dp)
    }

    ConstraintLayout(modifier = modifier) {
        val (icMenu, inputSearch, componentPhoto) = createRefs()

        Icon(
            modifier = Modifier
                .constrainAs(icMenu) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .onGloballyPositioned {
                    heightcomponent = with(localDensity) {
                        it.size.height.toDp()
                    }
                },
            painter = painterResource(id = resApp.drawable.ic_menu),
            tint = black,
            contentDescription = null
        )

        Box(modifier = Modifier.constrainAs(inputSearch) {
            start.linkTo(icMenu.end)
            end.linkTo(componentPhoto.start)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)

            width = Dimension.fillToConstraints
        }) {
            OutlinedTextField(
                modifier = Modifier
                    .padding(
                        horizontal = GetDimension.dimensionOf(res = resDimension.dimen._7sdp),
                    )
                    .fillMaxWidth(),
                value = searchValues,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    textColor = black,
                    cursorColor = black,
                    placeholderColor = grayLight
                ),
                placeholder = { Text(text = "Search...") },
                textStyle = TextStyle(
                    fontSize = GetDimension.dimensionOfText(res = resDimension.dimen._12sdp),
                    fontWeight = FontWeight.Light,
                ),
                singleLine = true,
                maxLines = 1,
                onValueChange = {
                    searchValues = it
                    onValueChange(it)
                },
            )
        }

        Image(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(50))
                .border(
                    width = GetDimension.dimensionOf(res = resDimension.dimen._1sdp),
                    color = blue,
                    shape = RoundedCornerShape(50)
                )
                .constrainAs(componentPhoto) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)

                    height = Dimension.value(heightcomponent)
                    width = Dimension.ratio("1:1")
                },
            painter = painter,
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}