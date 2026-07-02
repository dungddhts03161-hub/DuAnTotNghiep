package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(name = "GioHang")
@Table(name = "GIO_HANG")
public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maGH")
    private int maGH;

    @Column(name = "maTK", nullable = false, unique = true)
    private int maTK;

    @Column(name = "ngayTao", nullable = false)
    private LocalDateTime ngayTao = LocalDateTime.now();

    @Column(name = "trangThai", nullable = false)
    private int trangThai = 1;

    public Cart() {
    }

    public Cart(int maGH, int maTK, LocalDateTime ngayTao, int trangThai) {
        this.maGH = maGH;
        this.maTK = maTK;
        this.ngayTao = ngayTao;
        this.trangThai = trangThai;
    }

    public Cart(int maTK) {
        this(0, maTK, LocalDateTime.now(), 1);
    }

    public int getMaGH() {
        return maGH;
    }

    public void setMaGH(int maGH) {
        this.maGH = maGH;
    }

    public int getMaTK() {
        return maTK;
    }

    public void setMaTK(int maTK) {
        this.maTK = maTK;
    }

    public LocalDateTime getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDateTime ngayTao) {
        this.ngayTao = ngayTao;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
