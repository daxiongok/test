using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace BLL
{
    public class shops_goods_specManager
    {
        //根据商品id获取商品购买页详细信息
        public static List<Model.shops_goods_spec> getShops_goods_specById(int goods_id)
        {
            return DAL.shops_goods_specService.getShops_goods_specById(goods_id);
        }
        //根据商品类型id获取商品购买页详细信息
        public static Model.shops_goods_spec findShops_Goods_ImageById(int spec_id)
        {
            return DAL.shops_goods_specService.findShops_Goods_ImageById(spec_id);
        }
    }
}
