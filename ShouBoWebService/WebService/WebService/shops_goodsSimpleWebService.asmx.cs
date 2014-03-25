using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

using System.Web.Script.Serialization;

namespace WebService
{
    /// <summary>
    /// shops_goodsSimpleWebService 的摘要说明
    /// </summary>
    [WebService(Namespace = "http://hc.sou100.cn/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // 若要允许使用 ASP.NET AJAX 从脚本中调用此 Web 服务，请取消对下行的注释。
    // [System.Web.Script.Services.ScriptService]
    public class shops_goodsSimpleWebService : System.Web.Services.WebService
    {

        [WebMethod(Description = "商品列表")]
        public string getShops_goodsSimpleList(int pageIndex,int cate_id)
        {
            List<Model.shops_goodsSimple> objList = BLL.shops_goodsSimpleManager.getShops_goodsSimpleList(pageIndex, cate_id);

            JavaScriptSerializer jss = new JavaScriptSerializer();

            string result = jss.Serialize(objList);

            return result;
        }

    }
}
