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
		public static int rowCounts = 10;
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

        #region SQLÓï¾ä
        public static string CreateSQLIndex(string select, string tableName, int PageIndexs)
        {
            return CreateSQLIndex(select, tableName, null, null, null, null, null, PageIndexs);
        }
        public static string CreateSQLIndex(string select, string tableName, string where, string order, string orderBy, int PageIndexs)
        {
            return CreateSQLIndex(select, tableName, where, order, orderBy, null, null, PageIndexs);
        }
        public static string CreateSQLIndex(string select, string tableName, string where, string having, string groupBy, string order, string orderBy, int PageIndexs)
        {
            StringBuilder SQL = new StringBuilder();
            SQL.Append(" select ");
            SQL.Append(select);
            SQL.Append(" from ");
            SQL.Append(tableName);

            #region Where
            if (where != null)
            {
                SQL.Append(" where ");
                SQL.Append(where);
            }
            #endregion

            #region having
            if (having != null)
            {
                SQL.Append(" having ");
                SQL.Append(having);
            }
            #endregion

            #region groupBy
            if (groupBy != null)
            {
                SQL.Append(" group by ");
                SQL.Append(groupBy);
            }
            #endregion

            #region order
            if (order != null)
            {
                SQL.Append(" order by ");
                SQL.Append(order);
            }
            #endregion

            #region orderBy
            if (orderBy != null)
            {
                SQL.Append(orderBy);
            }
            #endregion

            #region PageIndex
            if (PageIndexs > 0 && rowCounts > 0)
            {
                int minRow = -1;
                int maxRow = -1;
                int rowCount = -1;

                rowCount = rowCounts;
                minRow = (PageIndexs - 1) * rowCount;
                maxRow = (PageIndexs) * rowCount;

                SQL.Append(" limit " + minRow + " , " + maxRow);
            }
            #endregion

            return SQL.ToString();
        }
        #endregion
		
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

