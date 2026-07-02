package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Entity(name = "SanPham")
@Table(name = "SAN_PHAM")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maSP")
    private int maSP;

    @Column(name = "tenSP", nullable = false, length = 100)
    private String tenSP;

    @Column(name = "moTa", length = 200)
    private String moTa;

    @Column(name = "donGia", nullable = false, precision = 10, scale = 2)
    private BigDecimal donGia = BigDecimal.ZERO;

    @Column(name = "soLuongTon", nullable = false)
    private int soLuongTon;

    @Column(name = "trangThai", nullable = false)
    private int trangThai = 1;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maDM", nullable = false)
    private Category danhMuc;

    @Transient
    private String hinhAnh;

    @Transient
    private List<String> sizes;

    @Transient
    private List<String> colors;

    public Product() {
    }

    public Product(int maSP, String tenSP, String moTa, BigDecimal donGia, int soLuongTon,
                   int trangThai, Category danhMuc, String hinhAnh, List<String> sizes, List<String> colors) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.moTa = moTa;
        this.donGia = donGia;
        this.soLuongTon = soLuongTon;
        this.trangThai = trangThai;
        this.danhMuc = danhMuc;
        this.hinhAnh = hinhAnh;
        this.sizes = sizes;
        this.colors = colors;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public BigDecimal getDonGia() {
        return donGia == null ? BigDecimal.ZERO : donGia;
    }

    public void setDonGia(BigDecimal donGia) {
        this.donGia = donGia;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public Category getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(Category danhMuc) {
        this.danhMuc = danhMuc;
    }

    public String getHinhAnh() {
        if (hinhAnh == null || hinhAnh.isBlank()) {
            int index = maSP <= 0 ? 1 : ((maSP - 1) % 8) + 1;
            return "assets/product-" + index + ".svg";
        }
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public List<String> getSizes() {
        if (sizes == null || sizes.isEmpty()) {
            return Arrays.asList("S", "M", "L", "XL");
        }
        return sizes;
    }

    public void setSizes(List<String> sizes) {
        this.sizes = sizes;
    }

    public List<String> getColors() {
        if (colors == null || colors.isEmpty()) {
            return Arrays.asList("Đen", "Trắng", "Be");
        }
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public boolean isAvailable() {
        return trangThai == 1 && soLuongTon > 0;
    }
}
