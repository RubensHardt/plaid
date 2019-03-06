package io.plaidapp.dribbble.domain

import io.plaidapp.core.data.CoroutinesDispatcherProvider
import io.plaidapp.core.dribbble.data.api.model.Shot
import io.plaidapp.core.util.HtmlParser
import io.plaidapp.dribbble.ui.shot.ShotDecorator
import io.plaidapp.dribbble.ui.shot.ShotUiModel
import kotlinx.coroutines.withContext
import java.text.NumberFormat
import javax.inject.Inject

class CreateShotUiModelUseCase @Inject constructor(
    private val htmlParser: HtmlParser,
    private val decorator: ShotDecorator,
    private val dispatcherProvider: CoroutinesDispatcherProvider
) {

    suspend operator fun invoke(model: Shot): ShotUiModel = withContext(dispatcherProvider.computation) {

        val desc = htmlParser.parse(model.description, decorator.linkColors, decorator.highlightColor)
        val numberFormatter = NumberFormat.getInstance(decorator.locale)

        return@withContext ShotUiModel(
            id = model.id,
            title = model.title,
            url = model.htmlUrl,
            formattedDescription = desc,
            imageUrl = model.images.best(),
            imageSize = model.images.bestSize(),
            likesCount = model.likesCount,
            formattedLikesCount = numberFormatter.format(model.likesCount),
            viewsCount = model.viewsCount,
            formattedViewsCount = numberFormatter.format(model.viewsCount),
            createdAt = model.createdAt,
            userName = model.user.name.toLowerCase(),
            userAvatarUrl = model.user.highQualityAvatarUrl
        )
    }
}