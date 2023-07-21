package com.longhrk.app.ui.screen.login

import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.bumptech.glide.Glide
import com.longhrk.app.core.GetDimension
import com.longhrk.app.ui.components.CheckBoxBase
import com.longhrk.app.ui.components.LoadingView
import com.longhrk.app.ui.components.OutLineTextFieldAuth
import com.longhrk.app.ui.theme.*
import com.longhrk.matrix.viewmodel.MatrixViewModel
import kotlinx.coroutines.launch
import com.longhrk.app.R as resApp
import com.longhrk.dimension.R as resDimension

@Composable
fun AuthScreen(
    matrixViewModel: MatrixViewModel,
    onHomeScreen: () -> Unit
) {
    val currentSession by matrixViewModel.currentSession.collectAsState()

    LaunchedEffect(Unit){
        if (currentSession != null){
            onHomeScreen()
        }
    }

    val isLoading by matrixViewModel.isShowProgressBar.collectAsState()
    val titleCurrent by matrixViewModel.titleCurrent.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { contextImage ->
                ImageView(contextImage).apply {
                    Glide.with(contextImage)
                        .load(resApp.drawable.bg_for_login)
                        .centerCrop()
                        .into(this)
                }
            })

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = GetDimension.dimensionOf(
                        res = resDimension.dimen._10sdp
                    )
                )
        ) {
            val (title, options, inputs) = createRefs()

            Text(
                modifier = Modifier
                    .constrainAs(title) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)

                        height = Dimension.wrapContent
                        width = Dimension.wrapContent
                    }
                    .padding(
                        top = GetDimension.dimensionOf(res = resDimension.dimen._70sdp),
                        start = GetDimension.dimensionOf(res = resDimension.dimen._20sdp),
                    ),
                text = titleCurrent,
                style = TextStyle(
                    fontSize = GetDimension.dimensionOfText(res = resDimension.dimen._32sdp),
                    fontWeight = FontWeight.Bold,
                    color = blue,
                    textAlign = TextAlign.Start
                )
            )

            OptionAuth(
                modifier = Modifier
                    .padding(
                        top = GetDimension.dimensionOf(res = resDimension.dimen._15sdp)
                    )
                    .constrainAs(options) {
                        top.linkTo(title.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)

                        height = Dimension.wrapContent
                        width = Dimension.wrapContent
                    },
                matrixViewModel = matrixViewModel
            )

            ContentAuth(
                modifier = Modifier
                    .padding(
                        top = GetDimension.dimensionOf(res = resDimension.dimen._15sdp)
                    )
                    .constrainAs(inputs) {
                        top.linkTo(options.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)

                        height = Dimension.fillToConstraints
                        width = Dimension.matchParent
                    },
                matrixViewModel = matrixViewModel,
                onClickGoogle = {},
                onClickFacebook = {}
            )
        }

        if (isLoading) {
            LoadingView()
        }
    }
}

@Composable
fun OptionAuth(
    modifier: Modifier,
    matrixViewModel: MatrixViewModel,
) {
    val currentTab by matrixViewModel.titleCurrent.collectAsState()

    val listAuth = mutableListOf(
        stringResource(id = resApp.string.login),
        stringResource(id = resApp.string.register)
    )

    Row(modifier = modifier) {
        listAuth.forEach {
            Box(modifier = Modifier
                .padding(horizontal = GetDimension.dimensionOf(res = resDimension.dimen._10sdp))
                .clickable {
                    matrixViewModel.setTitleCurrent(it)
                }
            ) {
                Text(
                    modifier = Modifier
                        .padding(
                            all = GetDimension.dimensionOf(res = resDimension.dimen._10sdp),
                        )
                        .align(Alignment.Center),
                    text = it,
                    style = TextStyle(
                        fontSize = GetDimension.dimensionOfText(res = resDimension.dimen._14sdp),
                        fontWeight = if (currentTab == it) FontWeight.Bold else FontWeight.Light,
                        color = if (currentTab == it) blue else gray,
                        textAlign = TextAlign.Center
                    )
                )

                if (currentTab == it) {
                    Divider(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .height(GetDimension.dimensionOf(res = resDimension.dimen._1sdp))
                            .width(GetDimension.dimensionOf(res = resDimension.dimen._28sdp)),
                        color = blue
                    )
                }
            }
        }
    }
}

