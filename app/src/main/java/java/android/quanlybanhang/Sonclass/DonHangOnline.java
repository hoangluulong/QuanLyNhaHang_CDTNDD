package java.android.quanlybanhang.Sonclass;

import java.util.List;

public class DonHangOnline {

    private  String idQuan;
    private String idKhachhang;
    private String idShipper;
    private long giaKhuyenMai;
    private int trangthai;

    private List<SanPham> dsChon;



    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }




    public long getGiaKhuyenMai() {
        return giaKhuyenMai;
    }

    public void setGiaKhuyenMai(long giaKhuyenMai) {
        this.giaKhuyenMai = giaKhuyenMai;
    }



    public DonHangOnline() {
    }

    public DonHangOnline(String idQuan, String idKhachhang, String idShipper,long giakhuyenmai,List<SanPham>dsChon,int trangthai) {
        this.idQuan = idQuan;
        this.idKhachhang = idKhachhang;
        this.idShipper = idShipper;
        this.giaKhuyenMai=giakhuyenmai;
        this.dsChon=dsChon;
        this.trangthai=trangthai;
    }

    public DonHangOnline(String idQuan, String idKhachhang, long giaKhuyenMai, int trangthai, List<SanPham> dsChon) {
        this.idQuan = idQuan;
        this.idKhachhang = idKhachhang;
        this.giaKhuyenMai = giaKhuyenMai;
        this.trangthai = trangthai;
        this.dsChon = dsChon;
    }

    public String getIdQuan() {
        return idQuan;
    }

    public void setIdQuan(String idQuan) {
        this.idQuan = idQuan;
    }

    public String getIdKhachhang() {
        return idKhachhang;
    }

    public void setIdKhachhang(String idKhachhang) {
        this.idKhachhang = idKhachhang;
    }

    public String getIdShipper() {
        return idShipper;
    }

    public void setIdShipper(String idShipper) {
        this.idShipper = idShipper;
    }

    public List<SanPham> getDsChon() {
        return dsChon;
    }

    public void setDsChon(List<SanPham> dsChon) {
        this.dsChon = dsChon;
    }



    //tinh tien cho don hang

    private long tinhTongDonHang()
    {
        if (dsChon.size()==0)
        {
            return 0;
        }
        long tongtien=0;

        for (int i = 0; i < dsChon.size(); i++) {

            tongtien+= dsChon.get(i).getGiaBan()*dsChon.get(i).getSoluong();

        }

        tongtien=tongtien-giaKhuyenMai;



        return tongtien;

    }
}
