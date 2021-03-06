package com.tecmanic.gogrocer.Adapters;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;
import com.tecmanic.gogrocer.Activity.MainActivity;
import com.tecmanic.gogrocer.Activity.ProductDetails;
import com.tecmanic.gogrocer.Config.BaseURL;
import com.tecmanic.gogrocer.Constans.RecyclerTouchListener;
import com.tecmanic.gogrocer.ModelClass.CartModel;
import com.tecmanic.gogrocer.ModelClass.varient_product;
import com.tecmanic.gogrocer.R;
import com.tecmanic.gogrocer.util.DatabaseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;
import static com.tecmanic.gogrocer.Config.BaseURL.IMG_URL;
import static com.tecmanic.gogrocer.Config.BaseURL.ProductVarient;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>  {
    SharedPreferences preferences;
    private DatabaseHandler dbcart;
    private List<CartModel> cartList;
    private final int limit = 4;

    Context context;

    private List<varient_product> varientProducts = new ArrayList<>();

    RecyclerView recyler_popup;
    LinearLayout cancl;
    String varient_id ,product_id;
    public CartAdapter(Context context, List<CartModel> cartList) {
        this.cartList = cartList;
        dbcart = new DatabaseHandler(context);
    }



    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView prodNAme,pDescrptn,pQuan,pPrice,pdiscountOff,pMrp,minus,plus,txtQuan;
        ImageView image;
        LinearLayout btn_Add,ll_addQuan;
        int minteger = 0;
        RelativeLayout rlQuan;
        String catId,catName;
        public MyViewHolder(View view) {
            super(view);
            prodNAme = (TextView) view.findViewById(R.id.txt_pName);
            pDescrptn = (TextView) view.findViewById(R.id.txt_pInfo);
            pQuan = (TextView) view.findViewById(R.id.txt_unit);
            pPrice = (TextView) view.findViewById(R.id.txt_Pprice);
            image = (ImageView) view.findViewById(R.id.prodImage);
            pdiscountOff = (TextView) view.findViewById(R.id.txt_discountOff);
            pMrp = (TextView) view.findViewById(R.id.txt_Mrp);
            rlQuan =  view.findViewById(R.id.rlQuan);
            btn_Add =  view.findViewById(R.id.btn_Add);
            ll_addQuan =  view.findViewById(R.id.ll_addQuan);
            txtQuan =  view.findViewById(R.id.txtQuan);
            minus =  view.findViewById(R.id.minus);
            plus =  view.findViewById(R.id.plus);
//            btn_Add.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    btn_Add.setVisibility(View.GONE);
//                    ll_addQuan.setVisibility(View.VISIBLE);
//                    txtQuan.setText("1");
//                    updateMultiply();
//                }
//            });
//            plus.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    increaseInteger();
////                    updateMultiply();
//
//                    if (Float.parseFloat(txtQuan.getText().toString()) == 1) {
//                       /* minus.setClickable(false);
//                        minus.setFocusable(false);*/
//                    } else if (Float.parseFloat(txtQuan.getText().toString()) > 1) {
//
//                    }
//                }
//            });
//            minus.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    decreaseInteger();
//                    updateMultiply();
//                }
//            });
          //  minus.setOnClickListener(this);
         //   plus.setOnClickListener(this);


        }

        private void display(Integer number) {

            txtQuan.setText("" + number);
        }


        @Override
        public void onClick(View view) {

        }
    }

    public CartAdapter(List<CartModel> cartList, Context context) {
        this.cartList = cartList;
        dbcart = new DatabaseHandler(context);
    }

    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_product_add, parent, false);
        context = parent.getContext();
        return new CartAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartAdapter.MyViewHolder holder, int position) {

        dbcart=new DatabaseHandler(context);
        CartModel cc = cartList.get(position);
        holder.prodNAme.setText(cc.getpNAme());
        holder.pDescrptn.setText(cc.getpDes());
        holder.pQuan.setText(cc.getpQuan());
        holder.pPrice.setText(cc.getpPrice());
        holder.pdiscountOff.setText(cc.getDiscountOff());
        holder.pMrp.setText(cc.getpMrp());
        holder.pMrp.setPaintFlags(holder.pMrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        Glide.with(context)
                .load(IMG_URL + cc.getpImage())
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(holder.image);




//        Double items = Double.parseDouble(dbcart.getInCartItemQty(cartList.get(position).getpId()));
        double price = Double.parseDouble(cartList.get(position).getpPrice());
        double mrp = Double.parseDouble(cartList.get(position).getpMrp());


        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ProductDetails.class);
                intent.putExtra("sId",cartList.get(position).getpId());
                intent.putExtra("sVariant_id",cartList.get(position).getVarient_id());
                intent.putExtra("sName",cartList.get(position).getpNAme());
                intent.putExtra("descrip",cartList.get(position).getpDes());
                intent.putExtra("price",cartList.get(position).getpPrice());
                intent.putExtra("mrp",cartList.get(position).getpMrp());
                intent.putExtra("unit",cartList.get(position).getUnit());
                intent.putExtra("qty",cartList.get(position).getpQuan());
                intent.putExtra("image", BaseURL.IMG_URL+cartList.get(position).getpImage());

                context.startActivity(intent);

            }
        });


        holder.plus.setOnClickListener(v -> {
            holder.btn_Add.setVisibility(View.GONE);
            holder.ll_addQuan.setVisibility(View.VISIBLE);
            int i = Integer.parseInt(cc.getpQuan());
            cartList.get(position).setpQuan(String.valueOf(i+1));
            holder.txtQuan.setText(""+(i+1));
            holder.pPrice.setText(""+(price*(i+1)));
            holder.pMrp.setText(""+(mrp*(i+1)) );
            updateMultiply(position);
//            notifyItemChanged(position);
        });
        holder.minus.setOnClickListener(v -> {
            int i = Integer.parseInt(cc.getpQuan());
            cartList.get(position).setpQuan(String.valueOf(i-1));
            holder.txtQuan.setText(""+(i-1));
            holder.pPrice.setText(""+(price*(i-1)));
            holder.pMrp.setText(""+(mrp*(i-1)) );
            if ((i-1)<0||(i-1)==0){
                holder.btn_Add.setVisibility(View.VISIBLE);
                holder.ll_addQuan.setVisibility(View.GONE);
            }
            updateMultiply(position);
        });
        holder.btn_Add.setOnClickListener(v -> {
            holder.btn_Add.setVisibility(View.GONE);
            holder.ll_addQuan.setVisibility(View.VISIBLE);
            cartList.get(position).setpQuan("1");
            holder.txtQuan.setText("1");
            updateMultiply(position);
        });

    }


    @Override
    public int getItemCount() {

        if(cartList.size() > limit){
            return limit;
        }
        else
        {
            return cartList.size();
        }
    }


    private void updateMultiply(int pos) {
        HashMap<String, String> map = new HashMap<>();
//            map.put("varient_id",cartList.get(position).getpId());
        map.put("varient_id",cartList.get(pos).getVarient_id());
        map.put("product_name",cartList.get(pos).getpNAme());
        map.put("category_id",cartList.get(pos).getpId());
        map.put("title",cartList.get(pos).getpDes());
        map.put("price",cartList.get(pos).getpPrice());
        map.put("mrp",cartList.get(pos).getpMrp());
        Log.d("fd",cartList.get(pos).getpImage());
        map.put("product_image",cartList.get(pos).getpImage());
        map.put("status",cartList.get(pos).getStatus());
        map.put("in_stock",cartList.get(pos).getIn_stock());
        map.put("unit_value",cartList.get(pos).getpQuan());
        map.put("unit",cartList.get(pos).getUnit());
        map.put("increament","0");
        map.put("rewards","0");
        map.put("stock","0");
        map.put("product_description","0");

//        Log.d("fgh",cartList.get(position).getUnit()+cartList.get(position).getpQuan());
//        Log.d("fghfgh",cartList.get(position).getpPrice());
        if (!cartList.get(pos).getpQuan().equalsIgnoreCase("0")) {
            if (dbcart.isInCart(map.get("varient_id"))) {
                dbcart.setCart(map, Integer.parseInt(cartList.get(pos).getpQuan()));
            } else {
                dbcart.setCart(map, Integer.parseInt(cartList.get(pos).getpQuan()));
            }
        } else {
            dbcart.removeItemFromCart(map.get("varient_id"));
        }
        try {
//            int items = (int) Double.parseDouble(dbcart.getInCartItemQty(map.get("varient_id")));
//            double price = Double.parseDouble(Objects.requireNonNull(map.get("price")).trim());
//            double mrp = Double.parseDouble(Objects.requireNonNull(map.get("mrp")).trim());
            //  Double reward = Double.parseDouble(map.get("rewards"));
            // tv_reward.setText("" + reward * items);
//            pDescrptn.setText(""+cartList.get(position).getpDes());
//            pPrice.setText("" +price* items);
//            txtQuan.setText("" + items);
//            pMrp.setText("" + mrp* items );
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                SharedPreferences preferences = context.getSharedPreferences("GOGrocer", Context.MODE_PRIVATE);
                preferences.edit().putInt("cardqnty",dbcart.getCartCount()).apply();
            }
        }catch (IndexOutOfBoundsException e){
            Log.d("qwer",e.toString());
        }
    }
//
//    public void increaseInteger() {
//        minteger = minteger + 1;
//        display(minteger);
//    }
//
//    public void decreaseInteger() {
//        if (minteger == 1) {
//            minteger = 1;
//            display(minteger);
//            ll_addQuan.setVisibility(View.GONE);
//            btn_Add.setVisibility(View.VISIBLE);
//        } else {
//            minteger = minteger - 1;
//            display(minteger);
//
//        }
//    }


}
