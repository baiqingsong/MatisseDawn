# Matisse的使用

* [Matisse简介](#matisse简介)
* [Matisse引用](#matisse引用)
* [Matisse使用](#Matisse使用)
* [参照网址](#参照网址)


## Matisse简介
本地图片、视频选择器

* 选择包括JPEG、PNG、GIF格式的图片和 MPEG、MP4 格式的视频
* 支持自定义主题，包括两个内置的主题
* 不同的图片加载器
* 定义自定义过滤规则
* 在 Activities 和 Fragments 中操作良好

## Matisse引用

build.gradle中
```
repositories {
    jcenter()
}

dependencies {
    compile 'com.zhihu.android:matisse:0.4.3'
    compile 'com.squareup.picasso:picasso:2.5.2'
    compile 'com.github.bumptech.glide:glide:3.7.0'
}
```


## Matisse使用

首先需要权限
```
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```

```
Matisse.from(this)
        .choose(MimeType.allOf())//选择类型，分为JPEG,PNG,GIF
        .maxSelectable(9)//最多选择张数
//      .capture(true)
//      .captureStrategy(new CaptureStrategy(true, ""))
        .countable(true)//选择图片的是否显示数字
        .imageEngine(new GlideEngine())//显示引擎，可以是PicassoEngine或者GlideEngine但是对应的需要引用对应的包，并且这个方法必须调用
//      .thumbnailScale(0.85f)
//      .theme(R.style.Matisse_Dracula)//设置模式，默认是Matisse_Zhihu,可以设置成Matisse_Dracula
        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))//设置显示时图片大小
        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)//限制方向,grid显示
//      .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))//添加过滤
        .forResult(REQUEST_CODE_CHOOSE);//requestCode参数
```

其中要注意的是：imageEngine必须设置，可以是PicassoEngine或者GlideEngine，
但是对应要引用picasso和glide的包
有几个问题没有解决：
* capture和captureStrategy一起使用显示相机，但是相机不可用
* addFilter添加过滤，但是没有效果

选择图片后onActivityResult方法中调用Matisse.obtainResult(data)这个返回图片的Uri集合
```
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
        mAdapter.setData(Matisse.obtainResult(data));
    }
}
```

## 参照网址

0.43版本可能有些功能不可用，可以参照
[https://github.com/zhihu/Matisse](https://github.com/zhihu/Matisse "参照网址")
代码中有本地的Matisse工具包