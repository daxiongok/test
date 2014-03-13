using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using System.Data;

using MySql.Data.MySqlClient;

namespace DAL
{
    public class shops_memberService
    {
        //登陆
        public static int login(Model.shops_member obj, string ipAddress)
        {
            if (validate(obj.user_name) > 0)
            {
                return loginValidate(obj, ipAddress);
            }
            return -1;
        }
        //注册
        public static int register(Model.shops_member obj, string ipAddress)
        {
            if (validate(obj.user_name) <= 0)
            {
                int result = addshops_member(obj, ipAddress);

                if (result > 0)
                {
                    return loginValidate(obj, ipAddress);
                }
                return -1;
            }
            return -2;
        }
        //增加用户
        private static int addshops_member(Model.shops_member obj, string ipAddress)
        {
            string SQL = "insert into shops_member (user_name ,email ,password ,gender ,reg_time ,last_login ,last_ip ,logins ,ugrade,outer_id ,feed_config ,pay_points ,grade_points ,user_grade ,isvalidate ) values (@user_name,@email,@password,@gender,@reg_time,@last_login,@last_ip,@logins,@ugrade,@outer_id,@feed_config,@pay_points,@grade_points,@user_grade,@isvalidate)";

            MySqlParameter[] pars = new MySqlParameter[] { 
                    new MySqlParameter("@user_name",MySqlDbType.VarChar),
                    new MySqlParameter("@email",MySqlDbType.VarChar),
                    new MySqlParameter("@password",MySqlDbType.VarChar),
                    new MySqlParameter("@gender",MySqlDbType.Int32),
                    new MySqlParameter("@reg_time",MySqlDbType.Int32),
                    new MySqlParameter("@last_login",MySqlDbType.Int32),
                    new MySqlParameter("@last_ip",MySqlDbType.VarChar),
                    new MySqlParameter("@logins",MySqlDbType.Int32),
                    new MySqlParameter("@ugrade",MySqlDbType.Int32),
                    new MySqlParameter("@outer_id",MySqlDbType.Int32),
                    new MySqlParameter("@feed_config",MySqlDbType.Text),
                    new MySqlParameter("@pay_points",MySqlDbType.Int32),
                    new MySqlParameter("@grade_points",MySqlDbType.Int32),
                    new MySqlParameter("@user_grade",MySqlDbType.Int32),
                    new MySqlParameter("@isvalidate",MySqlDbType.Int32)
                };
            pars[0].Value = obj.user_name;
            pars[1].Value = obj.email;
            pars[2].Value = obj.password;
            pars[3].Value = 0;
            pars[4].Value = DateTime.Now.Millisecond;
            pars[5].Value = DateTime.Now.Millisecond;
            pars[6].Value = ipAddress;
            pars[7].Value = 0;
            pars[8].Value = 0;
            pars[9].Value = 0;
            pars[10].Value = "";
            pars[11].Value = 0;
            pars[12].Value = 0;
            pars[13].Value = 0;
            pars[14].Value = 0;

            return DBHelper.MySQL.ExecuteNonQuery(SQL, CommandType.Text, pars);
        }
        //判断用户名是否存在，返回查询行数
        private static int validate(string user_name)
        {
            string SQL = "select user_id  from shops_member where user_name = @user_name ";

            MySqlParameter[] pars = new MySqlParameter[]{
                new MySqlParameter("@user_name",MySqlDbType.VarChar)
            };
            pars[0].Value = user_name;

            DataTable dt = DBHelper.MySQL.GetDT(SQL, pars);

            return dt.Rows.Count;
        }
        //判断用户名和密码是否存在返回查询条数

        private static int loginValidate(Model.shops_member obj, string ipAddress)
        {
            string SQL = "select user_id  from shops_member where user_name = @user_name and password =@password  ";

            MySqlParameter[] pars = new MySqlParameter[]{
                new MySqlParameter("@user_name",MySqlDbType.VarChar),  
                new MySqlParameter("@password",MySqlDbType.VarChar)
            };
            pars[0].Value = obj.user_name;
            pars[1].Value = obj.password;

            DataTable dt = DBHelper.MySQL.GetDT(SQL, pars);

            int result =Convert.ToInt32(dt.Rows[0][0]);

            addLoginsCount(result, ipAddress);

            return result;
        }
        //添加用户
        private static void addLoginsCount(int user_id, string ipAddress)
        {
            string SQL = "update shops_member set logins = logins +1, last_login = @last_login , last_ip = @last_ip  where user_id  = @user_id ";
            MySqlParameter[] pars = new MySqlParameter[]{
            new MySqlParameter("@user_id",MySqlDbType.Int32),
            new MySqlParameter("@last_login",MySqlDbType.Int32),
            new MySqlParameter("@last_ip",MySqlDbType.VarChar)
            };
            pars[0].Value = user_id;
            pars[1].Value = 1392425660;
            pars[2].Value = ipAddress;
            
            DBHelper.MySQL.ExecuteNonQuery(SQL, CommandType.Text, pars);

        }

    }
}
