package com.shark.anchorscrollview.demo;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.shark.anchorscrollview.AnchorScrollView;
import com.shark.anchorscrollview.demo.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private TestAdapter testAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testAdapter = new TestAdapter(getData());
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.webView1.getSettings().setJavaScriptEnabled(true);
        binding.webView1.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        binding.webView2.getSettings().setJavaScriptEnabled(true);
        binding.webView2.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        binding.webView3.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        binding.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                computeHeight();
            }
        });
        binding.webView1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                            computeHeight();


                }
                return false;
            }
        });

        binding.webView1.addJavascriptInterface(this, "Resize");
        binding.webView1.loadUrl("http://backend.test.env/hybrid/promotion/detail/4668000/?tabs_type=wiki&is_gray=1");
        binding.webView2.loadUrl("http://www.sina.com");
        binding.webView3.loadUrl("https://baike.baidu.com/item/大同月饼");

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(testAdapter);

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonState(binding.textView);
                binding.mainScroll.anchorView(binding.textView);
            }
        });
        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonState(binding.textView2);
                binding.mainScroll.anchorView(binding.textView2);
            }
        });
        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonState(binding.webView1);
                binding.mainScroll.anchorView(binding.webView1);
            }
        });
        binding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonState(binding.webView2);
                binding.mainScroll.anchorView(binding.webView2);
            }
        });
        binding.button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonState(binding.wikiLayout);
                binding.mainScroll.anchorView(binding.wikiLayout);
            }
        });
        binding.button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonState(binding.recyclerView);
                binding.mainScroll.anchorView(binding.recyclerView);
            }
        });
        binding.button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.webView3.setVisibility(binding.webView3.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                if (binding.webView3.getVisibility() == View.VISIBLE)
                    binding.mainScroll.anchorView(binding.wikiLayout);
            }
        });
        binding.mainScroll.addAnchorView(binding.textView, binding.textView2, binding.webView1, binding.webView2, binding.wikiLayout, binding.recyclerView);
        binding.mainScroll.setOnNestedScrollViewChangedListener(new AnchorScrollView.OnNestedScrollViewChangedListener() {
            @Override
            public void onAnchor(View view) {
                if (view == null) return;
                setButtonState(view);
            }

            @Override
            public void onScrollStop() {

            }

            @Override
            public void onScrollToBottom() {
                testAdapter.getDataList().addAll(getData());
                testAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "滑到底部加载更多", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public List<String> getData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("<" + i + ">");
        }
        return list;
    }

    public void setButtonState(View view) {
        binding.button.setTextColor(Color.BLACK);
        binding.button2.setTextColor(Color.BLACK);
        binding.button3.setTextColor(Color.BLACK);
        binding.button4.setTextColor(Color.BLACK);
        binding.button6.setTextColor(Color.BLACK);
        binding.button7.setTextColor(Color.BLACK);

        if (view.getId() == binding.textView.getId()) {
            binding.button.setTextColor(Color.YELLOW);
        }
        if (view.getId() == binding.textView2.getId()) {
            binding.button2.setTextColor(Color.YELLOW);
        }
        if (view.getId() == binding.webView1.getId()) {
            binding.button3.setTextColor(Color.YELLOW);
        }
        if (view.getId() == binding.webView2.getId()) {
            binding.button4.setTextColor(Color.YELLOW);
        }
        if (view.getId() == binding.wikiLayout.getId()) {
            binding.button6.setTextColor(Color.YELLOW);
        }
        if (view.getId() == binding.recyclerView.getId()) {
            binding.button7.setTextColor(Color.YELLOW);
        }
    }

    private static class TestAdapter extends RecyclerView.Adapter {
        private List<String> dataList;

        public TestAdapter(List<String> dataList) {
            this.dataList = dataList;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(View v) {
                super(v);
            }

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            TextView textView = new TextView(viewGroup.getContext());
            textView.setTextColor(Color.BLACK);
            textView.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            return new ViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            TextView textView = (TextView) viewHolder.itemView;
            textView.setText("Item:" + i);
            textView.setGravity(Gravity.CENTER);
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public List<String> getDataList() {
            return dataList;
        }

        public void setDataList(List<String> dataList) {
            this.dataList = dataList;
        }
    }

    public void webViewAutoHeight() {

    }

    public void computeHeight() {
        binding.webView1.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.webView1.loadUrl("javascript:Resize.fetchHeight(document.body.getBoundingClientRect().height)");
            }
        },100);
    }

    @JavascriptInterface
    public void fetchHeight(final float height) {
        Log.e("shark", "fetchHeight:" + height);
        final int newHeight = (int) (height * getResources().getDisplayMetrics().density);
        Log.e("shark", "fetchHeight2:" + newHeight);
        binding.webView1.post(new Runnable() {
            @Override
            public void run() {
                if (binding.webView1.getLayoutParams() instanceof LinearLayout.LayoutParams) {
                    Log.e("Shark", "View height:" + binding.webView1.getHeight());
                    LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) binding.webView1.getLayoutParams();
                    linearParams.height = newHeight;
                    binding.webView1.setLayoutParams(linearParams);
                    binding.webView1.postInvalidate();

                    Log.e("Shark", "Layout height:" + linearParams.height);
                    Log.e("Shark", "View height:" + binding.webView1.getHeight());
                    Log.e("Shark", "Content height:" + binding.webView1.getContentHeight());

                }
            }
        });
    }
}
