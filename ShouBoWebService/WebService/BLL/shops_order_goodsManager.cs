using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace BLL
{
    public class shops_order_goodsManager
    {
        public static List<Model.shops_order_goods> findShops_order_goods(List<Model.shops_order> objList)
        {
            return DAL.shops_order_goodsService.findShops_order_goods(objList);
        }
    }
}
