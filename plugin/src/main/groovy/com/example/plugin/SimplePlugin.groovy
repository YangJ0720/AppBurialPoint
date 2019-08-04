package com.example.plugin

import com.android.build.gradle.AppExtension
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
        // 方式一
        // project.android.registerTransform(new SimpleTransform(project))
        // 方式二
        def extension = project.extensions.findByType(AppExtension.class)
        extension.registerTransform(new SimpleTransform(project))
    }

}