@Composable
fun ContentAuth(
    modifier: Modifier,
    matrixViewModel: MatrixViewModel,
    onClickGoogle: () -> Unit,
    onClickFacebook: () -> Unit
) {
    val currentTab by matrixViewModel.titleCurrent.collectAsState()

    when (currentTab) {
        "Login" -> {
            LoginContent(
                modifier = modifier,
                matrixViewModel = matrixViewModel,
                onClickGoogle = onClickGoogle,
                onClickFacebook = onClickFacebook
            )
        }
        "Register" -> {
            RegisterContent(
                modifier = modifier,
                matrixViewModel = matrixViewModel,
                onClickGoogle = onClickGoogle,
                onClickFacebook = onClickFacebook
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterContent(
    modifier: Modifier,
    matrixViewModel: MatrixViewModel,
    onClickGoogle: () -> Unit,
    onClickFacebook: () -> Unit
) {
    val scope = rememberCoroutineScope()

    val keyBoardController = LocalSoftwareKeyboardController.current
    val localFocus = LocalFocusManager.current

    var visualTransformationPassword by remember {
        mutableStateOf("password")
    }

    var visualTransformationConfirm by remember {
        mutableStateOf("password")
    }

    var emailValue by remember {
        mutableStateOf("")
    }

    var passwordValue by remember {
        mutableStateOf("")
    }

    var confirmValue by remember {
        mutableStateOf("")
    }

    Column(modifier = modifier) {
        OutLineTextFieldAuth(
            modifier = Modifier
                .padding(vertical = GetDimension.dimensionOf(res = resDimension.dimen._10sdp))
                .fillMaxWidth()
                .background(white)
                .border(
                    width = GetDimension.dimensionOf(res = resDimension.dimen._1sdp),
                    color = black,
                    shape = RoundedCornerShape(20)
                ),
            onValueChange = {
                emailValue = it
            },
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
            keyboardAction = KeyboardActions(
                onNext = { localFocus.moveFocus(FocusDirection.Next) }
            ),
            placeholder = "Email"
        ) {}

        OutLineTextFieldAuth(
            modifier = Modifier
                .padding(vertical = GetDimension.dimensionOf(res = resDimension.dimen._10sdp))
                .fillMaxWidth()
                .background(white)
                .border(
                    width = GetDimension.dimensionOf(res = resDimension.dimen._1sdp),
                    color = black,
                    shape = RoundedCornerShape(20)
                ),
            icon = resApp.drawable.ic_eye,
            isIcon = true,
            onValueChange = {
                passwordValue = it
            },
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next,
            keyboardAction = KeyboardActions(
                onNext = { localFocus.moveFocus(FocusDirection.Next) }
            ),
            visualTransformation = visualTransformationPassword,
            placeholder = "Password"
        ) {
            visualTransformationPassword = if (visualTransformationPassword == "password") {
                "none"
            } else {
                "password"
            }
        }

        OutLineTextFieldAuth(
            modifier = Modifier
                .padding(vertical = GetDimension.dimensionOf(res = resDimension.dimen._10sdp))
                .fillMaxWidth()
                .background(white)
                .border(
                    width = GetDimension.dimensionOf(res = resDimension.dimen._1sdp),
                    color = black,
                    shape = RoundedCornerShape(20)
                ),
            icon = resApp.drawable.ic_eye,
            isIcon = true,
            onValueChange = {
                confirmValue = it
            },
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            keyboardAction = KeyboardActions(
                onDone = { keyBoardController?.hide() }
            ),
            visualTransformation = visualTransformationConfirm,
            placeholder = "Confirm"
        ) {
            visualTransformationConfirm = if (visualTransformationConfirm == "password") {
                "none"
            } else {
                "password"
            }
        }

        Button(
            modifier = Modifier
                .padding(
                    horizontal = GetDimension.dimensionOf(res = resDimension.dimen._20sdp),
                    vertical = GetDimension.dimensionOf(res = resDimension.dimen._15sdp)
                )
                .clip(shape = RoundedCornerShape(20))
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = blue,
                contentColor = white
            ),
            onClick = {
                scope.launch {

                }
            })
        {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = GetDimension.dimensionOf(res = resDimension.dimen._5sdp),
                    ),
                text = "Register",
                style = TextStyle(
                    fontSize = GetDimension.dimensionOfText(res = resDimension.dimen._16sdp),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )
        }

        ComponentMoreOption(
            modifier = Modifier.padding(vertical = GetDimension.dimensionOf(res = resDimension.dimen._10sdp)),
            onClickGoogle = onClickGoogle,
            onClickFacebook = onClickFacebook,
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginContent(
    modifier: Modifier,
    matrixViewModel: MatrixViewModel,
    onClickGoogle: () -> Unit,
    onClickFacebook: () -> Unit,
) {
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    val keyBoardController = LocalSoftwareKeyboardController.current
    val localFocus = LocalFocusManager.current

    var visualTransformation by remember {
        mutableStateOf("password")
    }

    var checkBoxStatus by remember {
        mutableStateOf(false)
    }

    var emailValue by remember {
        mutableStateOf("")
    }

    var passwordValue by remember {
        mutableStateOf("")
    }

    Column(modifier = modifier) {
        OutLineTextFieldAuth(
            modifier = Modifier
                .padding(vertical = GetDimension.dimensionOf(res = resDimension.dimen._10sdp))
                .fillMaxWidth()
                .background(white)
                .border(
                    width = GetDimension.dimensionOf(res = resDimension.dimen._1sdp),
                    color = black,
                    shape = RoundedCornerShape(20)
                ),
            onValueChange = {
                emailValue = it
            },
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
            keyboardAction = KeyboardActions(
                onNext = { localFocus.moveFocus(FocusDirection.Next) }
            ),
            placeholder = "Email"
        ) {}

        OutLineTextFieldAuth(
            modifier = Modifier
                .padding(vertical = GetDimension.dimensionOf(res = resDimension.dimen._10sdp))
                .fillMaxWidth()
                .background(white)
                .border(
                    width = GetDimension.dimensionOf(res = resDimension.dimen._1sdp),
                    color = black,
                    shape = RoundedCornerShape(20)
                ),
            icon = resApp.drawable.ic_eye,
            isIcon = true,
            onValueChange = {
                passwordValue = it
            },
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            keyboardAction = KeyboardActions(
                onDone = { keyBoardController?.hide() }
            ),
            visualTransformation = visualTransformation,
            placeholder = "Password"
        ) {
            visualTransformation = if (visualTransformation == "password") {
                "none"
            } else {
                "password"
            }
        }

        ConstraintLayout(
            modifier = Modifier
                .padding(vertical = GetDimension.dimensionOf(res = resDimension.dimen._15sdp))
                .fillMaxWidth()
        ) {
            val (checkBox, contentCheckBox, forgetPassword) = createRefs()

            CheckBoxBase(
                modifier = Modifier
                    .constrainAs(checkBox) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        top.linkTo(parent.top)

                        height = Dimension.wrapContent
                        width = Dimension.wrapContent
                    },
                values = checkBoxStatus,
                onCheckedChange = {
                    checkBoxStatus = it
                }
            )

            Text(
                modifier = Modifier
                    .constrainAs(contentCheckBox) {
                        top.linkTo(parent.top)
                        start.linkTo(checkBox.end)
                        bottom.linkTo(parent.bottom)

                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                    }
                    .padding(
                        horizontal = GetDimension.dimensionOf(res = resDimension.dimen._5sdp),
                    ),
                text = "Remember password",
                style = TextStyle(
                    fontSize = GetDimension.dimensionOfText(res = resDimension.dimen._12sdp),
                    fontWeight = FontWeight.Light,
                    color = black,
                    textAlign = TextAlign.Start
                )
            )

            Text(
                modifier = Modifier
                    .constrainAs(forgetPassword) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)

                        width = Dimension.wrapContent
                        height = Dimension.wrapContent
                    },
                text = "Forget password",
                style = TextStyle(
                    fontSize = GetDimension.dimensionOfText(res = resDimension.dimen._12sdp),
                    fontWeight = FontWeight.Bold,
                    color = black,
                    textAlign = TextAlign.Center
                )
            )
        }

        Button(
            modifier = Modifier
                .padding(
                    horizontal = GetDimension.dimensionOf(res = resDimension.dimen._20sdp),
                    vertical = GetDimension.dimensionOf(res = resDimension.dimen._15sdp)
                )
                .clip(shape = RoundedCornerShape(20))
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = blue,
                contentColor = white
            ),
            onClick = {
                scope.launch {
                    matrixViewModel.loginInApp(
                        username = emailValue.trim(),
                        password = passwordValue.trim(),
                        homeServer = "https://matrix-client.matrix.org/",
                        context = context
                    )
                }
            })
        {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = GetDimension.dimensionOf(res = resDimension.dimen._5sdp),
                    ),
                text = "Login",
                style = TextStyle(
                    fontSize = GetDimension.dimensionOfText(res = resDimension.dimen._16sdp),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )
        }

        ComponentMoreOption(
            modifier = Modifier.padding(vertical = GetDimension.dimensionOf(res = resDimension.dimen._10sdp)),
            onClickGoogle = onClickGoogle,
            onClickFacebook = onClickFacebook,
        )
    }
}

