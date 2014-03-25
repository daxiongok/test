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
        //根据购买者id获取支付记录
        public static List<Model.shops_order> findOrder_idByBuyer_id(int buyer_id)
        {
            List<Model.shops_order> objList = new List<Model.shops_order>();

            Model.shops_order obj = null;

            string SQL = DBHelper.MySQL.CreateSQLIndex("order_id,pay_time", "shops_order", "buyer_id = @buyer_id and pay_alter=1", "order_id ", "asc", 0);

            MySqlParameter[] pars = new MySqlParameter[]{
                new MySqlParameter("@buyer_id",MySqlDbType.Bit)
            };
            pars[0].Value = buyer_id;

            DataTable dt = DBHelper.MySQL.GetDT(SQL, pars);

            for (int i = 0; i < dt.Rows.Count; i++)
            {
                obj = new Model.shops_order();

                obj.order_id = Convert.ToInt32(dt.Rows[i]["order_id"]);
                obj.pay_time = Convert.ToInt32(dt.Rows[i]["pay_time"]);

                objList.Add(obj);

            }

            return objList;
        }
  
    
    
    
    }
}
