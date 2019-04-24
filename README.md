# CustomizeDemo
<<<<<<< HEAD
各种自定义控件
https://www.showdoc.cc/web/#/28579193749381?page_id=180675323706009

1.添加图片选择器 jeasonlzy/ImagePicker
版本为'com.android.support:appcompat-v7:27.1.1'出现两个问题
        
        a.会报多个不同版本错误，需要统一版本

        Error:Execution failed for task ':app:preDebugBuild'.
        > Android dependency 'com.android.support:appcompat-v7' has different version for the compile (27.0.2) and runtime (27.1.1) classpath. You should manually set the same version via DependencyResolution
        解决方式：
        需要在项目的build.gradle添加代码，统一版本：
        configurations.all {
                 resolutionStrategy.eachDependency { DependencyResolveDetails details ->
                     def requested = details.requested
                     if (requested.group == 'com.android.support') {
                         if (!requested.name.startsWith("multidex")) {
                             details.useVersion '27.1.1'
                         }
                     }
                 }
             }
             
             
        b.compileSdkVersion 版本 27 及以上，大图返回列表时数据空了
        java.lang.RuntimeException: Unable to resume activity {cn.dlc.zizhuyinliaoji.myapplication/com.lzy.imagepicker.ui.ImageGridActivity}: java.lang.IndexOutOfBoundsException
        解决方式：
        1.把版本讲到27以下，或者27版本以上的27.0.2/27.0.3，这两个测试可用，其他没有测试
        2.使用修改后的imagepicker-library，即在ImageDataSource类里onLoadFinished方法imageFolders.clear();前面加上代码
        if ((activity.getLifecycle().getCurrentState() == STARTED || activity.getLifecycle().getCurrentState() == RESUMED) && imageFolders.size() > 0) {
        return;
        }
        再导入工程，解决

