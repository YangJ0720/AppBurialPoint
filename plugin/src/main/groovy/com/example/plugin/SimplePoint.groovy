package com.example.plugin

import javassist.CtClass
import javassist.CtMethod

/**
 * @author YangJ
 */
class SimplePoint {

    /**
     * 添加埋点代码
     * @param ctClass
     * @param fileName
     * @param content
     */
    static void addCode(CtClass ctClass, String fileName, String content) {
        ctClass.defrost()
        // 获取点击事件Method
        CtMethod ctMethod = ctClass.getDeclaredMethods().find()
        println "addCode -> ctMethod = " + ctMethod
        // 将埋点代码插入该方法末尾
        ctMethod.insertAfter(content)
        // 将修改后的class文件保存到本地
        ctClass.writeFile(fileName)
        ctClass.detach()
        //
        println "埋点执行完成"
    }

    /**
     * 添加埋点代码
     * @param ctClass
     * @param fileName
     * @param hashMap
     */
    static void addCodeToService(CtClass ctClass, String fileName, HashMap<CtMethod, String> hashMap){
        ctClass.defrost()
        Iterator<Map.Entry<CtMethod, String>> iterator = hashMap.entrySet().iterator()
        while (iterator.hasNext()) {
            Map.Entry entry = iterator.next()
            CtMethod ctMethod = entry.key
            // 将埋点代码插入该方法末尾
            ctMethod.insertAfter(entry.value)
        }
        // 将修改后的class文件保存到本地
        ctClass.writeFile(fileName)
        ctClass.defrost()
        //
        println "埋点执行完成"
    }

    /**
     * 添加埋点代码
     * @param ctClass
     * @param ctMethod
     * @param fileName
     * @param content
     */
    static void addCode(CtClass ctClass, CtMethod ctMethod, String fileName, String content) {
        ctClass.defrost()
        println "addCode -> ctMethod = " + ctMethod
        // 将埋点代码插入该方法末尾
        ctMethod.insertAfter(content)
        // 将修改后的class文件保存到本地
        ctClass.writeFile(fileName)
        ctClass.detach()
        //
        println "埋点执行完成"
    }

}