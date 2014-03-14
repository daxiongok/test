using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using System.Data;

using MySql.Data.MySqlClient;

namespace DAL
{
    public class shops_orderService
    {
        public static Model.shops_order findOrder_idByBuyer_id(int buyer_id)
        {

            Model.shops_order obj = null;

            string SQL = DBHelper.MySQL.CreateSQLIndex("buyer_id,pay_time", "shops_order", "where buyer_id = @buyer_id", 0);

            MySqlParameter[] pars = new MySqlParameter[]{
                new MySqlParameter("@buyer_id",MySqlDbType.Int32)
            };
            pars[0].Value = buyer_id;

            DataTable dt = DBHelper.MySQL.GetDT(SQL, pars);

            if (dt.Rows.Count>0)
            {
                obj = new Model.shops_order();

                obj.order_id = Convert.ToInt32(dt.Rows[0]["order_id"]);
                obj.pay_time = Convert.ToInt64(dt.Rows[0]["pay_time"]);
            }

            return obj;
        }
  
    
    
    
    }
}
