package java.android.quanlybanhang.CongAdapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.android.quanlybanhang.R;
import java.android.quanlybanhang.Sonclass.DonHangOnline;
import java.android.quanlybanhang.Sonclass.SanPham;
import java.android.quanlybanhang.Activity.KhachHangActivity;
import java.util.List;

public class DonHangFragment  extends Fragment {
    private RecyclerView recyDonHang;
    private TextView giaKhuyenMai;
    private  TextView tongTien;
    private Button btnDatHang;
    private KhachHangActivity mainActivity;

    private List<SanPham> sanPhams;

    public DonHangFragment(List<SanPham> sanPhams) {
        this.sanPhams = sanPhams;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.donhang_fragment,container,false);
        mainActivity=(KhachHangActivity) getActivity();

        recyDonHang=view.findViewById(R.id.recyDonHang);
        giaKhuyenMai=view.findViewById(R.id.tvKhuyenMai);
        tongTien=view.findViewById(R.id.tvTongGia);
        btnDatHang=view.findViewById(R.id.btnDatDon);

        LinearLayoutManager manager=new LinearLayoutManager(mainActivity);
        recyDonHang.setLayoutManager(manager);

        DonHangAdapter donHangAdapter=new DonHangAdapter();
        donHangAdapter.setData(sanPhams);
        recyDonHang.setAdapter(donHangAdapter);


        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mReference= FirebaseDatabase.getInstance().getReference();

                DonHangOnline donHangOnline=new DonHangOnline("idQuan","idKhachhang",20000,0,sanPhams);

//                mReference.child("DonHangOnline").child("ShipperDaNhan").child(mainActivity.getIdKhachHang()).setValue(donHangOnline);
//                mReference.child("DonHangOnline").child("ShipperDaGiao").child(mainActivity.getIdKhachHang()).setValue(donHangOnline);

            }
        });
        return view;
    }
}
