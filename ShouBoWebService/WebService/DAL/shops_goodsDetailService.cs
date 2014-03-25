using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using System.Data;

namespace DAL
{
    public class shops_goodsDetailService
    {
        public static List<Model.shops_goodsDetail> getShops_goodsSimpleList( int goods_id)
        {
            List<Model.shops_goodsDetail> objList = new List<Model.shops_goodsDetail>();

            string SQL = DBHelper.MySQL.CreateSQLIndex("goods_id,goods_name,price,mk_price,unit,subtotalsale,spec_name_1,spec_name_2  ", "shops_goods ", ("shops_goods.goods_id = " + goods_id), null, null, 0);

            DataTable dt = DBHelper.MySQL.GetDT(SQL);

            Model.shops_goodsDetail obj = null;

            for (int i = 0; i < dt.Rows.Count; i++)
            {
                obj = new Model.shops_goodsDetail();

                obj.goods_id = Convert.ToInt32(dt.Rows[i]["goods_id"]);
                obj.goods_name = dt.Rows[i]["goods_name"].ToString();
                obj.price = Convert.ToDouble(dt.Rows[i]["price"]);
                obj.mk_price = Convert.ToDouble(dt.Rows[i]["mk_price"]);
                obj.unit = dt.Rows[i]["unit"].ToString(); 

                obj.subtotalsale = Convert.ToInt32(dt.Rows[i]["subtotalsale"]);
                obj.spec_name_1 = dt.Rows[i]["spec_name_1"].ToString();
                obj.spec_name_2 = dt.Rows[i]["spec_name_2"].ToString();

                objList.Add(obj);
            }
            return objList;
        }
    

    }
}
