using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using System.Data;

namespace DAL
{
    public class shops_goods_specService
    {
        public static List<Model.shops_goods_spec> getShops_Goods_ImageById(int goods_id)
        {
            List<Model.shops_goods_spec> list = new List<Model.shops_goods_spec>();

            string SQL = DBHelper.MySQL.CreateSQLIndex(" spec_1 ,spec_2 ,price ,mk_price ,internal_code  ", " shops_goods_spec ", "goods_id = " + goods_id, null, null, 0);

            DataTable dt = DBHelper.MySQL.GetDT(SQL);

            Model.shops_goods_spec obj = null;

            for (int i = 0; i < dt.Rows.Count; i++)
            {
                obj = new Model.shops_goods_spec();

                obj.spec_1 = dt.Rows[i]["spec_1"].ToString();
                obj.spec_2 = dt.Rows[i]["spec_2"].ToString();
                obj.price = Convert.ToDouble(dt.Rows[i]["price"]);
                obj.mk_price = Convert.ToDouble(dt.Rows[i]["mk_price"]);
                if (dt.Rows[i]["internal_code"].ToString().Trim()!="")
                {
                    obj.internal_code = Convert.ToInt32(dt.Rows[i]["internal_code"]);
                }
                
                list.Add(obj);
            }


            return list;
        }
    }
}
