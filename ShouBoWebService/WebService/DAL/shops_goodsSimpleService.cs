using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using System.Data;

using MySql.Data.MySqlClient;

namespace DAL
{
    public class shops_goodsSimpleService
    {
        //根据商品类型获取商品信息
        public static List<Model.shops_goodsSimple> getShops_goodsSimpleList(int pageIndex, int cate_id)
        {
            List<Model.shops_goodsSimple> objList = new List<Model.shops_goodsSimple>();

            string SQL = DBHelper.MySQL.CreateSQLIndex("goods_id,goods_name,default_image,price", "shops_goods ", ("cate_id_1 = " + cate_id + " or cate_id_2 = " + cate_id + " or cate_id_3 = " + cate_id ),null,null, pageIndex);

            DataTable dt = DBHelper.MySQL.GetDT(SQL);

            Model.shops_goodsSimple obj = null;

            for (int i = 0; i < dt.Rows.Count; i++)
            {
                obj = new Model.shops_goodsSimple();

                obj.goods_id  = Convert.ToInt32(dt.Rows[i]["goods_id"]);
                obj.goods_name = dt.Rows[i]["goods_name"].ToString();
                obj.default_image = dt.Rows[i]["default_image"].ToString();
                obj.price = Convert.ToDouble(dt.Rows[i]["price"]);

                objList.Add(obj);
            }

            return objList;

        }
 
    
    }
}
