package id.haweje.submission_jetpack.utils

import android.os.Handler
import android.os.Looper
import androidx.annotation.VisibleForTesting
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors @VisibleForTesting constructor(
    private val diskIO : Executor,
    private val mainThread: Executor
) {
    constructor() : this(DiskIOThreadExecutor(), MainThreadExecutor())

    fun diskIO(): Executor = diskIO

    fun mainThread(): Executor = mainThread

}

class MainThreadExecutor : Executor {
    private val threadHandler = Handler(Looper.getMainLooper())

    override fun execute(command: Runnable) {
        threadHandler.post(command)
    }

}

class DiskIOThreadExecutor :Executor {
    private val diskIO : Executor
    init {
        diskIO = Executors.newSingleThreadExecutor()
    }

    override fun execute(command: Runnable) {
        diskIO.execute(command)
    }

}
