package com.example.plugin

import com.android.SdkConstants
import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import javassist.NotFoundException
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

/**
 * @author YangJ
 */
class SimpleTransform extends Transform {

    /**
     * 点击事件埋点
     */
    private static final String POINT_CLICK = "android.view.View\$OnClickListener"
    /**
     * 长按事件埋点
     */
    private static final String POINT_LONG_CLICK = "android.view.View\$OnLongClickListener"
    /**
     * 当前Transform名称
     */
    private static final String DEF_NAME = "SimpleTransform"

    private ClassPool mPool
    private Project mProject

    SimpleTransform(Project project) {
        this.mPool = ClassPool.getDefault()
        this.mProject = project
    }

    @Override
    String getName() {
        // 指明当前Transform的名字
        return DEF_NAME
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        // 指明当前Transform的作用范围
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        // 是否增量构建
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        println "==================== transform ===================="
        mProject.android.bootClasspath.each { it ->
            mPool.appendClassPath(it.absolutePath)
            println "it.absolutePath = " + it.absolutePath
        }
        //
        transformInvocation.inputs.each { it ->
            // 遍历jar
            it.jarInputs.each { it_jar ->
                mPool.insertClassPath(it_jar.file.absolutePath)
                println "it_jar.file.absolutePath = " + it_jar.file.absolutePath
                //
                outputJarFile(transformInvocation, it_jar)
            }
            // 遍历文件夹
            it.directoryInputs.each { it_dir ->
                def path = it_dir.file.absolutePath
                mPool.insertClassPath(path)
                // 查找需要埋点的类
                findTarget(it_dir.file, path)
                println "it_dir.file.absolutePath = " + it_dir.file.absolutePath
                //
                outputDirectoryFile(transformInvocation, it_dir)
            }
        }
    }

    /**
     * 查找需要埋点的目标类
     * @param file
     * @param fileName
     */
    private void findTarget(File file, String fileName) {
        if (file.isDirectory()) {
            file.listFiles().each { it ->
                findTarget(it, fileName)
            }
        } else {
            modify(file, fileName)
        }
    }

    /**
     * 这个方法用于修改class文件
     * @param file
     * @param fileName
     */
    private void modify(File file, String fileName) {
        def filePath = file.absolutePath
        // 过滤掉R文件
        if (filePath.contains('R$') || filePath.endsWith("R.class")) {
            return
        }
        // 过滤掉BuildConfig文件
        if (filePath.endsWith('BuildConfig.class')) {
            return
        }
        // 过滤掉非class文件（工程中有module的情况，后续解决）
        if (!filePath.endsWith(SdkConstants.DOT_CLASS)) {
            return
        }
        // 根据class文件路径获取类名
        def className = filePath.replace(fileName, "")
                .replace("/", ".")
                .replace(SdkConstants.DOT_CLASS, "").substring(1)
        println "modify -> className = " + className
        //
        CtClass ctClass = mPool.get(className)
        println "modify -> ctClass = " + ctClass
        // 判断当前class是否继承Activity
        hasActivity(ctClass, fileName)
        // 判断当前class是否实现接口
        hasInterfaces(ctClass, fileName)
    }

    /**
     * 判断当前class是否继承Activity
     * @param ctClass
     * @param fileName
     */
    private void hasActivity(CtClass ctClass, String fileName) {
        CtClass superClass = ctClass.getSuperclass()
        println "superClass = " + superClass
        if (superClass.name == "androidx.appcompat.app.AppCompatActivity"
                || superClass.name == "androidx.fragment.app.FragmentActivity") {
            // 对onCreate方法进行埋点
            try {
                CtMethod ctMethod = ctClass.getDeclaredMethod("onCreate")
                println "ctMethod = " + ctMethod
                // 拼装埋点代码
                def content = "com.example.library.BurialPointManager.getInstance().getLogcat().holderActivityOnCreated(this);"
                addCode(ctClass, ctMethod, fileName, content)
            } catch (NotFoundException e) {
                println ctClass.name + " : " + e.getMessage()
            }
            // 对onDestroy方法进行埋点
            try {
                CtMethod ctMethod = ctClass.getDeclaredMethod("onDestroy")
                println "ctMethod = " + ctMethod
                // 拼装埋点代码
                def content = "com.example.library.BurialPointManager.getInstance().getLogcat().holderActivityOnDestroyed(this);"
                addCode(ctClass, ctMethod, fileName, content)
            } catch (NotFoundException e) {
                println ctClass.name + " : " + e.getMessage()
            }
        }
    }

    /**
     * 判断当前class是否实现接口
     * @param ctClass
     * @param fileName
     */
    private void hasInterfaces(CtClass ctClass, String fileName) {
        // 获取这个class实现的接口
        CtClass[] interfaces = ctClass.getInterfaces()
        println "modify -> interfaces = " + interfaces
        // 判断这个class是否实现接口，如果没有实现任何接口就直接返回
        if (interfaces.size() == 0) {
            return
        }
        // 检查是否实现点击事件 or 长按事件
        interfaces.each { it ->
            println "modify -> interfaces -> it = " + it
            if (mPool.get(POINT_CLICK) == it) {
                // 拼装埋点代码
                def content = "com.example.library.BurialPointManager.getInstance().getLogcat().holderOnClick(v);"
                addCode(ctClass, fileName, content)
            } else if (mPool.get(POINT_LONG_CLICK) == it) {
                // 拼装埋点代码
                def content = "com.example.library.BurialPointManager.getInstance().getLogcat().holderOnLongClick(v);"
                addCode(ctClass, fileName, content)
            }
        }
    }

    /**
     * 添加埋点代码
     * @param ctClass
     * @param fileName
     * @param content
     */
    private void addCode(CtClass ctClass, String fileName, String content) {
        SimplePoint.addCode(ctClass, fileName, content)
    }

    private void addCode(CtClass ctClass, CtMethod ctMethod, String fileName, String content) {
        SimplePoint.addCode(ctClass, ctMethod, fileName, content)
    }

    private void outputJarFile(TransformInvocation transformInvocation, JarInput input) {
        // 重命名输出文件（同目录copyFile会冲突）
        def jarName = input.name
        def md5Name = DigestUtils.md5Hex(input.file.getAbsolutePath())
        if (jarName.endsWith(".jar")) {
            jarName = jarName.substring(0, jarName.length() - 4)
        }
        def dest = transformInvocation.outputProvider.getContentLocation(
                jarName + md5Name, input.contentTypes, input.scopes, Format.JAR)
        FileUtils.copyFile(input.file, dest)
    }

    private void outputDirectoryFile(TransformInvocation transformInvocation, DirectoryInput input) {
        // 获取output目录
        def dest = transformInvocation.outputProvider.getContentLocation(
                input.name,
                input.contentTypes,
                input.scopes,
                Format.DIRECTORY)
        // 将input的目录复制到output指定目录
        FileUtils.copyDirectory(input.file, dest)
    }
}