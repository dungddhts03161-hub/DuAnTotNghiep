package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity(name = "DanhMuc")
@Table(name = "DANH_MUC")
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maDM")
    private int maDM;

    @Column(name = "tenDM", nullable = false, length = 100)
    private String tenDM;

    @Column(name = "moTa", length = 200)
    private String moTa;

    @Column(name = "trangThai", nullable = false)
    private int trangThai = 1;

    public Category() {
    }

    public Category(int maDM, String tenDM, String moTa, int trangThai) {
        this.maDM = maDM;
        this.tenDM = tenDM;
        this.moTa = moTa;
        this.trangThai = trangThai;
    }

    public Category(String tenDM, String moTa) {
        this.tenDM = tenDM;
        this.moTa = moTa;
        this.trangThai = 1;
    }

    public int getMaDM() {
        return maDM;
    }

    public void setMaDM(int maDM) {
        this.maDM = maDM;
    }

    public String getTenDM() {
        return tenDM;
    }

    public void setTenDM(String tenDM) {
        this.tenDM = tenDM;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
