//============================================================
// Producnt name:		ZXL
// Version: 			1.0
// Coded by:			ZXL (xiling.zhang@lnbdqn.com)
// Auto generated at: 	2012-8-3 14:22:29
//============================================================

using System;
using System.Collections.Generic;
using System.Text;
using System.Data.SqlClient;
using System.Data;
using System.Configuration;
using System.Xml;
namespace CqbdqnFramework.DAL
{

    public static class DBHelper
    {
        private static string ConnectionStrings = ConfigurationSettings.AppSettings["connStr"].ToString();
		public static int pageRowsNum = 10;
		public static SqlConnection conn;
        public static SqlTransaction tran;
        public static SqlCommand command;
       

		public static int newId()
        {
            
            XmlDocument doc = new XmlDocument();
            doc.Load(AppDomain.CurrentDomain.BaseDirectory+"\\inc\\c.xml");
            XmlNode xn = doc.DocumentElement;
            int n = Int32.Parse(xn.InnerText);
            xn.InnerText = (n + 1).ToString();
            doc.Save(AppDomain.CurrentDomain.BaseDirectory+"\\inc\\c.xml");
            return n;
        }
		
		public static void beginTransaction()
        {
            conn = new SqlConnection(ConnectionStrings);
            conn.Open();
            tran = conn.BeginTransaction();
            command = new SqlCommand();
            command.Connection = conn;
            command.Transaction = tran;
        }
		
        public static void executeTransaction(string sqlStr, SqlParameter[] Parameters)
        {
            command.CommandText = sqlStr;
            command.Parameters.Clear();
            command.Parameters.AddRange(Parameters);
            command.ExecuteNonQuery();
        }
		
        public static bool commitTransaction()
        {
            try
            {
                tran.Commit();
                return true;
            }
            catch
            {
                tran.Rollback();
                return false;
            }
            finally
            {
                command = null;
                tran = null;
                conn.Close();
                conn = null;
            }
        }
       
		public static string getSqlByPage(string selectSql,string idCloName, string orderByStr,int nowPage)
        {
            string sql = "SELECT ";
            sql = sql + " top " + pageRowsNum.ToString() + " ";   
            sql=sql+" * ";
            sql = sql + " FROM (" + selectSql + ") as childrenSelctTableAA ";
            sql = sql + " WHERE childrenSelctTableAA." + idCloName + " NOT IN(";
            sql = sql + " SELECT TOP (" + ((nowPage - 1) * pageRowsNum).ToString() + ") ";
            sql = sql + " " + idCloName + " ";
            sql = sql + " FROM (" + selectSql + ") as childrenSelctTableBB";
            sql = sql + " ORDER BY childrenSelctTableBB." + orderByStr + " ) ";
            sql = sql + " ORDER BY childrenSelctTableAA." + orderByStr + " ";
            return sql;
        }
		
        public static SqlDataReader ExecuteQuery(string cmdText, CommandType cmdType, SqlParameter[] pars)
        {
            SqlConnection conn = new SqlConnection(ConnectionStrings);
            SqlCommand cmd = new SqlCommand();
            try
            {
                PrepareExecute(cmdText, conn, cmd, cmdType, pars);
                SqlDataReader reader = cmd.ExecuteReader(CommandBehavior.CloseConnection);
                return reader;
            }
            catch (Exception ex)
            {
                conn.Close();
                throw new Exception(ex.Message);
            }
            finally
            {
                cmd.Parameters.Clear();
            }            
            
        }

        public static object ExecuteScalar(string cmdText, CommandType cmdType, SqlParameter[] pars)
        {
            object result = null;
            using (SqlConnection conn = new SqlConnection(ConnectionStrings))
            {
                SqlCommand cmd = new SqlCommand();
                PrepareExecute(cmdText, conn, cmd, cmdType, pars);
                result = cmd.ExecuteScalar();
                cmd.Parameters.Clear();
            }
            return result;
        }

        public static int ExecuteNonQuery(string cmdText, CommandType cmdType, SqlParameter[] pars)
        {
            int result = 0;
            using (SqlConnection conn = new SqlConnection(ConnectionStrings))
            {
                SqlCommand cmd = new SqlCommand();
                PrepareExecute(cmdText, conn, cmd, cmdType, pars);
                result = cmd.ExecuteNonQuery();
                cmd.Parameters.Clear();
            }   
            return result;
        }

        public static int ExecuteNonQueryByTransaction(string cmdText, CommandType cmdType, SqlParameter[] pars)
        {
                 SqlConnection conn = new SqlConnection(ConnectionStrings);
                 conn.Open();
                 SqlTransaction tran = conn.BeginTransaction(IsolationLevel.ReadCommitted);
                 int result = 0;
                 SqlCommand cmd = new SqlCommand();
                 PrepareExecute(cmdText, conn, cmd, cmdType, pars, tran);
                  try
                  {
                      result = cmd.ExecuteNonQuery();
                      tran.Commit();
                   }
                  catch (Exception ex)
                  {
                       tran.Rollback();
                       throw new Exception(ex.Message);
                  }
                  finally
                 {
                    conn.Close();
                    cmd.Parameters.Clear();
                 }

                return result;
        }

        public static DataSet GetDataSet(string cmdText, CommandType cmdType, SqlParameter[] pars)
        {
            using (SqlConnection conn = new SqlConnection(ConnectionStrings))
            {
                SqlCommand cmd = new SqlCommand();
                PrepareExecute(cmdText, conn, cmd, cmdType, pars);
                SqlDataAdapter da = new SqlDataAdapter(cmd);
                DataSet ds = new DataSet();
                da.Fill(ds);
                return ds;
            }
        }

        private static void PrepareExecute(string cmdText, SqlConnection conn, SqlCommand cmd, CommandType cmdType, SqlParameter[] pars)
        {
            PrepareExecute(cmdText, conn, cmd, cmdType, pars, null);
        }

        private static void PrepareExecute(string cmdText, SqlConnection conn, SqlCommand cmd, CommandType cmdType, SqlParameter[] pars, SqlTransaction tran)
        {
            cmd.Connection = conn;
            cmd.CommandText = cmdText;
            cmd.CommandType = CommandType.Text;
            if (tran != null)
            {
                cmd.Transaction = tran;
            }
            if (pars != null)
            {
                cmd.Parameters.AddRange(pars);
            }
            if (conn.State != ConnectionState.Open)
            {
                conn.Open();
            }

        }
    }
}

