package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "ThanhToan")
@Table(name = "THANH_TOAN")
public class Payment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maTT")
    private int maTT;

    @Column(name = "phuongThuc", nullable = false, length = 20)
    private String phuongThuc;

    @Column(name = "soTien", nullable = false, precision = 10, scale = 2)
    private BigDecimal soTien = BigDecimal.ZERO;

    @Column(name = "ngayThanhToan")
    private LocalDateTime ngayThanhToan;

    @Column(name = "trangThai", nullable = false, length = 20)
    private String trangThai = "CHUA_THANH_TOAN";

    @Column(name = "maDH", nullable = false, unique = true)
    private int maDH;

    public Payment() {
    }

    public Payment(int maTT, String phuongThuc, BigDecimal soTien,
                   LocalDateTime ngayThanhToan, String trangThai, int maDH) {
        this.maTT = maTT;
        this.phuongThuc = phuongThuc;
        this.soTien = soTien;
        this.ngayThanhToan = ngayThanhToan;
        this.trangThai = trangThai;
        this.maDH = maDH;
    }

    public int getMaTT() {
        return maTT;
    }

    public void setMaTT(int maTT) {
        this.maTT = maTT;
    }

    public String getPhuongThuc() {
        return phuongThuc;
    }

    public void setPhuongThuc(String phuongThuc) {
        this.phuongThuc = phuongThuc;
    }

    public BigDecimal getSoTien() {
        return soTien == null ? BigDecimal.ZERO : soTien;
    }

    public void setSoTien(BigDecimal soTien) {
        this.soTien = soTien;
    }

    public LocalDateTime getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(LocalDateTime ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public int getMaDH() {
        return maDH;
    }

    public void setMaDH(int maDH) {
        this.maDH = maDH;
    }
}
