package com.kotlin.panel.utils

import kotlinx.coroutines.*

/**
 * https://www.jianshu.com/p/6e6835573a9c
 */
object CoroutineUtil {
    /**
     * 第一种：runBlocking启动的协程任务会阻断当前线程，直到该协程执行结束。当协程执行结束之后，页面才会被显示出来。
     */
    fun coroutineRunBloacking() = runBlocking {
        LogTools.d("协程执行开始")
        repeat(8) {
            LogTools.d("协程执行$it")
            delay(1000)
        }
        LogTools.d("协程执行结束")
    }

    /**
     * 第二种：launch启动的协程任务不会阻断当前线程
     */
    fun coroutineLaunch() {
        LogTools.d("协程执行开始")
        val job = GlobalScope.launch {
            delay(2000)

            val token = getToken()
            val userInfo = getUserInfo(token)
            setUserInfo(userInfo)

            LogTools.d("协程执行结束")
        }

        //Job中的方法
//        job.isActive
//        job.isCancelled
//        job.isCompleted
//        job.cancel()
//        job.join()

    }

    /**
     * 带有suspend的叫做携程挂起
     */
    private suspend fun getToken(): String {
        return "token"
    }

    /**
     * suspend函数会将整个协程挂起，而不仅仅是这个suspend函数，也就是说一个协程中有多个挂起函数时，它们是顺序执行的
     */
    private suspend fun getUserInfo(token: String): String {
        return "$token-userInfo"
    }

    private fun setUserInfo(userInfo: String) {
        LogTools.d(userInfo)
    }

    /**
     * 第三种：async跟launch的用法基本一样，区别在于：async的返回值是Deferred，将最后一个封装成了该对象。async可以支持并发，此时一般都跟await一起使用
     *
     * ***async是不阻塞线程的,也就是说getResult1和getResult2是同时进行的，所以获取到result的时间是4s，而不是7s。
     */

    fun coroutineAsync() {
        GlobalScope.launch {
            val result1 = GlobalScope.async {
                getResult1()
            }
            val result2 = GlobalScope.async {
                getResult2()
            }
            val result = result1.await() + result2.await()
            LogTools.d("result=$result")
        }
    }

    private suspend fun getResult1(): Int {
        delay(3000)
        return 1
    }

    private suspend fun getResult2(): Int {
        delay(4000)
        return 2
    }
}