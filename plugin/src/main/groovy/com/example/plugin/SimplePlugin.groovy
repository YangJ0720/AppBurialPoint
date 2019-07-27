package com.example.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author YangJ
 */
class SimplePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println "---------------------------------"
        println "Hello Gradle Plugin"
        println "---------------------------------"
    }

}