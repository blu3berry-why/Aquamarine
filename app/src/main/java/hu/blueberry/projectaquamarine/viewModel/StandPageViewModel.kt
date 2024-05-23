package hu.blueberry.projectaquamarine.viewModel

import dagger.hilt.android.lifecycle.HiltViewModel
import hu.blueberry.drive.PermissionHandlingViewModel
import hu.blueberry.persistentstorage.Database
import javax.inject.Inject


@HiltViewModel
class StandPageViewModel @Inject constructor(
    private val database: Database
) : PermissionHandlingViewModel()
{

}