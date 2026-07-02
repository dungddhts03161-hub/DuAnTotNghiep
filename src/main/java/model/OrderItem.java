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

@Entity(name = "ChiTietDonHang")
@Table(name = "CHI_TIET_DON_HANG")
public class OrderItem implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maCTDH")
    private int maCTDH;

    @Column(name = "soLuong", nullable = false)
    private int soLuong;

    @Column(name = "donGia", nullable = false, precision = 10, scale = 2)
    private BigDecimal donGia = BigDecimal.ZERO;

    @Column(name = "maDH", nullable = false)
    private int maDH;

    @Column(name = "maSP", nullable = false)
    private int maSP;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maDH", insertable = false, updatable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maSP", insertable = false, updatable = false)
    private Product product;

    public OrderItem() {
    }

    public OrderItem(int maCTDH, Product product, int soLuong, BigDecimal donGia) {
        this.maCTDH = maCTDH;
        this.product = product;
        this.maSP = product == null ? 0 : product.getMaSP();
        this.soLuong = soLuong;
        this.donGia = donGia == null ? BigDecimal.ZERO : donGia;
    }

    public int getMaCTDH() {
        return maCTDH;
    }

    public void setMaCTDH(int maCTDH) {
        this.maCTDH = maCTDH;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        this.maSP = product == null ? 0 : product.getMaSP();
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
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

    public int getMaDH() {
        return maDH;
    }

    public void setMaDH(int maDH) {
        this.maDH = maDH;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
        this.maDH = order == null ? 0 : order.getMaDH();
    }

    @Transient
    public BigDecimal getThanhTien() {
        return getDonGia().multiply(BigDecimal.valueOf(soLuong));
    }
}
