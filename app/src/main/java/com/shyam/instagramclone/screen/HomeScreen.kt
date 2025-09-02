package com.shyam.instagramclone.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.shyam.instagramclone.R
import com.shyam.instagramclone.model.Post
import com.shyam.instagramclone.model.Stories
import com.shyam.instagramclone.model.User

@Composable
fun HomeScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        AppToolBar()
        StoriesSection(stotryList = getStories())
        Divider(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
                .height(2.dp)
        )
        PostSection(
            modifier = Modifier.fillMaxWidth(),
            postList = getPostData()
        )
    }
}

@Composable
fun PostSection(
    modifier: Modifier = Modifier,
    postList: List<Post>
) {
    LazyColumn(modifier = modifier) {
        items(
            items = postList,
            key = { it.id }
        ) { post ->
            PostItem(
                post = post,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun PostItem(
    modifier: Modifier = Modifier,
    post: Post
) {
    val pagerState = rememberPagerState(pageCount = { post.postImageList.size })
    Column(modifier = modifier.fillMaxWidth()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = post.profile),
                contentDescription = "profile picture",
                modifier = Modifier
                    .size(35.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = post.userName,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )

            Icon(
                painter = painterResource(id = R.drawable.threedot),
                contentDescription = "More options",
                modifier = Modifier
                    .size(22.dp)
                    .padding(end = 10.dp)
            )
        }

        PostCarousel(post.postImageList, pagerState)
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.align(CenterStart)) {
                Icon(
                    painter = painterResource(id = R.drawable.notification),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(15.dp))
                Icon(
                    painter = painterResource(id = R.drawable.message),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(15.dp))
                Icon(
                    painter = painterResource(id = R.drawable.share),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }

//            HorizontalPagerIndicator(
//                pagerState = pagerState,
//                activeColor = Color("#385FF".toColorInt()),
//                inactiveColor = Color("#C4C4C4".toColorInt()),
//                modifier = Modifier.align(Center)
//            )
            Icon(
                painter = painterResource(id = R.drawable.save),
                contentDescription = null,
                modifier = Modifier
                    .size(28.dp)
                    .align(Alignment.CenterEnd)
                    .padding(top = 5.dp)

            )

        }

        LikeSection(post.likedBy)
        val annotatedString = buildAnnotatedString {
            withStyle(style = SpanStyle(Color.Black, fontWeight = FontWeight.Bold)){
                append("${post.userName} ")
            }
            append("post.description")
        }
        Text(
            text = "",
            fontSize = 12.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            modifier = Modifier.padding(horizontal = 10.dp)
        )

    }
}

@Composable
fun LikeSection(likedBy: List<User>) {
    if (likedBy.size > 10) {
        Text(
            text = "${likedBy.size} likes",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            modifier = Modifier.padding(horizontal = 5.dp)
        )
    } else if (likedBy.size == 1) {
        Text(
            text = "liked by ${likedBy[0].userName} ",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            modifier = Modifier.padding(horizontal = 5.dp)
        )
    } else {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(5.dp)) {
            PostLikeViewByProfile(likedBy)
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = "liked by ${likedBy[0].userName} and ${likedBy.size - 1} others",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )

        }

    }
}

