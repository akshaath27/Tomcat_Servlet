package akshaath;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		String n = request.getParameter("txtName");
		String p = request.getParameter("txtPwd");

		// ✅ Name validation: Capital + min 3 letters
		if (n == null || !n.matches("^[A-Z][a-zA-Z]{2,}$")) {
			out.println("<font color=red size=18>Invalid Name: Must start with a capital letter and be at least 3 letters long.<br>");
			out.println("<a href=login.jsp>Try Again</a>");
			return;
		}

		// ✅ Password validation
		String specialChars = "!@#$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/\\\\|`~";
		String passwordPattern = "^(?=.*[A-Z])(?=.*\\d)(?=^[^" + specialChars + "]*[" + specialChars + "][^" + specialChars + "]*$).{8,}$";

		if (p == null || !p.matches(passwordPattern)) {
			out.println("<font color=red size=18>Invalid Password:<br>");
			out.println("• Must be at least 8 characters<br>");
			out.println("• Must contain 1 uppercase letter<br>");
			out.println("• Must contain 1 digit<br>");
			out.println("• Must contain exactly 1 special character<br>");
			out.println("<a href=login.jsp>Try Again</a>");
			return;
		}

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ash", "root", "ash@2004");

			PreparedStatement ps = con.prepareStatement("select uname from login where uname=? and password=?");
			ps.setString(1, n);
			ps.setString(2, p);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				RequestDispatcher rd = request.getRequestDispatcher("welcome.jsp");
				rd.forward(request, response);
			} else {
				out.println("<font color=red size=18>Login failed!<br>");
				out.println("<a href=login.jsp>Try Again</a>");
			}
			con.close();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			out.println("<font color=red>Error connecting to database.</font>");
		}
	}
}
