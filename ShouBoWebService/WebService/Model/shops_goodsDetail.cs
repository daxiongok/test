using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Model
{
    public class shops_goodsDetail
    {
        //产品ID
        public int goods_id { get; set; }
        //产品名字
        public string goods_name { get; set; }
        //产品价格
        public double price { get; set; }
        //推销价格
        public double mk_price { get; set; }
        //物品计量单位
        public string unit { get; set; }
        //出售情况
        public int subtotalsale { get; set; }
        //规格1
        public string spec_name_1 { get; set; }
        //规格2
        public string spec_name_2 { get; set; }
    }
}
