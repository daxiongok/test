using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace BLL
{
    public class shops_goodsDetailManager
    {
        //根据商品id获取商品详细
        public static List<Model.shops_goodsDetail> getShops_goodsSimpleList(int goods_id)
        {
            return DAL.shops_goodsDetailService.getShops_goodsSimpleList(goods_id);
        }
    }
}