@Composable
fun PostLikeViewByProfile(likedBy: List<User>) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy((-5).dp)) {
        items(likedBy.take(4)) { likedBy ->
            Image(
                painter = painterResource(id = likedBy.profileName),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .border(
                        width = 2.dp,
                        color = Color.White,
                        shape = CircleShape
                    )
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun PostCarousel(postImageList: List<Int>, pagerState: PagerState) {
    Box(modifier = Modifier.fillMaxWidth()) {
        NudgeCount(
            modifier = Modifier.align(Alignment.CenterEnd),
            pagerState = pagerState
        )
    }


    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) { page ->
        Image(
            painter = painterResource(id = postImageList[page]),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun StoriesSection(modifier: Modifier = Modifier, stotryList: List<Stories>) {
    LazyRow {
        items(stotryList) { story ->
            StoryItem(modifier = Modifier, story = story)
        }
    }
}

@Composable
fun StoryItem(modifier: Modifier, story: Stories) {
    Column(modifier = Modifier.padding(5.dp)) {
        Image(
            painter = painterResource(id = story.profile),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .border(
                    width = 2.dp, brush = Brush.linearGradient(
                        listOf(
                            Color("#DE0046".toColorInt()),
                            Color("#F7A348".toColorInt())
                        )
                    ),
                    shape = CircleShape
                )
                .padding(5.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = story.userName,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(60.dp),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Composable
fun AppToolBar() {
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.instagram_letter),
            contentDescription = null,
            modifier = Modifier
                .width(150.dp)
                .height(50.dp)
                .align(Alignment.TopStart)
        )
        Row(modifier = Modifier.align(Alignment.CenterEnd)) {
            Icon(
                painter = painterResource(id = R.drawable.add),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Icon(
                painter = painterResource(id = R.drawable.notification),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Icon(
                painter = painterResource(id = R.drawable.message),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun NudgeCount(
    modifier: Modifier = Modifier,
    pagerState: PagerState
) {
    Row(
        modifier = modifier
            .clip(CircleShape)
            .background(Color.Black.copy(alpha = 0.4f))
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(text = "${pagerState.currentPage + 1}", color = Color.White)
        Text(text = "/", color = Color.White)
        Text(text = "${pagerState.pageCount}", color = Color.White)
    }
}

fun getStories(): List<Stories> = listOf(
    Stories(userName = "rohit sharma", profile = R.drawable.rohit),
    Stories(userName = "jasprit ", profile = R.drawable.jp),
    Stories(userName = "hardik", profile = R.drawable.hardik),
    Stories(userName = "dhoni", profile = R.drawable.dhoni),
    Stories(userName = "rishab pant", profile = R.drawable.rishab),
    Stories(userName = "bhumrah", profile = R.drawable.bhumrh),
    Stories(userName = "rina", profile = R.drawable.rina),
    Stories(userName = "natasha", profile = R.drawable.natasha2),
    Stories(userName = "loki from asgard", profile = R.drawable.loki),
    Stories(userName = "pyal shrivastava", profile = R.drawable.pyal),
    Stories(userName = "tony stark", profile = R.drawable.tony),
    Stories(userName = "reetu pandey", profile = R.drawable.reetu),
    Stories(userName = "thor ", profile = R.drawable.thor),
    Stories(userName = "riya passwan", profile = R.drawable.natsha),
)

fun getPostData(): List<Post> = listOf(
    Post(
        id = 1,
        profileName = R.drawable.rohit,
        userName = "rohit",
        postImageList = listOf(R.drawable.rohit),
        description = "lets down the heaters",
        likedBy = listOf(
            User(profileName = R.drawable.rohit, userName = "rohit sharma"),
            User(profileName = R.drawable.jp, userName = "jasprit "),
            User(profileName = R.drawable.hardik, userName = "hardik"),
            User(profileName = R.drawable.dhoni, userName = "dhoni"),
            User(profileName = R.drawable.rishab, userName = "rishab pant"),
            User(profileName = R.drawable.bhumrh, userName = "bhumrah")
        ),
        profile = R.drawable.rohit
    ),
    Post(
        id = 2,
        profileName = R.drawable.pyal,
        userName = "pyal",
        postImageList = listOf(R.drawable.pyal),
        description = "lets down the heaters",
        likedBy = listOf(
            User(profileName = R.drawable.rohit, userName = "rohit sharma"),
            User(profileName = R.drawable.jp, userName = "jasprit "),
            User(profileName = R.drawable.hardik, userName = "hardik"),
            User(profileName = R.drawable.dhoni, userName = "dhoni"),
            User(profileName = R.drawable.rishab, userName = "rishab pant"),
            User(profileName = R.drawable.bhumrh, userName = "bhumrah"),
            User(profileName = R.drawable.rina, userName = "rina"),
            User(profileName = R.drawable.natsha, userName = "natasha"),
            User(profileName = R.drawable.loki, userName = "loki"),
            User(profileName = R.drawable.thor, userName = "thor"),
        ),
        profile = R.drawable.pyal
    ),
    Post(
        id = 3,
        profileName = R.drawable.rina,
        userName = "rina",
        postImageList = listOf(R.drawable.rina),
        description = "lets down the heaters",
        likedBy = listOf(
            User(profileName = R.drawable.rohit, userName = "rohit sharma"),
            User(profileName = R.drawable.jp, userName = "jasprit "),
            User(profileName = R.drawable.hardik, userName = "hardik"),
            User(profileName = R.drawable.dhoni, userName = "dhoni"),
            User(profileName = R.drawable.rishab, userName = "rishab pant"),
            User(profileName = R.drawable.bhumrh, userName = "bhumrah"),
            User(profileName = R.drawable.rina, userName = "rina"),
            User(profileName = R.drawable.natsha, userName = "natasha"),
            User(profileName = R.drawable.loki, userName = "loki"),
            User(profileName = R.drawable.thor, userName = "thor"),
        ),
        profile = R.drawable.rina
    ),
    Post(
        id = 4,
        profileName = R.drawable.hardik,
        userName = "hardik",
        postImageList = listOf(R.drawable.hardik),
        description = "lets down the heaters",
        likedBy = listOf(
            User(profileName = R.drawable.rohit, userName = "rohit sharma"),
            User(profileName = R.drawable.jp, userName = "jasprit "),
            User(profileName = R.drawable.hardik, userName = "hardik"),
            User(profileName = R.drawable.dhoni, userName = "dhoni"),
            User(profileName = R.drawable.rishab, userName = "rishab pant"),
            User(profileName = R.drawable.bhumrh, userName = "bhumrah"),
            User(profileName = R.drawable.rina, userName = "rina"),
            User(profileName = R.drawable.natsha, userName = "natasha"),
            User(profileName = R.drawable.loki, userName = "loki"),
            User(profileName = R.drawable.thor, userName = "thor"),
        ),
        profile = R.drawable.hardik
    ),
    Post(
        id = 5,
        profileName = R.drawable.dhoni,
        userName = "dhoni",
        postImageList = listOf(R.drawable.dhoni),
        description = "lets down the heaters",
        likedBy = listOf(
            User(profileName = R.drawable.rohit, userName = "rohit sharma"),
            User(profileName = R.drawable.jp, userName = "jasprit "),
            User(profileName = R.drawable.hardik, userName = "hardik"),
            User(profileName = R.drawable.dhoni, userName = "dhoni"),
            User(profileName = R.drawable.rishab, userName = "rishab pant"),
            User(profileName = R.drawable.bhumrh, userName = "bhumrah"),
            User(profileName = R.drawable.rina, userName = "rina"),
            User(profileName = R.drawable.natsha, userName = "natasha"),
            User(profileName = R.drawable.loki, userName = "loki"),
            User(profileName = R.drawable.thor, userName = "thor"),
        ),
        profile = R.drawable.dhoni
    ),
    Post(
        id = 6,
        profileName = R.drawable.rishab,
        userName = "rishab",
        postImageList = listOf(R.drawable.rishab),
        description = "lets down the heaters",
        likedBy = listOf(
            User(profileName = R.drawable.rohit, userName = "rohit sharma"),
            User(profileName = R.drawable.jp, userName = "jasprit "),
            User(profileName = R.drawable.hardik, userName = "hardik"),
            User(profileName = R.drawable.dhoni, userName = "dhoni"),
            User(profileName = R.drawable.rishab, userName = "rishab pant"),
            User(profileName = R.drawable.bhumrh, userName = "bhumrah"),
            User(profileName = R.drawable.rina, userName = "rina"),
            User(profileName = R.drawable.natsha, userName = "natasha"),
            User(profileName = R.drawable.loki, userName = "loki"),
            User(profileName = R.drawable.thor, userName = "thor"),
        ),
        profile = R.drawable.rishab
    ),
    Post(
        id = 7,
        profileName = R.drawable.bhumrh,
        userName = "bhumrah",
        postImageList = listOf(R.drawable.bhumrh),
        description = "lets down the heaters",
        likedBy = listOf(
            User(profileName = R.drawable.rohit, userName = "rohit sharma"),
            User(profileName = R.drawable.jp, userName = "jasprit "),
            User(profileName = R.drawable.hardik, userName = "hardik"),
            User(profileName = R.drawable.dhoni, userName = "dhoni"),
            User(profileName = R.drawable.rishab, userName = "rishab pant"),
            User(profileName = R.drawable.bhumrh, userName = "bhumrah"),
            User(profileName = R.drawable.rina, userName = "rina"),
            User(profileName = R.drawable.natsha, userName = "natasha"),
            User(profileName = R.drawable.loki, userName = "loki"),
            User(profileName = R.drawable.thor, userName = "thor"),
        ),
        profile = R.drawable.bhumrh
    ),
    Post(
        id = 8,
        profileName = R.drawable.bhumrh,
        userName = "bhumrah",
        postImageList = listOf(
            R.drawable.bhumrh, R.drawable.rishab,
            R.drawable.hardik,
            R.drawable.rohit,
        ),
        description = "lets down the heaters",
        likedBy = listOf(
            User(profileName = R.drawable.rohit, userName = "rohit sharma"),
            User(profileName = R.drawable.jp, userName = "jasprit "),
            User(profileName = R.drawable.hardik, userName = "hardik"),
            User(profileName = R.drawable.dhoni, userName = "dhoni"),
            User(profileName = R.drawable.rishab, userName = "rishab pant"),
            User(profileName = R.drawable.bhumrh, userName = "bhumrah"),
            User(profileName = R.drawable.rina, userName = "rina"),
            User(profileName = R.drawable.natsha, userName = "natasha"),
            User(profileName = R.drawable.loki, userName = "loki"),
            User(profileName = R.drawable.thor, userName = "thor"),
        ),
        profile = R.drawable.bhumrh
    ),
)

@Preview(showBackground = true)
@Composable
fun InstaPreview() {
    StoryItem(
        Modifier,
        Stories("Rohit", R.drawable.rohit)
    )
}
