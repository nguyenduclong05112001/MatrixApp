package com.longhrk.app.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.longhrk.app.core.GetDimension
import com.longhrk.app.ui.theme.black
import com.longhrk.app.ui.theme.gray
import com.longhrk.dimension.R

@Composable
fun OutLineTextFieldAuth(
    modifier: Modifier,
    icon: Int = 0,
    isIcon: Boolean = false,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardAction: KeyboardActions,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    visualTransformation: String = "none",
    iconClick: () -> Unit
) {
    var inputValues by remember {
        mutableStateOf("")
    }

    ConstraintLayout(modifier = modifier) {
        val (input, button) = createRefs()
        OutlinedTextField(
            modifier = Modifier
                .padding(
                    horizontal = GetDimension.dimensionOf(res = R.dimen._7sdp),
                )
                .constrainAs(input) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)

                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                },
            value = inputValues,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                textColor = black,
                cursorColor = black,
                placeholderColor = gray
            ),
            placeholder = { Text(text = placeholder) },
            textStyle = TextStyle(
                fontSize = GetDimension.dimensionOfText(res = R.dimen._14sdp),
                fontWeight = FontWeight.Light,
            ),
            singleLine = true,
            maxLines = 1,
            onValueChange = {
                inputValues = it
                onValueChange(it)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = keyboardAction,
            visualTransformation = if (visualTransformation == "none") VisualTransformation.None else PasswordVisualTransformation()
        )

        if (isIcon && inputValues.isNotEmpty()) {
            IconButton(
                modifier = Modifier
                    .constrainAs(button) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                        start.linkTo(input.end)

                        height = Dimension.wrapContent
                        width = Dimension.wrapContent
                    },
                onClick = iconClick
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = black
                )
            }
        }
    }
}