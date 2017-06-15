package cc.ibooker.ztextswitcher;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements ViewSwitcher.ViewFactory {
    private TextSwitcher textSwitcher;
    private  ZTextSwitcher zTextSwitcher;
    private int num = 0;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 第一种方案
         */
        // 自定义文本转换器
        textSwitcher = (TextSwitcher) findViewById(R.id.textswitcher);
        // 指定TextSwitcher的ViewFactory（视图工厂），ViewFactory会给TextSwitcher提供View
        textSwitcher.setFactory(this);

        // 设置转换动画，这里引用系统自带动画
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        textSwitcher.setInAnimation(in);
        textSwitcher.setOutAnimation(out);

        /**
         * 第二种方案
         */
        zTextSwitcher = (ZTextSwitcher) findViewById(R.id.ztextswitcher);

        // 开启线程进行文字切换
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (num < 100) {
                    SystemClock.sleep(1000);
                    num++;
                    handler.sendEmptyMessage(200);
                }
            }
        });
        if (executorService == null || executorService.isShutdown())
            executorService = Executors.newSingleThreadExecutor();
        executorService.execute(thread);
    }

    // 提供TextSwitcher切换View
    @Override
    public View makeView() {
        TextView textView = new TextView(this);
        textView.setTextSize(24);
        return textView;
    }

    /**
     * 通过handler执行主线程
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 200) {
                textSwitcher.setText(num + "");
                zTextSwitcher.setText(num + "");
            }
        }
    };
}
