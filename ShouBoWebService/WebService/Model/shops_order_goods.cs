using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Model
{
    public class shops_order_goods
    {
        public int rec_id  { get; set; }
        public int order_id { get; set; }
        public int goods_id { get; set; }

        public string goods_name  { get; set; }

        public int spec_id { get; set; }

        public int price { get; set; }

        public string goods_image { get; set; }




    }
}
