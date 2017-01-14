package com.example.gaurav.gitfetchapp.Repositories;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gaurav.gitfetchapp.GitHubEndpointInterface;
import com.example.gaurav.gitfetchapp.R;
import com.example.gaurav.gitfetchapp.ServiceGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import us.feras.mdv.MarkdownView;

public class FileViewActivity extends AppCompatActivity {
    private static final String TAG = FileViewActivity.class.getName();
    public static final String FILE_URL = "file_url";
    public static final String FILE_NAME = "file_name";
    private static String fileContents;
    private static StringBuilder sb;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.loading_bar)
    MaterialProgressBar circularProgressBar;
    //@BindView(R.id.webview_file)
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_file_view);
        ButterKnife.bind(this);
        webView = (WebView) findViewById(R.id.webview_file);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String fileName = intent.getStringExtra(FILE_NAME);
        String url = intent.getStringExtra(FILE_URL);

        /*Setting the filename as toolbar title*/
        getSupportActionBar().setTitle(fileName);

        sb = new StringBuilder();
        sb.append("<!doctype html><html><head>\n" +
                " <link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/sons_of_obidiah.css\"></link>\n" +
                "<script type=\"text/javascript\" src=\"file:///android_asset/prettify.js\"></script>\n" +
                "        </head><body onload=\"prettyPrint()\"><pre id=\"content\" class=\"prettyprint linenums\">");
        fetchFileContents(url);
    }

    private void fetchFileContents(String download_url){
        GitHubEndpointInterface gitHubEndpointInterface = ServiceGenerator.createService(
                GitHubEndpointInterface.class);
        Call<ResponseBody> call = gitHubEndpointInterface.downloadFileWithDynamicUrlSync(download_url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    BufferedReader reader = null;
                    StringBuilder sb = new StringBuilder();
                    try {
                        reader = new BufferedReader(new InputStreamReader(
                                response.body().byteStream()));
                        String line;
                        try {
                            while ((line = reader.readLine()) != null) {
                                sb.append(line);
                                sb.append("\n");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } finally {

                    }
                    setFileContents(sb.toString());
                } else {
                    Log.d(TAG, "server contact failed");
                }
                circularProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "error");
                circularProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private void setFileContents(String contents){
        sb.append(TextUtils.htmlEncode(contents));
        sb.append("</pre></body></HTML>");
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        //settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        //webView.setWebViewClient(new MyBrowser());
        //webView.getSettings().setJavaScriptEnabled(true);
        //webView.setHorizontalScrollBarEnabled(false);
        //webView.setVerticalScrollBarEnabled(false);
        webView.loadDataWithBaseURL(null, sb.toString(), "text/html", "utf-8", null);
    }
}
