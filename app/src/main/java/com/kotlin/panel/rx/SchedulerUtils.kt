package com.kotlin.panel.rx

import com.kotlin.panel.rx.scheduler.IoMainScheduler

object SchedulerUtils {
    fun <T> ioToMain(): IoMainScheduler<T> = IoMainScheduler()
}