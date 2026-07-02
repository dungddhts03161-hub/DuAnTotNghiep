package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity(name = "TaiKhoan")
@Table(name = "TAI_KHOAN")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maTK")
    private int maTK;

    @Column(name = "hoTen", nullable = false, length = 50)
    private String fullName;

    @Column(name = "soDienThoai", length = 10)
    private String phone;

    @Column(name = "email", nullable = false, unique = true, length = 200)
    private String email;

    @Column(name = "matKhau", nullable = false, length = 255)
    private String password;

    @Column(name = "vaiTro", nullable = false, length = 20)
    private String vaiTro = "KHACH_HANG";

    @Column(name = "trangThai", nullable = false)
    private int trangThai = 1;

    public User() {
    }

    public User(String fullName, String phone, String email, String password) {
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.vaiTro = "KHACH_HANG";
        this.trangThai = 1;
    }

    public User(int maTK, String fullName, String phone, String email, String password, String vaiTro, int trangThai) {
        this.maTK = maTK;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.vaiTro = vaiTro;
        this.trangThai = trangThai;
    }

    public int getMaTK() {
        return maTK;
    }

    public void setMaTK(int maTK) {
        this.maTK = maTK;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getHoTen() {
        return fullName;
    }

    public void setHoTen(String hoTen) {
        this.fullName = hoTen;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSoDienThoai() {
        return phone;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.phone = soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatKhau() {
        return password;
    }

    public void setMatKhau(String matKhau) {
        this.password = matKhau;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public boolean checkPassword(String rawPassword) {
        return password != null && password.equals(rawPassword);
    }
}
