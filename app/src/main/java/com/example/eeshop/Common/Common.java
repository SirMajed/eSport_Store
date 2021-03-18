package com.example.eeshop.Common;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.widget.TextView;

import com.example.eeshop.Model.CategoryModel;
import com.example.eeshop.Model.ProductModel;
import com.example.eeshop.Model.User;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Common {
    public static final String USER_REFERENCES = "User";

    public static final String POPULAR_CATEGORY_REF = "MostPopular";
    public static final String BEST_DEALS_REF = "BestDeals";
    public static final int DEFAULT_COLUMN_COUNT = 0;
    public static final int FULL_WIDTH_COLUMN = 1;
    public static final String CATEGORY_REF = "Category";
    public static CategoryModel categorySelected;
    public static ProductModel selectedProduct;
    public static User currentUser;

    public static String formatPrice(double price){ // هذي الميثود عشان نرتب السعر الي بيكون في السلة
        if(price!=0)
        {
            DecimalFormat df = new DecimalFormat("#,##0.00");
            df.setRoundingMode(RoundingMode.UP);
            String finalPrice = new StringBuilder(df.format(price)).toString();
            return finalPrice.replace(".",",");
        }
        else
        return "0,00";
    }


}
