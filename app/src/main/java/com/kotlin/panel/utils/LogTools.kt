package com.kotlin.panel.utils;

import android.content.Context
import android.os.Environment
import com.blankj.utilcode.util.LogUtils
import com.kotlin.panel.app.App
import java.io.File

/**
 * Explanation  --- 此Log类 唯一入口
 * LogTools.initLogTools() 初始化log系统
 * ----------------------------------
 * Log 源码看到一共由 VERBOSE = 2/DEBUG = 3/INFO = 4/WARN = 5/ERROR = 6/ASSERT = 7  六种log类型组成
 * 本log系统也是由这六种组成
 * ----------------------------------
 * w(WARN)/e(ERROR)/a(ASSERT) 这三种都会上传到cache/logger/ 对应文件中 注意调用问题;
 * 如果不想写入到文件,可以调用 类型<w
 * .e.g
 * v(VERBOSE)/d(DEBUG)/i(INFO)
 * ----------------------------------
 * 调用方式
 * e.g.
 * LogTools.i() 默认tag是aijialog / LogTools.iTag() developer自己定义tag，其他类型同这个例子
 * ----------------------------------
 * json调用
 * LogTools.json()
 */

object LogTools {

    private var IS_DEBUG: Boolean = true;
    private const val TAG: String = "kotlinPanel";
    private const val consoleSwitch: Boolean = true;//设置是否输出到控制台开关
    private const val filePrefix: String =
        "com.kotlin.panel";// .e.g com.kotlin.panel-2020-05-12.log
    private const val fileExtension: String = "log";
    private const val saveDays: Int = 5;
    private const val fileFilter = LogUtils.W;//写入到文件的类型


    fun v(vararg contents: Any?) {
        if (!IS_DEBUG) return
        LogUtils.v(contents)
    }

    fun v(subTag: String?, vararg contents: Any?) {
        if (!IS_DEBUG) return
        LogUtils.vTag(subTag, contents)
    }

    fun d(vararg contents: Any?) {
        if (!IS_DEBUG) return
        LogUtils.d(contents)
    }

    fun dTag(subTag: String?, vararg contents: Any?) {
        if (!IS_DEBUG) return
        LogUtils.dTag(subTag, contents)
    }

    fun i(vararg contents: Any?) {
        if (!IS_DEBUG) return
        LogUtils.i(contents)
    }

    fun iTag(subTag: String?, vararg contents: Any?) {
        if (!IS_DEBUG) return
        LogUtils.iTag(subTag, contents)
    }

    fun w(vararg contents: Any?) {
        if (!IS_DEBUG) return
        LogUtils.w(contents)
    }

    fun wTag(subTag: String?, vararg contents: Any?) {
        if (!IS_DEBUG) return
        LogUtils.wTag(subTag, contents)
    }

    fun e(vararg contents: Any?) {
        if (!IS_DEBUG) return
        LogUtils.e(contents)
    }

    fun eTag(subTag: String?, vararg contents: Any?) {
        if (!IS_DEBUG) return
        LogUtils.eTag(subTag, contents)
    }


    fun a(vararg contents: Any?) {
        if (!IS_DEBUG) return
        LogUtils.a(contents)
    }

    fun aTag(subTag: String?, vararg contents: Any?) {
        if (!IS_DEBUG) return
        LogUtils.aTag(subTag, contents)
    }


    fun json(content: Any?) {
        if (!IS_DEBUG) return
        LogUtils.json(content)
    }

    fun json(subTag: String?, content: Any?) {
        if (!IS_DEBUG) return
        LogUtils.json(subTag, content)
    }

    /**
     * @param type    log类型
     * @param subTag
     * @param content
     */
    fun json(type: Int, subTag: String?, content: Any?) {
        if (!IS_DEBUG) {
            return;
        }
        LogUtils.json(type, subTag, content);
    }

    fun initLogTools(context: Context?) {
        LogUtils.getConfig()
            // 设置 log 总开关，包括输出到控制台和文件，默认开
            .setLogSwitch(true)
            // 设置是否输出到控制台开关，默认开
            .setConsoleSwitch(consoleSwitch)
            // 设置 log 全局标签，默认为空，当全局标签不为空时，我们输出的 log 全部为该 tag， 为空时，如果传入的 tag 为空那就显示类名，否则显示 tag
            .setSaveDays(saveDays)
            .setGlobalTag(TAG)
            // 设置 log 头信息开关，默认为开
            .setLogHeadSwitch(true)
            // 打印 log 时是否存到文件的开关，默认关
            .setLog2FileSwitch(true)
            // 当自定义路径为空时，写入应用的/cache/log/目录中
            .setDir(getLogDir())
            // 当文件前缀为空时，默认为"util"，即写入文件为"util-MM-dd.txt"
            .setFilePrefix(filePrefix)
            .setFileExtension(fileExtension)
            // 输出日志是否带边框开关，默认开
            .setBorderSwitch(true)
            // 一条日志仅输出一条，默认开，为美化 AS 3.1 的 Logcat
            .setSingleTagSwitch(true)
            // log 的控制台过滤器，和 logcat 过滤器同理，默认 Verbose
            .setConsoleFilter(LogUtils.V)
            // log 文件过滤器，和 logcat 过滤器同理，默认 Verbose
            .setFileFilter(fileFilter)
            // log 栈深度，默认为 1
            .setStackDeep(2)
            // 设置栈偏移，比如二次封装的话就需要设置，默认为 0
            .setStackOffset(1);
    }


    fun setDebug(debug: Boolean) {
        IS_DEBUG = debug;
    }

    private fun getLogDir(): String {
        return getDiskCacheDir(App.instance) + File.separator + "logger" + File.separator;
    }

    private fun getDiskCacheDir(context: Context): String? {
        return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) {
            context.externalCacheDir?.path;
        } else {
            context.cacheDir.path;
        }
    }
}
