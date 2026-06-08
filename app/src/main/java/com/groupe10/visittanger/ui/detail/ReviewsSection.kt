package com.groupe10.visittanger.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.groupe10.visittanger.R
import com.groupe10.visittanger.domain.model.Review
import com.groupe10.visittanger.ui.components.RatingBar
import com.groupe10.visittanger.ui.theme.TangerGreen

@Composable
fun ReviewsSection(
    reviews: List<Review>,
    showAll: Boolean,
    onToggleShowAll: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.detail_reviews),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.detail_reviews_count, reviews.size),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            val displayedReviews = if (showAll) reviews else reviews.take(2)
            displayedReviews.forEach { review ->
                ReviewCard(review = review)
            }
        }

        if (reviews.size > 2) {
            TextButton(
                onClick = onToggleShowAll,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (showAll) {
                        stringResource(R.string.show_less)
                    } else {
                        stringResource(R.string.detail_see_all_reviews)
                    },
                    color = TangerGreen,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ReviewCard(review: Review) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(TangerGreen.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = review.userName.take(1).uppercase(),
                color = TangerGreen,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = review.userName,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = review.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            RatingBar(
                rating = review.rating,
                reviewCount = 0,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Text(
                text = review.comment,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
