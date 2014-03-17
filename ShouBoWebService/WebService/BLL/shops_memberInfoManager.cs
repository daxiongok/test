using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace BLL
{
    public class shops_memberInfoManager
    {
        //根据用户id获取用户信息
        public static Model.shops_memberInfo findShops_memberInfoById(int user_Id)
        {
            return DAL.shops_memberInfoService.findShops_memberInfoById(user_Id);
        }

    }
}
