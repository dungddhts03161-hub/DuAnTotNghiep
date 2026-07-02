package model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity(name = "DonHang")
@Table(name = "DON_HANG")
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maDH")
    private int maDH;

    @Column(name = "ngayDat", nullable = false)
    private LocalDateTime ngayDat;

    @Column(name = "tongTien", nullable = false, precision = 10, scale = 2)
    private BigDecimal tongTien = BigDecimal.ZERO;

    @Column(name = "trangThai", nullable = false, length = 20)
    private String trangThai = "CHO_XAC_NHAN";

    @Column(name = "phuongThucThanhToan", nullable = false, length = 20)
    private String phuongThucThanhToan = "COD";

    @Column(name = "maTK", nullable = false)
    private int maTK;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Transient
    private String hoTenNhan;

    @Transient
    private String soDienThoaiNhan;

    @Transient
    private String diaChiNhan;

    @Transient
    private String ghiChu;

    @Transient
    private List<OrderItem> items;

    @Transient
    private Payment payment;

    public Order() {
    }

    public Order(int maDH, LocalDateTime ngayDat, BigDecimal tongTien, String trangThai,
                 String phuongThucThanhToan, int maTK, String hoTenNhan, String soDienThoaiNhan,
                 String diaChiNhan, String ghiChu, List<OrderItem> items, Payment payment) {
        this.maDH = maDH;
        this.ngayDat = ngayDat;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
        this.phuongThucThanhToan = phuongThucThanhToan;
        this.maTK = maTK;
        this.hoTenNhan = hoTenNhan;
        this.soDienThoaiNhan = soDienThoaiNhan;
        this.diaChiNhan = diaChiNhan;
        this.ghiChu = ghiChu;
        this.items = items == null ? new ArrayList<>() : new ArrayList<>(items);
        this.payment = payment;
    }

    public int getMaDH() {
        return maDH;
    }

    public void setMaDH(int maDH) {
        this.maDH = maDH;
    }

    public LocalDateTime getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(LocalDateTime ngayDat) {
        this.ngayDat = ngayDat;
    }

    public String getNgayDatText() {
        return ngayDat == null ? "" : ngayDat.format(FORMATTER);
    }

    public BigDecimal getTongTien() {
        return tongTien == null ? BigDecimal.ZERO : tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(String phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    public int getMaTK() {
        return maTK;
    }

    public void setMaTK(int maTK) {
        this.maTK = maTK;
    }

    public String getHoTenNhan() {
        return hoTenNhan;
    }

    public void setHoTenNhan(String hoTenNhan) {
        this.hoTenNhan = hoTenNhan;
    }

    public String getSoDienThoaiNhan() {
        return soDienThoaiNhan;
    }

    public void setSoDienThoaiNhan(String soDienThoaiNhan) {
        this.soDienThoaiNhan = soDienThoaiNhan;
    }

    public String getDiaChiNhan() {
        return diaChiNhan;
    }

    public void setDiaChiNhan(String diaChiNhan) {
        this.diaChiNhan = diaChiNhan;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<OrderItem> getItems() {
        if (items != null) {
            return Collections.unmodifiableList(items);
        }
        if (orderItems == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(orderItems);
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
