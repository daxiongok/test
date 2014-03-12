using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using System.Data;

using MySql.Data.MySqlClient;

namespace DAL
{
    public class shops_memberInfoService
    {
        public static Model.shops_memberInfo findShops_memberInfoById(int user_Id)
        {
            string SQL = "select user_id,user_name,user_grade,grade_points from shops_member where user_id = @user_id ";

            MySqlParameter[] pars = new MySqlParameter[] { 
                new MySqlParameter("@user_id",MySqlDbType.Int32)
            };

            Model.shops_memberInfo obj = null;

            DataTable dt = DBHelper.MySQL.GetDT(SQL, pars);

            if (dt.Rows.Count > 0)
            {
                obj = new Model.shops_memberInfo();

                obj.userId = Convert.ToInt32(dt.Rows[0]["user_id"]);
                obj.userName = dt.Rows[0]["user_name"].ToString();
                obj.user_grade =  Convert.ToInt32(dt.Rows[0]["user_grade"]);
                obj.grade_points = Convert.ToInt32(dt.Rows[0]["grade_points"]);
            }
            return obj;
        }
    }
}
