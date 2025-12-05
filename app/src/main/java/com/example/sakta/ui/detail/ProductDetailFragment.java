package com.example.sakta.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sakta.R;

import java.util.Locale;

public class ProductDetailFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater i, @Nullable ViewGroup c, @Nullable Bundle b) {
        return i.inflate(R.layout.fragment_product_detail, c, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle b) {
        ImageView img = v.findViewById(R.id.photo);
        TextView title = v.findViewById(R.id.title);
        TextView desc = v.findViewById(R.id.desc);
        TextView type = v.findViewById(R.id.type);
        TextView oldP = v.findViewById(R.id.priceOld);
        TextView newP = v.findViewById(R.id.priceNew);
        TextView disc = v.findViewById(R.id.discount);
        TextView weight = v.findViewById(R.id.weight);
        TextView maker = v.findViewById(R.id.maker);

        Bundle args = getArguments();
        String titleArg = args != null ? args.getString("title", "") : "";
        String descArg = args != null ? args.getString("description", "") : "";
        int oldPrice = args != null ? args.getInt("oldPrice", 0) : 0;
        int newPrice = args != null ? args.getInt("newPrice", 0) : 0;
        int discount = args != null ? args.getInt("discount", 0) : 0;
        String pickup = args != null ? args.getString("pickup", "") : "";
        double distance = args != null ? args.getDouble("distance", 0) : 0;
        float rating = args != null ? args.getFloat("rating", 0f) : 0f;
        int imageRes = args != null ? args.getInt("imageRes", android.R.drawable.ic_menu_report_image)
                : android.R.drawable.ic_menu_report_image;

        img.setImageResource(imageRes);
        title.setText(!titleArg.isEmpty() ? titleArg : "Детали предложения");
        desc.setText(!descArg.isEmpty() ? descArg : getString(R.string.app_name));
        type.setText(String.format("Готово к выдаче, до %s", pickup.isEmpty() ? "конца дня" : pickup));
        oldP.setText(String.format(Locale.getDefault(), "%d c", oldPrice));
        newP.setText(String.format(Locale.getDefault(), "%d c", newPrice));
        disc.setText(String.format(Locale.getDefault(), "-%d%%", discount));
        weight.setText(String.format(Locale.getDefault(), "Оценка %.1f ★", rating));
        maker.setText(String.format(Locale.getDefault(), "~ %.1f км от вас", distance));
        v.findViewById(R.id.btnAddCart).setOnClickListener(x ->
                Toast.makeText(requireContext(), "Добавлено", Toast.LENGTH_SHORT).show());
    }
}
