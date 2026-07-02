CREATE DATABASE QL_CuaHangQuanAoOnline;
GO

USE QL_CuaHangQuanAoOnline;
GO

-- =========================
-- 1. BẢNG TÀI KHOẢN
-- =========================
CREATE TABLE TAI_KHOAN (
    maTK INT IDENTITY(1,1) PRIMARY KEY,
    hoTen NVARCHAR(50) NOT NULL,
    email VARCHAR(200) NOT NULL UNIQUE,
    matKhau VARCHAR(255) NOT NULL,
    soDienThoai VARCHAR(10),
    vaiTro VARCHAR(20) NOT NULL DEFAULT 'KHACH_HANG',
    trangThai TINYINT NOT NULL DEFAULT 1,

    CONSTRAINT CK_TAI_KHOAN_VaiTro 
        CHECK (vaiTro IN ('ADMIN', 'NHAN_VIEN', 'KHACH_HANG')),

    CONSTRAINT CK_TAI_KHOAN_TrangThai 
        CHECK (trangThai IN (0, 1))
);
GO

-- =========================
-- 2. BẢNG DANH MỤC
-- =========================
CREATE TABLE DANH_MUC (
    maDM INT IDENTITY(1,1) PRIMARY KEY,
    tenDM NVARCHAR(100) NOT NULL,
    moTa NVARCHAR(200),
    trangThai TINYINT NOT NULL DEFAULT 1,

    CONSTRAINT CK_DANH_MUC_TrangThai 
        CHECK (trangThai IN (0, 1))
);
GO

-- =========================
-- 3. BẢNG SẢN PHẨM
-- =========================
CREATE TABLE SAN_PHAM (
    maSP INT IDENTITY(1,1) PRIMARY KEY,
    tenSP NVARCHAR(100) NOT NULL,
    moTa NVARCHAR(200),
    donGia DECIMAL(10,2) NOT NULL,
    soLuongTon INT NOT NULL DEFAULT 0,
    trangThai TINYINT NOT NULL DEFAULT 1,
    maDM INT NOT NULL,

    CONSTRAINT FK_SAN_PHAM_DANH_MUC 
        FOREIGN KEY (maDM) REFERENCES DANH_MUC(maDM),

    CONSTRAINT CK_SAN_PHAM_DonGia 
        CHECK (donGia >= 0),

    CONSTRAINT CK_SAN_PHAM_SoLuongTon 
        CHECK (soLuongTon >= 0),

    CONSTRAINT CK_SAN_PHAM_TrangThai 
        CHECK (trangThai IN (0, 1))
);
GO

-- =========================
-- 4. BẢNG GIỎ HÀNG
-- Mỗi tài khoản có một giỏ hàng
-- =========================
CREATE TABLE GIO_HANG (
    maGH INT IDENTITY(1,1) PRIMARY KEY,
    maTK INT NOT NULL UNIQUE,
    ngayTao DATETIME NOT NULL DEFAULT GETDATE(),
    trangThai TINYINT NOT NULL DEFAULT 1,

    CONSTRAINT FK_GIO_HANG_TAI_KHOAN 
        FOREIGN KEY (maTK) REFERENCES TAI_KHOAN(maTK),

    CONSTRAINT CK_GIO_HANG_TrangThai 
        CHECK (trangThai IN (0, 1))
);
GO

-- =========================
-- 5. BẢNG CHI TIẾT GIỎ HÀNG
-- =========================
CREATE TABLE CHI_TIET_GIO_HANG (
    maCTGH INT IDENTITY(1,1) PRIMARY KEY,
    soLuong INT NOT NULL,
    donGia DECIMAL(10,2) NOT NULL,
    giamGia DECIMAL(10,2) NOT NULL DEFAULT 0,
    thanhTien AS CAST((soLuong * donGia - giamGia) AS DECIMAL(10,2)) PERSISTED,
    maGH INT NOT NULL,
    maSP INT NOT NULL,

    CONSTRAINT FK_CTGH_GIO_HANG 
        FOREIGN KEY (maGH) REFERENCES GIO_HANG(maGH)
        ON DELETE CASCADE,

    CONSTRAINT FK_CTGH_SAN_PHAM 
        FOREIGN KEY (maSP) REFERENCES SAN_PHAM(maSP),

    CONSTRAINT UQ_CTGH_GioHang_SanPham 
        UNIQUE (maGH, maSP),

    CONSTRAINT CK_CTGH_SoLuong 
        CHECK (soLuong > 0),

    CONSTRAINT CK_CTGH_DonGia 
        CHECK (donGia >= 0),

    CONSTRAINT CK_CTGH_GiamGia 
        CHECK (giamGia >= 0),

    CONSTRAINT CK_CTGH_ThanhTien 
        CHECK ((soLuong * donGia - giamGia) >= 0)
);
GO

-- =========================
-- 6. BẢNG ĐƠN HÀNG
-- =========================
CREATE TABLE DON_HANG (
    maDH INT IDENTITY(1,1) PRIMARY KEY,
    ngayDat DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
    tongTien DECIMAL(10,2) NOT NULL DEFAULT 0,
    trangThai VARCHAR(20) NOT NULL DEFAULT 'CHO_XAC_NHAN',
    phuongThucThanhToan VARCHAR(20) NOT NULL DEFAULT 'COD',
    maTK INT NOT NULL,

    CONSTRAINT FK_DON_HANG_TAI_KHOAN 
        FOREIGN KEY (maTK) REFERENCES TAI_KHOAN(maTK),

    CONSTRAINT CK_DON_HANG_TongTien 
        CHECK (tongTien >= 0),

    CONSTRAINT CK_DON_HANG_TrangThai 
        CHECK (trangThai IN (
            'CHO_XAC_NHAN',
            'DANG_XU_LY',
            'DANG_GIAO',
            'DA_GIAO',
            'DA_HUY'
        )),

    CONSTRAINT CK_DON_HANG_PhuongThuc 
        CHECK (phuongThucThanhToan IN (
            'COD',
            'CHUYEN_KHOAN',
            'THE',
            'VI_DIEN_TU'
        ))
);
GO

