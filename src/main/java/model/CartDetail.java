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

@Entity(name = "ChiTietGioHang")
@Table(name = "CHI_TIET_GIO_HANG")
public class CartDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maCTGH")
    private int maCTGH;

    @Column(name = "soLuong", nullable = false)
    private int soLuong;

    @Column(name = "donGia", nullable = false, precision = 10, scale = 2)
    private BigDecimal donGia = BigDecimal.ZERO;

    @Column(name = "giamGia", nullable = false, precision = 10, scale = 2)
    private BigDecimal giamGia = BigDecimal.ZERO;

    @Column(name = "maGH", nullable = false)
    private int maGH;

    @Column(name = "maSP", nullable = false)
    private int maSP;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maSP", insertable = false, updatable = false)
    private Product product;

    public CartDetail() {
    }

    public CartDetail(int maCTGH, int soLuong, BigDecimal donGia, BigDecimal giamGia,
                      BigDecimal thanhTien, int maGH, int maSP, Product product) {
        this.maCTGH = maCTGH;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.giamGia = giamGia;
        this.maGH = maGH;
        this.maSP = maSP;
        this.product = product;
    }

    public CartDetail(int soLuong, BigDecimal donGia, BigDecimal giamGia, int maGH, int maSP) {
        this(0, soLuong, donGia, giamGia, null, maGH, maSP, null);
    }

    public int getMaCTGH() {
        return maCTGH;
    }

    public void setMaCTGH(int maCTGH) {
        this.maCTGH = maCTGH;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getDonGia() {
        return donGia == null ? BigDecimal.ZERO : donGia;
    }

    public void setDonGia(BigDecimal donGia) {
        this.donGia = donGia;
    }

    public BigDecimal getGiamGia() {
        return giamGia == null ? BigDecimal.ZERO : giamGia;
    }

    public void setGiamGia(BigDecimal giamGia) {
        this.giamGia = giamGia;
    }

    @Transient
    public BigDecimal getThanhTien() {
        return getDonGia().multiply(BigDecimal.valueOf(soLuong)).subtract(getGiamGia());
    }

    public int getMaGH() {
        return maGH;
    }

    public void setMaGH(int maGH) {
        this.maGH = maGH;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        this.maSP = product == null ? 0 : product.getMaSP();
    }
}
