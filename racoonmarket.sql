-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th3 06, 2023 lúc 04:19 PM
-- Phiên bản máy phục vụ: 10.4.24-MariaDB
-- Phiên bản PHP: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `racoonmarket`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `account`
--

CREATE TABLE `account` (
  `Username` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `IDNhanVien` varchar(10) NOT NULL,
  `TrangThai` smallint(1) NOT NULL DEFAULT 1,
  `BanHang` smallint(1) NOT NULL DEFAULT 1,
  `NhapHang` smallint(1) NOT NULL DEFAULT 1,
  `TaiKhoan` smallint(1) NOT NULL DEFAULT 1,
  `HangHoa` smallint(1) NOT NULL DEFAULT 1,
  `NhanVien` smallint(1) NOT NULL DEFAULT 1,
  `KhachHang` smallint(1) NOT NULL DEFAULT 1,
  `NhaCungCap` smallint(1) NOT NULL DEFAULT 1,
  `BaoCao` smallint(1) NOT NULL DEFAULT 1,
  `PhieuChi` smallint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `account`
--

INSERT INTO `account` (`Username`, `Password`, `IDNhanVien`, `TrangThai`, `BanHang`, `NhapHang`, `TaiKhoan`, `HangHoa`, `NhanVien`, `KhachHang`, `NhaCungCap`, `BaoCao`, `PhieuChi`) VALUES
('admin', '123', 'admin', 1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
('Khai01', '01', 'NV09', 1, 1, 1, 0, 1, 0, 1, 1, 1, 1),
('Venus', '123', 'NV06', 1, 0, 0, 0, 0, 0, 0, 0, 0, 1),
('Venus19032001', 'Venus19032001@', 'NV06', 1, 1, 1, 0, 1, 0, 1, 1, 1, 1),
('Venus19302001', '123', 'NV09', 0, 0, 0, 0, 1, 0, 0, 0, 1, 0),
('Venuss', '123', 'admin', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitiethoadon`
--

CREATE TABLE `chitiethoadon` (
  `idhoadon` varchar(10) DEFAULT NULL,
  `idsanpham` varchar(10) DEFAULT NULL,
  `soluong` int(11) DEFAULT NULL,
  `giaBan` decimal(10,0) NOT NULL DEFAULT 0,
  `trangThai` smallint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `chitiethoadon`
--

INSERT INTO `chitiethoadon` (`idhoadon`, `idsanpham`, `soluong`, `giaBan`, `trangThai`) VALUES
('HD07129220', 'SP05', 1, '45000000', 1),
('HD07129220', 'SP06', 1, '50000000', 1),
('HD07129220', 'SP07', 2, '120000000', 1),
('HD09123934', 'SP05', 2, '45000000', 1),
('HD09123934', 'SP06', 2, '50000000', 1),
('HD09123934', 'SP07', 2, '120000000', 1),
('HD09124249', 'SP05', 1, '45000000', 1),
('HD09124249', 'SP06', 1, '50000000', 1),
('HD09125085', 'SP05', 21, '45000000', 1),
('HD09125085', 'SP2022287', 1, '10000', 1),
('HD12120530', 'SP06', 2, '50000000', 1),
('HD12120530', 'SP07', 4, '120000000', 1),
('HD12120530', 'SP2022287', 5, '10000', 1),
('HD12127886', 'SP06', 1, '50000000', 1),
('HD12127886', 'SP07', 1, '120000000', 1),
('HD12126013', 'SP05', 1, '45000000', 1),
('HD12126013', 'SP06', 1, '50000000', 1),
('HD12126013', 'SP07', 1, '120000000', 1),
('HD12126013', 'SP2022287', 1, '10000', 1),
('HD13127212', 'SP06', 1, '50000000', 1),
('HD13127212', 'SP07', 1, '120000000', 1),
('HD13127212', 'SP2022287', 1, '10000', 1),
('HD13122660', 'SP05', 2, '45000000', 1),
('HD13122660', 'SP06', 2, '50000000', 1),
('HD15127082', 'SP05', 1, '45000000', 1),
('HD15127082', 'SP06', 1, '50000000', 1),
('HD15127082', 'SP07', 1, '120000000', 1),
('HD15127082', 'SP2022287', 1, '10000', 1),
('HD15122925', 'SP07', 1, '120000000', 1),
('HD15122925', 'SP2022287', 1, '10000', 1),
('HD15127938', 'SP05', 1, '45000000', 1),
('HD15127938', 'SP07', 1, '120000000', 1),
('HD15127938', 'SP06', 1, '50000000', 1),
('HD15122960', 'SP05', 2, '45000000', 1),
('HD15122960', 'SP06', 3, '50000000', 1),
('HD15122960', 'SP07', 2, '120000000', 1),
('HD15124859', 'SP06', 1, '50000000', 1),
('HD15124859', 'SP07', 1, '120000000', 1),
('TH15129744', 'SP05', 3, '9000000', 4),
('TH15129744', 'SP06', 3, '9000000', 4),
('HD15126443', 'SP05', 2, '45000000', 1),
('HD15126443', 'SP06', 3, '50000000', 1),
('HD15126443', 'SP07', 2, '120000000', 1),
('HD15126284', 'SP05', 2, '45000000', 1),
('HD15126284', 'SP06', 3, '50000000', 1),
('HD15126284', 'SP07', 2, '120000000', 1),
('HD15126381', 'SP05', 1, '45000000', 1),
('HD17121365', 'SP05', 3, '45000000', 1),
('HD17121365', 'SP06', 3, '50000000', 1),
('HD17121365', 'SP07', 2, '120000000', 1),
('HD17121606', 'SP05', 1, '45000000', 1),
('HD17121606', 'SP06', 1, '50000000', 1),
('HD17121606', 'SP07', 1, '120000000', 1),
('HD17123280', 'SP06', 3, '50000000', 1),
('HD17123280', 'SP07', 3, '120000000', 1),
('HD17123280', 'SP05', 1, '45000000', 1),
('HD17124153', 'SP06', 1, '50000000', 1),
('HD17127750', 'SP06', 3, '50000000', 1),
('HD17127750', 'SP07', 2, '120000000', 1),
('HD17120762', 'SP06', 2, '50000000', 1),
('HD17120762', 'SP07', 1, '120000000', 1),
('HD17120762', 'SP05', 1, '45000000', 1),
('HD17120638', 'SP06', 2, '50000000', 1),
('HD17120638', 'SP07', 1, '120000000', 1),
('HD17128834', 'SP06', 4, '50000000', 1),
('HD17128834', 'SP07', 1, '120000000', 1),
('HD17120063', 'SP06', 5, '50000000', 1),
('HD17120063', 'SP07', 7, '120000000', 1),
('HD17120063', 'SP05', 3, '45000000', 1),
('HD17127046', 'SP05', 12, '45000000', 1),
('HD17127046', 'SP06', 14, '50000000', 1),
('HD17127046', 'SP07', 6, '120000000', 1),
('HD17129335', 'SP05', 1, '45000000', 1),
('HD17129335', 'SP07', 2, '120000000', 1),
('HD17129335', 'SP06', 1, '50000000', 1),
('HD17125911', 'SP07', 3, '120000000', 1),
('HD17125911', 'SP06', 3, '50000000', 1),
('HD17125911', 'SP05', 1, '45000000', 1),
('HD17122763', 'SP05', 5, '45000000', 1),
('HD17122763', 'SP06', 6, '50000000', 1),
('HD17122763', 'SP07', 2, '120000000', 1),
('HD17126901', 'SP06', 3, '50000000', 1),
('HD17126901', 'SP07', 2, '120000000', 1),
('HD17126901', 'SP05', 1, '45000000', 1),
('HD19129518', 'SP07', 1, '120000000', 1),
('HD19129518', 'SP05', 1, '45000000', 1),
('HD19129518', 'SP06', 1, '50000000', 1),
('HD19129200', 'SP06', 4, '50000000', 1),
('HD19129200', 'SP07', 2, '120000000', 1),
('HD19129200', 'SP05', 2, '45000000', 1),
('HD19128475', 'SP05', 2, '45000000', 1),
('HD19128475', 'SP06', 2, '50000000', 1),
('HD19128475', 'SP07', 1, '120000000', 1),
('HD19123886', 'SP06', 2, '50000000', 1),
('HD19123886', 'SP07', 2, '120000000', 1),
('HD19123886', 'SP2022287', 1, '10000', 1),
('HD19121926', 'SP06', 2, '50000000', 1),
('HD19121926', 'SP05', 1, '45000000', 1),
('HD19121926', 'SP07', 1, '120000000', 1),
('HD19121926', 'SP2022287', 1, '10000', 1),
('HD19122559', 'SP06', 1, '50000000', 1),
('HD19122559', 'SP05', 1, '45000000', 1),
('HD19122559', 'SP07', 1, '120000000', 1),
('HD19129382', 'SP07', 3, '120000000', 1),
('HD19129382', 'SP06', 3, '50000000', 1),
('HD19129382', 'SP05', 1, '45000000', 1),
('HD19129382', 'SP2022287', 1, '10000', 1),
('HD19126918', 'SP05', 2, '45000000', 1),
('HD19126918', 'SP06', 3, '50000000', 1),
('HD19126918', 'SP07', 2, '120000000', 1),
('HD19125613', 'SP05', 12, '45000000', 1),
('HD19127503', 'SP05', 1, '45000000', 1),
('HD19127503', 'SP06', 5, '50000000', 1),
('HD19127503', 'SP2022287', 1, '10000', 1),
('HD19127503', 'SP07', 2, '120000000', 1),
('HD19120766', 'SP06', 2, '50000000', 1),
('HD19120766', 'SP05', 1, '45000000', 1),
('HD19120766', 'SP07', 2, '120000000', 1),
('HD19120766', 'SP2022287', 1, '10000', 1),
('HD21120804', 'SP06', 1, '50000000', 1),
('HD21120804', 'SP07', 1, '120000000', 1),
('HD21120804', 'SP05', 1, '45000000', 1),
('HD21124021', 'SP05', 2, '45000000', 1),
('HD21124021', 'SP06', 2, '50000000', 1),
('HD21124021', 'SP07', 2, '120000000', 1),
('HD21124021', 'SP2022287', 2, '10000', 1),
('HD21120747', 'SP05', 1, '45000000', 1),
('HD21120747', 'SP06', 1, '50000000', 1),
('HD21120747', 'SP07', 1, '120000000', 1),
('HD21120747', 'SP2022287', 1, '10000', 1),
('HD21120106', 'SP06', 4, '50000000', 1),
('HD21120106', 'SP05', 1, '45000000', 1),
('HD21120106', 'SP07', 3, '120000000', 1),
('HD21120106', 'SP2022287', 2, '10000', 1),
('HD25124254', 'SP06', 3, '50000000', 1),
('HD25124254', 'SP07', 2, '120000000', 1),
('HD25124254', 'SP05', 1, '45000000', 1),
('HD25125907', 'SP06', 2, '50000000', 1),
('HD25125907', 'SP07', 2, '120000000', 1),
('HD25125907', 'SP2022287', 1, '10000', 1),
('HD25125907', 'SP05', 1, '45000000', 1),
('HD25126539', 'SP06', 1, '50000000', 1),
('HD25126539', 'SP07', 1, '120000000', 1),
('HD25126539', 'SP2022287', 1, '10000', 1),
('HD25121000', 'SP05', 1, '45000000', 1),
('HD25121000', 'SP06', 1, '50000000', 1),
('HD25121000', 'SP07', 1, '120000000', 1),
('HD25121000', 'SP2022287', 1, '10000', 1),
('HD25129098', 'SP06', 4, '50000000', 1),
('HD25129098', 'SP07', 4, '120000000', 1),
('NH25120225', 'SP06', 5, '9000000', 3),
('NH25120225', 'SP05', 2, '9000000', 3),
('NH25120225', 'SP07', 3, '80000000', 3),
('HD25122382', 'SP06', 1, '50000000', 1),
('HD25122382', 'SP07', 1, '120000000', 1),
('HD25122382', 'SP2022287', 1, '10000', 1),
('HD25129268', 'SP05', 1, '45000000', 1),
('HD25129268', 'SP06', 1, '50000000', 1),
('HD25129268', 'SP07', 1, '120000000', 1),
('HD25129268', 'SP2022287', 1, '10000', 1),
('HD25124502', 'SP06', 3, '50000000', 1),
('HD25124502', 'SP07', 3, '120000000', 1),
('HD25124502', 'SP2022287', 2, '10000', 1),
('HD25124502', 'SP05', 1, '45000000', 1),
('HD25129632', 'SP05', 1, '45000000', 1),
('HD25129632', 'SP06', 1, '50000000', 1),
('HD25129632', 'SP07', 1, '120000000', 1),
('HD25129632', 'SP2022287', 1, '10000', 1),
('HD27127479', 'SP05', 1, '45000000', 1),
('HD27127479', 'SP06', 1, '50000000', 1),
('HD27127479', 'SP07', 1, '120000000', 1),
('HD27122978', 'SP06', 1, '50000000', 1),
('HD27122978', 'SP07', 1, '120000000', 1),
('HD27122978', 'SP2022287', 1, '10000', 1),
('HD27120708', 'SP06', 2, '50000000', 1),
('HD27120708', 'SP05', 1, '45000000', 1),
('HD27120708', 'SP07', 4, '120000000', 1),
('HD27120708', 'SP2022287', 1, '10000', 1),
('HD27127462', 'SP06', 1, '50000000', 1),
('HD27127462', 'SP07', 1, '120000000', 1),
('HD27127462', 'SP2022287', 1, '10000', 1),
('HD27128272', 'SP06', 4, '50000000', 1),
('HD27128272', 'SP07', 2, '120000000', 1),
('HD27127836', 'SP06', 9, '50000000', 1),
('HD27127836', 'SP07', 10, '120000000', 1),
('HD27127289', 'SP05', 1, '45000000', 1),
('HD27127289', 'SP06', 1, '50000000', 1),
('HD27127289', 'SP07', 1, '120000000', 1),
('HD27127289', 'SP2022287', 1, '10000', 1),
('HD27127743', 'SP05', 10, '45000000', 1),
('HD27120066', 'SP05', 1, '45000000', 1),
('HD27120066', 'SP07', 1, '120000000', 1),
('HD27120066', 'SP2022287', 1, '10000', 1),
('HD27124975', 'SP06', 1, '50000000', 1),
('HD27124975', 'SP07', 1, '120000000', 1),
('HD27124975', 'SP2022287', 1, '10000', 1),
('HD27123340', 'SP07', 2, '120000000', 1),
('HD27123340', 'SP2022287', 1, '10000', 1),
('HD27123340', 'SP06', 1, '50000000', 1),
('HD29126931', 'SP06', 1, '50000000', 1),
('HD29126931', 'SP05', 1, '45000000', 1),
('HD29126931', 'SP2022287', 1, '10000', 1),
('HD29126931', 'SP07', 1, '120000000', 1),
('HD29123518', 'SP06', 4, '50000000', 1),
('HD29123518', 'SP05', 2, '45000000', 1),
('HD29123518', 'SP07', 3, '120000000', 1),
('HD29123518', 'SP2022287', 2, '10000', 1),
('HD30121358', 'SP05', 1, '45000000', 1),
('HD30121358', 'SP06', 1, '50000000', 1),
('HD30121358', 'SP07', 1, '120000000', 1),
('HD30121358', 'SP2022287', 1, '10000', 1),
('HD04017771', 'SP06', 1, '50000000', 1),
('HD04017771', 'SP05', 1, '45000000', 1),
('HD04017771', 'SP07', 1, '120000000', 1),
('HD04011972', 'SP06', 2, '50000000', 1),
('HD04011972', 'SP05', 1, '45000000', 1),
('HD04011972', 'SP07', 2, '120000000', 1),
('HD04011972', 'SP2022287', 1, '10000', 1),
('HD04011242', 'SP05', 1, '45000000', 1),
('HD04011242', 'SP06', 1, '50000000', 1),
('HD04011242', 'SP07', 3, '120000000', 1),
('HD04011242', 'SP2022287', 2, '10000', 1),
('HD04013745', 'SP05', 2, '45000000', 1),
('HD04013745', 'SP06', 2, '50000000', 1),
('HD04013745', 'SP07', 2, '120000000', 1),
('HD04013745', 'SP2022287', 2, '10000', 1),
('HD04012320', 'SP05', 3, '45000000', 1),
('HD04012320', 'SP06', 3, '50000000', 1),
('HD04012320', 'SP07', 3, '120000000', 1),
('HD04012320', 'SP2022287', 3, '10000', 1),
('HD04012933', 'SP05', 1, '45000000', 1),
('HD04012933', 'SP07', 1, '120000000', 1),
('HD04012933', 'SP2022287', 1, '10000', 1),
('HD19125219', 'SP05', 3, '45000000', 1),
('HD19125219', 'SP06', 6, '50000000', 1),
('HD19125219', 'SP07', 7, '120000000', 1),
('HD19125219', 'SP2022287', 2, '10000', 1),
('HD04016530', 'SP05', 1, '45000000', 1),
('HD04016530', 'SP06', 1, '50000000', 1),
('HD04016530', 'SP07', 1, '120000000', 1),
('HD04016530', 'SP2022287', 1, '10000', 1),
('HD29120028', 'SP06', 2, '50000000', 1),
('HD29120028', 'SP07', 4, '120000000', 1),
('HD29120028', 'SP2022287', 3, '10000', 1),
('HD08015752', 'SP05', 1, '45000000', 1),
('HD08015752', 'SP06', 1, '50000000', 1),
('HD08015752', 'SP07', 2, '120000000', 1),
('HD08015752', 'SP2022287', 1, '10000', 1),
('HD08019765', 'SP06', 1, '50000000', 1),
('HD08019765', 'SP07', 1, '120000000', 1),
('HD08019765', 'SP2022287', 1, '10000', 1),
('HD05027362', 'SP05', 2, '45000000', 1),
('HD05027362', 'SP06', 2, '50000000', 1),
('HD05027362', 'SP07', 1, '120000000', 1),
('HD05025133', 'SP05', 2, '45000000', 1),
('HD05025133', 'SP06', 4, '50000000', 1),
('HD05025133', 'SP07', 3, '120000000', 1),
('HD05025133', 'SP2022287', 1, '10000', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `config`
--

CREATE TABLE `config` (
  `username` varchar(20) DEFAULT NULL,
  `pass` varchar(30) DEFAULT NULL,
  `theme` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `config`
--

INSERT INTO `config` (`username`, `pass`, `theme`) VALUES
('admin', '123', 8);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `donvitinh`
--

CREATE TABLE `donvitinh` (
  `ID` varchar(10) NOT NULL,
  `Name` varchar(30) DEFAULT NULL,
  `GhiChu` varchar(250) DEFAULT NULL,
  `TrangThai` smallint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `donvitinh`
--

INSERT INTO `donvitinh` (`ID`, `Name`, `GhiChu`, `TrangThai`) VALUES
('DVT03', 'bịch', 'bịch', 1),
('DVT04', 'cái', 'cái', 1),
('DVT05', 'Chai', 'chai', 1),
('DVT091282', 'test123', 'test123', 1),
('MN01', 'lon', '', 1),
('MN02', 'gói', 'gói', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hoadon`
--

CREATE TABLE `hoadon` (
  `ID` varchar(10) NOT NULL,
  `IDnhanvien` varchar(10) DEFAULT NULL,
  `IDkhachHang` varchar(10) DEFAULT NULL,
  `ThoiGian` datetime DEFAULT NULL,
  `HinhThucThanhToan` int(1) DEFAULT 1,
  `GiamGia` decimal(10,0) DEFAULT NULL,
  `TongTienThanhToan` decimal(10,0) DEFAULT NULL,
  `soTienNhanDuoc` decimal(10,0) NOT NULL,
  `loaiGia` smallint(1) DEFAULT 0,
  `GhiChu` varchar(250) DEFAULT NULL,
  `trangThai` smallint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `hoadon`
--

INSERT INTO `hoadon` (`ID`, `IDnhanvien`, `IDkhachHang`, `ThoiGian`, `HinhThucThanhToan`, `GiamGia`, `TongTienThanhToan`, `soTienNhanDuoc`, `loaiGia`, `GhiChu`, `trangThai`) VALUES
('HD04011242', 'admin', 'KH01', '2023-01-04 01:28:33', 1, '0', '455020000', '455020000', 0, '', 1),
('HD04011972', 'admin', 'KH01', '2023-01-04 01:20:24', 1, '0', '385010000', '385010000', 0, '', 1),
('HD04012320', 'admin', 'KH01', '2023-01-04 01:29:39', 1, '0', '645030000', '645030000', 0, '', 1),
('HD04012933', 'admin', 'KH01', '2023-01-04 07:55:54', 1, '0', '165010000', '165010000', 0, '', 1),
('HD04013745', 'admin', 'KH01', '2023-01-04 01:29:13', 1, '0', '430020000', '430020000', 0, '', 1),
('HD04016530', 'admin', 'KH01', '2023-01-04 08:46:02', 1, '0', '215010000', '215010000', 0, '', 1),
('HD04017771', 'admin', 'KH01', '2023-01-04 01:20:18', 1, '0', '215000000', '215000000', 0, '', 1),
('HD05025133', 'admin', 'KH01', '2023-02-05 23:04:19', 1, '0', '650010000', '650010000', 0, '', 1),
('HD05027362', 'admin', 'KH01', '2023-02-05 22:59:13', 1, '0', '310000000', '310000000', 0, '', 1),
('HD07129220', 'NV09', 'KH002', '2022-12-07 17:39:52', 1, '0', '335000000', '335000000', 0, 'First Bill in da day for ma brother in da hood', 1),
('HD08015752', 'admin', 'KH01', '2023-01-08 10:14:06', 1, '0', '335010000', '335010000', 0, '', 1),
('HD08019765', 'admin', 'KH01', '2023-01-08 10:14:44', 1, '0', '170010000', '170010000', 0, '', 1),
('HD09123934', 'admin', 'KH01', '2022-12-09 09:34:07', 1, '0', '430000000', '430000000', 0, '', 1),
('HD09124249', 'admin', 'KH01', '2022-12-09 09:34:11', 1, '0', '95000000', '95000000', 0, '', 1),
('HD09125085', 'admin', 'KH01', '2022-12-09 10:15:35', 1, '0', '945010000', '945010000', 0, '', 1),
('HD12120530', 'admin', 'KH01', '2022-12-12 13:56:08', 1, '0', '580050000', '0', 0, '', 1),
('HD12126013', 'admin', 'KH01', '2022-12-12 14:01:51', 1, '0', '215010000', '215010000', 0, '', 1),
('HD12127886', 'admin', 'KH01', '2022-12-12 13:57:06', 1, '0', '170000000', '170000000', 0, '', 1),
('HD13122660', 'admin', 'KH01', '2022-12-13 21:08:52', 1, '0', '190000000', '190000000', 0, '', 1),
('HD13127212', 'admin', 'KH01', '2022-12-13 21:06:25', 1, '0', '170010000', '170010000', 0, '', 1),
('HD15122925', 'admin', 'KH01', '2022-12-15 19:50:06', 1, '0', '120010000', '120010000', 0, '', 1),
('HD15122960', 'admin', 'KH01', '2022-12-15 19:57:30', 1, '0', '480000000', '480000000', 0, '', 1),
('HD15124859', 'admin', 'KH01', '2022-12-15 19:57:48', 1, '0', '170000000', '170000000', 0, '', 1),
('HD15126284', 'admin', 'KH01', '2022-12-15 19:59:38', 1, '0', '480000000', '480000000', 0, '', 1),
('HD15126381', 'admin', 'KH01', '2022-12-15 19:50:13', 1, '0', '45000000', '0', 0, '', 1),
('HD15126443', 'admin', 'KH01', '2022-12-15 19:59:31', 1, '0', '480000000', '480000000', 0, '', 1),
('HD15127082', 'admin', 'KH01', '2022-12-15 17:19:32', 1, '0', '215010000', '215010000', 0, '', 1),
('HD15127938', 'admin', 'KH01', '2022-12-15 19:55:45', 1, '0', '215000000', '215000000', 0, '', 1),
('HD17120063', 'admin', 'KH01', '2022-12-17 14:55:31', 1, '0', '1225000000', '1225000000', 0, '', 1),
('HD17120638', 'admin', 'KH01', '2022-12-17 14:49:58', 1, '0', '220000000', '220000000', 0, '', 1),
('HD17120762', 'admin', 'KH01', '2022-12-17 14:49:12', 1, '0', '265000000', '265000000', 0, '', 1),
('HD17121365', 'admin', 'KH01', '2022-12-17 13:57:38', 1, '0', '525000000', '525000000', 0, '', 1),
('HD17121606', 'admin', 'KH01', '2022-12-17 13:58:36', 1, '0', '215000000', '215000000', 0, '', 1),
('HD17122763', 'admin', 'KH01', '2022-12-17 14:56:36', 1, '0', '765000000', '765000000', 0, '', 1),
('HD17123280', 'admin', 'KH01', '2022-12-17 13:57:43', 1, '0', '555000000', '0', 0, '', 1),
('HD17124153', 'admin', 'KH01', '2022-12-17 14:46:15', 1, '0', '50000000', '50000000', 0, '', 1),
('HD17125911', 'admin', 'KH01', '2022-12-17 14:56:27', 1, '0', '555000000', '555000000', 0, '', 1),
('HD17126901', 'admin', 'KH01', '2022-12-17 14:59:09', 1, '0', '435000000', '435000000', 0, '', 1),
('HD17127046', 'admin', 'KH01', '2022-12-17 14:56:03', 1, '0', '1960000000', '1960000000', 0, '', 1),
('HD17127750', 'admin', 'KH01', '2022-12-17 14:47:19', 1, '0', '390000000', '390000000', 0, '', 1),
('HD17128834', 'admin', 'KH01', '2022-12-17 14:54:43', 1, '0', '320000000', '320000000', 0, '', 1),
('HD17129335', 'admin', 'KH01', '2022-12-17 14:56:11', 1, '0', '335000000', '335000000', 0, '', 1),
('HD19120766', 'admin', 'KH01', '2022-12-19 16:20:12', 1, '0', '385010000', '385010000', 0, '', 1),
('HD19121926', 'admin', 'KH01', '2022-12-19 15:24:36', 1, '0', '265010000', '265010000', 0, '', 1),
('HD19122559', 'admin', 'KH01', '2022-12-19 15:24:40', 1, '0', '215000000', '215000000', 0, '', 1),
('HD19123886', 'admin', 'KH01', '2022-12-19 15:21:59', 1, '0', '340010000', '340010000', 0, '', 1),
('HD19125219', 'admin', 'KH01', '2022-12-19 15:18:20', 1, '0', '1275020000', '0', 0, '', 1),
('HD19125613', 'admin', 'KH01', '2022-12-19 15:42:25', 1, '0', '540000000', '540000000', 0, '', 1),
('HD19126918', 'admin', 'KH01', '2022-12-19 15:34:52', 1, '0', '480000000', '480000000', 0, '', 1),
('HD19127503', 'admin', 'KH01', '2022-12-19 16:20:07', 1, '0', '535010000', '535010000', 0, '', 1),
('HD19128475', 'admin', 'KH002', '2022-12-19 15:19:50', 1, '0', '310000000', '310000000', 0, '', 1),
('HD19129200', 'admin', 'KH003', '2022-12-19 15:19:02', 1, '0', '530000000', '530000000', 0, '', 1),
('HD19129382', 'admin', 'KH01', '2022-12-19 15:34:45', 1, '0', '555010000', '555010000', 0, '', 1),
('HD19129518', 'admin', 'KH01', '2022-12-19 15:16:42', 1, '0', '215000000', '215000000', 0, '', 1),
('HD21120106', 'admin', 'KH01', '2022-12-21 14:03:56', 1, '0', '605020000', '605020000', 0, '', 1),
('HD21120747', 'admin', 'KH01', '2022-12-21 14:02:38', 1, '0', '215010000', '215010000', 0, '', 1),
('HD21120804', 'admin', 'KH01', '2022-12-21 14:02:26', 1, '0', '215000000', '215000000', 0, '', 1),
('HD21124021', 'admin', 'KH01', '2022-12-21 14:02:33', 1, '0', '430020000', '430020000', 0, '', 1),
('HD25121000', 'admin', 'KH01', '2022-12-25 16:08:54', 1, '0', '215010000', '215010000', 0, '', 1),
('HD25122382', 'admin', 'KH01', '2022-12-25 16:10:06', 1, '0', '170010000', '170010000', 0, '', 1),
('HD25124254', 'admin', 'KH01', '2022-12-25 16:08:28', 1, '0', '435000000', '435000000', 0, '', 1),
('HD25124502', 'admin', 'KH01', '2022-12-25 18:09:25', 1, '0', '555020000', '555020000', 0, '', 1),
('HD25125907', 'admin', 'KH01', '2022-12-25 16:08:44', 1, '0', '385010000', '385010000', 0, '', 1),
('HD25126539', 'admin', 'KH01', '2022-12-25 16:08:49', 1, '0', '170010000', '170010000', 0, '', 1),
('HD25129098', 'admin', 'KH01', '2022-12-25 16:09:01', 1, '0', '680000000', '680000000', 0, '', 1),
('HD25129268', 'admin', 'KH01', '2022-12-25 18:09:17', 1, '0', '215010000', '215010000', 0, '', 1),
('HD25129632', 'admin', 'KH01', '2022-12-25 18:12:20', 1, '0', '215010000', '215010000', 0, '', 1),
('HD27120066', 'admin', 'KH002', '2022-12-27 20:48:50', 1, '0', '165010000', '165010000', 0, '', 1),
('HD27120708', 'admin', 'KH01', '2022-12-27 20:38:14', 1, '0', '625010000', '625010000', 0, '', 1),
('HD27122978', 'admin', 'KH01', '2022-12-27 20:38:05', 1, '0', '170010000', '170010000', 0, '', 1),
('HD27123340', 'admin', 'KH004', '2022-12-27 20:49:28', 1, '0', '290010000', '290010000', 0, '', 1),
('HD27124975', 'admin', 'KH002', '2022-12-27 20:49:19', 1, '0', '170010000', '170010000', 0, '', 1),
('HD27127289', 'admin', 'KH01', '2022-12-27 20:43:38', 1, '0', '215010000', '215010000', 0, '', 1),
('HD27127462', 'admin', 'KH01', '2022-12-27 20:42:40', 1, '0', '170010000', '170010000', 0, '', 1),
('HD27127479', 'admin', 'KH01', '2022-12-27 20:37:35', 1, '0', '215000000', '215000000', 0, '', 1),
('HD27127743', 'admin', 'KH01', '2022-12-27 20:43:56', 1, '0', '450000000', '450000000', 0, '', 1),
('HD27127836', 'admin', 'KH01', '2022-12-27 20:43:06', 1, '0', '1650000000', '0', 0, '', 1),
('HD27128272', 'admin', 'KH01', '2022-12-27 20:42:47', 1, '0', '440000000', '440000000', 0, '', 1),
('HD29120028', 'admin', 'KH01', '2022-12-29 19:14:38', 1, '0', '580030000', '0', 0, '', 1),
('HD29123518', 'admin', 'KH01', '2022-12-29 19:14:52', 1, '0', '650020000', '650020000', 0, '', 1),
('HD29126931', 'admin', 'KH01', '2022-12-29 19:14:43', 1, '0', '215010000', '215010000', 0, '', 1),
('HD30121358', 'admin', 'KH01', '2022-12-30 10:11:34', 1, '0', '215010000', '215010000', 0, '', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khachhang`
--

CREATE TABLE `khachhang` (
  `ID` varchar(10) NOT NULL,
  `Name` varchar(50) DEFAULT NULL,
  `SoDienThoai` varchar(10) DEFAULT NULL,
  `DiaChi` varchar(250) DEFAULT NULL,
  `GhiChu` varchar(250) DEFAULT NULL,
  `congno` decimal(10,0) DEFAULT 0,
  `TrangThai` smallint(1) DEFAULT 1,
  `ThoiGian` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `khachhang`
--

INSERT INTO `khachhang` (`ID`, `Name`, `SoDienThoai`, `DiaChi`, `GhiChu`, `congno`, `TrangThai`, `ThoiGian`) VALUES
('KH002', 'Nguyễn Phước Cường', '0975345345', '124 Yersin Quận 1 TP Hồ Chí Minh', 'VIP Guest', '0', 1, '2020-09-15 16:49:18'),
('KH003', 'Đỗ Thị Bích Vân', '0947188182', '240 Điện Biên Phủ TP BMT', 'VIP Guest', '0', 1, '2020-09-15 16:49:18'),
('KH004', 'Tống Quang Nam', '0975305305', '35/2 Ama Khê TP BMT', 'VIP Guest', '0', 1, '2020-09-15 16:49:18'),
('KH005', 'Lê Hồng Sơn', '0845456789', '29 Hoàng Hoa Thám Q.Thanh Xuân Hà Nội', 'Platinum Guest', '0', 1, '2020-09-15 16:49:18'),
('KH006', 'Trần Quang Sang', '0947888999', '12 Đường Cỏ Ống Côn Đảo', 'Regular Guest', '0', 1, '2020-09-15 16:49:18'),
('KH01', 'Khách Mới', '', '', '', '0', 1, '2020-09-15 16:49:18'),
('KH09121737', 'Khách Hàng Mới Mới', '0909898123', '1231 123123', '123123', '0', 1, '2022-12-09 10:29:04');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `loaisanpham`
--

CREATE TABLE `loaisanpham` (
  `ID` varchar(10) NOT NULL,
  `Name` varchar(30) DEFAULT NULL,
  `GhiChu` varchar(250) DEFAULT NULL,
  `TrangThai` smallint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `loaisanpham`
--

INSERT INTO `loaisanpham` (`ID`, `Name`, `GhiChu`, `TrangThai`) VALUES
('LSP01', 'Đồ Uống', 'KO', 1),
('LSP02', 'Đồ Ăn ', 'ko', 1),
('LSP03', 'Đồ Ăn Nhanh', 'KO', 1),
('LSP04', 'Đồ Gia Dụng', 'KO', 1),
('LSP05', 'bim sữa', '', 1),
('LSP06', 'Đồ ăn vặt', '', 1),
('LSP07', 'dụng cụ', 'hello', 1),
('LSP091218', '123123', 'test123123', 1),
('LSP12', 'Mechanical body parts', 'Made in 2077 by Arasaka Corporation', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhacungcap`
--

CREATE TABLE `nhacungcap` (
  `ID` varchar(10) NOT NULL,
  `Name` varchar(50) DEFAULT NULL,
  `SoDienThoai` varchar(10) DEFAULT NULL,
  `DiaChi` varchar(150) DEFAULT NULL,
  `GhiChu` varchar(150) DEFAULT NULL,
  `congno` decimal(10,0) DEFAULT 0,
  `TrangThai` smallint(1) DEFAULT 1,
  `ThoiGian` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `nhacungcap`
--

INSERT INTO `nhacungcap` (`ID`, `Name`, `SoDienThoai`, `DiaChi`, `GhiChu`, `congno`, `TrangThai`, `ThoiGian`) VALUES
('NCC01', 'Logitech Company', '0888123456', '59 Tô Hiến Thành Q1 TP Hồ Chí Minh', NULL, '0', 1, '2022-12-07 17:03:46'),
('NCC03', 'The Coca-Cola Company', '0888123456', 'Atlanta Georgia USA', NULL, '0', 1, '2022-12-07 17:03:46'),
('NCC04', 'PepsiCo Inc', '0888123457', 'ấp Purchase Harrison, New York', NULL, '0', 1, '2022-12-07 17:03:46'),
('NCC05', 'Arasaka Corporation', '0888123458', 'Arasaka Tower, Night City', NULL, '303000000', 1, '2022-12-07 17:03:46'),
('NCC06', 'CTY Thực Phẩm Massan Việt Nam', '0888123459', 'Sở Giao dịch Chứng khoán Thành phố Hồ Chí Minh', NULL, '0', 1, '2022-12-07 17:03:46'),
('NCC07', 'CTY Nhựa Long Thành', '0888123450', 'Khu công nghiệp Sóng Thần Bình Dương', NULL, '0', 1, '2022-12-07 17:03:46'),
('NCC091237', 'Nhà cung cấp mới', '0909909090', '123', '123123', '0', 1, '2022-12-09 10:30:19');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhanvien`
--

CREATE TABLE `nhanvien` (
  `id` varchar(10) NOT NULL,
  `Name` varchar(30) DEFAULT NULL,
  `SoDienThoai` varchar(10) DEFAULT NULL,
  `DiaChi` varchar(150) DEFAULT NULL,
  `NgaySinh` datetime DEFAULT NULL,
  `Luong` decimal(10,0) DEFAULT NULL,
  `GioiTinh` smallint(1) DEFAULT 1,
  `NgayVaoLam` datetime DEFAULT NULL,
  `GhiChu` varchar(150) DEFAULT NULL,
  `TrangThai` smallint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `nhanvien`
--

INSERT INTO `nhanvien` (`id`, `Name`, `SoDienThoai`, `DiaChi`, `NgaySinh`, `Luong`, `GioiTinh`, `NgayVaoLam`, `GhiChu`, `TrangThai`) VALUES
('admin', 'admin', '0935818820', 'BMT,DAK LAK', '2001-12-30 00:00:00', '100000000', 1, '2001-12-30 00:00:00', 'KO', 1),
('NV06', 'Châu Văn Hiệu', '0352461759', '209/12 Nguyễn Văn Cừ', '2000-12-31 00:00:00', '7500000', 1, '2021-12-26 00:00:00', 'hiệu', 1),
('NV09', 'lê hoàng khải', '123456789', '123asdasd', '2022-09-15 00:00:00', '5000000', 1, '2022-09-15 00:00:00', '', 1),
('NV091284', 'Lê Hoàng Khải hai', '0909321323', 'hai', '2000-01-01 00:00:00', '30000002', 1, '2000-01-01 00:00:00', '', 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhatkynhaphang`
--

CREATE TABLE `nhatkynhaphang` (
  `ID` varchar(10) NOT NULL,
  `ThoiGian` datetime DEFAULT NULL,
  `IDnhanvien` varchar(10) DEFAULT NULL,
  `IDNhaCungCap` varchar(10) DEFAULT NULL,
  `TongTien` decimal(10,0) DEFAULT NULL,
  `ThanhToan` decimal(10,0) DEFAULT NULL,
  `GhiChu` varchar(150) DEFAULT NULL,
  `TrangThai` smallint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `nhatkynhaphang`
--

INSERT INTO `nhatkynhaphang` (`ID`, `ThoiGian`, `IDnhanvien`, `IDNhaCungCap`, `TongTien`, `ThanhToan`, `GhiChu`, `TrangThai`) VALUES
('NH25120225', '2022-12-25 16:09:52', 'admin', 'NCC05', '303000000', '0', '', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhatkytrahangncc`
--

CREATE TABLE `nhatkytrahangncc` (
  `ID` varchar(10) NOT NULL,
  `ThoiGian` datetime DEFAULT NULL,
  `IDnhanvien` varchar(10) DEFAULT NULL,
  `IDncc` varchar(10) DEFAULT NULL,
  `TongTien` decimal(10,0) DEFAULT NULL,
  `hinhthuc` smallint(1) NOT NULL,
  `GhiChu` varchar(150) DEFAULT NULL,
  `TrangThai` smallint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `nhatkytrahangncc`
--

INSERT INTO `nhatkytrahangncc` (`ID`, `ThoiGian`, `IDnhanvien`, `IDncc`, `TongTien`, `hinhthuc`, `GhiChu`, `TrangThai`) VALUES
('TH15129744', '2022-12-15 19:59:08', 'admin', 'NCC05', '54000000', 0, '', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phieuchi`
--

CREATE TABLE `phieuchi` (
  `ID` int(10) NOT NULL,
  `ThoiGian` datetime DEFAULT NULL,
  `IDnhanvien` varchar(10) DEFAULT NULL,
  `TienChi` decimal(10,0) DEFAULT NULL,
  `GhiChu` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `phieuchi`
--

INSERT INTO `phieuchi` (`ID`, `ThoiGian`, `IDnhanvien`, `TienChi`, `GhiChu`) VALUES
(5, '2022-12-09 11:01:43', 'admin', '300000', '123'),
(6, '2022-12-09 11:02:48', 'admin', '1', '');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phieutranoncc`
--

CREATE TABLE `phieutranoncc` (
  `ID` int(11) NOT NULL,
  `IDNcc` varchar(10) DEFAULT NULL,
  `IDNhanVien` varchar(10) DEFAULT NULL,
  `SoTienTra` decimal(10,0) DEFAULT NULL,
  `ThoiGian` datetime DEFAULT NULL,
  `GhiChu` varchar(150) DEFAULT NULL,
  `TrangThai` smallint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sanpham`
--

CREATE TABLE `sanpham` (
  `ID` varchar(10) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `Barcode` varchar(30) DEFAULT NULL,
  `hinhAnh` varchar(200) NOT NULL DEFAULT 'empty.png',
  `GiaNhap` decimal(10,0) DEFAULT 0,
  `GiaBan` decimal(10,0) DEFAULT 0,
  `giaSi` decimal(10,0) NOT NULL DEFAULT 999999,
  `SoLuong` int(11) DEFAULT 0,
  `SoLuongToiThieu` int(11) DEFAULT 0,
  `IDNhaCungCap` varchar(10) NOT NULL,
  `IDDonViTinh` varchar(10) NOT NULL,
  `idLoaiSanPham` varchar(10) NOT NULL,
  `GhiChu` varchar(150) DEFAULT NULL,
  `TrangThai` smallint(1) NOT NULL DEFAULT 1,
  `ThoiGian` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `sanpham`
--

INSERT INTO `sanpham` (`ID`, `Name`, `Barcode`, `hinhAnh`, `GiaNhap`, `GiaBan`, `giaSi`, `SoLuong`, `SoLuongToiThieu`, `IDNhaCungCap`, `IDDonViTinh`, `idLoaiSanPham`, `GhiChu`, `TrangThai`, `ThoiGian`) VALUES
('SP05', 'Robotic Arm', '12312321312312', 'D:\\gocHocTap\\app\\DU_AN_1\\build\\classes\\IMAGE/MEoThanTai.jpg', '9000000', '45000000', '30000000', 364, 20, 'NCC05', 'DVT04', 'LSP12', 'Hỗ trợ thay thế, nâng cấp, cải tiến cơ thể (Tay)', 1, '2022-12-07 17:21:04'),
('SP06', 'Robotic Leg', '', 'C:\\Users\\Admin\\Pictures\\black-panther-lexus-lc-500-digital-art-neon-art-marvel-5120x3348-8298.jpg', '9000000', '50000000', '35000000', 333, 20, 'NCC05', 'DVT04', 'LSP12', 'Hỗ trợ thay thế, nâng cấp, cải tiến cơ thể (Chân)', 1, '2022-12-07 17:21:04'),
('SP07', 'Laser Eyes', '', 'C:\\Users\\Admin\\Desktop\\shoponline\\hinhanh\\afas\\anfas neurobion 2.jpg', '80000000', '120000000', '100000000', 363, 20, 'NCC05', 'DVT04', 'LSP12', 'Hỗ trợ thay thế, nâng cấp, cải tiến cơ thể (Mắt)', 1, '2022-12-07 17:21:04'),
('SP2022287', 'sản phẩm mới', '', 'C:\\Users\\Admin\\Desktop\\shoponline\\hinhanh\\afas\\Bình dạ an.jpg', '5000', '10000', '5000', 955, 10, 'NCC01', 'DVT04', 'LSP01', '', 1, '2022-12-09 10:15:21');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `thongtincuahang`
--

CREATE TABLE `thongtincuahang` (
  `tencuahang` varchar(100) NOT NULL,
  `diachi` varchar(50) DEFAULT NULL,
  `sodienthoai` varchar(10) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `footercontent` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `thongtincuahang`
--

INSERT INTO `thongtincuahang` (`tencuahang`, `diachi`, `sodienthoai`, `email`, `footercontent`) VALUES
('Tạp hóa Gấu Mèo', 'Hà Huy Tập, TP.BMT, Đăk Lăk', '0909999999', 'gaumeo.taphoa@gmail.com', '');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `thutienkhachhang`
--

CREATE TABLE `thutienkhachhang` (
  `ID` int(20) NOT NULL,
  `ThoiGian` datetime DEFAULT NULL,
  `IDNhanVien` varchar(10) DEFAULT NULL,
  `IDKhachHang` varchar(10) DEFAULT NULL,
  `SoTien` decimal(10,0) DEFAULT NULL,
  `GhiChu` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `thutienkhachhang`
--

INSERT INTO `thutienkhachhang` (`ID`, `ThoiGian`, `IDNhanVien`, `IDKhachHang`, `SoTien`, `GhiChu`) VALUES
(17, '2022-12-09 11:01:15', 'admin', 'KH003', '30000000', ''),
(18, '2022-12-09 11:03:28', 'admin', 'KH002', '10000000', ''),
(19, '2022-12-19 03:25:59', 'admin', 'KH003', '20000000', '');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `trichkho`
--

CREATE TABLE `trichkho` (
  `ID` varchar(10) NOT NULL,
  `IDNhanVien` varchar(10) DEFAULT NULL,
  `ThoiGian` datetime DEFAULT NULL,
  `GhiChu` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`Username`),
  ADD KEY `IDNhanVien` (`IDNhanVien`);

--
-- Chỉ mục cho bảng `chitiethoadon`
--
ALTER TABLE `chitiethoadon`
  ADD KEY `idsanpham` (`idsanpham`);

--
-- Chỉ mục cho bảng `donvitinh`
--
ALTER TABLE `donvitinh`
  ADD PRIMARY KEY (`ID`);

--
-- Chỉ mục cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `IDnhanvien` (`IDnhanvien`),
  ADD KEY `IDkhachHang` (`IDkhachHang`);

--
-- Chỉ mục cho bảng `khachhang`
--
ALTER TABLE `khachhang`
  ADD PRIMARY KEY (`ID`);

--
-- Chỉ mục cho bảng `loaisanpham`
--
ALTER TABLE `loaisanpham`
  ADD PRIMARY KEY (`ID`);

--
-- Chỉ mục cho bảng `nhacungcap`
--
ALTER TABLE `nhacungcap`
  ADD PRIMARY KEY (`ID`);

--
-- Chỉ mục cho bảng `nhanvien`
--
ALTER TABLE `nhanvien`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `nhatkynhaphang`
--
ALTER TABLE `nhatkynhaphang`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `IDnhanvien` (`IDnhanvien`),
  ADD KEY `IDncc` (`IDNhaCungCap`);

--
-- Chỉ mục cho bảng `nhatkytrahangncc`
--
ALTER TABLE `nhatkytrahangncc`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `IDnhanvien` (`IDnhanvien`),
  ADD KEY `IDncc` (`IDncc`);

--
-- Chỉ mục cho bảng `phieuchi`
--
ALTER TABLE `phieuchi`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `IDnhanvien` (`IDnhanvien`);

--
-- Chỉ mục cho bảng `phieutranoncc`
--
ALTER TABLE `phieutranoncc`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `IDNhanVien` (`IDNhanVien`),
  ADD KEY `IDNcc` (`IDNcc`);

--
-- Chỉ mục cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `IDDonViTinh` (`IDDonViTinh`),
  ADD KEY `idLoaiSanPham` (`idLoaiSanPham`),
  ADD KEY `IDNhaCungCap` (`IDNhaCungCap`);

--
-- Chỉ mục cho bảng `thongtincuahang`
--
ALTER TABLE `thongtincuahang`
  ADD PRIMARY KEY (`tencuahang`);

--
-- Chỉ mục cho bảng `thutienkhachhang`
--
ALTER TABLE `thutienkhachhang`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `IDNhanVien` (`IDNhanVien`),
  ADD KEY `IDKhachHang` (`IDKhachHang`);

--
-- Chỉ mục cho bảng `trichkho`
--
ALTER TABLE `trichkho`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `IDNhanVien` (`IDNhanVien`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `phieuchi`
--
ALTER TABLE `phieuchi`
  MODIFY `ID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `phieutranoncc`
--
ALTER TABLE `phieutranoncc`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT cho bảng `thutienkhachhang`
--
ALTER TABLE `thutienkhachhang`
  MODIFY `ID` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `account`
--
ALTER TABLE `account`
  ADD CONSTRAINT `account_ibfk_1` FOREIGN KEY (`IDNhanVien`) REFERENCES `nhanvien` (`id`);

--
-- Các ràng buộc cho bảng `chitiethoadon`
--
ALTER TABLE `chitiethoadon`
  ADD CONSTRAINT `chitiethoadon_ibfk_2` FOREIGN KEY (`idsanpham`) REFERENCES `sanpham` (`ID`);

--
-- Các ràng buộc cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  ADD CONSTRAINT `hoadon_ibfk_1` FOREIGN KEY (`IDnhanvien`) REFERENCES `nhanvien` (`id`),
  ADD CONSTRAINT `hoadon_ibfk_2` FOREIGN KEY (`IDkhachHang`) REFERENCES `khachhang` (`ID`);

--
-- Các ràng buộc cho bảng `nhatkynhaphang`
--
ALTER TABLE `nhatkynhaphang`
  ADD CONSTRAINT `nhatkynhaphang_ibfk_1` FOREIGN KEY (`IDnhanvien`) REFERENCES `nhanvien` (`id`),
  ADD CONSTRAINT `nhatkynhaphang_ibfk_2` FOREIGN KEY (`IDNhaCungCap`) REFERENCES `nhacungcap` (`ID`);

--
-- Các ràng buộc cho bảng `nhatkytrahangncc`
--
ALTER TABLE `nhatkytrahangncc`
  ADD CONSTRAINT `nhatkytrahangncc_ibfk_1` FOREIGN KEY (`IDnhanvien`) REFERENCES `nhanvien` (`id`),
  ADD CONSTRAINT `nhatkytrahangncc_ibfk_2` FOREIGN KEY (`IDncc`) REFERENCES `nhacungcap` (`ID`);

--
-- Các ràng buộc cho bảng `phieuchi`
--
ALTER TABLE `phieuchi`
  ADD CONSTRAINT `phieuchi_ibfk_1` FOREIGN KEY (`IDnhanvien`) REFERENCES `nhanvien` (`id`);

--
-- Các ràng buộc cho bảng `phieutranoncc`
--
ALTER TABLE `phieutranoncc`
  ADD CONSTRAINT `phieutranoncc_ibfk_1` FOREIGN KEY (`IDNhanVien`) REFERENCES `nhanvien` (`id`),
  ADD CONSTRAINT `phieutranoncc_ibfk_2` FOREIGN KEY (`IDNcc`) REFERENCES `nhacungcap` (`ID`);

--
-- Các ràng buộc cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  ADD CONSTRAINT `sanpham_ibfk_1` FOREIGN KEY (`IDDonViTinh`) REFERENCES `donvitinh` (`ID`),
  ADD CONSTRAINT `sanpham_ibfk_2` FOREIGN KEY (`idLoaiSanPham`) REFERENCES `loaisanpham` (`ID`),
  ADD CONSTRAINT `sanpham_ibfk_3` FOREIGN KEY (`IDNhaCungCap`) REFERENCES `nhacungcap` (`ID`);

--
-- Các ràng buộc cho bảng `thutienkhachhang`
--
ALTER TABLE `thutienkhachhang`
  ADD CONSTRAINT `thutienkhachhang_ibfk_1` FOREIGN KEY (`IDNhanVien`) REFERENCES `nhanvien` (`id`),
  ADD CONSTRAINT `thutienkhachhang_ibfk_2` FOREIGN KEY (`IDKhachHang`) REFERENCES `khachhang` (`ID`);

--
-- Các ràng buộc cho bảng `trichkho`
--
ALTER TABLE `trichkho`
  ADD CONSTRAINT `trichkho_ibfk_1` FOREIGN KEY (`IDNhanVien`) REFERENCES `nhanvien` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