-- =========================
-- 7. BẢNG CHI TIẾT ĐƠN HÀNG
-- =========================
CREATE TABLE CHI_TIET_DON_HANG (
    maCTDH INT IDENTITY(1,1) PRIMARY KEY,
    soLuong INT NOT NULL,
    donGia DECIMAL(10,2) NOT NULL,
    thanhTien AS CAST((soLuong * donGia) AS DECIMAL(10,2)) PERSISTED,
    maDH INT NOT NULL,
    maSP INT NOT NULL,

    CONSTRAINT FK_CTDH_DON_HANG 
        FOREIGN KEY (maDH) REFERENCES DON_HANG(maDH)
        ON DELETE CASCADE,

    CONSTRAINT FK_CTDH_SAN_PHAM 
        FOREIGN KEY (maSP) REFERENCES SAN_PHAM(maSP),

    CONSTRAINT UQ_CTDH_DonHang_SanPham 
        UNIQUE (maDH, maSP),

    CONSTRAINT CK_CTDH_SoLuong 
        CHECK (soLuong > 0),

    CONSTRAINT CK_CTDH_DonGia 
        CHECK (donGia >= 0)
);
GO

-- =========================
-- 8. BẢNG THANH TOÁN
-- Theo sơ đồ tổng thể của bạn
-- =========================
CREATE TABLE THANH_TOAN (
    maTT INT IDENTITY(1,1) PRIMARY KEY,
    maDH INT NOT NULL UNIQUE,
    phuongThuc VARCHAR(20) NOT NULL,
    soTien DECIMAL(10,2) NOT NULL,
    ngayThanhToan DATETIME NULL,
    trangThai VARCHAR(20) NOT NULL DEFAULT 'CHUA_THANH_TOAN',

    CONSTRAINT FK_THANH_TOAN_DON_HANG 
        FOREIGN KEY (maDH) REFERENCES DON_HANG(maDH)
        ON DELETE CASCADE,

    CONSTRAINT CK_THANH_TOAN_SoTien 
        CHECK (soTien >= 0),

    CONSTRAINT CK_THANH_TOAN_PhuongThuc 
        CHECK (phuongThuc IN (
            'COD',
            'CHUYEN_KHOAN',
            'THE',
            'VI_DIEN_TU'
        )),

    CONSTRAINT CK_THANH_TOAN_TrangThai 
        CHECK (trangThai IN (
            'CHUA_THANH_TOAN',
            'DA_THANH_TOAN',
            'THAT_BAI',
            'HOAN_TIEN'
        ))
);
GO

-- =========================
-- 9. DỮ LIỆU MẪU ĐỂ TEST JPA
-- =========================
INSERT INTO TAI_KHOAN (hoTen, email, matKhau, soDienThoai, vaiTro, trangThai)
VALUES (N'Celine Demo', 'demo@celinecloset.vn', '123456', '0901234567', 'KHACH_HANG', 1);
GO

INSERT INTO DANH_MUC (tenDM, moTa, trangThai)
VALUES
(N'Đầm', N'Đầm công sở và đầm dạo phố', 1),
(N'Áo', N'Áo sơ mi, áo kiểu, áo thun', 1),
(N'Chân váy', N'Chân váy midi, chữ A, xếp ly', 1),
(N'Set đồ', N'Set phối sẵn theo phong cách boutique', 1);
GO

INSERT INTO SAN_PHAM (tenSP, moTa, donGia, soLuongTon, trangThai, maDM)
VALUES
(N'Đầm linen cổ vuông Celine', N'Chất linen pha cotton, dáng xòe nhẹ, hợp đi làm và đi cà phê.', 620000, 18, 1, 1),
(N'Đầm midi tay phồng Elise', N'Thiết kế nữ tính, có lót trong, phần eo ôm vừa phải.', 690000, 12, 1, 1),
(N'Áo sơ mi lụa mềm Luna', N'Bề mặt vải rũ nhẹ, dễ phối cùng quần tây hoặc chân váy.', 390000, 30, 1, 2),
(N'Áo kiểu cổ nơ Paris', N'Dáng áo nhẹ nhàng, điểm nhấn nơ cổ, phù hợp phong cách thanh lịch.', 420000, 20, 1, 2),
(N'Chân váy midi Grace', N'Form chữ A, chất vải đứng dáng, dễ mặc trong nhiều hoàn cảnh.', 450000, 15, 1, 3),
(N'Chân váy xếp ly Muse', N'Thiết kế xếp ly mềm, chiều dài qua gối, tạo vẻ dịu dàng.', 470000, 11, 1, 3),
(N'Set áo vest và váy Ivy', N'Set phối sẵn gồm áo khoác ngắn và chân váy, hợp dự tiệc nhẹ.', 890000, 8, 1, 4),
(N'Set dạo phố Minimal', N'Áo tay ngắn phối chân váy đơn giản, dễ mặc hằng ngày.', 760000, 16, 1, 4);
GO
