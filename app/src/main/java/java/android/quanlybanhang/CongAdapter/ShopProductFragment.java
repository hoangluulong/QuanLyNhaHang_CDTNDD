package java.android.quanlybanhang.CongAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.android.quanlybanhang.R;
import java.android.quanlybanhang.Sonclass.SanPham;
import java.android.quanlybanhang.Activity.KhachHangActivity;
import java.util.ArrayList;
import java.util.List;

public class ShopProductFragment extends Fragment  {
    public static final String TAG= ShopProductFragment.class.getName();

    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private FloatingActionButton fabAdd;
    private  RecyclerView recyProduct;
    private RecyclerView recyLoaiSP;
    private DatabaseReference mReference;
    private  List<SanPham> dsSanPhamTheoLoai;
    private List<String> dsLoaiSanPham;
    private boolean isExpanded=true;
    private KhachHangActivity mainActivity;
    private Context context;

    private ShopProductAdapter loaiTraiAdapter;
    private Shop_Adapter shop_adapter;
    private String Shop_Name="Highland";

    public ShopProductFragment(KhachHangActivity mainActivity, Context context) {
            this.mainActivity=mainActivity;
            this.context=context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("BBB","onCreate ShopFragment");
        View view=inflater.inflate(R.layout.shop_product,container,false);
        initUI(view);
        initToolBar();
        initRecycleView();
        initToolbarAnimation();
        onClickButtonCart();
        return view;
    }





    private  void initUI(View view)
    {
        this.dsSanPhamTheoLoai=new ArrayList<>();
        this.dsLoaiSanPham=new ArrayList<>();
        mReference=FirebaseDatabase.getInstance().getReference();
        appBarLayout=view.findViewById(R.id.AppBarLayout);

        collapsingToolbarLayout=view.findViewById(R.id.CollapsingToolbarLayout);
        toolbar=view.findViewById(R.id.toolBar);


        fabAdd=view.findViewById(R.id.FloatingActionButton);
        recyProduct=view.findViewById(R.id.recyShopproduct);
        recyLoaiSP=view.findViewById(R.id.recyLoaisanPham);
        getloaiDoUong();

    }

    private  void initToolBar()
    {
       mainActivity.setSupportActionBar(toolbar);

       if(mainActivity.getSupportActionBar()!=null)
       {
           mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

           toolbar.setNavigationOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if(mainActivity.getSupportFragmentManager()!=null)
                   {
                       mainActivity.getSupportFragmentManager().popBackStack();
                   }

//                   if(getFragmentManager()!=null)
//                   {
//                       getFragmentManager().popBackStack();
//                   }else {
//                       Toast.makeText(mainActivity,"abc xyz",Toast.LENGTH_SHORT).show();
//
//                   }

//                   HomeFragment fragment1=new HomeFragment();
//
//                   getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment1).commit();
//                   fragmentTransaction.addToBackStack("");
                   Log.d("uuu","uuu");

               }
           });
       }
    }

    private void initRecycleView()
    {
        recyProduct.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        recyProduct.setLayoutManager(linearLayoutManager);
        loaiTraiAdapter=new ShopProductAdapter();
        loaiTraiAdapter.setData(dsSanPhamTheoLoai);
        recyProduct.setAdapter(loaiTraiAdapter);

        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        recyLoaiSP.setLayoutManager(linearLayoutManager1);
         shop_adapter=new Shop_Adapter();
            shop_adapter.setData(dsLoaiSanPham);


        shop_adapter.setOnClick(new Shop_Adapter.IclickchangRecycleView() {
            @Override
            public void changListProduct(String s) {
//                dsSanPhamTheoLoai=getListproduct(s);
//                loaiTraiAdapter.notifyDataSetChanged();

                    loaiTraiAdapter.setData(getListproduct(s));
                    loaiTraiAdapter.notifyDataSetChanged();
            }

            @Override
            public void setAdapterForProductList(String s) {
                setRecycleView(s);
            }
        });
        recyLoaiSP.setAdapter(shop_adapter);
    }

    public void  setRecycleView(String s)
    {

        loaiTraiAdapter.setData( getListproduct(s));
        loaiTraiAdapter.notifyDataSetChanged();

    }



    private void initToolbarAnimation()
    {

        collapsingToolbarLayout.setTitle(Shop_Name);


        Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.background);

        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@Nullable Palette palette) {
                int myColor =palette.getVibrantColor(getResources().getColor(R.color.green));
                collapsingToolbarLayout.setContentScrimColor(myColor);
                collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.black));
            }
        });


        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.BaseOnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(Math.abs(verticalOffset)>200)
                {
                    isExpanded=false;
                }else {
                    isExpanded=true;
                }


            }
        });
    }

    private List<SanPham> getListproduct(String s)
    {
                dsSanPhamTheoLoai=new ArrayList<>();
            String id_shop=mainActivity.getShop_Id();
            List<SanPham> sanPhams=new ArrayList<>();
            mReference.child(id_shop).child("sanpham").child(s).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    SanPham sanPham=snapshot.getValue(SanPham.class);
                    sanPhams.add(sanPham);
                    dsSanPhamTheoLoai.add((sanPham));
                    loaiTraiAdapter.notifyDataSetChanged();
                    Log.d("CCC",sanPhams.size()+"abc");

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            Log.d("ccc",sanPhams.size()+"");



            return sanPhams;
    }

    private void getloaiDoUong()
    {
        String id_shop=mainActivity.getShop_Id();

        mReference.child(id_shop).child("sanpham").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String bien="";
                bien=snapshot.getKey();

                dsLoaiSanPham.add(bien);
                shop_adapter.notifyDataSetChanged();

//                bien=previousChildName.toString();

                Log.d("CCC",bien+"acb");

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


    private  void  onClickButtonCart()
    {
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Click Cart",Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d("BBB","onCreated ShopFragment");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("BBB","onResume ShopFragment");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("BBB","onStop ShopFragment");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("BBB","onDestroy ShopFragment");
    }


}
