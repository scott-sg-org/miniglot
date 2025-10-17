package com.example.miniglot

import org.apache.commons.dbcp2.BasicDataSource
import java.io.IOException
import java.io.PrintWriter
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import javax.servlet.GenericServlet
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import com.example.miniglot.Utility.cleanInputAsString

class LoginServlet : GenericServlet() {

    @Throws(ServletException::class, IOException::class)
    override fun service(req: ServletRequest, res: ServletResponse) {
        var tmpUser : String = req.getParameter("userId")
        var tmpPass : String = req.getParameter("password")
        var userId : String = ""
        var password : String = "" 
        if (! tmpUser.isNullOrEmpty()) userId = cleanInputAsString(tmpUser)
        if (! tmpPass.isNullOrEmpty()) password = cleanInputAsString(tmpPass)
        res.contentType = "text/html"
        val out: PrintWriter = res.writer

        out.println("<html><head><title>Hello World!</title></head>")
        if (! userId.isNullOrEmpty()) {
            out.println("<body><h1>Hello User $userId</h1>${getUser(userId)}</body></html>")
        } else {
            out.println("<body><h1>Hello World!</h1></body></html>")
        }
    }

    private fun getUser(userId: String): String {
        var conn: Connection? = null
        var stmt: Statement? = null
        var rs: ResultSet? = null
        val result = StringBuilder()

        try {
            val bds = DataSource.getInstance().getBds()
            conn = bds.getConnection()

            val query = "SELECT * FROM users WHERE id = " + userId 
            stmt = conn.createStatement()
            rs = stmt.executeQuery(query)

            while (rs.next()) {
                result.append(
                    "<li>${rs.getInt(1)} ${rs.getString(2)} ${rs.getString(3)} " +
                    "${rs.getString(4)} ${rs.getString(5)}</li>"
                )
            }
        } catch (e: SQLException) {
            println(e.message)
        } finally {
            try {
                rs?.close()
                stmt?.close()
                conn?.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

        return result.toString()
    }
}
