package pl.msiwak.todoapp.util.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import pl.msiwak.todoapp.ui.task.TaskViewModel
import pl.msiwak.todoapp.ui.taskList.TaskListViewModel
import pl.msiwak.todoapp.util.firebase.FirebaseDatabase
import pl.msiwak.todoapp.util.firebase.FirebaseDatabaseImpl
import pl.msiwak.todoapp.util.helpers.ResourceProvider
import pl.msiwak.todoapp.util.helpers.ResourceProviderImpl

val viewModelModule: Module = module {
    viewModel { TaskViewModel(get(), get()) }
    viewModel { TaskListViewModel(get()) }
}


val appModule: Module = module {
    single<FirebaseDatabase> { FirebaseDatabaseImpl() }
    single<ResourceProvider> { ResourceProviderImpl(androidContext()) }
}