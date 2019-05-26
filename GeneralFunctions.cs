using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data.SqlClient;
using System.Data;

public class GeneralFunctions
{
//    public static string ConnString = @"Server=localhost;Port=3306;Database=tollboothDB;
//    Uid=toll;Pwd = toll@123;";
    public static string ConnString = @"Data Source=103.209.144.155,1533;Initial Catalog=tollboothDB;User ID=toll;Password=ms@123";
    public static string tollid="", tollname="";
    public GeneralFunctions()
    {
    }

    public static DataTable ExecuteDataTable(string Query, out string error)
    {
        SqlConnection con = new SqlConnection(ConnString);
        DataTable dt = new DataTable();
        try
        {
             SqlCommand cmd = new  SqlCommand(Query, con);
            cmd.CommandTimeout = 1000;
            if (con.State == ConnectionState.Closed)
                con.Open();
            dt.Load(cmd.ExecuteReader());
            error = "";
        }
        catch (Exception ex)
        {
            error = "ExecuteDataTable : " + ex.Message;
        }
        finally
        {
            con.Close();
        }
        return dt;
    }

    public static Object ExecuteScalar(string Query, out string error)
    {
        SqlConnection con = new SqlConnection(ConnString);
        object obj = null;
        try
        {
            SqlCommand cmd = new SqlCommand(Query, con);
            if (con.State == ConnectionState.Closed)
                con.Open();
            obj = cmd.ExecuteScalar();
            error = "";
        }
        catch (Exception ex)
        {
            error = "ExecuteScalar : " + ex.Message;
        }
        finally
        {
            con.Close();
        }
        return obj;
    }

    public static  SqlDataReader ExecuteDataReader(string Query, out string error)
    {
        SqlConnection con = new SqlConnection(ConnString);
        SqlDataReader dr = null;
        try
        {
            SqlCommand cmd = new SqlCommand(Query, con);
            if (con.State == ConnectionState.Closed)
                con.Open();
            dr = cmd.ExecuteReader();
            error = "";
        }
        catch (Exception ex)
        {
            error = "ExecuteDataReader : " + ex.Message;
        }
        finally
        {
            con.Close();
        }
        return dr;
    }

    public static bool ExecuteNonQuery(string Query, out string error)
    {
        SqlConnection con = new SqlConnection(ConnString);
        int rowsAffected = 0;
        bool bStatus = false;
        try
        {
            SqlCommand cmd = new SqlCommand(Query, con);
            if (con.State == ConnectionState.Closed)
                con.Open();
            rowsAffected = cmd.ExecuteNonQuery();
            if (rowsAffected > 0)
                bStatus = true;
            error = "";
        }
        catch (Exception ex)
        {
            error = "ExecuteNonQuery : " + ex.Message;
        }
        finally
        {
            con.Close();
        }
        return bStatus;
    }

    public static bool ExecuteNonQuery(string Query, SqlParameter[] MySqlPara, out string error)
    {
        SqlConnection con = new SqlConnection(ConnString);
        int rowsAffected = 0;
        bool bStatus = false;

        try
        {
             SqlCommand cmd = new SqlCommand(Query, con);
            cmd.Parameters.AddRange(MySqlPara);
            if (con.State == ConnectionState.Closed)
                con.Open();
            rowsAffected = cmd.ExecuteNonQuery();
            if (rowsAffected > 0)
                bStatus = true;
            error = "";
        }
        catch (Exception ex)
        {
            error = "ExecuteNonQuery Para : " + ex.Message;
        }
        finally
        {
            con.Close();
        }
        return bStatus;
    }

    public static DataTable dt_Communicate()
    {
        DataTable dt = new DataTable();
        try
        {
            dt.Columns.Add("Status");
            dt.Columns.Add("Message");
            dt.Columns.Add("Value");
            DataRow dr = dt.NewRow();
            dr["Status"] = "false";
            dr["Message"] = "";
            dr["Value"] = "";
            dt.Rows.Add(dr);
        }
        catch (Exception)
        {
        }
        return dt;
    }

    public static string GenerateOTP()
    {
        String otp = "";
        try
        {
            Random generator = new Random();
            otp = generator.Next(0, 999999).ToString("D6");
        }
        catch (Exception)
        {
        }
        return otp;
    }

    public static string AlphaNumeric()
    {
        string str = "";
        try
        {
            var chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            var stringChars = new char[3];
            var random = new Random();

            for (int i = 0; i < stringChars.Length; i++)
            {
                stringChars[i] = chars[random.Next(chars.Length)];
            }

            str = new String(stringChars);

            chars = "0123456789";
            stringChars = new char[3];
            random = new Random();

            for (int i = 0; i < stringChars.Length; i++)
            {
                stringChars[i] = chars[random.Next(chars.Length)];
            }

            str += new String(stringChars);
        }
        catch (Exception)
        {

        }
        return str;
    }
}