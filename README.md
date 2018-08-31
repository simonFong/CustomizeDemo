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
=======

#### 项目介绍
{**以下是码云平台说明，您可以替换为您的项目简介**
码云是开源中国推出的基于 Git 的代码托管平台（同时支持 SVN）。专为开发者提供稳定、高效、安全的云端软件开发协作平台
无论是个人、团队、或是企业，都能够用码云实现代码托管、项目管理、协作开发。企业项目请看 [https://gitee.com/enterprises](https://gitee.com/enterprises)}

#### 软件架构
软件架构说明


#### 安装教程

1. xxxx
2. xxxx
3. xxxx

#### 使用说明

1. xxxx
2. xxxx
3. xxxx

#### 参与贡献

1. Fork 本项目
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request


#### 码云特技

1. 使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2. 码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3. 你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4. [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5. 码云官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6. 码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
>>>>>>> 285480b06239f8e908fb126953797a4f15f018c9