@Composable
fun ComponentMoreOption(
    modifier: Modifier,
    onClickFacebook: () -> Unit,
    onClickGoogle: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = GetDimension.dimensionOf(res = resDimension.dimen._10sdp),
                ),
            text = "OR CONNECT WITH",
            style = TextStyle(
                fontSize = GetDimension.dimensionOfText(res = resDimension.dimen._14sdp),
                fontWeight = FontWeight.Normal,
                color = black,
                textAlign = TextAlign.Center
            )
        )

        Row(
            modifier = Modifier
                .padding(
                    vertical = GetDimension.dimensionOf(res = resDimension.dimen._10sdp)
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ItemMoreOption(
                modifier = Modifier
                    .clickable {
                        onClickFacebook()
                    }
                    .padding(horizontal = GetDimension.dimensionOf(res = resDimension.dimen._10sdp))
                    .clip(RoundedCornerShape(50))
                    .size(GetDimension.dimensionOf(res = resDimension.dimen._30sdp)),
                icon = resApp.drawable.ic_facebook
            )

            ItemMoreOption(
                modifier = Modifier
                    .clickable {
                        onClickGoogle()
                    }
                    .padding(horizontal = GetDimension.dimensionOf(res = resDimension.dimen._10sdp))
                    .clip(RoundedCornerShape(50))
                    .size(GetDimension.dimensionOf(res = resDimension.dimen._30sdp)),
                icon = resApp.drawable.ic_google
            )
        }
    }
}

@Composable
fun ItemMoreOption(
    modifier: Modifier,
    icon: Int,
) {
    Box(modifier = modifier) {
        AndroidView(
            factory = {
                ImageView(it).apply {
                    Glide.with(it)
                        .load(icon)
                        .centerCrop()
                        .into(this)
                }
            }
        )
    }
}