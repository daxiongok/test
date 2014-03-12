using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

using System.Web.Script.Serialization;

namespace WebService
{
    /// <summary>
    /// shops_goodsDetailWebService 的摘要说明
    /// </summary>
    [WebService(Namespace = "http://hc.sou100.cn/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // 若要允许使用 ASP.NET AJAX 从脚本中调用此 Web 服务，请取消对下行的注释。
    // [System.Web.Script.Services.ScriptService]
    public class shops_goodsDetailWebService : System.Web.Services.WebService
    {
        //dadadadadadad
        //测试看看
        //这次一定行！！！
        //我再传一次,zhebiankankan
        //vs自带测试
        //认真测试一次
        //我这边测试一次

        [WebMethod(Description = "获取单个商品返回了三个JSON")]
        public string getShops_GoodsDetail(int goods_id)
        {
            List<Model.shops_goods_image> imgList = BLL.shops_goods_imageManager.getShops_Goods_ImageById(goods_id);

            List<Model.shops_goodsDetail> detList = BLL.shops_goodsDetailManager.getShops_goodsSimpleList(goods_id);

            List<Model.shops_goods_spec> speList = BLL.shops_goods_specManager.getShops_Goods_ImageById(goods_id);


            JavaScriptSerializer jss = new JavaScriptSerializer();

            string imgresult = jss.Serialize(imgList);
            string detresult = jss.Serialize(detList);
            string speresult = jss.Serialize(speList);


            return detresult + imgresult + speresult;
        }
    }
}
