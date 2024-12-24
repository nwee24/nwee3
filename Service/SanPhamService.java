package Service;

import Model.SanPham;

import java.lang.reflect.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

public class SanPhamService extends SQLServerService {
    public int luuSanPham(SanPham sp) {
        try {
            String sql = "insert into sanpham values(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, sp.getMaSp());
            preparedStatement.setString(2, sp.getTenSp());
            preparedStatement.setInt(3, sp.getSoLuong());
            preparedStatement.setInt(4, sp.getDonGia());
            preparedStatement.setString(5, sp.getMaDM());
            preparedStatement.setInt(6, 0);
            return preparedStatement.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    public Vector<SanPham> docSanPhamTheoDanhMuc(String madm) {
        Vector<SanPham> dsSp = new Vector<SanPham>();
        try {
            String sql = "select * from sanpham where madm=? and isdeleted=0";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, madm);

            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                SanPham sp = new SanPham();
                sp.setMaSp(result.getString(1));
                sp.setTenSp(result.getString(2));
                sp.setSoLuong(result.getInt(3));
                sp.setDonGia(result.getInt(4));
                sp.setMaDM(result.getString(5));
                sp.setIsDeleted(0);
                dsSp.add(sp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dsSp;
    }

    public int xoaSanPham(String maSP) {
        int result = 0;
        try {
            String sql = "UPDATE sanpham SET isdeleted = 1  WHERE masp= ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, maSP);
            System.out.println("xoa san pham: " + maSP);
            result = preparedStatement.executeUpdate();
            System.out.println("ket qua xoa: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Vector<SanPham> timKiemSanPham(String tenSP) {
        Vector<SanPham> vec = new Vector<SanPham>();
        try {
            String sql = "SELECT * FROM sanpham WHERE TenSP LIKE ? AND isdeleted = 0";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + tenSP + "%");
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                SanPham sp = new SanPham();
                sp.setMaSp(result.getString("MaSP"));
                sp.setTenSp(result.getString("TenSP"));
                sp.setSoLuong(result.getInt("SoLuong"));
                sp.setDonGia(result.getInt("DonGia"));
                sp.setMaDM(result.getString("MaDM"));
                sp.setIsDeleted(0);
                vec.add(sp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return vec;
    }

    public int themSanPham(SanPham sp) {
        int result = 0;
        try {

            String checkMaDM = "SELECT COUNT(*) FROM DanhMuc WHERE MaDM = ?";
            PreparedStatement checkStatement = conn.prepareStatement(checkMaDM);
            checkStatement.setString(1, sp.getMaDM());
            ResultSet rs = checkStatement.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            if (count == 0) { // Nếu MaDM không tồn tại, thêm MaDM vào bảng DanhMuc
                String insertMaDM = "INSERT INTO DanhMuc (MaDM, TenDM, isDeleted) VALUES (?, ?, 0)"; PreparedStatement insertStatement = conn.prepareStatement(insertMaDM); insertStatement.setString(1, sp.getMaDM()); insertStatement.setString(2, "Tên danh mục mặc định"); // Bạn có thể thay đổi tên danh mục mặc định nếu cần
                insertStatement.executeUpdate();}


                String sql = "INSERT INTO sanpham (MaSP, TenSP, SoLuong, DonGia, maDM, Isdeleted) VALUES (?, ?, ?, ?, ?, 0)";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, sp.getMaSp());
                preparedStatement.setString(2, sp.getTenSp());
                preparedStatement.setInt(3, sp.getSoLuong());
                preparedStatement.setInt(4, sp.getDonGia());
                preparedStatement.setString(5, sp.getMaDM());
                return preparedStatement.executeUpdate();
            } catch(Exception e){
                e.printStackTrace();
                return -1;
            }
        }

    public int suaSanPham(SanPham sp) {
        int result = 0;
        try {
            String sql = "UPDATE sanpham SET TenSP = ?, SoLuong = ?, DonGia = ?, MaDM = ? WHERE MaSP = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, sp.getTenSp());
            preparedStatement.setInt(2, sp.getSoLuong());
            preparedStatement.setInt(3, sp.getDonGia());
            preparedStatement.setString(4, sp.getMaDM());
            preparedStatement.setString(5, sp.getMaSp());
            result = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return result;
    }

}
