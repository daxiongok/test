using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace BLL
{
    public class shops_memberManager
    {
        //登陆
        public static int login(Model.shops_member obj, string ipAddress) 
        {
            return DAL.shops_memberService.login(obj, ipAddress);
        }
        //注册
        public static int register(Model.shops_member obj, string ipAddress)
        {
            return DAL.shops_memberService.register(obj, ipAddress);
        }
    }
}
