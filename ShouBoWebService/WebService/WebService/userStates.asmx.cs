using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

using System.Web.Script.Serialization;

namespace WebService
{
    /// <summary>
    /// userStates 的摘要说明
    /// </summary>
    [WebService(Namespace = "http://hc.sou100.cn/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // 若要允许使用 ASP.NET AJAX 从脚本中调用此 Web 服务，请取消对下行的注释。
    // [System.Web.Script.Services.ScriptService]
    public class userStates : System.Web.Services.WebService
    {
        [WebMethod(Description = "登录的方法")]
        public int loginValidate(string userName, string userPsd)
        {
            Model.shops_member obj = new Model.shops_member();
            obj.user_name = userName;
            obj.password = userPsd;

            return BLL.shops_memberManager.login(obj, HttpContext.Current.Request.UserHostAddress);
        }

        [WebMethod(Description = "注册的方法")]
        public int register(string userName, string userPsd, string eMail)
        {
            Model.shops_member obj = new Model.shops_member();
            obj.user_name = userName;
            obj.password = userPsd;
            obj.email = eMail;

            return BLL.shops_memberManager.register(obj, HttpContext.Current.Request.UserHostAddress);
        }

        [WebMethod(Description = "获取单个用户信息")]
        public string findShops_memberInfoById(int userId)
        {
            JavaScriptSerializer js = new JavaScriptSerializer();

            Model.shops_memberInfo obj = BLL.shops_memberInfoManager.findShops_memberInfoById(userId);

            string json = js.Serialize(obj);

            return json;
        }
    }
}
