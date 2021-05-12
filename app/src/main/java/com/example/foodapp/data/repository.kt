package com.example.foodapp.data

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    recipesLocalDatasource: recipes_Local_DataSource
) {
    var remote=remoteDataSource
    var local=recipesLocalDatasource

}