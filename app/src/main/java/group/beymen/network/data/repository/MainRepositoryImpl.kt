package group.beymen.network.data.repository

import group.beymen.network.data.source.local.MainRoomDB
import group.beymen.network.data.source.remote.MainService
import group.beymen.network.domain.repository.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val mainService: MainService,
    private val mainRoomDB: MainRoomDB,
) : MainRepository