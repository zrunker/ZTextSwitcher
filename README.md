# ZTextSwitcher
Android文本转换器TextSwitcher使用和自定义，简单的来说TextSwitcher是用来转换TextView,主要来实现TextView的转换动画。使用工具Android Studio
>作者：邹峰立，微博：zrunker，邮箱：zrunker@yahoo.com，微信公众号：书客创作，个人平台：[www.ibooker.cc](http://www.ibooker.cc)。

>本文选自[书客创作](http://www.ibooker.cc)平台第15篇文章。[阅读原文](http://www.ibooker.cc/article/15/detail) 。

![书客创作](http://upload-images.jianshu.io/upload_images/3480018-ba920f336694bcb9..jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

TextSwitcher继承ViewSwitcher，是用来实现文本转换的控件，一般用来转换TextView的文本。平时在开发过程中，改变TextView文本，只需要使用TextView.setText("文本")即可，那么TextSwitcher转换TextView有哪些特殊的地方呢？

**首先，说说TextSwitcher的这个转换到底指什么？**

这里的转换并不只是对同一个TextView进行文本转换，还可以对不同TextView进行文本转换。

拿生活中的例子来说，你平时在支付宝上发布上门寄快件信息，支付宝上快递公司可能派快递员过来取快件，然后寄出去。当你再次在支付宝上发布上门寄快件信息，支付宝上快递公司同样会派快递员过来取快件，然后寄出去。这里的快递员就相当于TextView，可能是同一个快递员，也可以是不同的快递员。快件信息就相当于文本。而支付宝平台就相当于TextSwitcher，用来处理信息转换。

**其次，说说TextSwitcher文本转换特点。**

TextSwitcher最大的特点就是在处理文本转换的时候，设置转换过程动画。对于多个TextView只需要一次设置即可。
```
// 设置转换动画，这里引用系统自带动画
Animation in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
Animation out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
textSwitcher.setInAnimation(in);
textSwitcher.setOutAnimation(out);
```
与TextSwitcher最为密切的是ViewFactory，它是用来给TextSwitcher提供视图的工厂，ViewFactory通过makeView方法制造View视图。
```
// 提供TextSwitcher切换View
@Override
public View makeView() {
   TextView textView =new TextView(this);
   textView.setTextSize(24);
   return textView;
}
```
**最后，实例分析。**

TextSwitcher使用起来很简单，只需两个关键步骤：定义TextSwitcher实体、设置ViewFactory。

TextSwitcher的使用有两种方式，一种是直接在XML布局文件中添加，之后在Java文件中进行相关属性设置。二种是自定义TextSwitcher。

1、XML布局文件添加
```
<TextSwitcher
   android:id="@+id/textswitcher"
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"
   android:text="@string/app_name"/>
```
这时候要在Java代码中设置ViewFactory，这里可以通过继承ViewSwitcher.ViewFactory接口，实现ViewFactory。
```
// 自定义文本转换器
textSwitcher= (TextSwitcher) findViewById(R.id.textswitcher);
// 指定TextSwitcher的ViewFactory（视图工厂），ViewFactory会给TextSwitcher提供View
textSwitcher.setFactory(this);
```
2、自定义TextSwitcher
```
public class ZTextSwitcher extends TextSwitcher implements TextSwitcher.ViewFactory {
   private Context mContext;
   public ZTextSwitcher(Context context) {
      this(context, null);
   }
   public ZTextSwitcher(Context context, AttributeSet attrs) {
      super(context,attrs);
      this.mContext= context;
      init();
   }
   private void init() {
      // 设置转换动画，这里引用系统自带动画
      Animation in = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in);
      Animation out = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_out);
      this.setInAnimation(in);
      this.setOutAnimation(out);
      // 设置ViewSwitcher.ViewFactory
      this.setFactory(this);
   }
   @Override
   public View makeView() {
      TextView textView =new TextView(mContext);
      textView.setTextSize(24);
      return textView;
   }
}
```
最后在XML布局文件中引入即可：
```
<cc.ibooker.ztextswitcher.ZTextSwitcher
   android:id="@+id/ztextswitcher"
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"
   android:text="@string/app_name"/>
```
[GitHub地址](https://github.com/zrunker/ZTextSwitcher)
[阅读原文](http://www.ibooker.cc/article/15/detail) 

----------
![微信公众号：书客创作](http://upload-images.jianshu.io/upload_images/3480018-c397c00643b057d3..jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
