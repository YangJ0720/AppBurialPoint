# AppBurialPoint

### Android客户端埋点方案，基于Javassist实现

项目特点
* 使用Javassist操作字节码文件的方式替换基于反射实现的数据埋点功能
* 可以修改第三方SDK中的代码逻辑（这个有待研究）

## APK下载
[Download](https://github.com/YangJ0720/AppBurialPoint/blob/master/apk/app-debug.apk)

## 相关实现
首先需要创建一个Gradle插件，相关创建步骤可以看[这里](https://github.com/YangJ0720/AppBurialPoint/blob/master/doc/创建Gradle插件.txt)

其次需要实现一个自己的Transform对代码进行扫描并埋点，这里我只对Activity生命周期方法和View点击事件进行埋点，代码如下：
```
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
```

## 效果图

### 对点击事件埋点：
可以看到在.java文件中的点击事件并没有埋点代码，如下：
![image](https://github.com/YangJ0720/AppBurialPoint/blob/master/jpg/1.png)
但是在生成的.class文件中存在埋点代码，如下：
![image](https://github.com/YangJ0720/AppBurialPoint/blob/master/jpg/2.png)

### 对Activity生命周期方法埋点
同样没有写入埋点代码，如下：
![image](https://github.com/YangJ0720/AppBurialPoint/blob/master/jpg/3.png)
但是生成的字节码文件中存在埋点代码，如下：
![image](https://github.com/YangJ0720/AppBurialPoint/blob/master/jpg/4.png)

### 运行结果
编译运行生成的apk文件，产生数据埋点文件如下：
![image](https://github.com/YangJ0720/AppBurialPoint/blob/master/jpg/5.png)


在kotlin中同样可以实现，不同的是在该目录下
⁨Javassist⁩ ▸ ⁨app⁩ ▸ ⁨build⁩ ▸ ⁨tmp⁩ ▸ ⁨kotlin-classes⁩ ▸ ⁨debug⁩ ▸ ⁨com⁩ ▸ ⁨example⁩ ▸ ⁨javassist⁩  
生成的字节码文件并不能预览编译结果，但是实际埋点代码生效，难道是我打开方式不对？

