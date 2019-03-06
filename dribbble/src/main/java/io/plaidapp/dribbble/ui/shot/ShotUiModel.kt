package io.plaidapp.dribbble.ui.shot

import io.plaidapp.core.dribbble.data.api.model.Images
import io.plaidapp.core.dribbble.data.api.model.Shot
import java.util.Date

/**
 * Shot model for the UI
 */
data class ShotUiModel(
    val id: Long,
    val title: String,
    val url: String,
    val formattedDescription: CharSequence,
    val imageUrl: String,
    val imageSize: Images.ImageSize,
    val likesCount: Int,
    val formattedLikesCount: String,
    val viewsCount: Int,
    val formattedViewsCount: String,
    val createdAt: Date?,
    val userName: String,
    val userAvatarUrl: String
) {
    val shouldHideDescription = formattedDescription.isEmpty()
}

/**
 * A sync conversion which skips long running work in order to publish a result asap. For a more
 * complete conversion see [io.plaidapp.dribbble.domain.CreateShotUiModelUseCase].
 */
fun Shot.toShotUiModel(): ShotUiModel {
    return ShotUiModel(
        id = id,
        title = title,
        url = htmlUrl,
        formattedDescription = "",
        imageUrl = images.best(),
        imageSize = images.bestSize(),
        likesCount = likesCount,
        formattedLikesCount = likesCount.toString(),
        viewsCount = viewsCount,
        formattedViewsCount = viewsCount.toString(),
        createdAt = createdAt,
        userName = user.name.toLowerCase(),
        userAvatarUrl = user.highQualityAvatarUrl
    )
}
