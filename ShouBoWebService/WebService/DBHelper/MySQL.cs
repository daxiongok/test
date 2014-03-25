using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using System.Configuration;

using System.Data;

using MySql.Data.MySqlClient;

namespace DBHelper
{
    public class MySQL
    {
        public static int rowCounts = 16;



        public static MySqlConnection conn;
        public static MySqlTransaction tran;
        public static MySqlCommand command;

        private static string MySQLConntionStr = ConfigurationManager.ConnectionStrings["MySQLStr"].ToString();

        private static MySqlConnection CreateMySqlCon()
        {
            return new MySqlConnection(MySQLConntionStr);
        }

        #region SQL语句
        public static string CreateSQLIndex(string select, string tableName, int PageIndexs)
        {
            return CreateSQLIndex(select, tableName, null, PageIndexs);
        }
        public static string CreateSQLIndex(string select, string tableName, string where, int PageIndexs)
        {
            return CreateSQLIndex(select, tableName, where, null, null, PageIndexs);
        }
        public static string CreateSQLIndex(string select, string tableName, string where, string order, string orderBy, int PageIndexs)
        {
            return CreateSQLIndex(select, tableName, where, null, null, order, orderBy, PageIndexs);
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

        #region 修正Cmd
        private static void fixCmd(MySqlConnection con, MySqlCommand cmd, string SQL, MySqlParameter[] pars, CommandType type)
        {
            fixCmd(con, cmd, SQL, pars, type, null);
        }
        private static void fixCmd(MySqlConnection con, MySqlCommand cmd, string SQL, MySqlParameter[] pars, CommandType type, MySqlTransaction tran)
        {

            cmd.Connection = con;
            cmd.CommandText = SQL;
            cmd.CommandType = type;
            if (pars != null)
            {
                cmd.Parameters.Clear();
                cmd.Parameters.AddRange(pars);
            }
            if (tran != null)
            {
                cmd.Transaction = tran;
            }

        }
        #endregion

        #region DataTable
        public static DataTable GetDT(string SQL)
        {
            return GetDT(SQL, null);
        }
        public static DataTable GetDT(string SQL, MySqlParameter[] pars)
        {
            return GetDT(SQL, pars, CommandType.Text);
        }
        public static DataTable GetDT(string SQL, MySqlParameter[] pars, CommandType type)
        {
            using (MySqlConnection con = CreateMySqlCon())
            {
                con.Open();
                MySqlCommand cmd = new MySqlCommand();
                fixCmd(con, cmd, SQL, pars, type);
                MySqlDataAdapter da = new MySqlDataAdapter(cmd);
                DataTable dt = new DataTable();
                da.Fill(dt);
                con.Close();
                return dt;
            }

        }
        #endregion

        #region 增加
        public static int ExecuteNonQuery(string SQL, CommandType cmdType, MySqlParameter[] pars)
        {
            int result = 0;
            using (MySqlConnection con = CreateMySqlCon())
            {
                con.Open();
                MySqlCommand cmd = new MySqlCommand();
                fixCmd(con, cmd, SQL, pars, cmdType);
                result = cmd.ExecuteNonQuery();
                con.Close();
            }
            return result;
        }

        public static int ExecuteNonQueryByTransaction(string SQL, CommandType cmdType, MySqlParameter[] pars)
        {
            MySqlConnection con = CreateMySqlCon();
            conn.Open();
            MySqlTransaction tran = conn.BeginTransaction(IsolationLevel.ReadCommitted);
            int result = 0;
            MySqlCommand cmd = new MySqlCommand();
            fixCmd(con, cmd, SQL, pars, cmdType, tran);
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
        #endregion

        #region 事务
        public static void beginTransaction()
        {
            conn = CreateMySqlCon();
            conn.Open();
            tran = conn.BeginTransaction();
            command = new MySqlCommand();
            command.Connection = conn;
            command.Transaction = tran;
        }
        public static void executeTransaction(string sqlStr, MySqlParameter[] Parameters)
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
        #endregion
    }
}
