
import androidx.room.Room
import com.newsapp.annexnews.data.local.NewsDatabase
import com.newsapp.annexnews.data.local.NewsTypeConvertor
import com.newsapp.annexnews.data.manager.LocalUserManagerImpl
import com.newsapp.annexnews.data.remote.NewsApi
import com.newsapp.annexnews.domain.manager.LocalUserManager
import com.newsapp.annexnews.domain.usecases.app_entry.ReadAppEntry
import com.newsapp.annexnews.domain.usecases.app_entry.SaveAppEntry
import com.newsapp.annexnews.domain.usecases.news.DeleteArticle
import com.newsapp.annexnews.domain.usecases.news.GetNews
import com.newsapp.annexnews.domain.usecases.news.GetSavedArticle
import com.newsapp.annexnews.domain.usecases.news.GetSavedArticles
import com.newsapp.annexnews.domain.usecases.news.SearchNews
import com.newsapp.annexnews.domain.usecases.news.UpsertArticle
import com.newsapp.annexnews.presentation.bookmark.BookmarkViewModel
import com.newsapp.annexnews.presentation.details.DetailsViewModel
import com.newsapp.annexnews.presentation.home.HomeViewModel
import com.newsapp.annexnews.presentation.mainActivity.MainViewModel
import com.newsapp.annexnews.presentation.notification.NewsNotificationWorker
import com.newsapp.annexnews.presentation.onboarding.OnBoardingViewModel
import com.newsapp.annexnews.presentation.search.SearchViewModel
import com.newsapp.annexnews.util.Constants.BASE_URL
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsApi::class.java)
}

    single {
        Room.databaseBuilder(
            androidApplication(),
            NewsDatabase::class.java,
            "news_db"
        ).addTypeConverter(NewsTypeConvertor())
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        val database = get<NewsDatabase>()
        database.newsDao
    }

    single<LocalUserManager> {
        LocalUserManagerImpl(get())
    }

    factory { SaveAppEntry(get()) }
    factory { ReadAppEntry(get()) }
    factory { NewsNotificationWorker(androidContext(), get()) }

    factory { GetSavedArticles(get()) }
    factory { GetSavedArticle(get()) }
    factory { GetNews(get()) }
    factory{ SearchNews(get()) }
    factory { DeleteArticle(get()) }
    factory { UpsertArticle(get()) }



    viewModel { MainViewModel(get(), androidApplication()) }
    viewModel { OnBoardingViewModel(get()) }
    viewModel { BookmarkViewModel(get()) }
    viewModel { DetailsViewModel(get(), get(), get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}