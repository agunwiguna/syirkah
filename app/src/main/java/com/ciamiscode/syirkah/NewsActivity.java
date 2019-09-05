package com.ciamiscode.syirkah;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class NewsActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE   = "extra_title";
    public static final String EXTRA_DESCRIPTION   = "extra_description";
    public static final String EXTRA_CREATED_AT   = "extra_created_at";
    public static final String EXTRA_BANNER   = "extra_banner";

    ImageView imgNews;
    TextView txtTitle,txtTanggal,txtDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        imgNews = findViewById(R.id.imgDetailNews);
        txtTitle = findViewById(R.id.tvDetailJudul);
        txtTanggal = findViewById(R.id.tvDetailTanggal);
        txtDesc = findViewById(R.id.tvDetailIsi);

        String title = getIntent().getStringExtra(EXTRA_TITLE);
        String created_at = getIntent().getStringExtra(EXTRA_CREATED_AT);
        String description = getIntent().getStringExtra(EXTRA_DESCRIPTION);
        String banner = getIntent().getStringExtra(EXTRA_BANNER);

        //String rIsi = Html.fromHtml(description).toString().replaceAll("\n", "").trim();

        txtTitle.setText(title);
        txtTanggal.setText(created_at);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtDesc.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT));
        } else {
            txtDesc.setText(Html.fromHtml(description));
        }

        String img_url = "https://syirkah-web.solution.dipointer.com/src/news/"+banner;
        Glide.with(this).load(img_url).into(imgNews);
    }
}
