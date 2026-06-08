package com.groupe10.visittanger.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.groupe10.visittanger.domain.model.Review
import com.groupe10.visittanger.ui.components.RatingBar
import com.groupe10.visittanger.ui.theme.*

@Composable
fun ReviewsSection(
    reviews: List<Review>,
    showAll: Boolean,
    onToggleShowAll: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Visitor Reviews",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = StitchPrimary
                )
            )
            Spacer(modifier = Modifier.width(12.dp))
            Surface(
                color = StitchSurfaceContainerHigh,
                shape = CircleShape
            ) {
                Text(
                    text = "${reviews.size}",
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp),
                    style = MaterialTheme.typography.labelSmall,
                    color = StitchPrimary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            val displayedReviews = if (showAll) reviews else reviews.take(2)
            displayedReviews.forEach { review ->
                ReviewCard(review = review)
            }
        }
        
        if (reviews.size > 2) {
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(
                onClick = onToggleShowAll,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.textButtonColors(contentColor = StitchSecondary)
            ) {
                Text(
                    text = if (showAll) "SHOW LESS" else "READ ALL ${reviews.size} REVIEWS",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ReviewCard(review: Review) {
    Surface(
        color = StitchSurfaceContainerLow.copy(alpha = 0.5f),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            // Avatar circle with initials
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(StitchPrimary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = review.userName.take(1).uppercase(),
                    color = StitchPrimary,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = review.userName,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = StitchOnSurface
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = review.date,
                        style = MaterialTheme.typography.labelSmall,
                        color = StitchOutline
                    )
                }
                
                RatingBar(
                    rating = review.rating,
                    reviewCount = 0,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = review.comment,
                    style = MaterialTheme.typography.bodyMedium,
                    color = StitchOnSurfaceVariant,
                    lineHeight = 20.sp,
                    maxLines = if (review.comment.length > 100) 4 else 10,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
