package com.example.maket.ui.food;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.maket.R;
import com.example.maket.ui.catalog.Product;
import com.example.maket.ui.catalog.ProductAdapter;
import com.google.android.material.button.MaterialButton;

public class FoodProfileActivity extends AppCompatActivity {

    public static final String EXTRA_PRODUCT = "product";

    private ViewPager2 vpImages;
    private TextView tvTitle, tvDesc, tvPriceOld, tvPriceNew;
    private MaterialButton btnBook;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_profile);

        vpImages = findViewById(R.id.vpImages);
        tvTitle = findViewById(R.id.tvTitle);
        tvDesc = findViewById(R.id.tvDesc);
        tvPriceOld = findViewById(R.id.tvPriceOld);
        tvPriceNew = findViewById(R.id.tvPriceNew);
        btnBook = findViewById(R.id.btnBook);

        Product p = (Product) getIntent().getSerializableExtra(EXTRA_PRODUCT);
        if (p == null) {
            finish();
            return;
        }

        tvTitle.setText(p.getTitle());
        tvDesc.setText(p.getDescription());
        tvPriceOld.setText(p.getOldPrice() + " с");
        tvPriceNew.setText(p.getNewPrice() + " с");

        int[] images = p.getImageResIds();
        if (images == null || images.length == 0) {
            images = new int[]{ android.R.drawable.ic_menu_report_image };
        }
        vpImages.setAdapter(new ProductAdapter.ImagePagerAdapter(images));

        btnBook.setOnClickListener(v ->
                Toast.makeText(this, "Заказ оформлен (декорация)", Toast.LENGTH_SHORT).show()
        );
    }
}
