package dao;

import java.sql.*;
import model.Account;

public class AccountDAO {

	public int createAccount(Account account) {

	    int generatedId = -1;

	    String query = "INSERT INTO account(name, balance, account_type) VALUES(?,?,?)";

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

	        ps.setString(1, account.getName());
	        ps.setDouble(2, account.getBalance());
	        ps.setString(3, account.getClass().getSimpleName());

	        ps.executeUpdate();

	        ResultSet rs = ps.getGeneratedKeys();
	        if (rs.next()) {
	            generatedId = rs.getInt(1);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return generatedId;
	}


    public Account getAccount(int accNo) {

        String query = "SELECT * FROM account WHERE account_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, accNo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Account account = new model.SavingsAccount();
                account.setAccountNumber(rs.getInt("account_id"));
                account.setName(rs.getString("name"));
                account.setBalance(rs.getDouble("balance"));

                return account;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateBalance(int accNo, double balance) {

        String query = "UPDATE account SET balance=? WHERE account_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setDouble(1, balance);
            ps.setInt(2, accNo);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAccount(int accNo) {

        String query = "DELETE FROM account WHERE account_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, accNo);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
