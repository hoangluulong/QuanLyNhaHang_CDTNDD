package java.android.quanlybanhang.CongAdapter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.android.quanlybanhang.R;
import java.android.quanlybanhang.Sonclass.SanPham;
import java.android.quanlybanhang.Activity.AnimationUlti;
import java.android.quanlybanhang.Activity.KhachHangActivity;
import java.util.List;

public class Cart_Fragment extends Fragment {
    private RecyclerView recyclerProduct;
    private ProductAdapter productAdapter;
    private View mView;
    private KhachHangActivity mainActivity;
    private CheckBox cbCartFragment;
    private TextView tvCartFragment;
    private Button btnCartFragment;




    private List<SanPham> quans;

    public Cart_Fragment(List<SanPham> quan) {
        this.quans=quan;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mView=inflater.inflate(R.layout.cart_fragment,container,false);
        Log.d("BBB","onCreate CartFragment");

        mainActivity=(KhachHangActivity)  getActivity();

        recyclerProduct=mView.findViewById(R.id.recycleCartFragment);
        cbCartFragment=mView.findViewById(R.id.cbCartFragment);
        tvCartFragment=mView.findViewById(R.id.tvCartFragment);
        btnCartFragment=mView.findViewById(R.id.btnCartFragment);


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mainActivity);
        recyclerProduct.setLayoutManager(linearLayoutManager);

        productAdapter=new ProductAdapter(quans);
        productAdapter.setData( mainActivity,new ProductAdapter.IclickAddToCartListener() {
            @Override
            public void onClickAddToCart(ImageView imageToCart, SanPham product) {
                AnimationUlti.translateAnimation(mainActivity.getViewAnimation(), imageToCart, mainActivity.getViewEndAnimation(), new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        imageToCart.setBackgroundResource(R.drawable.cart_unshopping);
                        product.setAddToCart(true);
                        productAdapter.notifyDataSetChanged();
                        mainActivity.setCountProductInBuild(quans.size()+1);

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

            }

            @Override
            public void setGiaDonHang(long donHang) {

                tvCartFragment.setText(addDauPhay(donHang)+" VNĐ");
            }


        });

        recyclerProduct.setAdapter(productAdapter);

        btnCartFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DonHangFragment donHangFragment=new DonHangFragment(productAdapter.getListProductIsChecked());
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,donHangFragment).commit();


            }
        });

        cbCartFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbCartFragment.isChecked()==true)
                {
                    productAdapter.setCheckedCheckbox(true);

                    productAdapter.iclickAddToCartListener.setGiaDonHang(productAdapter.setGiaDonHang());
                }else {
                    productAdapter.setCheckedCheckbox(false);
                    productAdapter.iclickAddToCartListener.setGiaDonHang(productAdapter.setGiaDonHang());

                }

            }
        });

        return mView;
    }

    //them dau phay cho don gia
    private String addDauPhay(long abc)
    {
        String x="";
        if(abc==0)
        {
            return "0";
        }
        while (abc/1000>0)
        {
            if(x=="")
            {
                if(abc%1000==0)
                {
                    x="000";
                }


            }else {
                x=(abc%1000)+","+x;

            }

            abc=abc/1000;
            Log.d("bbb",x+" bbb");
        }
        if(abc!=0)
        {
            x=abc+","+x;
        }

        Log.d("bbb",x+" bbb");



        return  x;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


//        history_adapter=new History_Adapter();
//        LinearLayoutManager manager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
//        recyclerViewHistory.setLayoutManager(manager);
//
//        history_adapter.setData(getList());
//
//
//        recyclerViewHistory.setAdapter(history_adapter);





    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("BBB","onResume CartFragment");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("BBB","onStop CartFragment");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("BBB","onDestroy CartFragment");
    }

    //    private List<History_quan> getList()
//    {
//        quans=new ArrayList<History_quan>();
//
//        quans.add(new History_quan(123,"Cong Bui",3,"6/8/2021"));
//
//        quans.add(new History_quan(123,"Coffee House",3,"6/8/2021"));
//        quans.add(new History_quan(123,"HighLand",2,"6/8/2021"));
//
//
//        return quans;
//    }

//    private List<Product> getListProduct()
//    {
//        quans=new ArrayList<Product>();
//
//        quans.add(new Product(123,"Cong Bui",3+""));
//
//        quans.add(new Product(123,"Coffee House",3+""));
//        quans.add(new Product(123,"HighLand",2+""));
//
//
//        return quans;
//    }
}
