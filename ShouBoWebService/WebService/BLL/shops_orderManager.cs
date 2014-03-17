using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace BLL
{
    public class shops_orderManager
    {
        public static List<Model.shops_order> findOrder_idByBuyer_id(int buyer_id)
        {
            return DAL.shops_orderService.findOrder_idByBuyer_id(buyer_id);
        }
    }
}
