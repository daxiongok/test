using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

using System.Text;

using System.Web.Script.Serialization;

namespace WebService
{
    /// <summary>
    /// shops_order 的摘要说明
    /// </summary>
    [WebService(Namespace = "http://hc.sou100.cn/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // 若要允许使用 ASP.NET AJAX 从脚本中调用此 Web 服务，请取消对下行的注释。
    // [System.Web.Script.Services.ScriptService]
    public class shops_order : System.Web.Services.WebService
    {
        /// <summary>
        /// 获取商品订单
        /// </summary>
        /// <param name="buyer_id"></param>
        /// <returns></returns>
        [WebMethod(Description = "商品订单(返回两个JSON)")]
        public string getShops_order(int buyer_id)
        {
            JavaScriptSerializer js = new JavaScriptSerializer();

            StringBuilder json = new StringBuilder();

            List<Model.shops_order> objList1 = BLL.shops_orderManager.findOrder_idByBuyer_id(buyer_id);

            List<Model.shops_order_goods> objList2 = BLL.shops_order_goodsManager.findShops_order_goods(objList1);

            json.Append(js.Serialize(objList1));

            json.Append(js.Serialize(objList2));

            return json.ToString();
        }
    }
}
