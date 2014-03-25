using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using System.Data;

using MySql.Data.MySqlClient;

namespace DAL
{
    public class shops_order_goodsService
    {
        //根据商品id排序
        public static List<Model.shops_order_goods> findShops_order_goods(List<Model.shops_order> objList)
        {
            StringBuilder index = new StringBuilder();

            for (int i = 0; i < objList.Count; i++)
            {
                if (i == 0)
                {
                    index.Append(objList[i].order_id.ToString());
                }
                else
                {
                    index.Append(","+objList[i].order_id.ToString());
                }

            }

            string SQL = DBHelper.MySQL.CreateSQLIndex("rec_id,order_id,goods_id,goods_name,spec_id,price,quantity,goods_image", "shops_order_goods ", "order_id  in (" + index.ToString() + ")", "order_id ", "asc", 0);

            List<Model.shops_order_goods> objList2 = new List<Model.shops_order_goods>();

            DataTable dt = DBHelper.MySQL.GetDT(SQL);

            Model.shops_order_goods obj = null;

            for (int i = 0; i < dt.Rows.Count; i++)
            {
                obj = new Model.shops_order_goods();

                obj.goods_id =Convert.ToInt32( dt.Rows[i]["goods_id"]);
                obj.goods_name = dt.Rows[i]["goods_name"].ToString();
                obj.spec_id =  Convert.ToInt32(dt.Rows[i]["spec_id"]);
                obj.price = Convert.ToDouble(dt.Rows[i]["price"]);
                obj.quantity = Convert.ToInt32(dt.Rows[i]["quantity"]);
                obj.goods_image = dt.Rows[i]["goods_image"].ToString();

                objList2.Add(obj);
            }

            return objList2;
        }
    }
}
