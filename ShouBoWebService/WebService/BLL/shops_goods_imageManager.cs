using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace BLL
{
    //根据商品id获取图片链接地址
    public class shops_goods_imageManager
    {
        public static List<Model.shops_goods_image> getShops_Goods_ImageById(int goods_id)
        {
            return DAL.shops_goods_imageService.getShops_Goods_ImageById(goods_id);
        }
    }
}
