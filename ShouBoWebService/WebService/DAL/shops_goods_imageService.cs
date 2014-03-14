using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using System.Data;

namespace DAL
{
    public class shops_goods_imageService
    {
        public static List<Model.shops_goods_image> getShops_Goods_ImageById(int goods_id)
        {
            List<Model.shops_goods_image> list = new List<Model.shops_goods_image>();

            string SQL = DBHelper.MySQL.CreateSQLIndex(" image_url "," shops_goods_image  ","goods_id = " + goods_id,null,null,0);

            DataTable dt = DBHelper.MySQL.GetDT(SQL);

            Model.shops_goods_image imgs = null;

            for (int i = 0; i < dt.Rows.Count; i++)
            {
                imgs = new Model.shops_goods_image();

                imgs.image_url = dt.Rows[i]["image_url"].ToString();

                list.Add(imgs);
            }


            return list;
        }
  
    
    }
}
