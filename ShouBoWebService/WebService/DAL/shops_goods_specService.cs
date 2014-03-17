using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using System.Data;

namespace DAL
{
    public class shops_goods_specService
    {
        //根据商品id获取商品购买页详细信息
        public static List<Model.shops_goods_spec> getShops_goods_specById(int goods_id)
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
                if (dt.Rows[i]["internal_code"].ToString().Trim() != "")
                {
                    obj.internal_code = Convert.ToInt32(dt.Rows[i]["internal_code"]);
                }

                list.Add(obj);
            }


            return list;
        }
        //根据商品类型id获取商品购买页详细信息
        public static Model.shops_goods_spec findShops_Goods_ImageById(int spec_id)
        {
            string SQL = DBHelper.MySQL.CreateSQLIndex(" spec_1 ,spec_2 ,price ,mk_price ,internal_code  ", " shops_goods_spec ", "spec_id = " + spec_id, null, null, 0);

            DataTable dt = DBHelper.MySQL.GetDT(SQL);

            Model.shops_goods_spec obj = null;

            if (dt.Rows.Count > 0)
            {
                obj = new Model.shops_goods_spec();

                obj.spec_1 = dt.Rows[0]["spec_1"].ToString();
                obj.spec_2 = dt.Rows[0]["spec_2"].ToString();
                obj.price = Convert.ToDouble(dt.Rows[0]["price"]);
                obj.mk_price = Convert.ToDouble(dt.Rows[0]["mk_price"]);
                if (dt.Rows[0]["internal_code"].ToString().Trim() != "")
                {
                    obj.internal_code = Convert.ToInt32(dt.Rows[0]["internal_code"]);
                }
            }

            return obj;
        }
    
    
    
    }
}
