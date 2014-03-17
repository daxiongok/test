using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace BLL
{
    public class shops_goodsSimpleManager
    {//根据商品类型获取商品信息
        public static List<Model.shops_goodsSimple> getShops_goodsSimpleList(int pageIndex, int cate_id)
        {
            return DAL.shops_goodsSimpleService.getShops_goodsSimpleList(pageIndex, cate_id);
        }
    }
}
