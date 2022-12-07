package com.example.navigator

interface Navigator {
    fun <T> getFeature(clazz: Class<T>): T
}