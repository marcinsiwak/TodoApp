package pl.msiwak.todoapp.util.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import pl.msiwak.todoapp.ui.task.TaskViewModel
import pl.msiwak.todoapp.ui.taskList.TaskListViewModel

val viewModelModule: Module = module {
    viewModel { TaskViewModel() }
    viewModel { TaskListViewModel() }
}

val useCaseModule: Module = module {

}