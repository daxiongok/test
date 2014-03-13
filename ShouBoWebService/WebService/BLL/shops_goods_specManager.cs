using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace BLL
{
    public class shops_goods_specManager
    {   //获取商品购买页信息
        public static List<Model.shops_goods_spec> getShops_Goods_ImageById(int goods_id)
        {
            return DAL.shops_goods_specService.getShops_Goods_ImageById(goods_id);
        }
    }
}
