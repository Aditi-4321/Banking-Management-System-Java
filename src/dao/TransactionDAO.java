package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Transaction;

public class TransactionDAO {

    public void addTransaction(Transaction txn) {

        String query = "INSERT INTO transaction(account_id, amount, txn_type, date) VALUES(?,?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, txn.getAccountNumber());
            ps.setDouble(2, txn.getAmount());
            ps.setString(3, txn.getTransactionType());
            ps.setTimestamp(4, new Timestamp(txn.getDate().getTime()));

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Transaction> getTransactions(int accNo) {

        List<Transaction> list = new ArrayList<>();

        String query = "SELECT * FROM transaction WHERE account_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, accNo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Transaction txn = new Transaction();

                txn.setTransactionId(rs.getInt("txn_id"));
                txn.setAccountNumber(rs.getInt("account_id"));
                txn.setAmount(rs.getDouble("amount"));
                txn.setTransactionType(rs.getString("txn_type"));
                txn.setDate(rs.getTimestamp("date"));

                list.add(txn);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
