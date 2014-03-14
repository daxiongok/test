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
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // 若要允许使用 ASP.NET AJAX 从脚本中调用此 Web 服务，请取消对下行的注释。
    // [System.Web.Script.Services.ScriptService]
    public class shops_order : System.Web.Services.WebService
    {

        //[WebMethod(Description="商品订单(返回两个JSON)")]
        //public string HelloWorld(int spec_id)
        //{
        //    JavaScriptSerializer js = new JavaScriptSerializer();

        //    StringBuilder json = new StringBuilder();

        //    Model.shops_goods_spec objSpec = BLL.shops_goods_specManager.findShops_Goods_ImageById(spec_id);

            

        //    json.Append(js.Serialize(objSpec));


        //    return json.ToString();
        //}
    }
}
