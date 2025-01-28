package com.example.projectti

import android.app.Application
import com.example.projectti.DependencitionInjection.AppContainer
import com.example.projectti.DependencitionInjection.BukuContainer

class BukuApplications: Application(){
    lateinit var container: BukuContainer
    override fun onCreate() {
        super.onCreate()
        container= BukuContainer()
    }
}