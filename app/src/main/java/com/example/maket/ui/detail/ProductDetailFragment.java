package com.example.maket.ui.detail;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.annotation.*;
import androidx.fragment.app.Fragment;

import com.example.sakta.R;


public class ProductDetailFragment extends Fragment {
    @Nullable
@Override
    public View onCreateView(@NonNull LayoutInflater i, @Nullable ViewGroup c, @Nullable Bundle b) {
        return i.inflate(R.layout.fragment_product_detail, c, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle b) {
        ImageView img = v.findViewById(R.id.photo);
        TextView title = v.findViewById(R.id.title), desc = v.findViewById(R.id.desc), type = v.findViewById(R.id.type), oldP = v.findViewById(R.id.priceOld), newP = v.findViewById(R.id.priceNew), disc = v.findViewById(R.id.discount), weight = v.findViewById(R.id.weight), maker = v.findViewById(R.id.maker);
        img.setImageResource(android.R.drawable.ic_menu_report_image);
        int imageRes   = R.drawable.shawerma;
        title.setText("Шаурма куриная");
        desc.setText("Шаурма — это сочное и сытное блюдо, в котором нежное, обжаренное на вертеле мясо (курица, говядина, баранина) заворачивают в тонкий лаваш вместе со свежими, хрустящими овощами и ароматным чесночным или пряным соусом. Сочетание теплого, слегка поджаренного лаваша, сочного мяса, хрустящих овощей и пикантного соуса создает гармоничный и неповторимый вкус, что делает шаурму популярным стритфудом");
        type.setText("Тип: остаток еды");
        oldP.setText("300 сом");
        newP.setText("200 сом");
        disc.setText("-30%");
        weight.setText("450 г");
        maker.setText("Производитель: Али Бургер(Сын Айзирек)");
        v.findViewById(R.id.btnAddCart).setOnClickListener(x -> Toast.makeText(requireContext(), "Добавлено", Toast.LENGTH_SHORT).show());
        img.setImageResource(imageRes);
    }
}