package luca.app.mcjohn

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import luca.app.mcjohn.repository.EventRepository
import luca.app.mcjohn.viewModel.EventViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {
    //ViewModel
    viewModel { EventViewModel(get())}

    //Repository
    single { EventRepository(get()) }

    //Retrofit
    single {
        createRetrofit()
    }
}

fun createRetrofit(): Retrofit {
    return Retrofit.Builder()
            .baseUrl("https://eventi-mcjohn.azurewebsites.net/")
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
}