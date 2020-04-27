package com.example.friendscirclemvvm.utils

import android.content.Context
import com.alibaba.fastjson.JSON
import java.io.*

class FileUtil(private val context: Context) {

    fun <T> saveToFile(fileName: String, content: T) {
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(JSON.toJSONString(content).toByteArray())
        }
    }

    fun <T> appendToFileWithLine(fileName: String, content: T) {
        context.openFileOutput(fileName, Context.MODE_APPEND).use {
            it.write(JSON.toJSONString(content).toByteArray())
            it.write("\r\n".toByteArray())
        }
    }

    fun exists(fileName: String) : Boolean {
        val file = File(context.filesDir, fileName)
        return file.exists()
    }

    fun deleteFile(fileName: String) : Boolean {
        val file = File(context.filesDir, fileName)
        if (file.exists()) {
            return context.deleteFile(fileName)
        }
        return false
    }

    fun <T> readFileToObject(fileName: String, clazz: Class<T>): T {
        val bufferedReader = BufferedReader(InputStreamReader(context.openFileInput(fileName)))
        bufferedReader.use {
            return JSON.parseObject(it.readText(), clazz)
        }
    }

    fun <T> readFileByLines(fileName: String, start: Long, end: Long, clazz: Class<T>): MutableList<T> {
        val result = mutableListOf<T>();
        val bufferedReader = BufferedReader(InputStreamReader(context.openFileInput(fileName)))
        bufferedReader.use {
            var currentPoint = 0
            while (currentPoint < start) {
                it.readLine()
                currentPoint += 1
            }
            while (currentPoint < end) {
                val line = it.readLine()
                if (line != null) {
                    result.add(JSON.parseObject(line, clazz))
                    currentPoint += 1
                } else {
                    break
                }
            }
        }
        return result
    }

    fun <T> readFileToList(fileName: String, clazz: Class<T>): List<T> {
        val bufferedReader = BufferedReader(InputStreamReader(context.openFileInput(fileName)))
        bufferedReader.use {
            return JSON.parseArray(it.readText(), clazz)
        }
    }

}